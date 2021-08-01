package siedler.gui;

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

import player.PlayerColor;
import positions.EdgePosition;
import siedler.controller.AiPlayer;
import siedler.controller.Controller;
import siedler.controller.GameState;
import siedler.framework.Player;
import siedler.framework.Presenter;
import siedler.framework.PrintToConsole;
import streets.StreetType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Initializes the GUI onto the screen in regards to the Controller.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class FxEngineController extends FxController implements Player, Presenter {

    private RollAnimation mClock = new RollAnimation(this);
    private ClassLoader mClassLoader = getClass().getClassLoader();
    private boolean mTradeFlag = true;
    
    private Stage mStage;
    private Scene mScene;
    private Parent mRoot;
    
    private Clip mDiceClipName;
    private Clip mButtonClipName;
    
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
        
        cheatResources(FxMenuController.getStartingAmount()); 
        
        refreshOutput();
        refreshRessourceMessage();
        mController.newGame();
    }
    
    public void setupPlayers() {
    	
    	AiPlayer aiPlayer = new AiPlayer(mController);
    	
    	int aiPlayers = FxMenuController.getAiAmount();
    	int humanPlayers = FxMenuController.getHumanAmount();
    	int allPlayers = aiPlayers + humanPlayers;
    	
    	for (int i = 0; i < allPlayers; i++) {
    		if (humanPlayers > 0) {
				mController.addPlayer(this, PlayerColor.getColorList().get(i));
				humanPlayers--;
    		} else if (aiPlayers > 0) {
				mController.addPlayer(aiPlayer, PlayerColor.getColorList().get(i));
				aiPlayers--;
    		} 
    	}
    }
    
    //Set up additional resources for testing
    //You may change this method for testing!
    public void cheatResources(int amount) {
        for (int i = 0; i < mController.getNumberOfPlayers(); i++) {
        	//Change amount of starting wood for all players
        	mController.getPlayerData().get(i).getHand().addResources(MaterialType.WOOD, amount); 
        	
        	//Change amount of starting clay for all players
        	mController.getPlayerData().get(i).getHand().addResources(MaterialType.CLAY, amount); 
        	
        	//Change amount of starting wool for all players
        	mController.getPlayerData().get(i).getHand().addResources(MaterialType.WOOL, amount);
        	
        	//Change amount of starting wheat for all players
        	mController.getPlayerData().get(i).getHand().addResources(MaterialType.WHEAT, amount);
        	
        	//Change amount of starting ore for all players
        	mController.getPlayerData().get(i).getHand().addResources(MaterialType.ORE, amount); 
        }
    }
    
    public void refreshRessourceMessage() {
        
        if (this.mMaterialsLeftToSelect > 0) {
        	this.mChooseRessource.setText("Please select a ressource!");
        	
        } else { 
        	this.mChooseRessource.setText("");
        }
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
            case MOVE_BURGLAR -> refreshBurglar();
            default -> refreshOptionalMoves(); //Should not come into play
        }
    }

    
    protected void refreshOptionalMoves() {
        mMapNode.addPlaceholderNodes(mController);
        mDiceButton.setVisible(true);
        mDiceButton.setImage(new Image(mClassLoader.getResourceAsStream(FINISH_BUTTON_IMAGE_NAME)));
        mBurglarMessage.setText("");
    }
    
    protected void refreshRollDices() {
        mDiceButton.setVisible(true);
        mDiceButton.setImage(new Image(mClassLoader.getResourceAsStream(DICE_BUTTON_IMAGE_NAME)));
    }
    
    public void addVillageSetupPlaceholders() {
     //Checks if optional is present.
    	var map = mMapNode.getMap();
    	if (map.isEmpty()) {
        	return;
        }
    	mSetupPhase.setText("Place your 2 villages and roads!");
        mMapNode.addBuildingPlaceholders(BuildRules.getStartNodePositions(map.get()));
    }  

    public void addStreetSetupPlaceholders() {
     //Checks if optional is present. 
    	var map = mMapNode.getMap();
    	if (map.isEmpty()) {
        	return;
        }
    	mSetupPhase.setText("Place your 2 villages and roads!");
        PlayerColor color = mController.getCurrentPlayerColor();
        for (StreetType type : StreetType.values()) {
        	List<EdgePosition> positions = BuildRules.getStartEdgePositions(map.get(), color, type);
            mMapNode.addStreetPlaceholders(positions, type);
        }
    }
    
    public void refreshBurglar() {
    	mBurglarMessage.setText("Move the burglar!");
    	mDiceButton.setVisible(false);
    }

    public void diceButtonClicked() {
    	
    	AudioInputStream mDiceClipNameAis;
        AudioInputStream mButtonClipNameAis;
    	
    	try { 
            URL buttonResource = getClass().getResource("/resources/buttonPressedSound.wav");
			mButtonClipNameAis = AudioSystem.getAudioInputStream(buttonResource);
            mButtonClipName = AudioSystem.getClip();
            mButtonClipName.open(mButtonClipNameAis);
    		
			URL diceResource = getClass().getResource("/resources/DiceSound.wav");
			mDiceClipNameAis = AudioSystem.getAudioInputStream(diceResource);
		    mDiceClipName = AudioSystem.getClip();
		    mDiceClipName.open(mDiceClipNameAis);
		    
        } catch (Exception e) {
			e.printStackTrace();
        }
    	mButtonClipName.setFramePosition(0);
    	mButtonClipName.start();

        if (mController.isItMyTurn(this)) {
        	if (mController.getState() == GameState.OPTIONAL_MOVES) {
                mController.endMove();
                mMapNode.refreshOutput();
            } else {
            	mDiceClipName.setFramePosition(0);
            	mDiceClipName.start();
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
        if (FxMenuController.ismIsStandardMap()) {
	        mMapNode.setLayoutX(390);
	        mMapNode.setLayoutY(300);
	        mMapNode.setScaleX(1.6);
	        mMapNode.setScaleY(1.6);
	        mMapNode.setScaleZ(0.7);
        } else {
          mMapNode.setLayoutX(390);
          mMapNode.setLayoutY(300);
          mMapNode.setScaleX(0.7);
          mMapNode.setScaleY(0.7);
          mMapNode.setScaleZ(0.7);
        }
        mAnchorPane.getChildren().add(mMapNode);
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
    
    public void showHelp() {
        PrintToConsole.println("*---Welcome to Siedler!---*");
        PrintToConsole.println("");
        PrintToConsole.println("*---How to play:---*");
        PrintToConsole.print("Trading: Press the \"T\" key and the corresponding material key ");
        PrintToConsole.print("from \"1\" to \"5\" on your keyboard for the resource to ");
        PrintToConsole.print ("trade in and then another key from \"1\" to \"5\" to get a corresponding resource. \n");
        PrintToConsole.print("Take a Development Card: Press the \"D\" key on your keyboard \n");
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
        PrintToConsole.println("->Ships cost 1 Wool and 1 Wood in this game and are necessary to cross the water!\n");
    }
    

}
