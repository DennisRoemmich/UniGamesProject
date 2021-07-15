package gui;

import buildings.Building;
import diceRolling.DiceRolling;
import javafx.animation.AnimationTimer;
import javafx.fxml.Initializable;
import buildings.BuildingType;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import map.BuildRules;
import map.Map;
import map.MapGenerator;
import player.PlayerColor;
import positions.EdgePosition;
import positions.EdgePositionZCord;
import positions.NodePosition;
import siedlerFramework.PrintToConsole;
import streets.Street;
import streets.StreetType;

import java.util.ResourceBundle;

public class FXController implements Initializable {

    @FXML
    private AnchorPane back;
    @FXML
    private ImageView diceButton;
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;

    Roller clock = new Roller();

    String finishButton = "./resources/FinishButton.png";


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
                    roll();
                    count = 0;
                }
            }
        }
    }

    public void updateDiceViews(int n){
        setDiceImage(DiceRolling.dice1, dice1);
        setDiceImage(DiceRolling.dice2, dice2);
    }

    public void roll(){
        int n = DiceRolling.getNumber();
        System.out.println(n);
        updateDiceViews(n);
    }

    public void diceButtonClicked(MouseEvent mouseEvent){
        diceButton.setImage(new Image(finishButton));
        clock.start();
    }




    public void setDiceImage(int n, ImageView dice){
        String diceImage = "./resources/Dice" + n + ".png";

        dice.setImage(new Image(diceImage));

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Map map = MapGenerator.generateTestMap();
        MapNode mapNode = new MapNode();
        int x = 0;
        int y = 0;
        for(EdgePositionZCord zCord : EdgePositionZCord.values()) {
            EdgePosition positionEdge = new EdgePosition(x,y,zCord);
            Street street = new Street(positionEdge, StreetType.ROAD, PlayerColor.BLUE);
            mapNode.getMap().addStreet(street);
        }

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
