package gui;

import buildings.BuildingType;
import developmentCards.CardType;
import diceRolling.DiceRolling;
import helper.QuickJSON;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import map.BuildRules;
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
import streets.StreetType;

import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class FXController implements Initializable, Player, Presenter, SiedlerEventHandler {

    private Controller controller = new Controller();
    private MapNode mapNode;

    private boolean tradeFlag = true;
    private MaterialType sellType = MaterialType.ORE;
    private MaterialType chosen;

    private boolean animationStopFlag = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane back;
    @FXML
    private ImageView background, tradeWithBankButton, diceButton, dice1, dice2, playerMaterials;
    @FXML
    private Label woodLabel, wheatLabel, woolLabel, clayLabel, oreLabel;
    @FXML
    private Label woodAmount, wheatAmount, woolAmount, clayAmount, oreAmount;
    @FXML
    private Label currentPlayer, winner, tradeError, points, consoleInfo;
    @FXML
    private Label victoryPointCard, knightCard, roadCard, inventionCard, monopolyCard;
    @FXML
    private Label victoryAmount, knightAmount, roadAmount, inventionAmount, monopolyAmount;

    RollAnimation clock = new RollAnimation(this);
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
        if (mapNode != null) {
            mapNode.refreshOutput();
            setResources();
            setWinner();
            setPoints();
            setCards();
            updateDiceViews();

            if (controller.isItMyTurn(this)) {
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
                        diceButton.setVisible(false);
                        break;
                    case SETUP_VILLAGE:
                        mapNode.addBuildingPlaceholders(BuildRules.getStartNodePositions(mapNode.getMap()), BuildingType.VILLAGE);
                        break;
                    case SETUP_STREET:
                        PlayerColor color = controller.getCurrentPlayerColor();
                        for (StreetType type : StreetType.values()) {
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
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToTradingWithBankScene(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(classLoader.getResource("gui/TradingWithBankGUI.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //        FXMLLoader loader = new FXMLLoader(new File("src/gui/SiedlerGUI.fxml").toURI().toURL());
        //        root = loader.load();
        //        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        //        scene = new Scene(root);
        //        stage.setScene(scene);
        //        stage.show();
    }

    @Override
    public void handleTileCLick(TilePosition position) {
        if (controller.getState() == GameState.MOVE_BURGLAR) {

        }
    }

    @Override
    public void handleStreetClick(EdgePosition position) {

    }

    @Override
    public void handleBuildingClick(NodePosition position) {

    }

    private class Roller extends AnimationTimer {

        private long FRAMES_PER_SEC = 10L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private long last = 0;
        private int count = 0;

        private boolean isRunning = false;

        @Override
        public void handle(long l) {
            isRunning = true;
            if (l - last > INTERVAL) {
                int r = 2 + (int) (Math.random() * 5);
                setDiceImage(r, dice1);
                int j = 2 + (int) (Math.random() * 5);
                setDiceImage(j, dice2);
                last = l;
                count++;
                if (count > MAX_ROLLS || animationStopFlag) {
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

    public void updateDiceViews(int l, int r) {
        setDiceImage(l, dice1);
        setDiceImage(r, dice2);
    }

        public void updateDiceViews() {
        setDiceImage(DiceRolling.dice1, dice1);
        setDiceImage(DiceRolling.dice2, dice2);
    }

    public void finishRoll() {
        controller.handleRoll();
        refreshOutput();
    }

    public void diceButtonClicked() {
        if (controller.isItMyTurn(this)) {
            if (controller.getState() == GameState.OPTIONAL_MOVES) {
                controller.endMove();
                mapNode.refreshOutput();
            } else {
                clock.start();
            }
        }
    }

    public void setDiceImage(int n, ImageView dice) {
        String diceImage = "resources/Dice" + n + ".png";
        dice.setImage(new Image(classLoader.getResourceAsStream(diceImage)));
    }

    public void setPlayerMaterialsImage(String s, ImageView playerMaterials) {

        String playerMaterialsImage = "resources/Player" + s + "Materials.png";

        try {
            playerMaterials.setImage(new Image(classLoader.getResourceAsStream(playerMaterialsImage)));
        } catch (Exception e) {
            playerMaterialsImage = "resources/Player" + "Blue" + "Materials.png";
            playerMaterials.setImage(new Image(classLoader.getResourceAsStream(playerMaterialsImage)));
        }
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

        setupFonts();
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

    private void setupFonts() {
        String fontName = "Arial";
        int fontSize = 15;
        //Set Fonts
        woodLabel.setFont(Font.font(fontName, fontSize));
        wheatLabel.setFont(Font.font(fontName, fontSize));
        clayLabel.setFont(Font.font(fontName, fontSize));
        oreLabel.setFont(Font.font(fontName, fontSize));
        woolLabel.setFont(Font.font(fontName, fontSize));

        woodAmount.setFont(Font.font(fontName, fontSize));
        wheatAmount.setFont(Font.font(fontName, fontSize));
        clayAmount.setFont(Font.font(fontName, fontSize));
        oreAmount.setFont(Font.font(fontName, fontSize));
        woolAmount.setFont(Font.font(fontName, fontSize));

        currentPlayer.setFont(Font.font(fontName, fontSize));

    }

    private void setupPlayers() {
        AiPlayer aiPlayer = new AiPlayer(controller);
        controller.addPlayer(this, PlayerColor.BLUE);
        for (PlayerColor color : PlayerColor.values()) {
            controller.addPlayer(aiPlayer, color);
        }
    }

    public void setResources() {

        setPlayerMaterialsImage(controller.getCurrentPlayerColor().name(), playerMaterials);
        currentPlayer.setText(controller.getCurrentPlayerColor().name() + "'s turn");
        woodAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOD)));
        wheatAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WHEAT)));
        woolAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOL)));
        oreAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.ORE)));
        clayAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.CLAY)));
    }


    public MaterialType chooseResource(KeyEvent event) {
        return switch (event.getCode()) {
            case DIGIT1 -> MaterialType.WOOD;
            case DIGIT2 -> MaterialType.WHEAT;
            case DIGIT3 -> MaterialType.WOOL;
            case DIGIT4 -> MaterialType.ORE;
            case DIGIT5 -> MaterialType.CLAY;
            default -> throw new IllegalArgumentException("Unexpected value: " + event.getCode());
            //default -> null;
        };
    }

    //Sets Sell MaterialType with the first keystroke
    public void setFirstKeyStroke(KeyEvent event) {
        if (chooseResource(event) != null) {
            sellType = chooseResource(event);
            PrintToConsole.println("First material set!");
            tradeFlag = true;
        }
    }

    //Sets the Purchase value for the 2nd keystroke
    public void setSecondKeyStroke(KeyEvent event) {
        if (chooseResource(event) != null) {
            controller.bankTrade(chooseResource(event), sellType);
            PrintToConsole.println("Trade accepted!");
        }
        PrintToConsole.println("Trade flag reset!");
        tradeFlag = false;
    }

    public void setWinner() {
        if (controller.isGameHasWinner()) {

            winner.setText("Player " + controller.getWinningColor() + " has won!");
        }
    }

    public void setPoints() {
        int winPoints = controller.getWinPoints();
        points.setText("Victory Points:        " + winPoints);
    }

    public void setCards() {
        knightAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.KNIGHT)));
        victoryAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.VICTORY)));
        roadAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.ROAD)));
        inventionAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.INVENTION)));
        monopolyAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.MONOPOLY)));
    }


    public void showHelp() {
        PrintToConsole.println("*---Welcome to Siedler!---*");
        PrintToConsole.println("");
        PrintToConsole.println("*---How to play:---*");
        PrintToConsole.println("Trading: Press a key from \"1\" to \"5\" on your keyboard for the resource to trade in and then another key from \"1\" to \"5\" to get the corresponding resource.");
        PrintToConsole.println("Take a Development Card: Press the \"0\" key on your keyboard");
        PrintToConsole.println("Play a Development Card: Press the \"K\", \"R\", \"I\", \"M\" key on your keyboard for the desired card to play");
        PrintToConsole.println("Important: If you like to play a development card, please choose the resource you want to get by pressing the desired number key on your keyboard BEFORE playing the card. Please press \"Enter\" afterwards such that no accidental trading occurs :)");
        PrintToConsole.println("");
        PrintToConsole.println("*---Differences to the standard game---*");
        PrintToConsole.println("The invention card gives you 5 of one resource instead of 2 of any. With 4:1 trading the result is the same. ");
        PrintToConsole.println("The Road building card gives you 2 Clay and Wood instead of letting you build two roads. With those resources you still may build those 2 roads or something else as you desire!");
        refreshOutput();
    }

    //Trade functionality
    @FXML
    public void trade(KeyEvent event) {

    }

    public void handleKeyInput(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER, SPACE:
                diceButtonClicked();
        }
    }

    public void rollAnimation() {
        clock.start();
    }

    public Controller getController() {
        return controller;
    }
}
