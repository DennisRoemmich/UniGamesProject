package gui;

import helper.QuickJSon;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import map.BuildRules;
import materials.MaterialType;
import org.json.simple.JSONObject;
import controller.Controller;
import controller.GameState;
import framework.Player;
import framework.Presenter;
import framework.PrintToConsole;
import player.PlayerColor;
import positions.EdgePosition;
import streets.StreetType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FxEngineController extends FxController implements Player, Presenter {

    private RollAnimation mClock = new RollAnimation(this);
    private ClassLoader mClassLoader = getClass().getClassLoader();
    private boolean mTradeFlag = true;
    
    private Stage mStage;
    private Scene mScene;
    private Parent mRoot;
    
    //Material for cards
    private int mMaterialsLeftToSelect = 0;

    private FxKeyEventController mFxKeyEventController = new FxKeyEventController(this);

    public static final String FINISH_BUTTON_IMAGE_NAME = "resources/FinishButton.png";
    public static final String DICE_BUTTON_IMAGE_NAME = "resources/DiceButton.png";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        setupController();
        setupMapNode();
        setupPlayers();
        refreshOutput();
        mController.newGame();
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if (mController.getState() == GameState.OPTIONAL_MOVES) {
            mTradeFlag = false;
        }
        refreshOutput();
        return QuickJSon.create("reply", "valid");
    }

    public void switchToMainScene(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(mClassLoader.getResource("resources/SiedlerGUI.fxml"));
        mRoot = loader.load();
        mStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        mScene = new Scene(mRoot);
        mStage.setScene(mScene);
        mStage.show();
    }
    
    public void helpScreenButtonClicked() throws IOException {    	
    	showHelp();
    	
        ClassLoader classLoader = getClass().getClassLoader();
        var resource = classLoader.getResource("resources/HelpScreen.fxml");
        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 700);
        Stage helpStage = new Stage();
        helpStage.setTitle("Info");
        helpStage.setScene(scene);
        helpStage.show();
    }

    public void switchToTradingWithBankScene(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(mClassLoader.getResource("gui/TradingWithBankGUI.fxml"));
        mRoot = loader.load();
        mStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        mScene = new Scene(mRoot);
        mStage.setScene(mScene);
        mStage.show();
    }

    @Override
    protected void refreshDynamicUiElements() {
        mTradeButton.setVisible(false);
        mDiceButton.setVisible(false);

        if (!mController.isItMyTurn(this)) {
            return;
        }

        switch (mController.getState()) {
            case OPTIONAL_MOVES -> refreshOptionalMoves();
            case ROLL_DICES -> refreshRollDices();
            case SETUP_VILLAGE -> addVillageSetupPlaceholders();
            case SETUP_STREET -> addStreetSetupPlaceholders();
            default -> refreshOptionalMoves(); //Should not come into play
        }
    }
    
    public void showHelp() {
        PrintToConsole.println("*---Welcome to Siedler!---*");
        PrintToConsole.println("");
        PrintToConsole.println("*---How to play:---*");
        PrintToConsole.print("Trading: Press the \"T\" key and the corresponding material key ");
        PrintToConsole.print("from \"1\" to \"5\" on your keyboard for the resource to ");
        PrintToConsole.print ("trade in and then another key from \"1\" to \"5\" to get a corresponding resource. \n");
        PrintToConsole.print("Take a Development Card: Press the \"0\" key on your keyboard \n");
        PrintToConsole.print("Play a Development Card: Press the \"K\", \"R\", \"I\", \"M\" ");
        PrintToConsole.print("key on your keyboard for the desired card to play. ");
        PrintToConsole.print("If you like to play a development card, ");
        PrintToConsole.print("you might need to choose the resource(s) you want to ");
        PrintToConsole.print("get by pressing the desired number key on your keyboard. \n");
        PrintToConsole.println("Dices: You may roll the dice by pressing the ENTER key or clicking on the image");
        PrintToConsole.println("Cheating: Press \"C\" and get 1 of each material... ");
        PrintToConsole.println("");
        PrintToConsole.println("*---Differences to the standard game---*");
        PrintToConsole.print("-> The Road building card gives 2 Clay and Wood building roads. ");
        PrintToConsole.print("With those resources you may build those 2 roads or something else as you desire! \n");
        PrintToConsole.print("-> Since the players have grown suspicious of each other (social distancing hooray ;), ");
        PrintToConsole.print("the players in this version have decided to not trade with each other. ");
        PrintToConsole.print("Thus player trading is not possible in this version of Siedler!\n");
        PrintToConsole.print("-> In this version the burglar raids neutral villages instead ");
        PrintToConsole.print("of pillaging the other player ones so the other players do not lose anything!\n");
    }
    
    protected void refreshOptionalMoves() {
        mMapNode.addPlaceholderNodes(mController);
        mTradeButton.setVisible(true);
        mDiceButton.setVisible(true);
        mDiceButton.setImage(new Image(mClassLoader.getResourceAsStream(FINISH_BUTTON_IMAGE_NAME)));
    }
    
    protected void refreshRollDices() {
        mDiceButton.setVisible(true);
        mDiceButton.setImage(new Image(mClassLoader.getResourceAsStream(DICE_BUTTON_IMAGE_NAME)));
    }
    
    public void addVillageSetupPlaceholders() {
     //Checks if optional is present.
    	if (!mMapNode.getMap().isPresent()) {
        	return;
        }       
        mMapNode.addBuildingPlaceholders(BuildRules.getStartNodePositions(mMapNode.getMap().get()));
    }  

    public void addStreetSetupPlaceholders() {
     //Checks if optional is present. 
    	if (!mMapNode.getMap().isPresent()) {
        	return;
        }
        PlayerColor color = mController.getCurrentPlayerColor();
        for (StreetType type : StreetType.values()) {
        	List<EdgePosition> positions = BuildRules.getStartEdgePositions(mMapNode.getMap().get(), color, type);
            mMapNode.addStreetPlaceholders(positions, type);
        }
    }


    public void diceButtonClicked() {
        if (mController.isItMyTurn(this)) {
            if (mController.getState() == GameState.OPTIONAL_MOVES) {
                mController.endMove();
                mMapNode.refreshOutput();
            } else {
                mClock.start();
            }
        }
    }

    public void setupController() {
        mController = new Controller();
        mController.setPresenter(this);
    }

    public void setupMapNode() {
        mMapNode = new MapNode(mController);
        mMapNode.refreshOutput();
        mMapNode.setLayoutX(390);
        mMapNode.setLayoutY(300);
        mMapNode.setScaleX(0.7);
        mMapNode.setScaleY(0.7);
        mMapNode.setScaleZ(0.7);
        mAnchorPane.getChildren().add(mMapNode);
    }
    
    //Setup Players AI and players (this)
    public void setupPlayers() {
        mController.addPlayer(this, PlayerColor.BLUE);
        mController.addPlayer(this, PlayerColor.GREEN);
        mController.addPlayer(this, PlayerColor.YELLOW);
        mController.addPlayer(this, PlayerColor.WHITE);
        mController.addPlayer(this, PlayerColor.PURPLE);
    }

    public MaterialType chooseResource(KeyEvent event) {
        return switch (event.getCode()) {
            case DIGIT1 -> MaterialType.WOOD;
            case DIGIT2 -> MaterialType.WHEAT;
            case DIGIT3 -> MaterialType.WOOL;
            case DIGIT4 -> MaterialType.ORE;
            case DIGIT5 -> MaterialType.CLAY;
            default -> throw new IllegalArgumentException("Unexpected value: " + event.getCode());
        };
    }

    @FXML
    public void handleKeyInput(KeyEvent event) {
        mFxKeyEventController.handleKeyInput(event);
    }

    public Controller getController() {
        return mController;
    }

    public int getMaterialsLeftToSelect() {
        return mMaterialsLeftToSelect;
    }

    public void setMaterialsLeftToSelect(int materialsLeftToSelect) {
        this.mMaterialsLeftToSelect = materialsLeftToSelect;
    }

	public boolean ismTradeFlag() {
		return mTradeFlag;
	}
}
