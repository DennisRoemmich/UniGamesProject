package gui;

import buildings.Building;
import buildings.BuildingType;
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
import positions.EdgePositionZCord;
import positions.NodePosition;
import siedlerController.AiPlayer;
import siedlerController.Controller;
import siedlerFramework.Player;
import siedlerFramework.Presenter;
import siedlerFramework.PrintToConsole;
import streets.Street;
import streets.StreetType;

import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class FXController implements Initializable, Player, Presenter {

    private Controller controller = new Controller();

    @FXML
    private AnchorPane back;
    @FXML
    private ImageView background;
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

    @Override
    public void refreshOutput() {

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

    public void updateDiceViews() {
        setDiceImage(DiceRolling.dice1, dice1);
        setDiceImage(DiceRolling.dice2, dice2);
    }

    public void finishRoll(){
        controller.handleRoll();
        updateDiceViews();
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

        System.out.println(back.getWidth());
        System.out.println(back.getHeight());
        System.out.println(diceButton.getFitHeight());
    }

    public void setDiceImage(int n, ImageView dice){
        String diceImage = "./resources/Dice" + n + ".png";

        dice.setImage(new Image(diceImage));

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new Controller();


        int colorIndex = ThreadLocalRandom.current().nextInt(0, PlayerColor.values().length);
        var colors = PlayerColor.values();
        PlayerColor color = colors[colorIndex];

        System.out.println(background.fitHeightProperty());
        System.out.println(background.fitWidthProperty());
        /*DO NOT DELETE; BINDING IS BEING IMPLEMENTED HERE BUT IS A PAIN IN THE ASS*/

        AiPlayer aiPlayer = new AiPlayer(controller);

        controller.addPlayer(this, PlayerColor.BLUE);
        controller.addPlayer(aiPlayer, PlayerColor.RED);
        controller.addPlayer(aiPlayer, PlayerColor.GREEN);
        controller.addPlayer(aiPlayer, PlayerColor.YELLOW);

        controller.newGame();

        background.fitHeightProperty().bind(back.heightProperty());
        background.fitWidthProperty().bind(back.widthProperty());

        //diceButton.yProperty().bind(back.heightProperty().subtract(100));
        //diceButton.xProperty().bind(back.widthProperty().subtract(100));
        diceButton.fitHeightProperty().bind(back.heightProperty().multiply(0.2));

        dice1.fitHeightProperty().bind(back.heightProperty().multiply(0.15));
        //dice1.xProperty().bind(back.widthProperty().multiply(0.75));

        dice2.fitHeightProperty().bind(back.heightProperty().multiply(0.15));
        dice2.yProperty().bind(back.heightProperty().multiply(0.62));

        dice1.yProperty().bind(dice2.yProperty());
        dice1.xProperty().bind(back.widthProperty().multiply(0.79));
        //diceButton.fitHeightProperty().bind(back.heightProperty().divide(4));

        //dice1.fitHeightProperty().bind(back.heightProperty().divide(5));
        //dice1.yProperty().bind(diceButton.layoutYProperty().subtract(100));
        //dice1.xProperty().bind(diceButton.layoutXProperty().subtract(50));

        MapFrame mapFrame = new MapFrame(controller.getMap());

        mapFrame.getMapNode().refreshOutput();
        mapFrame.setLayoutX(300);
        mapFrame.setLayoutY(150);

        mapFrame.getMapNode().addPlaceholderNodes(PlayerColor.BLUE);

        back.getChildren().add(mapFrame);

        var firstBuilding = new Building(new NodePosition(-2,1,true), color);
        controller.getMap().addBuilding(firstBuilding);

        var possibleStreets = BuildRules.getValidEdgePositions(controller.getMap(), color);
        for(int i = 0; i < 10 && possibleStreets.size() != 0; i++) {
            int streetIndex = ThreadLocalRandom.current().nextInt(0, possibleStreets.size());
            var newStreet = new Street(possibleStreets.get(streetIndex), StreetType.ROAD, color);
            controller.getMap().addStreet(newStreet);
            possibleStreets = BuildRules.getValidEdgePositions(controller.getMap(), color);
        }
        var possibleBuildings = BuildRules.getValidNodePositions(controller.getMap(), color, BuildingType.VILLAGE);
        while(possibleBuildings.size() != 0) {
            int buildingIndex = ThreadLocalRandom.current().nextInt(0, possibleBuildings.size());
            controller.getMap().addBuilding(new Building(possibleBuildings.get(buildingIndex), color));
            possibleBuildings = BuildRules.getValidNodePositions(controller.getMap(), color, BuildingType.VILLAGE);
        }
        mapFrame.getMapNode().refreshOutput();
        //PrintToConsole.println(possibleStreets.toString());
    }

    public void rollAnimation(){
        clock.start();
    }
}
