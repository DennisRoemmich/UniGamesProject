package gui;

import buildings.BuildingType;
import cards.CardType;
import dice.DiceRolling;
import helper.ListUtility;
import helper.QuickJSon;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import map.BuildRules;
import materials.MaterialType;
import org.json.simple.JSONObject;
import player.PlayerColor;
import siedlerController.AiPlayer;
import siedlerController.Controller;
import siedlerController.GameState;
import siedlerFramework.Player;
import siedlerFramework.Presenter;
import siedlerFramework.PrintToConsole;
import streets.StreetType;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class FXEngineController extends FXController implements Player, Presenter {

    protected RollAnimation clock = new RollAnimation(this);
    protected ClassLoader classLoader = getClass().getClassLoader();

    private int materialsLeftToSelect = 0;

    protected FXKeyEventController fxKeyEventController = new FXKeyEventController(this);

    public final static String finishButtonImageName = "resources/FinishButton.png";
    public final static String diceButtonImageName = "resources/DiceButton.png";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        setupController();
        setupMapNode();
        setupPlayers(3);
        refreshOutput();
        controller.newGame();
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        switch (controller.getState()) {
            case OPTIONAL_MOVES:
                tradeFlag = false;
                break;
        }
        refreshOutput();
        return QuickJSon.create("reply", "valid");
    }

    public void addVillageSetupPlaceholders() {
        if(mapNode.getMap().isEmpty()) return;
        mapNode.addBuildingPlaceholders(BuildRules.getStartNodePositions(mapNode.getMap().get()), BuildingType.VILLAGE);

    }

    public void addStreetSetupPlaceholders() {
        if(mapNode.getMap().isEmpty()) return;
        PlayerColor color = controller.getCurrentPlayerColor();
        for (StreetType type : StreetType.values()) {
            mapNode.addStreetPlaceholders(BuildRules.getStartEdgePositions(mapNode.getMap().get(), color, type), type);
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
    protected void refreshDynamicUiElements() {
        tradeButton.setVisible(false);
        diceButton.setVisible(false);

        if (!controller.isItMyTurn(this)) {
            return;
        }

        switch (controller.getState()) {
            case OPTIONAL_MOVES -> {
                mapNode.addPlaceholderNodes(controller);
                tradeButton.setVisible(true);
                diceButton.setVisible(true);
                diceButton.setImage(new Image(classLoader.getResourceAsStream(finishButtonImageName)));
            }
            case ROLL_DICES -> {
                diceButton.setVisible(true);
                diceButton.setImage(new Image(classLoader.getResourceAsStream(diceButtonImageName)));
            }
            case SETUP_VILLAGE -> addVillageSetupPlaceholders();
            case SETUP_STREET -> addStreetSetupPlaceholders();
        }
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

    public void setupController() {
        controller = new Controller();
        controller.setPresenter(this);
    }

    public void setupMapNode() {
        mapNode = new MapNode(controller);
        mapNode.refreshOutput();
        mapNode.setLayoutX(390);
        mapNode.setLayoutY(300);
        mapNode.setScaleX(0.7);
        mapNode.setScaleY(0.7);
        mapNode.setScaleZ(0.7);
        back.getChildren().add(mapNode);
    }

    public void setupPlayers(int amountOfTotalPlayers) {
        controller.addPlayer(this, PlayerColor.BLUE);
        AiPlayer aiPlayer = new AiPlayer(controller);
        while (controller.getNumberOfPlayers() < amountOfTotalPlayers) {
            controller.addPlayer(aiPlayer, ListUtility.getRandomElement(Arrays.stream(PlayerColor.values()).toList()));
        }
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

    @FXML
    public void handleKeyInput(KeyEvent event) {
        fxKeyEventController.handleKeyInput(event);
    }

    public Controller getController() {
        return controller;
    }

    public int getMaterialsLeftToSelect() {
        return materialsLeftToSelect;
    }

    public void setMaterialsLeftToSelect(int materialsLeftToSelect) {
        this.materialsLeftToSelect = materialsLeftToSelect;
    }
}
