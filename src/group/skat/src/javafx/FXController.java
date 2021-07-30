package javafx;

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
import javafx.enums.FXCardPosition;
import javafx.enums.GUIState;
import javafx.enums.FXHandShelfPosition;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.*;

/**
 * central class for GUI
 */
public class FXController implements Player, Initializable {

    public Label LabelPlayerActive3;
    public Label LabelPlayerActive2;
    public Label LabelPlayerActive1;
    /** this is the index of the player inside the Game. If set to -1 the Console will always use the currentPlayer as perspective, making it hotseat*/
    int playerGameIndex = -1;

    private boolean mSuitGame = false;
    private boolean mKeyPositionSkat = false;
    private boolean mNextClicked = false;

    private SkatController mController;
    private Scene mScene;

    HashMap<String, FXButton> mButtonDict;

    private FXHandShelf mMidHandShelf;
    private FXHandShelf mLeftHandShelf;
    private FXHandShelf mRightHandShelf;

    private FXSkat mFxSkat;
    private FXCurrentTrick mFxCurrentTrick;

    private int skatSetPlayerIndex;

   /* INITIALIZE */

    /** This is called after initialize and after the initialization of the controller object*/
    private void init() {


        var classLoader = FXController.class.getClassLoader();
        URL resource = classLoader.getResource("images/Views/ViewBackgroundBlank.png");

        if (resource == null) {

            Print.debug("ERROR","This is null!");

        }

        FXPresenter.setFxController(this);
        FXButton.setFXController(this);

        createButtons();

        FXPresenter.update();
    }

    private void initGameStart() {

        initHandShelfs();
        mFxSkat = new FXSkat(this, AnchorSkatCardLeft, AnchorSkatCardRight);
        mFxCurrentTrick = new FXCurrentTrick(this, AnchorTrickOne, AnchorTrickTwo, AnchorTrickThree);
    }

    private void initHandShelfs() {

        mLeftHandShelf = new FXHandShelf(AnchorPlayerhandShelfLeft, this, FXHandShelfPosition.LEFT_PLAYER);
        mMidHandShelf = new FXHandShelf(AnchorPlayerhandShelfMid, this, FXHandShelfPosition.MID_PLAYER);
        mRightHandShelf = new FXHandShelf(AnchorPlayerhandShelfRight, this, FXHandShelfPosition.RIGHT_PLAYER);
    }

    public void createButtons() {

        // work with dictionary of buttons

        mButtonDict = new HashMap<String, FXButton>();

        String identifier;

        identifier = "SORT";
        mButtonDict.put(identifier, new FXButton(identifier, AnchorButtonSort, new Image("images/Buttons/ButtonSort.png"), new Image("images/Buttons/ButtonSortHighlighted.png"), false));

        var placeholder = new Image("images/Buttons/ButtonGameColor1.png");

        identifier = "PA1";
        mButtonDict.put(identifier, new FXButton(identifier, AnchorButtonPA1, placeholder, placeholder, false));

        identifier = "PA2";
        mButtonDict.put(identifier, new FXButton(identifier, AnchorButtonPA2, placeholder, placeholder, false));

        identifier = "PA3";
        mButtonDict.put(identifier, new FXButton(identifier, AnchorButtonPA3, placeholder, placeholder, false));

        identifier = "PA4";
        mButtonDict.put(identifier, new FXButton(identifier, AnchorButtonPA4, placeholder, placeholder, false));

        identifier = "PA5";
        mButtonDict.put(identifier, new FXButton(identifier, AnchorButtonPA5, placeholder, placeholder, false));

        identifier = "PLAY";
        mButtonDict.put(identifier, new FXButton(identifier, IVButtonPlay, new Image("images/Buttons/ButtonPlay.png"), new Image("images/Buttons/ButtonPlayHighlighted.png")));

        identifier = "NEXT";
        mButtonDict.put(identifier, new FXButton(identifier, IVButtonNext, new Image("images/Buttons/ButtonNext.png"), new Image("images/Buttons/ButtonNextHighlighted.png")));

    }

