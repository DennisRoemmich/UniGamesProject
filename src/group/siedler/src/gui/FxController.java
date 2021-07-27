package gui;

import cards.CardType;
import controller.Controller;
import dice.DiceRolling;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import materials.MaterialType;
import java.util.ResourceBundle;

/**
 * Initialization of all the FXML data.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class FxController implements Initializable {

    protected Controller mController = new Controller();
    protected MapNode mMapNode = new MapNode(mController);


    @FXML
    protected AnchorPane mAnchorPane;
    @FXML
    protected ImageView mBackground; 
    @FXML
    protected ImageView mTradeWithBankButton;
    @FXML
    protected ImageView mDiceButton;
    @FXML
    protected ImageView mTradeButton;
    @FXML
    protected ImageView mDice1;
    @FXML
    protected ImageView mDice2;
    @FXML
    protected ImageView mPlayerMaterials;
    @FXML
    protected Label mWoodLabel;
    @FXML
    protected Label mWheatLabel; 
    @FXML
    protected Label mWoolLabel;
    @FXML
    protected Label mClayLabel; 
    @FXML
    protected Label mOreLabel;
    @FXML
    protected Label mWoodAmount;
    @FXML
    protected Label mWheatAmount; 
    @FXML
    protected Label mWoolAmount;
    @FXML
    protected Label mClayAmount;
    @FXML
    protected Label mOreAmount;
    @FXML
    protected Label mCurrentPlayer;
    @FXML
    protected Label mWinner;
    @FXML
    protected Label mTradeError;
    @FXML
    protected Label mPoints;
    @FXML
    protected Label mVictoryPointCard;
    @FXML
    protected Label mKnightCard;
    @FXML
    protected Label mRoadCard;
    @FXML
    protected Label mInventionCard;
    @FXML
    protected Label mMonopolyCard;
    @FXML
    protected Label mVictoryAmount;
    @FXML
    protected Label mKnightAmount;
    @FXML
    protected Label mRoadAmount;
    @FXML
    protected Label mInventionAmount;
    @FXML
    protected Label mMonopolyAmount;
    @FXML
    protected Label mBurglarMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mTradeButton = new ImageView();
    }

    public void finishRoll() {
        mController.handleRoll();
        refreshOutput();
    }

    public void refreshOutput() {
        mMapNode.setMap(mController.getMap());
        mMapNode.refreshOutput();
        refreshResources();
        refreshStatusMessage();
        refreshWinner();
        refreshPoints();
        refreshCardLabels();
        refreshDiceViews();
        refreshDynamicUiElements();
    }

    public void refreshResources() {
        setPlayerMaterialsImage(mController.getCurrentPlayerColor().name(), mPlayerMaterials);
        mCurrentPlayer.setText(mController.getCurrentPlayerColor().name() + "'s turn");
        mWoodAmount.setText(String.valueOf(mController.getCurrentPlayerHand().getAmount(MaterialType.WOOD)));
        mWheatAmount.setText(String.valueOf(mController.getCurrentPlayerHand().getAmount(MaterialType.WHEAT)));
        mWoolAmount.setText(String.valueOf(mController.getCurrentPlayerHand().getAmount(MaterialType.WOOL)));
        mOreAmount.setText(String.valueOf(mController.getCurrentPlayerHand().getAmount(MaterialType.ORE)));
        mClayAmount.setText(String.valueOf(mController.getCurrentPlayerHand().getAmount(MaterialType.CLAY)));
    }


    public void refreshWinner() {
        if (mController.isGameHasWinner()) {

            mWinner.setText("Player " + mController.getWinColor() + " has won!");
        }
    }

    public void refreshPoints() {
        mPoints.setText("Victory Points:    " + mController.getWinPoints());
    }

    public void refreshCardLabels() {
        mKnightAmount.setText(String.valueOf(mController.getCurrentPlayerCards().getAmount(CardType.KNIGHT)));
        mVictoryAmount.setText(String.valueOf(mController.getCurrentPlayerCards().getAmount(CardType.VICTORY)));
        mRoadAmount.setText(String.valueOf(mController.getCurrentPlayerCards().getAmount(CardType.ROAD)));
        mInventionAmount.setText(String.valueOf(mController.getCurrentPlayerCards().getAmount(CardType.INVENTION)));
        mMonopolyAmount.setText(String.valueOf(mController.getCurrentPlayerCards().getAmount(CardType.MONOPOLY)));
    }
    
    public void refreshStatusMessage() {
      
        if (mController.getCurrentPlayerHand().isTradeImpossible()) {
        	mTradeError.setText("Trade not possible!");
        	mController.getCurrentPlayerHand().setTradePossible();
        	
        } else {
        	mTradeError.setText("");
        }
    }

    public void refreshDiceViews() {
        setDiceViews(DiceRolling.getDice1(), DiceRolling.getDice2());
    }

    
    protected void refreshDynamicUiElements() {
    	// Must be implemented in subclass
    }

    // Setter

    public void setDiceImage(int rolledNumber, ImageView dice) {
        String diceImage = "resources/Dice" + rolledNumber + ".png";
        dice.setImage(new Image(getClass().getClassLoader().getResourceAsStream(diceImage)));
    }

    public void setDiceViews(int diceNumber1, int diceNumber2) {
        setDiceImage(diceNumber1, mDice1);
        setDiceImage(diceNumber2, mDice2);
    }

    public void setPlayerMaterialsImage(String colorName, ImageView playerMaterials) {

        String playerMaterialsImage = "resources/Player" + colorName + "Materials.png";

        ClassLoader loader = getClass().getClassLoader();

        try {
            playerMaterials.setImage(new Image(loader.getResourceAsStream(playerMaterialsImage)));
        } catch (Exception e) {
            playerMaterialsImage = "resources/Player" + "BLUE" + "Materials.png";
            playerMaterials.setImage(new Image(loader.getResourceAsStream(playerMaterialsImage)));
        }
    }
}
