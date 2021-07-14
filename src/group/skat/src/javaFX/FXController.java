package javaFX;

import controller.GameMove;
import console.Print;
import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.SkatGame;
import engine.SkatPlayer;
import engine.Trump;
import engine.enums.CardColor;
import engine.enums.GameMode;
import engine.enums.GamePhase;
import framework.GameController;
import framework.Player;
import javaFX.enums.FXCardPosition;
import javaFX.enums.GUIState;
import javaFX.enums.FXHandShelfPosition;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.*;

public class FXController implements Player, Initializable {

    /** this is the index of the player inside the Game. If set to -1 the Console will always use the currentPlayer as perspective, making it hotseat*/
    int playerGameIndex = -1;
    boolean suitGame = false;

    private SkatController controller;
    private Scene scene;
    private FXPresenter presenter;

    HashMap<String, FXButton> buttonDict;

    private FXHandShelf midHandShelf;
    private FXHandShelf leftHandShelf;
    private FXHandShelf rightHandShelf;

    private FXSkat fxSkat;
    private FXCurrentTrick fxCurrentTrick;

   /* INITIALIZE */

    /** This is called after initialize and after the initialization of the controller object*/
    private void init(){



        // URL resource = getClass().getResource("/path/to/image.jpg");


        var classLoader = FXController.class.getClassLoader();
        URL resource = classLoader.getResource("images/Views/ViewBackgroundBlank.png");

        if (resource == null) {

            Print.debug("ERROR","This is null!");

        }

        var bgImage = new Image("images/Views/ViewBackgroundBlank.png");

        FXPresenter.setFxController(this);
        FXButton.setFXController(this);

        bindings();
        createButtons();

        FXPresenter.update();
    }

    private void initGameStart(){

        initHandShelfs();
        fxSkat = new FXSkat(this, AnchorSkatCardLeft, AnchorSkatCardRight);
        fxCurrentTrick = new FXCurrentTrick(this, AnchorTrickOne, AnchorTrickTwo, AnchorTrickThree);
    }

    private void initHandShelfs() {

        leftHandShelf = new FXHandShelf(AnchorPlayerhandShelfLeft, this, FXHandShelfPosition.LEFT_PLAYER);
        midHandShelf = new FXHandShelf(AnchorPlayerhandShelfMid, this, FXHandShelfPosition.MID_PLAYER);
        rightHandShelf = new FXHandShelf(AnchorPlayerhandShelfRight, this, FXHandShelfPosition.RIGHT_PLAYER);
    }

    public void createButtons(){

        // work with dictionary of buttons

        buttonDict = new HashMap<String, FXButton>();

        String identifier;

        identifier = "SORT";
        buttonDict.put(identifier, new FXButton(identifier, AnchorButtonSort, new Image("images/Buttons/ButtonSort.png"), new Image("images/Buttons/ButtonSortHighlighted.png"), false));

        Image placeholder = new Image("images/Buttons/Button1GameColor.png");
        Image highlight = new Image("images/Buttons/ButtonSelectedForeground.png");

        identifier = "PA1";
        buttonDict.put(identifier, new FXButton(identifier, AnchorButtonPA1, placeholder, highlight, true));

        identifier = "PA2";
        buttonDict.put(identifier, new FXButton(identifier, AnchorButtonPA2, placeholder, highlight, true));

        identifier = "PA3";
        buttonDict.put(identifier, new FXButton(identifier, AnchorButtonPA3, placeholder, highlight, true));

        identifier = "PA4";
        buttonDict.put(identifier, new FXButton(identifier, AnchorButtonPA4, placeholder, highlight, true));

        identifier = "PA5";
        buttonDict.put(identifier, new FXButton(identifier, AnchorButtonPA5, placeholder, highlight, true));

        identifier = "PLAY";
        buttonDict.put(identifier, new FXButton(identifier, IVButtonPlay, new Image("images/Buttons/ButtonPlay.png"), new Image("images/Buttons/ButtonPlayHighlighted.png")));

        identifier = "NEXT";
        buttonDict.put(identifier, new FXButton(identifier, IVButtonNext, new Image("images/Buttons/ButtonNext.png"), new Image("images/Buttons/ButtonNextHighlighted.png")));

    }