    public SkatGame game() {

       return mController.getGame();
    }

    /**
     * @return state of GUI
     */
    public GUIState getState() {

        if (game() == null) {

            return GUIState.NOT_STARTED;
        }

        switch (game().getGamePhase()) {

            case NOT_STARTED:

                return GUIState.NOT_STARTED;

            case AUCTION:

                return getStateHelp(GamePhase.AUCTION);

            case DECLARING:

                return getStateHelp(GamePhase.DECLARING);

            case PLAYING:

                if (game().getCurrentPlayer().getGameIndex() == getPlayerGameIndex()) {

                    return GUIState.PLAYING_YOUR_MOVE;

                } else {

                    return GUIState.PLAYING_NOT_YOUR_MOVE;

                }

            case ENDED:

                if (mNextClicked) {

                    return GUIState.NOT_STARTED;

                } else {

                    return GUIState.GAME_FINISHED;

                }

            case ABORTED:

                return GUIState.GAME_ABORTED;
        }

        return GUIState.GAME_FINISHED;
    }

    private GUIState getStateHelp(GamePhase phase) {

        if (phase == GamePhase.ABORTED) {

            return GUIState.GAME_ABORTED;
        }

        if (phase == GamePhase.AUCTION) {

            if (game().getAuction().getQuestioner().getGameIndex() == getPlayerGameIndex()) {

                return GUIState.AUCTION_ASKING;
            }
            if (game().getAuction().getHearer() != null && game().getAuction().getHearer().getGameIndex() == getPlayerGameIndex()) {

                return GUIState.AUCTION_HEARING;
            }

        } else {

            if (game().getAuction().getAuctionWinner().getGameIndex() == getPlayerGameIndex()) {

                if (!game().skatIsDropped()) {

                    return GUIState.DECLARE_SKAT;

                } else if (mSuitGame) {

                    return GUIState.DECLARE_TRUMPCOLOR;

                } else {

                    return GUIState.DECLARE_TRUMPTYPE;

                }
            }
        }
        return GUIState.AUCTION_WATCHING;
    }

    /**
     * @return index of currentplayer
     */
    public int getPlayerGameIndex() {

        if (game() == null) {

            return 0;
        }

        if (skatSetPlayerIndex == -1) {

            return game().getCurrentPlayer().getGameIndex();

        } else {

            return skatSetPlayerIndex;

        }

    }

    public FXHandShelf[] getFxHandShelfs() {

        return new FXHandShelf[]{mLeftHandShelf, mMidHandShelf, mRightHandShelf};
    }

    public FXSkat getFxSkat() {

        return mFxSkat;
    }

    public FXCurrentTrick getFxCurrentTrick() {

        return mFxCurrentTrick;
    }

    /* SETTER */

    public void setController(GameController controller) {


        this.mController = (SkatController) controller;
        init();

    }

    public void setScene(Scene scene) {

        this.mScene = scene;

        scene.setOnKeyPressed(this::keyboardPressed);
        scene.setOnKeyReleased(this::keyboardReleased);
    }

    public void setKeyPositionSkat(boolean b) {

        mKeyPositionSkat = b;
    }

    /* GETTER */

    /**
     * @return current player
     */
    public SkatPlayer getPlayer() {

        var curGame = mController.getGame();

        if (curGame == null) {

            return null;
        }

        return curGame.getCurrentPlayer();
    }

    public SkatController getController() {

        return mController;
    }

    /* GETTER */

    /**  Use this Function to set the (Game)Index of the player who is playing in the FXClass if hotseat is NOT played */
    public void setPlayerGameIndex(int playerGameIndex) {

        this.playerGameIndex = playerGameIndex;

    }

    public boolean hasMove() {

        return hasMove;
    }

    /* OVERRIDE */

    private boolean hasMove = false;

    @Override
    public JSONObject requestMove(JSONObject inputType) {

        hasMove = inputType.get("YOURMOVE").equals("TRUE");
        FXPresenter.update();

        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
         * this function is not needed to be overwritten in this game
         */
    }

