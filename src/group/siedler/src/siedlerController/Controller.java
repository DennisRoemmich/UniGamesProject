package siedlerController;

import buildings.Building;

import buildings.BuildingType;
import developmentCards.CardSet;
import developmentCards.CardType;
import diceRolling.DiceRolling;
import gui.SiedlerEventHandler;
import helper.QuickJSON;
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
import siedlerFramework.GameController;
import siedlerFramework.Player;
import siedlerFramework.PrintToConsole;
import siedlerFramework.WriteError;
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

    private List<PlayerData> playerData = new ArrayList<>();
    private Map map = MapGenerator.generateVariableMap(7, 5);
    private GameState state = GameState.NOT_RUNNING;
	private int currentPlayer = 0;
	private PlayerColor winningColor;
    private boolean gameHasWinner = false;

    public Controller() {
        mPlayers = new ArrayList<>();
    }

    public int getNumberOfPlayers() {
        return mPlayers.size();
    }
    
    public List<PlayerData> getPlayerData() {
    	return playerData;
    }

    public void addPlayer(Player player, PlayerColor color) {
        if(state != GameState.NOT_RUNNING) {
            return;
        }
        if(playerData.stream().anyMatch(playerData -> playerData.getColor() == color)) {
            return;
        }
        mPlayers.add(player);
        PlayerData data = new PlayerData(color);
        data.setHand(new MaterialSet());
        playerData.add(data);
    }

    @Override
    public void newGame() {
        if(playerData.size() == 0) {
            WriteError.writeErrorLog("No player added to game.");
            return;
        }
    	map = MapGenerator.generateKonstanzMap();
        state = GameState.SETUP_VILLAGE;
        gameStep();
    }

    private void gameStep() {
        if(state != GameState.NOT_RUNNING) {
            PrintToConsole.println("Next move" + state.toString());
            mPlayers.get(currentPlayer).requestMove(QuickJSON.create("type", state.toString()));
        }
    }

    private boolean canPlayerTakeAction(PlayerColor color) {
        return color == getCurrentPlayerColor();
    }

    // Game Logic

    private int getWinPoints(PlayerColor color) {
        if(playerData.stream().anyMatch(pD -> pD.getColor().equals(color))) {
            List<Building> buildings = map.getBuildings(color);
            int villagePoints = buildings.stream().filter(b -> b.getType() == BuildingType.VILLAGE).toList().size();
            int townPoints = buildings.stream().filter(b -> b.getType() == BuildingType.TOWN).toList().size() * 2;
            return villagePoints + townPoints;
        } else {
            return 0;
        }
    }

    public int getWinPoints() {
		return playerData.get(currentPlayer).getWinPoints();
	}

    // Required Moves

    public void handleRoll() {
        if(state != GameState.ROLL_DICES) {
            PrintToConsole.println("handleRoll() called at wrong time");
            return;
        }

        DiceRolling.reRoll();
        int rolledNumber = DiceRolling.getSum();
        if(rolledNumber == 7) {
            state = GameState.MOVE_BURGLAR;
        } else {
            DiceRolling.handOutResources(rolledNumber, map, playerData);
            PrintToConsole.println("Materials of " + getCurrentPlayerColor());
            PrintToConsole.println(getCurrentPlayerHand().toString());
            state = GameState.OPTIONAL_MOVES;
        }
        mPresenter.refreshOutput();
        gameStep();
    }

    public void moveBurglar(TilePosition newPosition) {
        if(state != GameState.MOVE_BURGLAR) {
            PrintToConsole.println("moveBurglar() called at wrong time");
            return;
        }

        if(map.getTile(newPosition).isPresent()) {
            Tile tile = map.getTile(newPosition).get();
            if(!tile.isWater()) {
                map.setBurglarPosition(newPosition);
                state = GameState.OPTIONAL_MOVES;
            }
        }
        gameStep();
    }

    // Optional Moves

    public void placeBuilding(NodePosition position) {
        if(!(state == GameState.OPTIONAL_MOVES || state == GameState.SETUP_VILLAGE)) {
            PrintToConsole.println("placeBuilding(...) called at wrong time");
            return;
        }

        if(map.getBuilding(position).isEmpty()) {
            if(state != GameState.SETUP_VILLAGE && !getCurrentPlayerHand().isSuperset(Building.getCost(BuildingType.VILLAGE))) {
                PrintToConsole.println("placeBuilding(...) called without enough materials");
                return;
            }
            Building building = new Building(position, getCurrentPlayerColor());
            map.addBuilding(building);
            if(state == GameState.SETUP_VILLAGE) {
                state = GameState.SETUP_STREET;
                gameStep();
            } else {
                getCurrentPlayerHand().removeResourceSet(Building.getCost(BuildingType.VILLAGE));
            }
        } else {
            if(!getCurrentPlayerHand().isSuperset(Building.getCost(BuildingType.TOWN))) {
                PrintToConsole.println("placeBuilding(...) called without enough materials");
                return;
            }
            Optional<Building> building = map.getBuilding(position);
            if(building.isPresent()) {
                building.get().upgrade();
                getCurrentPlayerHand().removeResourceSet(Building.getCost(BuildingType.TOWN));
                playerData.get(currentPlayer).increaseWinPoints();
            }
        }
        if(handleWinner()) {
            state = GameState.NOT_RUNNING;
        }
        mPresenter.refreshOutput();
    }
    
    public void takeCard() {
    	
    	CardSet set = getCurrentPlayerCards();
    	int cardRoll = ThreadLocalRandom.current().nextInt(0, 24);
    	
    	if(!CardSet.getCost().isSubset(getCurrentPlayerHand())) {
    		PrintToConsole.println("Not enough ressources!");
    		return;
    	}
    	
    	getCurrentPlayerHand().removeResourceSet(CardSet.getCost());
    	
    	if (cardRoll <14) {
    		set.addResources(CardType.KNIGHT, 1);
    	}
    	
    	if (cardRoll <19 && cardRoll >= 14) {
    		set.addResources(CardType.VICTORY, 1);
    		playerData.get(currentPlayer).increaseWinPoints();
    	}
    	
    	if (cardRoll <21 && cardRoll >= 19) {
    		set.addResources(CardType.ROAD, 1);
    	}
    	
    	if (cardRoll <23 && cardRoll >= 21) {
    		set.addResources(CardType.INVENTION, 1);
    	}
    	
    	if (cardRoll <25 && cardRoll >= 23) {
    		set.addResources(CardType.MONOPOLY, 1);
    	} 
    	
    	handleWinner();
    	mPresenter.refreshOutput();
    }
    
    public void playCard(CardType type, MaterialType materialtype) {

    	if(getCurrentPlayerCards().getAmount(type) == 0) {
    		PrintToConsole.println("You do not own this card!");
    		return;
    	}
    	switch(type) {
	    	case KNIGHT: 
	    		state = GameState.MOVE_BURGLAR;
	    		mPlayers.get(currentPlayer).requestMove(QuickJSON.create("move", GameState.MOVE_BURGLAR.toString()));
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		break;
	    	case VICTORY:
	    		break; //Can't play victory cards
	    	case ROAD:
	    		//TODO: Place two roads for free
	    		getCurrentPlayerHand().addResources(MaterialType.WOOD, 2);
	    		getCurrentPlayerHand().addResources(MaterialType.CLAY, 2);
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		break;
	    	case INVENTION:
	    		//TODO: Get two materials for free
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		getCurrentPlayerHand().addResources(materialtype, 5);
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		break;
	    	case MONOPOLY:
	    		int amountOfMaterial = 0;
	    		for(int i = 0; i< getNumberOfPlayers(); i++) {
	    			amountOfMaterial += playerData.get(i).getHand().getAmount(materialtype);
	    			playerData.get(i).getHand().removeResources(materialtype, playerData.get(i).getHand().getAmount(MaterialType.WOOD));
	    		}
	    		getCurrentPlayerHand().addResources(materialtype, amountOfMaterial);
	    		getCurrentPlayerCards().removeResources(type, 1);
	    		break;
	    	}
    	mPresenter.refreshOutput();
    }

    public void placeStreet(EdgePosition position) {
        List<StreetType> possibleTypes = BuildRules.getPossibleStreetType(map, position);
        placeStreet(position, possibleTypes.get(0));
    }

    public void placeStreet(EdgePosition position, StreetType type) {
        if(!(state == GameState.OPTIONAL_MOVES || state == GameState.SETUP_STREET)) {
            PrintToConsole.println("placeStreet(...) called at wrong time");
            return;
        }

        if(state != GameState.SETUP_STREET && !getCurrentPlayerHand().isSuperset(Street.getCost(type))) {
            PrintToConsole.println("placeStreet(...) called without enough materials");
            return;
        }

        if(!BuildRules.getPossibleStreetType(map, position).contains(type)) {
            PrintToConsole.println("placeStreet(...) called with illegal type");
            return;
        }

        Street street = new Street(type, getCurrentPlayerColor());
        PositionedStreet positionedStreet = new PositionedStreet(street, position);
        map.addStreet(positionedStreet);
        if(state == GameState.SETUP_STREET) {
            nextPlayer();
            if(map.getBuildings(getCurrentPlayerColor()).size() == 2) {
                handOutStartMaterials();
                state = GameState.ROLL_DICES;
            } else {
                state = GameState.SETUP_VILLAGE;
            }
            gameStep();
        } else {
            getCurrentPlayerHand().removeResourceSet(Street.getCost(type));
            if(handleWinner()) {
                state = GameState.NOT_RUNNING;
            }
        }
        mPresenter.refreshOutput();
    }

    private void handOutStartMaterials() {
        for(PlayerData pD : playerData) {
            for(Building building : map.getBuildings(pD.getColor())) {
                for(TilePosition tilePosition : MapTools.getTilesPositions(building.getPosition())) {
                    var tile = map.getTile(tilePosition);
                    if(tile.isPresent() && tile.get().isHasHitnumber()) {
                        MaterialType type = ((ResourceTile)tile.get()).getResourceType();
                        pD.getHand().addResources(type, 1);
                    }
                }
            }
        }
    }
    
    public void bankTrade(MaterialType purchaseType, MaterialType sellType) {
        if (state != GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("bankTrade(...) called at wrong time");
            return;
        }

    	MaterialSet newHand;
	
    	newHand = getCurrentPlayerHand().tradeWithBank(getCurrentPlayerHand(), purchaseType, sellType );
    	playerData.get(currentPlayer).setHand(newHand);
    	newHand.toString();
    }

    private boolean preparationPhaseActive() {
        for(PlayerData pD : playerData) {
            if(map.getBuildings().stream().filter(b -> b.getColor() == pD.getColor()).toList().size() < 2) {
                return true;
            }
        }
        return false;
    }


    private boolean handleWinner() {
        for (PlayerColor playerColor : playerData.stream().map(PlayerData::getColor).toList()) {
            if (getWinPoints(playerColor) >= 10) {
                System.out.println(playerColor + " player wins!");
                mPresenter.refreshOutput();
                return true;
            }
        }
        return false;
    }

    public void endMove() {
        nextPlayer();
        if(handleWinner()) {
            state = GameState.NOT_RUNNING;
        } else  {
            state = GameState.ROLL_DICES;
        }
        if(mPresenter != null) {
            mPresenter.refreshOutput();
        }
        gameStep();
    }

    public void nextPlayer() {
        currentPlayer++;
        if(currentPlayer >= mPlayers.size()) {
            currentPlayer = 0;
        }
    }

    // Getter & Setter (Little functionality, more convenience)

    public boolean isItMyTurn(Player player) {
        return mPlayers.get(currentPlayer) == player;
    }

    public PlayerColor getCurrentPlayerColor() {
        return playerData.get(currentPlayer).getColor();
    }

    public MaterialSet getCurrentPlayerHand() {
        return playerData.get(currentPlayer).getHand();
    }

    public Map getMap() {
        return map;
    }
    
    public boolean isGameHasWinner() {
		return gameHasWinner;
	}
    
    public PlayerColor getWinningColor() {
    	return this.winningColor;
    }

    public GameState getState() {
        return state;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    // Siedler Event Handler

    @Override
    public void handleTileCLick(TilePosition position) {
        moveBurglar(position);
    }

    @Override
    public void handleStreetClick(EdgePosition position) {
        if(state == GameState.OPTIONAL_MOVES || state == GameState.SETUP_STREET) {
            StreetType type = BuildRules.getPossibleStreetType(map, position).get(0);
            placeStreet(position, type);
        }
    }

    @Override
    public void handleBuildingClick(NodePosition position) {
        if(state == GameState.OPTIONAL_MOVES || state == GameState.SETUP_VILLAGE) {
            placeBuilding(position);
        }
    }

    // Unused Framework functionality

    @Override
    protected JSONObject executeMove(JSONObject move) { return null; }

    @Override
    public JSONObject metaSettingsToJSON() { return null; }

    @Override
    public JSONObject gameSettingsToJSON() { return null; }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) { }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) { }
//    public List<Card> getPlayerCards(){
//    	return playerData.get(currentPlayer).getCards();
//    }
    public CardSet getCurrentPlayerCards() {
        return playerData.get(currentPlayer).getCards();
    }

}