    private SkatGame game(){

       return controller.getGame();
    }

    private GUIState guiState(){ // zu Klasse machen?

        return GUIState.NOT_STARTED;
    }

    public GUIState getState(){

        if (game() == null) {

            return GUIState.NOT_STARTED;
        }

        switch (game().getGamePhase()) {

            case NOT_STARTED -> {

                return GUIState.NOT_STARTED;
            }

            case AUCTION -> {

                if (game().getAuction().getQuestioner().getGameIndex() == getPlayerGameIndex()) {

                    return GUIState.AUCTION_ASKING;
                }
                if (game().getAuction().getHearer().getGameIndex() == getPlayerGameIndex()) {

                    return GUIState.AUCTION_HEARING;
                }

                return GUIState.AUCTION_WATCHING;
            }

            case DECLARING -> {

                if (game().getAuction().getAuctionWinner().getGameIndex() == getPlayerGameIndex()) {

                    if (!game().skatIsDropped()) {

                        return GUIState.DECLARE_SKAT;

                    } else {

                        if (suitGame) {

                            return GUIState.DECLARE_TRUMPCOLOR;

                        } else {

                            return GUIState.DECLARE_TRUMPTYPE;
                        }
                    }
                }
            }

            case PLAYING -> {

                if (game().getCurrentPlayer().getGameIndex() == getPlayerGameIndex()) {

                    return GUIState.PLAYING_YOUR_MOVE;

                } else {

                    return GUIState.PLAYING_NOT_YOUR_MOVE;

                }
            }

            case ENDED -> {

                return GUIState.GAME_FINISHED;

                // TODO : hier weitermachen; if set ended -> GUIState.SetFinished

            }

            case ABORTED -> {

                return GUIState.GAME_ABORTED;
            }
        }

        return GUIState.GAME_FINISHED;
    }


    public int getPlayerGameIndex(){

        if (game() == null) {

            return 0;
        }

        if (playerGameIndex == -1) {

            return game().getCurrentPlayer().getGameIndex();

        } else {

            return playerGameIndex;
        }
    }

    public FXHandShelf[] getFxHandShelfs() {

        return new FXHandShelf[]{leftHandShelf, midHandShelf, rightHandShelf};
    }

    public FXSkat getFxSkat() {

        return fxSkat;
    }

    public FXCurrentTrick getFxCurrentTrick() {

        return fxCurrentTrick;
    }

    /* LAYOUT */

    private void bindings(){


    }


    /* SETTER */

    public void setController(GameController controller) {


        this.controller = (SkatController) controller;
        init();

    }

    public void setScene(Scene scene) {

        this.scene = scene;

        scene.setOnKeyPressed(this::keyboardPressed);
        scene.setOnKeyReleased(this::keyboardReleased);
    }

    /* GETTER */

    // TODO: @andi NOT FINISHED!!!
    public SkatPlayer getPlayer() {

        var curGame = controller.getGame();

        if (curGame == null) {

            return null;
        }

        return curGame.getCurrentPlayer();
    }

    public SkatController getController() {

        return controller;
    }

    /* GETTER */

    /**  Use this Function to set the (Game)Index of the player who is playing in the FXClass if hotseat is NOT played */
    public void setPlayerGameIndex(int playerGameIndex){

        this.playerGameIndex = playerGameIndex;

    }


    /* OVERRIDE */

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /* HELPER */

    Text newText(String label, int size){

        var text = new Text();
        text.setText(label);
        text.setFont(Font.font("Avenir", FontWeight.NORMAL, size));

        return text;

    }

    /* SKATMOVES */

    public boolean makeMove(GameMove move) {

        return controller.makeMove(move);
    }

    /* EVENT ABSTRACTIONS */


    /* FX EVENTS */