    /* HELPER */


    Text newText(String label, int size) {

        var text = new Text();
        text.setText(label);
        text.setFont(Font.font("Avenir", FontWeight.NORMAL, size));

        return text;

    }

    /* SKATMOVES */

    /**
     * executes move
     * @param move move
     * @return true if move was executed, false if not
     */
    public boolean makeMove(GameMove move) {

        if (move == null) {

            return false;
        }

        if (hasMove) {

            return mController.makeMove(move);

        } else {

            Print.debug("INFO", "You can't make a move - it's not your turn.");
            return false;
        }

    }

    /* EVENT ABSTRACTIONS */


    /* FX EVENTS */


    /** This should be an FXButton */
    public void showHidedebugView() {

       if ( anchor_DebugView.isVisible() ) {
           anchor_DebugView.setVisible(false);
           label_ShowHideDebugView.setText("⌗");
       } else {
           anchor_DebugView.setVisible(true);
           var text = new StringBuilder();
           text.append("ROOT :      height: ").append(String.format("%.0f", anchor_root.getHeight())).append("     width: ").append(String.format("%.0f", anchor_root.getWidth())).append("\n");
           text.append("WINDOW :    height: ").append(String.format("%.0f", mScene.getHeight())).append("     width: ").append(String.format("%.0f", mScene.getWidth()));
           label_WindowSize.setText(text.toString());
           label_ShowHideDebugView.setText("⤫");
       }

    }

    /**
     * handels button-click-events
     * @param identifier button
     */
    public void buttonClicked(String identifier) {

        switch (identifier) {

            case "SORT":

                if (makeMove(new SkatMove(ActionType.SORT))) {

                    mMidHandShelf.deselectAll();
                    mMidHandShelf.setSelectedCardIndex(-1);

                    FXPresenter.updateHandShelfs();
                    mFxSkat.update();
                }
                break;

            case "PA1", "PA2", "PA3", "PA4", "PA5":

                pAButtonClicked(identifier);

                break;

            case "PLAY":

                if (makeMove(new SkatMove(ActionType.NEW_GAME))) {

                    initGameStart();
                    FXPresenter.update();
                }

                mNextClicked = false;

                break;

            case "NEXT":

                mNextClicked = true;

                break;

            default:

                Print.debug("WARNING", "(FXController.buttonClicked) There is no handling for Button " + identifier);

                break;

        }

        FXPresenter.update();
    }

    /**
     * helps to handle button-click-events
     * @param identifier button
     */
    private void pAButtonClicked(String identifier) {

        switch (getState()) {

            case AUCTION_ASKING, AUCTION_HEARING:

                if (identifier.equals("PA2")) {

                    makeMove(new SkatMove(ActionType.RAISE_OR_ACCEPT));
                }

                if (identifier.equals("PA4")) {

                    makeMove(new SkatMove(ActionType.PASS));
                }

                break;

            case DECLARE_SKAT:

                if (identifier.equals("PA3")) {

                    makeMove(new SkatMove(ActionType.DROP_SKAT));
                }

                break;

            case DECLARE_TRUMPTYPE:

                if (identifier.equals("PA2")) {

                    mSuitGame = true;
                }

                if (identifier.equals("PA3")) {

                    makeMove(new SkatMove(new Trump(GameMode.GRAND)));
                }

                if (identifier.equals("PA4")) {

                    makeMove(new SkatMove(new Trump(GameMode.NULL)));
                }

                break;

            case DECLARE_TRUMPCOLOR:

                makeMove( switch ( identifier ) {

                    case "PA1" -> new SkatMove(new Trump(CardColor.CLUBS));

                    case "PA2" -> new SkatMove(new Trump(CardColor.SPADES));

                    case "PA4" -> new SkatMove(new Trump(CardColor.HEARTS));

                    case "PA5" -> new SkatMove(new Trump(CardColor.DIAMONDS));

                    default -> null;

                });

            break;

            default: break;
        }


    }

