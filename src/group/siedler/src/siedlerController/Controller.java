package siedlerController;

import buildings.BuildingType;
import diceRolling.DiceRolling;
import map.Map;
import org.json.simple.JSONObject;
import player.PlayerColor;
import player.PlayerData;
import positions.NodePosition;
import siedlerFramework.GameController;
import siedlerFramework.Player;

import java.util.List;
import java.util.Optional;

public class Controller extends GameController {

    List<PlayerData> playerData;
    Map map;

    public int getNumberOfPlayers() {
        return mPlayers.size();
    }
    
    public List<PlayerData> getPlayerData() {
    	return playerData;
    }
    private boolean isRunning = false;

    public void addPlayer(Player player, PlayerColor color) {
        if(!isRunning) {
            return;
        }
        if(playerData.stream().anyMatch(playerData -> playerData.getColor() == color)) {
            return;
        }
        mPlayers.add(player);
        playerData.add(new PlayerData(color));
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
    	isRunning = true;
    }

    public void placeBuilding(Player player, NodePosition position, BuildingType type) {
        if(mPlayers.contains(player)){
            PlayerColor color = playerData.get(mPlayers.indexOf(player)).getColor();
            if(canPlayerTakeAction(color)) {
                if(preparationPhaseActive()) {
                    type = BuildingType.VILLAGE;
                    playerData.get(mPlayers.indexOf(player)).increaseNumberOfTowns();
                }
            }
        }
    }
    
    //
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

    public void rollDices() {
        int rolledNumber = DiceRolling.getNumber();
        if(rolledNumber == 7) {
            // TODO : Implement Burglar
        } else {
            DiceRolling.handOutResources(rolledNumber, map, playerData);
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
    
    private void checkForWinner() {
    	for (int i = 0; i < getNumberOfPlayers(); i++) {
    		if (playerData.get(i).getNumberOfVillages() + (playerData.get(i).getNumberOfTowns())/2 >= 10) {
    			System.out.println("Current player wins!");
    			isRunning = false;
    		}
    	}
    }
    
}
