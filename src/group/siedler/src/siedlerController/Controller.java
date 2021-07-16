package siedlerController;

import buildings.Building;
import buildings.BuildingType;
import diceRolling.DiceRolling;
import helper.QuickJSON;
import map.Map;
import map.MapGenerator;
import materials.MaterialSet;
import org.json.simple.JSONObject;
import player.PlayerColor;
import player.PlayerData;
import positions.EdgePosition;
import positions.NodePosition;
import siedlerFramework.GameController;
import siedlerFramework.Player;
import siedlerFramework.PrintToConsole;
import siedlerFramework.WriteError;
import streets.Street;
import streets.StreetType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller extends GameController {

    private List<PlayerData> playerData = new ArrayList<>();
    private Map map = MapGenerator.generateBasicMap();

    private int currentPlayer = 0;

    private boolean currentPlayerHasRolled = false;

    public Controller() {
        mPlayers = new ArrayList<>();
    }

    public int getNumberOfPlayers() {
        return mPlayers.size();
    }
    
    public List<PlayerData> getPlayerData() {
    	return playerData;
    }
    private boolean isRunning = false;

    public void addPlayer(Player player, PlayerColor color) {
        if(isRunning) {
            return;
        }
        if(playerData.stream().anyMatch(playerData -> playerData.getColor() == color)) {
            return;
        }
        mPlayers.add(player);
        PlayerData data = new PlayerData(color);
        data.setHand(MaterialSet.getFullHand());
        playerData.add(data);
    }

    @Override
    protected JSONObject executeMove(JSONObject move) {
        return null;
    }

    @Override
    public JSONObject metaSettingsToJSON() {
        JSONObject metaSettings = new JSONObject();
        return metaSettings;
    }

    @Override
    public JSONObject gameSettingsToJSON() {
        JSONObject gameSettings = new JSONObject();
        return gameSettings;
    }

    @Override
    public void restoreMetaSettings(JSONObject metaSettings) {

    }

    @Override
    public void restoreGameSettings(JSONObject gameSettings) {

    }

    @Override
    public void newGame() {
        if(playerData.size() == 0) {
            WriteError.writeErrorLog("No player added to game.");
            return;
        }
        isRunning = true;
    	map = MapGenerator.generateMap(playerData.stream().map(PlayerData::getColor).toList());
    }

    private void gameStep() {
        if(!isRunning) {
            return;
        }
        if(currentPlayerHasRolled) {
            mPlayers.get(currentPlayer).requestMove(QuickJSON.create("type", "optionalMove"));
        } else {
            mPlayers.get(currentPlayer).requestMove(QuickJSON.create("type", "rollDices"));
        }
    }

    public void placeBuilding(NodePosition position) {
        if(map.getBuilding(position).isEmpty()) {
            Building building = new Building(position, getCurrentPlayerColor());
            map.addBuilding(building);
            getCurrentPlayerHand().removeResourceSet(Building.getCost(BuildingType.VILLAGE));
        } else {
            Optional<Building> building = map.getBuilding(position);
            if(building.isPresent()) {
                building.get().upgrade();
                getCurrentPlayerHand().removeResourceSet(Building.getCost(BuildingType.TOWN));
            }
        }
        handleWinner();
        mPresenter.refreshOutput();
    }

    public void placeStreet(EdgePosition position, StreetType type) {
        Street street = new Street(position, type, getCurrentPlayerColor());
        map.addStreet(street);
        getCurrentPlayerHand().removeResourceSet(Street.getCost(type));
        mPresenter.refreshOutput();
    }

    // Not needed anymore
    public void upgradeBuilding(Player player, NodePosition position, BuildingType type) {
    	if(mPlayers.contains(player)){
            PlayerColor color = playerData.get(mPlayers.indexOf(player)).getColor();
            if(canPlayerTakeAction(color)) {
                if(preparationPhaseActive()) {
                	type = BuildingType.TOWN;
                   // playerData.get(mPlayers.indexOf(player)).increaseNumberOfTowns();
                }
            }
        }
    }

    private boolean canPlayerTakeAction(PlayerColor color) {
        return true;
        // TODO : Check if it's the turn of the player
    }

    private boolean preparationPhaseActive() {
        for(PlayerData pD : playerData) {
            if(map.getBuildings().stream().filter(b -> b.getColor() == pD.getColor()).toList().size() < 2) {
                return true;
            }
        }
        return false;
    }
    
    private void handleWinner() {
    	for (PlayerColor playerColor : playerData.stream().map(PlayerData::getColor).toList()) {
    	    List<Building> buildings = map.getBuildings(playerColor);
            int villagePoints = buildings.stream().filter(b -> b.getType() == BuildingType.VILLAGE).toList().size();
            int townPoints = buildings.stream().filter(b -> b.getType() == BuildingType.TOWN).toList().size() * 2;
    		if (villagePoints + townPoints >= 10) {
    		    System.out.println(playerColor + " player wins!");
    		    isRunning = false;
                mPresenter.refreshOutput();
    		}
    	}
    }

    public void handleRoll() {
        DiceRolling.reRoll();
        int rolledNumber = DiceRolling.getSum();
        if(rolledNumber == 7) {
            // TODO : Implement Burglar
        } else {
            DiceRolling.handOutResources(rolledNumber, map, playerData);
            PrintToConsole.println(playerData.get(0).getHand().toString());
        }
        currentPlayerHasRolled = true;
        mPresenter.refreshOutput();
        gameStep();
    }

    public void endMove() {
        currentPlayer++;
        if(currentPlayer >= mPlayers.size()) {
            currentPlayer = 0;
        }
        currentPlayerHasRolled = false;
        handleWinner();
        gameStep();
    }

    public boolean isItMyTurn(Player player) {
        return mPlayers.get(currentPlayer) == player;
    }

    public boolean hasCurrentPlayerRolled() {
        return currentPlayerHasRolled;
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

    public boolean isRunning() {
        return isRunning;
    }
}