    /**
     * handles fxCard-click-events
     * @param pos position of clicked fxCard
     * @param index index of clicked card
     */
    public void fxCardClicked(FXCardPosition pos, int index) {

        var gamePhase = mController.getGame().getGamePhase();

        var skatSelectedIndex = mFxSkat.getSelectedCardIndex();
        var shelfSelectedCardIndex = mMidHandShelf.getSelectedCardIndex();

        if (pos == FXCardPosition.HANDSHELF_MID && index != -1 && mMidHandShelf.getFXCardAt(index).getCard() == null) {

            return;
        }

        if (gamePhase == GamePhase.DECLARING) {

            possibleSkatHandMove(pos, skatSelectedIndex, shelfSelectedCardIndex, index);

        } else if (gamePhase == GamePhase.PLAYING && pos == FXCardPosition.TRICK) {

            possibleTrickMove(shelfSelectedCardIndex);

        } else if ((gamePhase == GamePhase.AUCTION || gamePhase == GamePhase.PLAYING) && pos == FXCardPosition.HANDSHELF_MID) {

            mMidHandShelf.cardClickedAt(index);
        }

    }

    /**
     * helps handling move on hand with skat
     * @param pos position of fxCard
     * @param skatSelectedIndex index of card which is selected in skat
     * @param shelfSelectedCardIndex index of card which is selected on hand
     * @param index index of clicked card
     */
    private void possibleSkatHandMove(FXCardPosition pos, int skatSelectedIndex, int shelfSelectedCardIndex, int index) {

        if (pos == FXCardPosition.HANDSHELF_MID) {

            if (skatSelectedIndex != -1) {

                var move = new SkatMove(ActionType.ON_SKATHAND, 10 + skatSelectedIndex, index);

                if (makeMove(move)) {

                    mFxSkat.getFXCardAt(skatSelectedIndex).setSelected(false);
                    mFxSkat.setSelectedCardIndex(-1);

                    mMidHandShelf.deselectAll();
                    mMidHandShelf.setSelectedCardIndex(-1);
                }

            } else {

                mMidHandShelf.cardClickedAt(index);

            }

        } else if (pos == FXCardPosition.SKAT) {

            if (shelfSelectedCardIndex != -1) {

                var move = new SkatMove(ActionType.ON_SKATHAND, shelfSelectedCardIndex, 10 + index);

                if (makeMove(move)) {

                    mFxSkat.getFXCardAt(index).setSelected(false);
                    mFxSkat.setSelectedCardIndex(-1);

                    mMidHandShelf.getFXCardAt(shelfSelectedCardIndex).setSelected(false);
                    mMidHandShelf.setSelectedCardIndex(-1);
                }

            } else {

                mFxSkat.cardClickedAt(index);
            }
        }
        FXPresenter.update();
    }

    /**
     * helps handling card-play onto trick
     * @param shelfSelectedCardIndex index of card which is selected on hand
     */
    private void possibleTrickMove(int shelfSelectedCardIndex) {

        if (shelfSelectedCardIndex != -1) {

            for (Timeline tl : mFxCurrentTrick.getTimelines()) {

                if (tl != null) {

                    tl.stop();
                }
            }

            var move = new SkatMove(shelfSelectedCardIndex);

            if (makeMove(move)) {

                mMidHandShelf.getFXCardAt(shelfSelectedCardIndex).setSelected(false);
                mMidHandShelf.setSelectedCardIndex(-1);
            }
        }

        if (getController().getGame().getGamePhase() == GamePhase.ENDED) {

            FXPresenter.updateHandShelfs();
            mFxCurrentTrick.update();

        } else {

            FXPresenter.update();
        }
    }


