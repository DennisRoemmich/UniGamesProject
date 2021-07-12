package javaFX;

import controller.GameMove;
import console.Print;
import controller.SkatController;
import controller.SkatMove;
import controller.enums.ActionType;
import engine.SkatGame;
import engine.SkatPlayer;
import engine.enums.GamePhase;
import framework.GameController;
import framework.Player;
import framework.Presenter;
import javaFX.enums.FXCardPosition;
import javaFX.enums.GUIState;
import javaFX.enums.FXHandShelfPosition;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        FXButton.setFXController(this);
        FXPresenter.setFxController(this);

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
        buttonDict.put(identifier, new FXButton(identifier, AnchorButtonSort, new Image("./images/Buttons/ButtonSort.png"), new Image("./images/Buttons/ButtonSortHighlighted.png"), false));

        Image placeholder = new Image("./images/Buttons/Button1GameColor.png");
        Image highlight = new Image("./images/Buttons/ButtonSelectedForeground.png");

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
        buttonDict.put(identifier, new FXButton(identifier, IVButtonPlay, new Image("./images/Buttons/ButtonPlay.png"), new Image("./images/Buttons/ButtonPlayHighlighted.png")));

        identifier = "NEXT";
        buttonDict.put(identifier, new FXButton(identifier, IVButtonNext, new Image("./images/Buttons/ButtonNext.png"), new Image("./images/Buttons/ButtonNextHighlighted.png")));


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
                if ( game().getAuction().getQuestioner().getGameIndex() == getPlayerGameIndex()) {
                    return GUIState.AUCTION_ASKING;
                }
                if ( game().getAuction().getHearer().getGameIndex() == getPlayerGameIndex()) {
                    return GUIState.AUCTION_HEARING;
                }

                return GUIState.AUCTION_WATCHING;

            }
            case DECLARING -> {

                if (game().getAuction().getAuctionWinner().getGameIndex() == getPlayerGameIndex()){

                    if (!game().skatIsDropped()){
                        return GUIState.DECLARE_SKAT;
                    } else {
                        if(suitGame){
                            return GUIState.DECLARE_TRUMPCOLOR;
                        } else {
                            return GUIState.DECLARE_TRUMPTYPE;
                        }
                    }

                }

            }
            case PLAYING -> {

                if(game().getCurrentPlayer().getGameIndex() == getPlayerGameIndex()){
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


    private int getPlayerGameIndex(){

        if( game() == null ){
            return 0;
        }

        if(playerGameIndex == -1){
            return game().getCurrentPlayer().getGameIndex();
        } else {
            return playerGameIndex;
        }

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

    }

    /* GETTER */

    // TODO: @andi NOT FINISHED!!!
    public SkatPlayer getPlayer() {

        var curGame = controller.getGame();

        if ( controller.getGame() == null ){
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



            }

            case "PA1", "PA2", "PA3", "PA4", "PA5" -> {

                PAButtonClicked(identifier);

            }

            case "PLAY" -> {

                if (makeMove(new SkatMove(ActionType.NEW_GAME))){

                    initGameStart();

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




    }



    public void fxCardClicked(FXCardPosition pos, int index) {

        if (controller.getGame().getGamePhase() == GamePhase.DECLARING) {

            var skatSelectedIndex = fxSkat.getSelectedCardIndex();
            var shelfSelectedCardIndex = midHandShelf.getSelectedCardIndex();

            if (pos == FXCardPosition.HANDSHELF_MID) {

                if (skatSelectedIndex != -1) {

                    var move = new SkatMove(ActionType.ON_SKATHAND, 10 + skatSelectedIndex, index);

                    if (makeMove(move)) {

                        midHandShelf.setSelectedCardIndex(index);
                    }
                }
                midHandShelf.cardClickedAt(index);

            } else if (pos == FXCardPosition.SKAT) {

                if (shelfSelectedCardIndex != -1) {

                    var move = new SkatMove(ActionType.ON_SKATHAND, shelfSelectedCardIndex, 10 + index);

                    if (makeMove(move)) {

                        fxSkat.setSelectedCardIndex(index);
                    }
                }
                fxSkat.cardClickedAt(index);
            }
        } else if (controller.getGame().getGamePhase() == GamePhase.PLAYING && pos == FXCardPosition.TRICK) {


        }
    }

    /* OUTLETS */

    public AnchorPane anchor_root;
    public AnchorPane anchor_DebugView;
    public Label label_ShowHideDebugView;
    public Label label_WindowSize;
    public ImageView ImageViewBackground;
    public AnchorPane AnchorPlayerhandShelfMid;
    public AnchorPane AnchorPlayerhandShelfLeft;
    public AnchorPane AnchorPlayerhandShelfRight;
    public AnchorPane AnchorCardEx;
    public ImageView BackgroundCard;
    public ImageView CardForeground;
    public ImageView CardForeground1;
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
    public ImageView ImageViewWRNBackground;
    public Label LabelResultNewGame1;
    public Label LabelResultNewGame2;
    public Label LabelResultNewGame3;
    public Label LabelResultNewGame4;
    public Label LabelResultNewGame5;
    public Label LabelGameNo;
    public ImageView IVButtonPlay;
    public ImageView IVButtonNext;

    public AnchorPane AnchorTrickOne = new AnchorPane();
    public AnchorPane AnchorTrickTwo = new AnchorPane();
    public AnchorPane AnchorTrickThree = new AnchorPane();

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
}
