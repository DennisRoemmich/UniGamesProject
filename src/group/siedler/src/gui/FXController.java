package gui;

import diceRolling.DiceRolling;
import helper.QuickJSON;
import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import map.BuildRules;
import map.Map;
import map.MapGenerator;
import org.json.simple.JSONObject;
import player.PlayerColor;
import positions.EdgePosition;
import siedlerController.Controller;
import siedlerFramework.Player;
import siedlerFramework.PrintToConsole;

import java.util.ResourceBundle;

public class FXController implements Initializable, Player {

    private Controller controller = new Controller();

    @FXML
    private AnchorPane back;
    @FXML
    private ImageView diceButton;
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;

    Roller clock = new Roller();

    public final static Image finishButtonImage = new Image("./resources/FinishButton.png");
    public final static Image diceButtonImage = new Image("./resources/DiceButton.png");

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if(!inputType.containsKey("type")) {
            return QuickJSON.create("reply", "invalid input");
        }
        switch (inputType.get("type").toString()) {
            case "rollDices":
                diceButton.setImage(diceButtonImage);
                diceButton.setVisible(true);
        }
        return QuickJSON.create("reply", "valid");
    }


    private class Roller extends AnimationTimer{

        private long FRAMES_PER_SEC = 10L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private long last = 0;
        private int count = 0;

        @Override
        public void handle(long l) {
            if(l - last > INTERVAL){
                int r = 2 + (int)(Math.random() * 5);
                setDiceImage(r, dice1);
                int j = 2 + (int)(Math.random() * 5);
                setDiceImage(j, dice2);
                last = l;
                count++;
                if (count > MAX_ROLLS){
                    clock.stop();
                    finishRoll();
                    count = 0;
                }
            }
        }
    }

    public void updateDiceViews(int n){
        setDiceImage(DiceRolling.dice1, dice1);
        setDiceImage(DiceRolling.dice2, dice2);
    }

    public void finishRoll(){
        int n = DiceRolling.getNumber();
        System.out.println(n);
        updateDiceViews(n);
        controller.handleRoll(n);
    }

    public void diceButtonClicked(MouseEvent mouseEvent){
        if(controller.isItMyTurn(this)) {
            if(controller.hasCurrentPlayerRolled()) {
                diceButton.setVisible(false);
                controller.endMove();
            } else {
                diceButton.setImage(finishButtonImage);
                clock.start();
            }
        }
    }

    public void setDiceImage(int n, ImageView dice){
        String diceImage = "./resources/Dice" + n + ".png";

        dice.setImage(new Image(diceImage));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new Controller();
        controller.addPlayer(this, PlayerColor.BLUE);
        controller.startGame();

        Map map = MapGenerator.generateTestMap();
        MapNode mapNode = new MapNode();

        mapNode.setMap(map);
        mapNode.refreshOutput();
        mapNode.setLayoutX(300);
        mapNode.setLayoutY(150);

        mapNode.addPlaceholderNodes(PlayerColor.BLUE);

        back.getChildren().add(mapNode);

        var possibleStreets = BuildRules.getValidPositions(map, PlayerColor.BLUE);
        for(EdgePosition position : possibleStreets) {

        }
        /*var possibleBuildings = BuildRules.getValidPositions(map, PlayerColor.BLUE, BuildingType.VILLAGE);
        while(possibleBuildings.size() != 0) {
            map.addBuilding(new Building(possibleBuildings.get(0), PlayerColor.BLUE));
            possibleBuildings = BuildRules.getValidPositions(map, PlayerColor.BLUE, BuildingType.VILLAGE);
        }*/
        mapNode.refreshOutput();
        PrintToConsole.println(possibleStreets.toString());
    }

    public void rollAnimation(){
        clock.start();
    }
}