    /**
     * handles key events
     * @param e key event
     */
    public void keyboardPressed(KeyEvent e) {

        var key = e.getCode();


        var guiState = getState();
        GamePhase gamePhase;

        if (guiState == GUIState.NOT_STARTED) {

            gamePhase = GamePhase.NOT_STARTED;

        } else {

            gamePhase = getController().getGame().getGamePhase();
        }


        switch (key) {

            case LEFT: keyLeftClicked(); break;
            case RIGHT: keyRightClicked(); break;
            case UP: keyUpClicked(guiState); break;
            case DOWN: keyDownClicked(guiState); break;
            case SPACE: keySpaceClicked(); break;
            case ENTER: keyEnterClicked(gamePhase); break;
            case BACK_SLASH: keyHashTagClicked(); break;
            case S: keySClicked(gamePhase); break;
            default: break;
        }
    }

    /**
     * handles key release events
     * @param e key event
     */
    public void keyboardReleased(KeyEvent e) {

        var key = e.getCode();

        if (key == KeyCode.SPACE) {

            keySpaceReleased();
        }
    }

    /**
     * if left arrow is clicked
     */
    private void keyLeftClicked() {

        var midHandSelIndex = mMidHandShelf.getSelectedCardIndex();

        if (!mKeyPositionSkat) {

            if (midHandSelIndex == -1) {

                mMidHandShelf.getFXCardAt(0).setSelected(true);
                mMidHandShelf.setSelectedCardIndex(0);

            } else if (midHandSelIndex > 0) {

                mMidHandShelf.deselectAll();

                mMidHandShelf.getFXCardAt(midHandSelIndex - 1).setSelected(true);
                mMidHandShelf.setSelectedCardIndex(midHandSelIndex - 1);
            }

        } else {

            mFxSkat.deselectAll();

            mFxSkat.getFXCardAt(0).setSelected(true);
            mFxSkat.setSelectedCardIndex(0);
        }

        FXPresenter.update();
    }

    /**
     * if right arrow is clicked
     */
    private void keyRightClicked() {

        var gamePhase = getController().getGame().getGamePhase();

        var size = getPlayer().getHand().getSize();
        var midHandSelIndex = mMidHandShelf.getSelectedCardIndex();

        if (gamePhase == GamePhase.DECLARING) {

            size = 10;
        }

        if (!mKeyPositionSkat) {

            if (midHandSelIndex == -1) {

                mMidHandShelf.getFXCardAt(size - 1).setSelected(true);
                mMidHandShelf.setSelectedCardIndex(size - 1);

            } else if (midHandSelIndex < size - 1) {

                mMidHandShelf.deselectAll();

                mMidHandShelf.getFXCardAt(midHandSelIndex + 1).setSelected(true);
                mMidHandShelf.setSelectedCardIndex(midHandSelIndex + 1);
            }

        } else {

            mFxSkat.deselectAll();

            mFxSkat.getFXCardAt(1).setSelected(true);
            mFxSkat.setSelectedCardIndex(1);
        }

        FXPresenter.update();
    }

    /**
     * if up arrow is clicked
     * @param phase phase, because consequence depends on phase
     */
    private void keyUpClicked(GUIState phase) {

        if (phase == GUIState.DECLARE_SKAT) {

            var skatSelIndex = mFxSkat.getSelectedCardIndex();
            var midHandSelIndex = mMidHandShelf.getSelectedCardIndex();

            mKeyPositionSkat = true;

            if (skatSelIndex != -1) {

                mFxSkat.deselectAll();
            }

            if (midHandSelIndex < 5) {

                mFxSkat.getFXCardAt(0).setSelected(true);
                mFxSkat.setSelectedCardIndex(0);

            } else {

                mFxSkat.getFXCardAt(1).setSelected(true);
                mFxSkat.setSelectedCardIndex(1);
            }

        }

        FXPresenter.update();
    }

    /**
     * if down arrow is clicked
     * @param phase phase, because consequence depends on phase
     */
    private void keyDownClicked(GUIState phase) {

        if (phase == GUIState.DECLARE_SKAT) {


            var midHandSelIndex = mMidHandShelf.getSelectedCardIndex();

            mKeyPositionSkat = false;

            if (midHandSelIndex == -1) {

                mMidHandShelf.getFXCardAt(0).setSelected(true);
                mMidHandShelf.setSelectedCardIndex(0);
            }
        }

        FXPresenter.update();
    }