    /** This should be an FXButton */
    public void showHidedebugView(MouseEvent mouseEvent) {

       if( anchor_DebugView.isVisible() ){
           anchor_DebugView.setVisible(false);
           label_ShowHideDebugView.setText("⌗");
       } else {
           anchor_DebugView.setVisible(true);
           var text = new StringBuilder();
           text.append("ROOT :      height: ").append(String.format("%.0f", anchor_root.getHeight())).append("     width: ").append(String.format("%.0f", anchor_root.getWidth())).append("\n");
           text.append("WINDOW :    height: ").append(String.format("%.0f", scene.getHeight())).append("     width: ").append(String.format("%.0f", scene.getWidth()));
           label_WindowSize.setText(text.toString());
           label_ShowHideDebugView.setText("⤫");
       }

    }

    public void buttonClicked(String identifier){

        switch (identifier){

            case "SORT" -> {

                if (makeMove(new SkatMove(ActionType.SORT))) {

                    midHandShelf.deselectAll();
                    midHandShelf.setSelectedCardIndex(-1);

                    FXPresenter.updateHandShelfs();
                    fxSkat.update();
                }
            }

            case "PA1", "PA2", "PA3", "PA4", "PA5" -> {

                PAButtonClicked(identifier);

            }

            case "PLAY" -> {

                if (makeMove(new SkatMove(ActionType.NEW_GAME))){

                    initGameStart();
                    FXPresenter.update();
                }
            }

            case "NEXT" -> {

            }

            default -> {

                Print.debug("WARNING", "(FXController.buttonClicked) There is no handling for Button " + identifier);

            }

        }

        FXPresenter.update();
    }

    private void PAButtonClicked(String identifier){

        switch (getState()){

            case AUCTION_ASKING, AUCTION_HEARING -> {

                if (identifier.equals("PA2")) {

                    makeMove(new SkatMove(ActionType.RAISE_OR_ACCEPT));
                }

                if (identifier.equals("PA4")) {

                    makeMove(new SkatMove(ActionType.PASS));
                }

            }

            case DECLARE_SKAT -> {

                if (identifier.equals("PA3")) {

                    makeMove(new SkatMove(ActionType.DROP_SKAT));
                }

            }

            case DECLARE_TRUMPTYPE -> {

                if (identifier.equals("PA2")) {

                    suitGame = true;
                }

                if (identifier.equals("PA3")) {

                    makeMove(new SkatMove(new Trump(GameMode.GRAND)));
                }

                if (identifier.equals("PA4")) {

                    makeMove(new SkatMove(new Trump(GameMode.NULL)));
                }

            }

            case DECLARE_TRUMPCOLOR -> {

                makeMove( switch ( identifier ) {

                    case "PA1" -> new SkatMove(new Trump(CardColor.CLUBS));

                    case "PA2" -> new SkatMove(new Trump(CardColor.SPADES));

                    case "PA4" -> new SkatMove(new Trump(CardColor.HEARTS));

                    case "PA5" -> new SkatMove(new Trump(CardColor.DIAMONDS));

                    default -> null;

                });

            }

        }


    }



    public void fxCardClicked(FXCardPosition pos, int index) {

        var gamePhase = controller.getGame().getGamePhase();

        var skatSelectedIndex = fxSkat.getSelectedCardIndex();
        var shelfSelectedCardIndex = midHandShelf.getSelectedCardIndex();

        if (pos == FXCardPosition.HANDSHELF_MID && index != -1 && midHandShelf.getFXCardAt(index).getCard() == null) {

            return;
        }

        if (gamePhase == GamePhase.DECLARING) {

            possibleSkatHandMove(pos, skatSelectedIndex, shelfSelectedCardIndex, index);

        } else if (gamePhase == GamePhase.PLAYING && pos == FXCardPosition.TRICK) {

            possibleTrickMove(shelfSelectedCardIndex);

        } else if (gamePhase == GamePhase.AUCTION || gamePhase == GamePhase.PLAYING) {

            if (pos == FXCardPosition.HANDSHELF_MID) {

                midHandShelf.cardClickedAt(index);
            }
        }
    }

