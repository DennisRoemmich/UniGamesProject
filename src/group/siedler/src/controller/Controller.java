package controller;

import buildings.Building;

import buildings.BuildingType;
import cards.CardSet;
import cards.CardType;
import dice.DiceRolling;
import framework.GameController;
import framework.Player;
import framework.PrintToConsole;
import framework.WriteError;
import gui.SiedlerEventHandler;
import helper.QuickJSon;
import map.BuildRules;
import map.Map;
import map.MapGenerator;
import map.MapTools;
import materials.MaterialSet;
import materials.MaterialType;
import org.json.simple.JSONObject;
import player.PlayerColor;
import player.PlayerData;
import positions.EdgePosition;
import positions.NodePosition;
import positions.TilePosition;
import streets.PositionedStreet;
import streets.Street;
import streets.StreetType;
import tiles.ResourceTile;
import tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Controller extends GameController implements SiedlerEventHandler {

    private List<PlayerData> mPlayerData = new ArrayList<>();
    private Map mMap;
    private GameState mState = GameState.NOT_RUNNING;
	private int mCurrentPlayer = 0;
	private PlayerColor mWinColor;
    private boolean mGameHasWinner = false;
    private PlayerData mCurrentKnightLeader;

    public Controller() {
        mPlayers = new ArrayList<>();
    }

    public int getNumberOfPlayers() {
        return mPlayers.size();
    }
    
    public List<PlayerData> getPlayerData() {
    	return mPlayerData;
    }

    public void addPlayer(Player player, PlayerColor color) {
        if (mState != GameState.NOT_RUNNING) {
            return;
        }
        if (mPlayerData.stream().anyMatch(playerData -> playerData.getColor() == color)) {
            return;
        }
        mPlayers.add(player);
        PlayerData data = new PlayerData(color);
        data.setHand(new MaterialSet());
        mPlayerData.add(data);
    }

    @Override
    public void newGame() {
        if (mPlayerData.isEmpty()) {
            WriteError.writeErrorLog("No player added to game.");
            return;
        }
    	mMap = MapGenerator.generateKonstanzMap();
        mState = GameState.SETUP_VILLAGE;
        gameStep();
    }

    private void gameStep() {
        if (mState != GameState.NOT_RUNNING) {
            PrintToConsole.println("Next move" + mState.toString());
            mPlayers.get(mCurrentPlayer).requestMove(QuickJSon.create("type", mState.toString()));
        }
    }

    // Game Logic

    private int getWinPoints(PlayerColor color) {
        if (mPlayerData.stream().anyMatch(pD -> pD.getColor().equals(color))) {
            List<Building> buildings = mMap.getBuildings(color);
            int villagePoints = buildings.stream().filter(b -> b.getType() == BuildingType.VILLAGE).toList().size();
            int townPoints = buildings.stream().filter(b -> b.getType() == BuildingType.TOWN).toList().size() * 2;
            return villagePoints + townPoints;
        } else {
            return 0;
        }
    }

    public int getWinPoints() {
		return mPlayerData.get(mCurrentPlayer).getWinPoints();
	}

    // Required Moves

    public void handleRoll() {
        if (mState != GameState.ROLL_DICES) {
            PrintToConsole.println("handleRoll() called at wrong time");
            return;
        }

        DiceRolling.reRoll();
        int rolledNumber = DiceRolling.getSum();
        if (rolledNumber == 7) {
            mState = GameState.MOVE_BURGLAR;
        } else {
            DiceRolling.handOutResources(rolledNumber, mMap, mPlayerData);
            PrintToConsole.println("Materials of " + getCurrentPlayerColor());
            PrintToConsole.println(getCurrentPlayerHand().toString());
            mState = GameState.OPTIONAL_MOVES;
        }
        mPresenter.refreshOutput();
        gameStep();
    }

    public void moveBurglar(TilePosition newPosition) {
        if (mState != GameState.MOVE_BURGLAR) {
            PrintToConsole.println("moveBurglar() called at wrong time");
            return;
        }
        //Checks if optional is present
        if (mMap.getTile(newPosition).isPresent()) {
            Tile tile = mMap.getTile(newPosition).get();
            if (!tile.isWater()) {
                mMap.setBurglarPosition(newPosition);
                int cardRoll = ThreadLocalRandom.current().nextInt(0, 4);
                
                switch (cardRoll) {
                case 1: 
                	getCurrentPlayerHand().addResources(MaterialType.CLAY, 1);
                	PrintToConsole.println("Clay robbed!");
                	break;
                case 2: 
                	getCurrentPlayerHand().addResources(MaterialType.ORE, 1);
                	PrintToConsole.println("Ore robbed!");
                	break;
                case 3: 
                	getCurrentPlayerHand().addResources(MaterialType.WHEAT, 1);
                	PrintToConsole.println("Wheat robbed!");
                	break;
                case 4: 
                	getCurrentPlayerHand().addResources(MaterialType.WOOL, 1);
                	PrintToConsole.println("Wool robbed!");
                	break;
                default: 
                	getCurrentPlayerHand().addResources(MaterialType.WOOD, 1);
                	PrintToConsole.println("Wood robbed!");
                	break;
                }                
                mState = GameState.OPTIONAL_MOVES;
            }
        }
        gameStep();
    }

    // Optional Moves

    public void placeBuilding(NodePosition position) {
        if (!(mState == GameState.OPTIONAL_MOVES || mState == GameState.SETUP_VILLAGE)) {
            PrintToConsole.println("placeBuilding(...) called at wrong time");
            return;
        }

        if (mMap.getBuilding(position).isEmpty()) {
            if (mState != GameState.SETUP_VILLAGE && !getCurrentPlayerHand().isSuperset(Building.getCost(BuildingType.VILLAGE))) {
                PrintToConsole.println("placeBuilding(...) called without enough materials");
                return;
            }
            Building building = new Building(position, getCurrentPlayerColor());
            mMap.addBuilding(building);
            if (mState == GameState.SETUP_VILLAGE) {
                mState = GameState.SETUP_STREET;
                gameStep();
            } else {
                getCurrentPlayerHand().removeResourceSet(Building.getCost(BuildingType.VILLAGE));
                mPlayerData.get(mCurrentPlayer).increaseWinPoints();
            }
        } else {
            if (!getCurrentPlayerHand().isSuperset(Building.getCost(BuildingType.TOWN))) {
                PrintToConsole.println("placeBuilding(...) called without enough materials");
                return;
            }
            Optional<Building> building = mMap.getBuilding(position);
            if (building.isPresent()) {
                building.get().upgrade();
                getCurrentPlayerHand().removeResourceSet(Building.getCost(BuildingType.TOWN));
                mPlayerData.get(mCurrentPlayer).increaseWinPoints();
            }
        }
        if (handleWinner()) {
            mState = GameState.NOT_RUNNING;
        }
        mPresenter.refreshOutput();
    }
    
    public void takeCard() {
    	
    	CardSet set = getCurrentPlayerCards();
    	int cardRoll = ThreadLocalRandom.current().nextInt(0, 24);
    	
    	if (!CardSet.getCost().isSubset(getCurrentPlayerHand())) {
    		PrintToConsole.println("Not enough ressources!");
    		return;
    	}
    	
    	getCurrentPlayerHand().removeResourceSet(CardSet.getCost());
    	
    	if (cardRoll < 14) {
    		set.addResources(CardType.KNIGHT, 1);
    	}
    	
    	if (cardRoll < 19 && cardRoll >= 14) {
    		set.addResources(CardType.VICTORY, 1);
    		mPlayerData.get(mCurrentPlayer).increaseWinPoints();
    	}
    	
    	if (cardRoll < 21 && cardRoll >= 19) {
    		set.addResources(CardType.ROAD, 1);
    	}
    	
    	if (cardRoll < 23 && cardRoll >= 21) {
    		set.addResources(CardType.INVENTION, 1);
    	}
    	
    	if (cardRoll < 25 && cardRoll >= 23) {
    		set.addResources(CardType.MONOPOLY, 1);
    	} 
    	
    	handleWinner();
    	mPresenter.refreshOutput();
    }

    public void playInventionCard(MaterialType typeA, MaterialType typeB) {
        if (mState != GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("It's not the right time to play a card!");
            return;
        }
        
    	if (getCurrentPlayerCards().getAmount(CardType.INVENTION) == 0) {
    		PrintToConsole.println("You do not own this card!");
    		return;
    	}
        
        getCurrentPlayerHand().addResources(typeA, 1);
        getCurrentPlayerHand().addResources(typeB, 1);
        getCurrentPlayerCards().removeResources(CardType.INVENTION, 1);
    }
    
    public void checkKnights() {
    	int knightCounter = mPlayerData.get(mCurrentPlayer).getKnightCounter();
		if ( knightCounter < 3 ) {
			return;
		}
//		mPlayerData.get(mCurrentPlayer).increaseWinPoints();
//		mPlayerData.get(mCurrentPlayer).increaseWinPoints();
//		boolean newLeader = true;
//		for (int i = 0; i < getNumberOfPlayers(); i++) {
//			if (knightCounter <= mPlayerData.get(i).getKnightCounter()) {
//				newLeader = false;
//				//return;
//			}
//		}
//		if (newLeader) {
//			this.mCurrentKnightLeader.decreaseWinPointsBy2();
//			this.mCurrentKnightLeader = mPlayerData.get(mCurrentPlayer);
//			this.mCurrentKnightLeader.increaseWinPoints();
//			this.mCurrentKnightLeader.increaseWinPoints();
//					
//		}
    }
    
    public void playCard(CardType type, MaterialType materialtype) {

        if (mState != GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("It's not the right time to play a card!");
            return;
        }

    	if (getCurrentPlayerCards().getAmount(type) == 0) {
    		PrintToConsole.println("You do not own this card!");
    		return;
    	}

    	switch (type) {
	    	case KNIGHT: 
	    		mState = GameState.MOVE_BURGLAR;
	    		mPlayers.get(mCurrentPlayer).requestMove(QuickJSon.create("move", GameState.MOVE_BURGLAR.toString()));
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		mPlayerData.get(mCurrentPlayer).increaseKnightCounter();
	    		checkKnights();
	    		break;
	    	case VICTORY:
	    		break; //Can't play victory cards
	    	case ROAD:
	    		getCurrentPlayerHand().addResources(MaterialType.WOOD, 2);
	    		getCurrentPlayerHand().addResources(MaterialType.CLAY, 2);
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		break;
	    	case INVENTION:
	    		//Should use the inventionCard method instead
	    		break;
	    	case MONOPOLY:
	    		int amountOfMaterial = 0;
	    		for (int i = 0; i < getNumberOfPlayers(); i++) {
	    			amountOfMaterial += mPlayerData.get(i).getHand().getAmount(materialtype);
	    			mPlayerData.get(i).getHand().removeResources(materialtype, amountOfMaterial);
	    		}
	    		getCurrentPlayerHand().addResources(materialtype, amountOfMaterial);
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		break;
	    	}
    	mPresenter.refreshOutput();
    }

    public void placeStreet(EdgePosition position) {
        List<StreetType> possibleTypes = BuildRules.getPossibleStreetType(mMap, position);
        placeStreet(position, possibleTypes.get(0));
    }

    public void placeStreet(EdgePosition position, StreetType type) {
        if (!(mState == GameState.OPTIONAL_MOVES || mState == GameState.SETUP_STREET)) {
            PrintToConsole.println("placeStreet(...) called at wrong time");
            return;
        }

        if (mState != GameState.SETUP_STREET && !getCurrentPlayerHand().isSuperset(Street.getCost(type))) {
            PrintToConsole.println("placeStreet(...) called without enough materials");
            return;
        }

        if (!BuildRules.getPossibleStreetType(mMap, position).contains(type)) {
            PrintToConsole.println("placeStreet(...) called with illegal type");
            return;
        }

        Street street = new Street(type, getCurrentPlayerColor());
        PositionedStreet positionedStreet = new PositionedStreet(street, position);
        mMap.addStreet(positionedStreet);
        if (mState == GameState.SETUP_STREET) {
            nextPlayer();
            if (mMap.getBuildings(getCurrentPlayerColor()).size() == 2) {
                handOutStartMaterials();
                mState = GameState.ROLL_DICES;
            } else {
                mState = GameState.SETUP_VILLAGE;
            }
            gameStep();
        } else {
            getCurrentPlayerHand().removeResourceSet(Street.getCost(type));
            if (handleWinner()) {
                mState = GameState.NOT_RUNNING;
            }
        }
        mPresenter.refreshOutput();
    }

    private void handOutStartMaterials() {
        for (PlayerData pD : mPlayerData) {
            for (Building building : mMap.getBuildings(pD.getColor())) {
                for (TilePosition tilePosition : MapTools.getTilesPositions(building.getPosition())) {
                    var tile = mMap.getTile(tilePosition);
                    if (tile.isPresent() && tile.get().isHasHitnumber()) {
                        MaterialType type = ((ResourceTile) tile.get()).getResourceType();
                        pD.getHand().addResources(type, 1);
                    }
                }
            }
        }
    }
    
    public void bankTrade(MaterialType purchaseType, MaterialType sellType) {
        if (mState != GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("bankTrade(...) called at wrong time");
            return;
        }

    	MaterialSet newHand;
	
    	newHand = getCurrentPlayerHand().tradeWithBank(getCurrentPlayerHand(), purchaseType, sellType );
    	mPlayerData.get(mCurrentPlayer).setHand(newHand);
    	newHand.toString();
    }

    public boolean handleWinner() {
        for (PlayerColor playerColor : mPlayerData.stream().map(PlayerData::getColor).toList()) {
            if (getWinPoints(playerColor) >= 10) {
                PrintToConsole.println(playerColor + " player wins!");
                this.mWinColor = playerColor;
                mGameHasWinner = true;
                mPresenter.refreshOutput();
                return true;
            }
        }
        return false;
    }

    public void endMove() {
        nextPlayer();
        if (handleWinner()) {
            mState = GameState.NOT_RUNNING;
        } else  {
            mState = GameState.ROLL_DICES;
        }
        if (mPresenter != null) {
            mPresenter.refreshOutput();
        }
        gameStep();
    }

    public void nextPlayer() {
        mCurrentPlayer++;
        if (mCurrentPlayer >= mPlayers.size()) {
            mCurrentPlayer = 0;
        }
    }

    // Getter & Setter (Little functionality, more convenience)

    public boolean isItMyTurn(Player player) {
        return mPlayers.get(mCurrentPlayer) == player;
    }

    public PlayerColor getCurrentPlayerColor() {
        return mPlayerData.get(mCurrentPlayer).getColor();
    }

    public MaterialSet getCurrentPlayerHand() {
        return mPlayerData.get(mCurrentPlayer).getHand();
    }

    public Map getMap() {
        return mMap;
    }
    
    public boolean isGameHasWinner() {
		return mGameHasWinner;
	}

    public GameState getState() {
        return mState;
    }

    public int getCurrentPlayer() {
        return mCurrentPlayer;
    }
    
    public PlayerColor getWinColor() {
    	return this.mWinColor;
    }

    // Siedler Event Handler

    @Override
    public void handleTileCLick(TilePosition position) {
        moveBurglar(position);
    }

    @Override
    public void handleStreetClick(EdgePosition position) {
        if (mState == GameState.OPTIONAL_MOVES || mState == GameState.SETUP_STREET) {
            StreetType type = BuildRules.getPossibleStreetType(mMap, position).get(0);
            placeStreet(position, type);
        }
    }

    @Override
    public void handleBuildingClick(NodePosition position) {
        if (mState == GameState.OPTIONAL_MOVES || mState == GameState.SETUP_VILLAGE) {
            placeBuilding(position);
        }
    }

    // Unused Framework functionality

    @Override
    protected JSONObject executeMove(JSONObject move) { 
    	return null; 
    }

    @Override
    public JSONObject metaSettingsToJSon() {
    	return null; 
    }

    @Override
    public JSONObject gameSettingsToJSon() { 
    	return null;
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) { 
    	//Still unused
    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) { 
    	//Still unused
    }

    public CardSet getCurrentPlayerCards() {
        return mPlayerData.get(mCurrentPlayer).getCards();
    }

}
