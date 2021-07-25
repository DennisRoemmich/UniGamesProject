package gui;

import buildings.Building;
import buildings.BuildingType;
import com.sun.prism.shader.Solid_TextureFirstPassLCD_AlphaTest_Loader;
import diceRolling.DiceRolling;
import helper.ListUtility;
import helper.QuickJSON;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import map.BuildRules;
import map.Map;
import map.MapGenerator;
import map.MapTools;
import materials.MaterialType;

import org.json.simple.JSONObject;
import player.PlayerColor;
import positions.*;
import siedlerController.AiPlayer;
import siedlerController.Controller;
import siedlerController.GameState;
import siedlerFramework.Player;
import siedlerFramework.Presenter;
import siedlerFramework.PrintToConsole;
import streets.Street;
import streets.StreetType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class FXController implements Initializable, Player, Presenter, SiedlerEventHandler {

    private Controller controller = new Controller();
    private MapNode mapNode;
    
    private boolean tradeFlag = true;
    private MaterialType sellType;

    private boolean animationStopFlag = false;

    private Stage stage;
    private Scene scene;
    private Parent root;
    

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
    @FXML
    private ImageView playerMaterials;
    @FXML
    private Label woodLabel;
    @FXML
    private Label wheatLabel;
    @FXML
    private Label woolLabel;
    @FXML
    private Label clayLabel;
    @FXML
    private Label oreLabel;   
    @FXML
    private Label woodAmount;
    @FXML
    private Label wheatAmount;
    @FXML
    private Label woolAmount;
    @FXML
    private Label clayAmount;
    @FXML
    private Label oreAmount;
    @FXML
    private Label currentPlayer;
    @FXML
    private ImageView tradeWithBankButton;

    Roller clock = new Roller();

    ClassLoader classLoader = getClass().getClassLoader();

    public final static String finishButtonImageName = "resources/FinishButton.png";
    public final static String diceButtonImageName = "resources/DiceButton.png";

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        switch (controller.getState()) {
            case OPTIONAL_MOVES:
                tradeFlag = false;
                break;
        }
        refreshOutput();
        return QuickJSON.create("reply", "valid");
    }

    @Override
    public void refreshOutput() {
        if(mapNode != null) {
            mapNode.refreshOutput();
            setResources();
            //trade();
            updateDiceViews();

            if(controller.isItMyTurn(this)) {
                switch (controller.getState()) {
                    case OPTIONAL_MOVES:
                        mapNode.addPlaceholderNodes(controller);
                        diceButton.setVisible(true);
                        diceButton.setImage(new Image(classLoader.getResourceAsStream(finishButtonImageName)));
                        break;
                    case ROLL_DICES:
                        diceButton.setVisible(true);
                        diceButton.setImage(new Image(classLoader.getResourceAsStream(diceButtonImageName)));
                        break;
                    case MOVE_BURGLAR:
                        diceButton.setVisible(true);
                        diceButton.setImage(new Image(classLoader.getResourceAsStream(finishButtonImageName)));
                        break;
                    case SETUP_VILLAGE:
                        mapNode.addBuildingPlaceholders(BuildRules.getStartNodePositions(mapNode.getMap()), BuildingType.VILLAGE);
                        break;
                    case SETUP_STREET:
                        PlayerColor color = controller.getCurrentPlayerColor();
                        for(StreetType type : StreetType.values()) {
                            mapNode.addStreetPlaceholders(BuildRules.getStartEdgePositions(mapNode.getMap(), color, type), type);
                        }
                        break;
                }
            } else {
                diceButton.setVisible(false);
            }
        }
    }

    public void switchToMainScene(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(classLoader.getResource("resources/SiedlerGUI.fxml"));
        root = loader.load();
        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToTradingWithBankScene(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(classLoader.getResource("gui/TradingWithBankGUI.fxml"));
        root = loader.load();
        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handleTileCLick(TilePosition position) {
        if(controller.getState() == GameState.MOVE_BURGLAR) {

        }
    }

    @Override
    public void handleStreetClick(EdgePosition position) {

    }

    @Override
    public void handleBuildingClick(NodePosition position) {

    }

    private class Roller extends AnimationTimer{

        private long FRAMES_PER_SEC = 10L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private long last = 0;
        private int count = 0;

        private boolean isRunning = false;

        @Override
        public void handle(long l) {
            isRunning = true;
            if(l - last > INTERVAL){
                int r = 2 + (int)(Math.random() * 5);
                setDiceImage(r, dice1);
                int j = 2 + (int)(Math.random() * 5);
                setDiceImage(j, dice2);
                last = l;
                count++;
                if (count > MAX_ROLLS || animationStopFlag){
                    clock.stop();
                    finishRoll();
                    count = 0;
                    isRunning = false;
                }
            }
        }

        public boolean isRunning() {
            return isRunning;
        }
    }

    public void updateDiceViews() {
        setDiceImage(DiceRolling.dice1, dice1);
        setDiceImage(DiceRolling.dice2, dice2);
    }

    public void finishRoll(){
        controller.handleRoll();
        refreshOutput();
    }

    public void diceButtonClicked(){
        if(controller.isItMyTurn(this)) {
            if(controller.getState() == GameState.OPTIONAL_MOVES) {
                diceButton.setVisible(false);
                controller.endMove();
                mapNode.refreshOutput();
            } else {
                diceButton.setImage(new Image(classLoader.getResourceAsStream(finishButtonImageName)));
                clock.start();
            }
        }

        System.out.println(back.getWidth());
        System.out.println(back.getHeight());
        System.out.println(diceButton.getFitHeight());
    }

    public void setDiceImage(int n, ImageView dice){
        String diceImage = "resources/Dice" + n + ".png";
        dice.setImage(new Image(classLoader.getResourceAsStream(diceImage)));
    }

    public void setPlayerMaterialsImage (String s, ImageView playerMaterials){

        String playerMaterialsImage = "resources/Player" + s + "Materials.png";

        playerMaterials.setImage(new Image(classLoader.getResourceAsStream(playerMaterialsImage)));
    }
/*
    public void tradeWithBankButtonClicked(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(new File("src/gui/TradingWithBankGUI.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200,900);

        Stage tradingStage = new Stage();
        tradingStage.setTitle("Trade with Bank");
        tradingStage.setScene(scene);
        tradingStage.show();
    }

*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new Controller();
        controller.setPresenter(this);
        mapNode = new MapNode(controller);


        int colorIndex = ThreadLocalRandom.current().nextInt(0, PlayerColor.values().length);
        var colors = PlayerColor.values();
        PlayerColor color = colors[colorIndex];

        /*DO NOT DELETE; BINDING IS BEING IMPLEMENTED HERE BUT IS A PAIN IN THE ASS*/

        setupBindings();
        setupPlayers();

        tradeWithBankButton.setVisible(false);

        controller.newGame();
        mapNode.setMap(controller.getMap());
        setResources();

        mapNode.refreshOutput();
        mapNode.setLayoutX(390);
        mapNode.setLayoutY(300);
        mapNode.setScaleX(0.7);
        mapNode.setScaleY(0.7);
        mapNode.setScaleZ(0.7);

        back.getChildren().add(mapNode);
        refreshOutput();
    }

    private void setupBindings() {
        /*
        background.fitHeightProperty().bind(back.heightProperty());
        background.fitWidthProperty().bind(back.widthProperty());

        diceButton.fitHeightProperty().bind(back.heightProperty().multiply(0.2));

        dice1.fitHeightProperty().bind(back.heightProperty().multiply(0.15));

        dice2.fitHeightProperty().bind(back.heightProperty().multiply(0.15));
        dice2.yProperty().bind(back.heightProperty().multiply(0.62));

        dice1.yProperty().bind(dice2.yProperty());
        dice1.xProperty().bind(back.widthProperty().multiply(0.79));

        //playerMaterials.setImage(new Image("./resources/PlayerGreenMaterials.png"));
        playerMaterials.yProperty().bind(back.heightProperty().multiply(0.01));
        playerMaterials.xProperty().bind(back.widthProperty().multiply(0.7));
        playerMaterials.fitHeightProperty().bind(back.heightProperty().multiply(0.6));

        //woodLabel.setText("Wood");
*/
        //Set Fonts
        woodLabel.setFont(Font.font("Arial", 15));
        wheatLabel.setFont(Font.font("Arial", 15));
        clayLabel.setFont(Font.font("Arial", 15));
        oreLabel.setFont(Font.font("Arial", 15));
        woolLabel.setFont(Font.font("Arial", 15));
        
        woodAmount.setFont(Font.font("Arial", 15));
        wheatAmount.setFont(Font.font("Arial", 15));
        clayAmount.setFont(Font.font("Arial", 15));
        oreAmount.setFont(Font.font("Arial", 15));
        woolAmount.setFont(Font.font("Arial", 15));
        
        currentPlayer.setFont(Font.font("Arial", 15));


    }

    private void setupPlayers() {
        AiPlayer aiPlayer = new AiPlayer(controller);
        controller.addPlayer(this, PlayerColor.BLUE);
        controller.addPlayer(aiPlayer, PlayerColor.RED);
        controller.addPlayer(aiPlayer, PlayerColor.GREEN);
        controller.addPlayer(aiPlayer, PlayerColor.YELLOW);
    }
    
    public void setResources() {

    	currentPlayer.setText(controller.getCurrentPlayerColor().name() + "'s turn");
    	woodAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOD)));
    	wheatAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WHEAT)));
    	woolAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOL)));
    	oreAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.ORE)));
    	clayAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.CLAY)));
    }
    
    //Sets Sell MaterialType with the first keystroke
    public void setFirstKeyStroke(KeyEvent event) {
        MaterialType type = switch (event.getCode()) {
            case DIGIT1 -> MaterialType.WOOD;
            case DIGIT2 -> MaterialType.WHEAT;
            case DIGIT3 -> MaterialType.WOOL;
            case DIGIT4 -> MaterialType.ORE;
            case DIGIT5 -> MaterialType.CLAY;
            default -> null;
        };
        if(type != null) {
            sellType = type;
            System.out.println("First material set!");
            tradeFlag = true;
        }
    }
    
  //Sets the Purchase value for the 2nd keystroke
    public void setSecondKeyStroke(KeyEvent event) {
        MaterialType type = switch (event.getCode()) {
            case DIGIT1 -> MaterialType.WOOD;
            case DIGIT2 -> MaterialType.WHEAT;
            case DIGIT3 -> MaterialType.WOOL;
            case DIGIT4 -> MaterialType.ORE;
            case DIGIT5 -> MaterialType.CLAY;
            default -> null;
        };
        if(type != null) {
            controller.bankTrade(type, sellType);
            System.out.println("Trade accepted!");
        }
        System.out.println("Trade flag reset1!");
        tradeFlag = false;
    }
    
    //Trade functionality
    @FXML
    public void trade(KeyEvent event) {
        handleKeyInput(event);

    	if(controller.getState() == GameState.OPTIONAL_MOVES) {
            if (!tradeFlag) {
                setFirstKeyStroke(event);
            } else {
                setSecondKeyStroke(event);
            }
        }

    	refreshOutput();
    }

    public void handleKeyInput(KeyEvent event) {
        switch(event.getCode()) {
             case ENTER, SPACE:
                diceButtonClicked();
        }
    }


    public void rollAnimation(){
        clock.start();
    }
}