    private void possibleSkatHandMove(FXCardPosition pos, int skatSelectedIndex, int shelfSelectedCardIndex, int index) {

        if (pos == FXCardPosition.HANDSHELF_MID) {

            if (skatSelectedIndex != -1) {

                var move = new SkatMove(ActionType.ON_SKATHAND, 10 + skatSelectedIndex, index);

                if (makeMove(move)) {

                    fxSkat.getFXCardAt(skatSelectedIndex).setSelected(false);
                    fxSkat.setSelectedCardIndex(-1);

                    midHandShelf.deselectAll();
                    midHandShelf.getFXCardAt(index).setSelected(true);
                    midHandShelf.setSelectedCardIndex(-1);
                }

            } else {

                midHandShelf.cardClickedAt(index);

            }

        } else if (pos == FXCardPosition.SKAT) {

            if (shelfSelectedCardIndex != -1) {

                var move = new SkatMove(ActionType.ON_SKATHAND, shelfSelectedCardIndex, 10 + index);

                if (makeMove(move)) {

                    fxSkat.getFXCardAt(index).setSelected(false);
                    fxSkat.setSelectedCardIndex(-1);

                    midHandShelf.getFXCardAt(shelfSelectedCardIndex).setSelected(false);
                    midHandShelf.setSelectedCardIndex(-1);
                }

            } else {

                fxSkat.cardClickedAt(index);
            }
        }
        fxSkat.update();
        midHandShelf.update();
    }

    private void possibleTrickMove(int shelfSelectedCardIndex) {

        if (shelfSelectedCardIndex != -1) {

            for (Timeline tl : fxCurrentTrick.getTimelines()) {

                if (tl != null) {

                    tl.stop();
                }
            }

            var move = new SkatMove(shelfSelectedCardIndex);

            if (makeMove(move)) {

                midHandShelf.getFXCardAt(shelfSelectedCardIndex).setSelected(false);
                midHandShelf.setSelectedCardIndex(-1);
            }
        }
        FXPresenter.updateHandShelfs();
        fxCurrentTrick.update();
    }




    public void keyboardPressed(KeyEvent e) {

        var key = e.getCode();

        Print.debug("maik", "Key pressed: " + key);

        switch (key) {

            case LEFT -> keyLeftClicked();
            case RIGHT -> keyRightClicked();
        //    case UP -> keyUpClicked();
        //    case DOWN -> keyDownClicked();
            case SPACE -> keySpaceClicked();
            case ENTER -> keyEnterClicked();
            case BACK_SLASH -> keyHashTagClicked();
            case S -> keySClicked();
            default -> Print.debug("maik", "Not a valid keyEvent: " + key);
        }
    }

    public void keyboardReleased(KeyEvent e) {

        var key = e.getCode();

        Print.debug("maik", "Key released: " + key);

        switch (key) {

            case SPACE -> keySpaceReleased();
        }
    }

    private void keyLeftClicked() {

        var size = getPlayer().getHand().getSize();
        var selIndex = midHandShelf.getSelectedCardIndex();

        if (selIndex == -1) {

            fxCardClicked(FXCardPosition.HANDSHELF_MID, 0);

        } else if (selIndex > 0) {

            midHandShelf.deselectAll();
            midHandShelf.setSelectedCardIndex(-1);
            fxCardClicked(FXCardPosition.HANDSHELF_MID, selIndex - 1);
        }
    }

    private void keyRightClicked() {

        var gamePhase = getController().getGame().getGamePhase();

        var size = getPlayer().getHand().getSize();
        var selIndex = midHandShelf.getSelectedCardIndex();

        if (gamePhase == GamePhase.DECLARING) {

            size = 10;
        }

        if (selIndex == -1) {

            fxCardClicked(FXCardPosition.HANDSHELF_MID, size - 1);

        } else if (selIndex < size - 1) {

            midHandShelf.deselectAll();
            midHandShelf.setSelectedCardIndex(-1);
            fxCardClicked(FXCardPosition.HANDSHELF_MID, selIndex + 1);
        }
    }

    private void keyUpClicked() {

        var selIndex = fxSkat.getSelectedCardIndex();
        var midHandSelIndex = midHandShelf.getSelectedCardIndex();

        if (selIndex == -1) {

            midHandShelf.deselectAll();
            midHandShelf.setSelectedCardIndex(-1);

            if (midHandSelIndex < 5) {

                fxCardClicked(FXCardPosition.SKAT, 0);

            } else {

                fxCardClicked(FXCardPosition.SKAT, 1);
            }
        }
    }

    private void keyDownClicked() {


    }

