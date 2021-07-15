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

    }

    public void placeBuilding(Player player, NodePosition position, BuildingType type) {
        if(mPlayers.contains(player)){
            PlayerColor color = playerData.get(mPlayers.indexOf(player)).getColor();
            if(canPlayerTakeAction(color)) {
                if(preparationPhaseActive()) {
                    // Ignore the type, it's always a village
                    type = BuildingType.VILLAGE;

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
}