    /**
     * handles space click event
     */
    private void keySpaceClicked() {

        var selIndex = mMidHandShelf.getSelectedCardIndex();
        var selCard = mMidHandShelf.getFXCardAt(selIndex).getCard();

        if (selIndex != -1) {

            var preview = new FXCard(AnchorPreview, FXCardPosition.PREVIEW, 0, this);

            if (!preview.isEqualTo(selCard)) {

                preview.changeCard(selCard);
            }

            AnchorPreview.setVisible(true);
        }

        FXPresenter.update();
    }

    /**
     * handles space released event
     */
    private void keySpaceReleased() {

        AnchorPreview.setVisible(false);

        FXPresenter.update();
    }

    /**
     * handles enter click event
     * @param phase phase, bc consequence depends on phase
     */
    private void keyEnterClicked(GamePhase phase) {

        if (phase == GamePhase.NOT_STARTED || phase == GamePhase.ENDED || phase == GamePhase.ABORTED) {

            buttonClicked("PLAY");

            FXPresenter.update();

        } else if (phase == GamePhase.AUCTION) {

            buttonClicked("PA2");

            FXPresenter.update();

        } else if (phase == GamePhase.DECLARING) {

            buttonClicked("PA3");

            FXPresenter.update();

        } else if (phase == GamePhase.PLAYING) {

            var selIndex = mMidHandShelf.getSelectedCardIndex();

            fxCardClicked(FXCardPosition.TRICK, selIndex);

        }
    }

    /**
     * handles #-click event
     */
    private void keyHashTagClicked() {

        var gamePhase = getController().getGame().getGamePhase();

        if (gamePhase == GamePhase.AUCTION) {

            buttonClicked("PA4");

        } else if (gamePhase == GamePhase.DECLARING) {

            var skatSelIndex = mFxSkat.getSelectedCardIndex();
            var midHandSelIndex = mMidHandShelf.getSelectedCardIndex();

            if (skatSelIndex != -1 && midHandSelIndex != -1) {

                fxCardClicked(FXCardPosition.SKAT, skatSelIndex);
            }
        }

        FXPresenter.update();
    }

    /**
     * handles s-click event
     * @param phase phase, bc consequence depends on phase
     */
    private void keySClicked(GamePhase phase) {

        if (phase != GamePhase.NOT_STARTED) {

            buttonClicked("SORT");
        }

        FXPresenter.update();
    }


    public void addPlayerWithAmount(int amount) {



        for (var i = 0; i < amount; i++) {
            mController.addPlayer(this);
        }

        if(amount > 1){
            skatSetPlayerIndex = -1;
        } else {
            skatSetPlayerIndex = mController.getCurPlayerNo()-1;
        }




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

    public Label LabelAuctionValue;
    public AnchorPane AnchorMarker;

    public AnchorPane AnchorPlayerView1;
    public AnchorPane AnchorPlayerIcon1;
    public AnchorPane AnchorPlayerInfo1;
    public Label LabelPlayerName1;
    public Label LabelPlayerPoints1;

    public AnchorPane AnchorPlayerView2;
    public AnchorPane AnchorPlayerIcon2;
    public AnchorPane AnchorPlayerInfo2;
    public Label LabelPlayerName2;
    public Label LabelPlayerPoints2;

    public AnchorPane AnchorPlayerView3;
    public AnchorPane AnchorPlayerIcon3;
    public AnchorPane AnchorPlayerInfo3;
    public Label LabelPlayerName3;
    public Label LabelPlayerPoints3;
    public ImageView IVGameInfoColor;
    public Label LabelGameInfoRound;
    public Label LabelGameInfoMode;
    public AnchorPane anchorGameInfoView;
    public Label LabelWinner;

    public AnchorPane AnchorPreview = new AnchorPane();
}