    private void keySpaceClicked() {

        var selIndex = midHandShelf.getSelectedCardIndex();
        var selCard = midHandShelf.getFXCardAt(selIndex).getCard();

        if (selIndex != -1) {

            var preview = new FXCard(AnchorPreview, FXCardPosition.PREVIEW, 0, this);

            if (!preview.isEqualTo(selCard)) {

                preview.changeCard(selCard);
            }

            AnchorPreview.setVisible(true);
        }
    }

    private void keySpaceReleased() {

        AnchorPreview.setVisible(false);
    }

    private void keyEnterClicked() {

        var guiState = getState();
        GamePhase gamePhase;

        if (guiState == GUIState.NOT_STARTED) {

            gamePhase = GamePhase.NOT_STARTED;

        } else {

            gamePhase = getController().getGame().getGamePhase();
        }


        if (guiState == GUIState.NOT_STARTED) {

            buttonClicked("PLAY");

        } else if (gamePhase == GamePhase.AUCTION) {

            buttonClicked("PA2");

        } else if (gamePhase == GamePhase.DECLARING) {

            buttonClicked("PA3");

        } else if (gamePhase == GamePhase.PLAYING) {

            var selIndex = midHandShelf.getSelectedCardIndex();

            fxCardClicked(FXCardPosition.TRICK, selIndex);
        }
    }

    private void keyHashTagClicked() {

        var gamePhase = getController().getGame().getGamePhase();

        if (gamePhase == GamePhase.AUCTION) {

            buttonClicked("PA4");
        }
    }

    private void keySClicked() {

        buttonClicked("SORT");
    }

    /* OUTLETS */



    public AnchorPane anchor_DebugView;
    public AnchorPane AnchorGameMessage;
    public AnchorPane AnchorAuctionDialog;
    public AnchorPane anchor_root;
    public AnchorPane AnchorPlayerhandShelfMid;
    public AnchorPane AnchorPlayerhandShelfLeft;
    public AnchorPane AnchorPlayerhandShelfRight;
    public AnchorPane AnchorCardEx;
    public AnchorPane AnchorTrickOne;
    public AnchorPane AnchorTrickTwo;
    public AnchorPane AnchorTrickThree;
    public AnchorPane AnchorViewSkat;
    public AnchorPane AnchorSkatCardLeft;
    public AnchorPane AnchorSkatCardRight;
    public AnchorPane anchorButtonsPlayActions;
    public AnchorPane AnchorButtonPA1;
    public AnchorPane AnchorButtonPA2;
    public AnchorPane AnchorButtonPA3;
    public AnchorPane AnchorButtonPA4;
    public AnchorPane AnchorButtonPA5;
    public AnchorPane AnchorButtonSort;
    public AnchorPane AnchorWelcomeResultNewGameView;

    public Label label_ShowHideDebugView;
    public Label label_WindowSize;
    public Label LabelGameMessage;
    public Label LabelAuctionViewText;
    public Label LabelAuctionViewTitle;
    public Label LabelResultNewGame1;
    public Label LabelResultNewGame2;
    public Label LabelResultNewGame3;
    public Label LabelResultNewGame4;
    public Label LabelResultNewGame5;
    public Label LabelGameNo;

    public ImageView ImageViewWRNBackground;
    public ImageView IVButtonPlay;
    public ImageView IVButtonNext;
    public ImageView ImageViewBackground;
    public ImageView BackgroundCard;
    public ImageView CardForeground;
    public ImageView CardForeground1;

    public AnchorPane AnchorPreview = new AnchorPane();

    public void AnchorButtonSortClicked(MouseEvent mouseEvent) {
    }

    public void AnchorButtonPA5Clicked(MouseEvent mouseEvent) {
    }

    public void AnchorButtonPA4Clicked(MouseEvent mouseEvent) {
    }

    public void AnchorButtonPA3Clicked(MouseEvent mouseEvent) {
    }

    public void AnchorButtonPA2Clicked(MouseEvent mouseEvent) {
    }

    public void AnchorButtonPA1Clicked(MouseEvent mouseEvent) {
    }

    public void thisIsTest(MouseEvent mouseEvent) {

        Print.debug("WARNING", "This is it");

    }
}
