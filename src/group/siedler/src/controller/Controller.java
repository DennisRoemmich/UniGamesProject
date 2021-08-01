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
import gui.FxMenuController;
import gui.SiedlerEventHandler;
import helper.QuickJSon;
import map.BuildRules;
import map.Map;
import map.MapGenerator;
import map.MapTools;
import materials.MaterialSet;
import materials.MaterialType;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Responsible for the different game moves and main functions of the Sieder von Konstanz game.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class Controller extends GameController implements SiedlerEventHandler {

    private Map mMap;

	private int mCurrentPlayer = 0;
	private PlayerColor mWinColor;
    private boolean mGameHasWinner = false;

    public void newGame() {

        if (mPlayerData.isEmpty()) {
            WriteError.writeErrorLog("No player added to game.");
            return;
        }
    	if (FxMenuController.ismIsStandardMap()) {
            mMap = MapGenerator.generateVariableMap(5, 5);
    	} else {
    		mMap = MapGenerator.generateKonstanzMap();
    	}
        mState = GameState.SETUP_VILLAGE;
        gameStep();

    }

    private void gameStep() {
        if (mState != GameState.NOT_RUNNING) {
            PrintToConsole.println("Next move" + mState.toString());
            mPlayers.get(mCurrentPlayer).requestMove(QuickJSon.create("type", mState.toString()));
        }
    }

    //------- Required Moves -------

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
        callPresenterUpdate();
        gameStep();
    }

    public void moveBurglar(TilePosition newPosition) {

        if (mState != GameState.MOVE_BURGLAR) {
            PrintToConsole.println("moveBurglar() called at wrong time");
            return;
        }
        //Checks if optional is present
        Optional<Tile> opTile = mMap.getTile(newPosition);
        if (opTile.isPresent()) {
            Tile tile = opTile.get();
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

    //------- Optional Moves-------

    public void placeBuilding(NodePosition position) {


        if (!(mState == GameState.OPTIONAL_MOVES || mState == GameState.SETUP_VILLAGE)) {
            PrintToConsole.println("placeBuilding(...) called at wrong time");
            return;
        }

        if (mMap.getBuilding(position).isEmpty()) {
        	MaterialSet cost = Building.getCost(BuildingType.VILLAGE);
            if (mState != GameState.SETUP_VILLAGE && !getCurrentPlayerHand().isSuperset(cost)) {
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
        callPresenterUpdate();
    }
    
    public void takeCard() {
    	CardSet set = getCurrentPlayerCards();
    	int cardRoll = ThreadLocalRandom.current().nextInt(0, 100);
    	
    	if (!CardSet.getCost().isSubset(getCurrentPlayerHand())) {
    		PrintToConsole.println("Not enough ressources!");
    		return;
    	}
    	
    	getCurrentPlayerHand().removeResourceSet(CardSet.getCost());
    	
    	if (cardRoll < 56) {
    		set.addCard(CardType.KNIGHT);
    	}
    	
    	if (cardRoll < 76 && cardRoll >= 56) {
    		set.addCard(CardType.VICTORY);
    		mPlayerData.get(mCurrentPlayer).increaseWinPoints();
    	}
    	
    	if (cardRoll < 80 && cardRoll >= 76) {
    		set.addCard(CardType.ROAD);
    	}
    	
    	if (cardRoll < 92 && cardRoll >= 80) {
    		set.addCard(CardType.INVENTION);
    	}
    	
    	if (cardRoll >= 92) {
    		set.addCard(CardType.MONOPOLY);
    	} 
    	
    	handleWinner();
    	callPresenterUpdate();
    }

    public void playCard(CardType cardType, MaterialType... materialTypes) {
        if (mState != GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("It's not the right time to play a card!");
            return;
        }

        if (getCurrentPlayerCards().getAmount(cardType) == 0) {
            PrintToConsole.println("You do not own this card!");
            return;
        }

        switch (cardType) {
            case KNIGHT: 
            	mState = GameState.MOVE_BURGLAR;
                mPlayers.get(mCurrentPlayer).requestMove(QuickJSon.create("move", GameState.MOVE_BURGLAR.toString()));
            	break;
            case ROAD :
                getCurrentPlayerHand().addResources(MaterialType.WOOD, 2);
                getCurrentPlayerHand().addResources(MaterialType.CLAY, 2);
                break;
            case INVENTION:
            	playInventionCard(materialTypes);
            	break;
            case MONOPOLY:
            	playMonopolyCard(materialTypes);
            	break;
            case VICTORY:
            	getNumberOfPlayers();
            	break;
        }
        callPresenterUpdate();
    }

    private void playInventionCard(MaterialType... materialTypes) {
        if (mState != GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("It's not the right time to play a card!");
            return;
        }

        if (getCurrentPlayerCards().getAmount(CardType.INVENTION) == 0) {
            PrintToConsole.println("You do not own this card!");
            return;
        }

        // Extract materials, fill with random if needed
        MaterialType typeA;
        MaterialType typeB;
        switch (materialTypes.length) {
            case 0:
                typeA = MaterialType.getRandom();
                typeB = MaterialType.getRandom();
                break;
            case 1:
                typeA = materialTypes[0];
                typeB = materialTypes[0];
                break;
            default:
                typeA = materialTypes[0];
                typeB = materialTypes[1];
                break;

        }

        getCurrentPlayerHand().addResources(typeA, 1);
        getCurrentPlayerHand().addResources(typeB, 1);
        getCurrentPlayerCards().removeCard(CardType.INVENTION);
    }

    private void playMonopolyCard(MaterialType... materialTypes) {
        MaterialType materialtype = materialTypes.length > 0 ? materialTypes[0] : MaterialType.getRandom();

        int amountOfMaterial = 0;
        for (int i = 0; i < getNumberOfPlayers(); i++) {
            amountOfMaterial += mPlayerData.get(i).getHand().getAmount(materialtype);
            mPlayerData.get(i).getHand().removeResources(materialtype, amountOfMaterial);
        }
        getCurrentPlayerHand().addResources(materialtype, amountOfMaterial);
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
        callPresenterUpdate();
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
    
    public void bankTrade(MaterialType sellType, MaterialType purchaseType) {
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

        for (PlayerData data : mPlayerData) {
            if (data.getWinPoints() >= 10) {
                PrintToConsole.println(data.getColor() + " player wins!");
                this.mWinColor = data.getColor();
                mGameHasWinner = true;
                callPresenterUpdate();
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
        callPresenterUpdate();
        gameStep();
    }

    public void nextPlayer() {
        mCurrentPlayer++;
        if (mCurrentPlayer >= mPlayers.size()) {
            mCurrentPlayer = 0;
        }
    }

    // -------Getter & Setter (Little functionality, more convenience) -------

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
    
    public int getWinPoints() {
		return mPlayerData.get(mCurrentPlayer).getWinPoints();
	}
    
    public CardSet getCurrentPlayerCards() {
        return mPlayerData.get(mCurrentPlayer).getCards();
    }
    
    // -------Siedler Event Handler-------

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

}
