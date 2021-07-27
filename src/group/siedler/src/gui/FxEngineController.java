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

    private int mMaterialsLeftToSelect = 0;

    private FxKeyEventController mFxKeyEventController = new FxKeyEventController(this);

    public static final String FINISH_BUTTON_IMAGE_NAME = "resources/FinishButton.png";
    public static final String DICE_BUTTON_IMAGE_NAME = "resources/DiceButton.png";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        setupController();
        setupMapNode();
        setupPlayers(6);
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
    public void setupPlayers(int amountOfTotalPlayers) {
        mController.addPlayer(this, PlayerColor.BLUE);
        mController.addPlayer(this, PlayerColor.GREEN);
        mController.addPlayer(this, PlayerColor.YELLOW);
        mController.addPlayer(this, PlayerColor.WHITE);
        mController.addPlayer(this, PlayerColor.PURPLE);
//        AiPlayer aiPlayer = new AiPlayer(mController);
//        while (mController.getNumberOfPlayers() < amountOfTotalPlayers) {
//            mController.addPlayer(aiPlayer, ListUtility.getRandomElement(Arrays.stream(PlayerColor.values()).toList()));
//        }
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
