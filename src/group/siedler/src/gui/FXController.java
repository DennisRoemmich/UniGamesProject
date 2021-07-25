package gui;

import buildings.BuildingType;
import cards.CardType;
import dice.DiceRolling;
import helper.QuickJSon;
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
import map.MapGenerator;
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

public class FXController implements Initializable {

    protected Controller controller = new Controller();
    protected MapNode mapNode = new MapNode(controller);

    protected boolean tradeFlag = true;
    protected MaterialType sellType = MaterialType.ORE;
    protected MaterialType chosen;

    protected Stage stage;
    protected Scene scene;
    protected Parent root;

    @FXML
    protected AnchorPane back;
    @FXML
    protected ImageView background, tradeWithBankButton, diceButton, tradeButton, dice1, dice2, playerMaterials;
    @FXML
    protected Label woodLabel, wheatLabel, woolLabel, clayLabel, oreLabel;
    @FXML
    protected Label woodAmount, wheatAmount, woolAmount, clayAmount, oreAmount;
    @FXML
    protected Label currentPlayer, winner, tradeError, points, consoleInfo;
    @FXML
    protected Label victoryPointCard, knightCard, roadCard, inventionCard, monopolyCard;
    @FXML
    protected Label victoryAmount, knightAmount, roadAmount, inventionAmount, monopolyAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tradeButton = new ImageView();
    }

    public void finishRoll() {
        controller.handleRoll();
        refreshOutput();
    }

    public void refreshOutput() {
        mapNode.setMap(controller.getMap());
        mapNode.refreshOutput();
        refreshResources();
        refreshWinner();
        refreshPoints();
        refreshCardLabels();
        refreshDiceViews();
        refreshDynamicUiElements();
    }

    public void refreshResources() {
        setPlayerMaterialsImage(controller.getCurrentPlayerColor().name(), playerMaterials);
        currentPlayer.setText(controller.getCurrentPlayerColor().name() + "'s turn");
        woodAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOD)));
        wheatAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WHEAT)));
        woolAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOL)));
        oreAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.ORE)));
        clayAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.CLAY)));
    }


    public void refreshWinner() {
        if (controller.isGameHasWinner()) {

            winner.setText("Player " + controller.getWinningColor() + " has won!");
        }
    }

    public void refreshPoints() {
        points.setText("Victory Points:        " + controller.getWinPoints());
    }

    public void refreshCardLabels() {
        knightAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.KNIGHT)));
        victoryAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.VICTORY)));
        roadAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.ROAD)));
        inventionAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.INVENTION)));
        monopolyAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.MONOPOLY)));
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
        setDiceImage(diceNumber1, dice1);
        setDiceImage(diceNumber2, dice2);
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
