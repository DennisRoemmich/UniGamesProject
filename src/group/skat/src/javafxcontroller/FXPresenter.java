package javafxcontroller;


import console.Print;
import engine.enums.CardValue;
import engine.enums.GameMode;
import javafxcontroller.enums.GUIState;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * handles all the updates while playing the game
 */
public class FXPresenter {

    private static FXController mFxController;

    private static Image viewNewGame = new Image("images/Views/ViewNewGame.png");
    private static Image viewResult = new Image("images/Views/ResultView.png");

    private static Image backGroundBlank = new Image("images/Views/ViewBackgroundBlank.png");
    private static Image backGroundShelfs = new Image("images/Views/ViewBackgroundPlay.png");

    private static Image markerClubs = new Image("images/Other/MarkerClubs.png");
    private static Image markerSpades = new Image("images/Other/MarkerSpades.png");
    private static Image markerHearts = new Image("images/Other/MarkerHearts.png");
    private static Image markerDiamonds = new Image("images/Other/MarkerDiamonds.png");

    private static Image club = new Image("images/Other/Club.png");
    private static Image spade = new Image("images/Other/Spade.png");
    private static Image heart = new Image("images/Other/Heart.png");
    private static Image diamond = new Image("images/Other/Diamond.png");

    private static Image iconDeclarer = new Image("images/Other/PlayerIconDeclarer.png");
    private static Image iconTogether = new Image("images/Other/PlayerIconTogether.png");

    private static Image[] acceptButton2 = new Image[]{ new Image ("images/Buttons/ButtonAcceptCancel2.png"), new Image ("images/Buttons/ButtonAcceptCancel2Highlighted.png") };
    private static Image[] cancelButton4 = new Image[]{ new Image ("images/Buttons/ButtonAcceptCancel4.png"), new Image ("images/Buttons/ButtonAcceptCancel4Highlighted.png") };

    private static Image[] acceptButton3 = new Image[]{ new Image ("images/Buttons/ButtonAcceptSkat.png"), new Image ("images/Buttons/ButtonAcceptSkatHighlighted.png") };

    private static Image[] suitButton = new Image[]{ new Image ("images/Buttons/ButtonGameType2.png"), new Image ("images/Buttons/ButtonGameType2Highlighted.png") };
    private static Image[] grandButton = new Image[]{ new Image ("images/Buttons/ButtonGameType3.png"), new Image ("images/Buttons/ButtonGameType3Highlighted.png") };
    private static Image[] nullButton = new Image[]{ new Image ("images/Buttons/ButtonGameType4.png"), new Image ("images/Buttons/ButtonGameType4Highlighted.png") };

    private static Image[] clubsButton = new Image[]{ new Image ("images/Buttons/ButtonGameColor1.png"), new Image ("images/Buttons/ButtonGameColor1Highlighted.png") };
    private static Image[] spadesButton = new Image[]{ new Image ("images/Buttons/ButtonGameColor2.png"), new Image ("images/Buttons/ButtonGameColor2Highlighted.png") };
    private static Image[] greyButton = new Image[]{ new Image ("images/Buttons/ButtonGameColor3.png"), new Image ("images/Buttons/ButtonGameColor3Highlighted.png") };
    private static Image[] heartsButton = new Image[]{ new Image ("images/Buttons/ButtonGameColor4.png"), new Image ("images/Buttons/ButtonGameColor4Highlighted.png") };
    private static Image[] diamondsButton = new Image[]{ new Image ("images/Buttons/ButtonGameColor5.png"), new Image ("images/Buttons/ButtonGameColor5Highlighted.png") };


    private FXPresenter() {}

    public static void setFxController(FXController fxController) {

        FXPresenter.mFxController = fxController;

    }

    /**
     * actual update method, the only one used from outside this class
     */
    public static void update() {

        if(mFxController.getController().getGame() != null && mFxController.getFxSkat() == null) {

            mFxController.initGameStart();
        }

        hideAll();

        var state = mFxController.getState();


        switch (state) {

            case NOT_STARTED:

                mFxController.ImageViewBackground.setImage(backGroundBlank);
                newGameView(true);
                break;

            case AUCTION_WATCHING, AUCTION_ASKING, AUCTION_HEARING:

                updateAuction(state);

                break;


            case WAIT_FOR_DECLARER:

                playerViews(true);
                gameInfo(true);
                updateHandShelfs();
                mFxController.getFxSkat().update();
                break;

            case DECLARE_SKAT:

                skatView(true);
                playerViews(true);
                gameInfo(true);
                updateHandShelfs();
                mFxController.getFxSkat().update();
                buttonsAcceptSkat();// call last
                break;

            case DECLARE_TRUMPTYPE:

                declareGameTypeView("Game Type", """
                Declare Game Type.
                        """);

                updateHandShelfs();
                playerViews(true);
                gameInfo(true);
                mFxController.setKeyPositionSkat(false);
                buttonsChooseMode(); // call last
                break;

            case DECLARE_TRUMPCOLOR:

                playerViews(true);
                gameInfo(true);
                declareGameTypeView("Game Color", """
                Declare Game Color.
                        """);
                updateHandShelfs();
                buttonsChooseColor(); // call last
                break;

            case PLAYING_YOUR_MOVE:

                mFxController.setKeyPositionSkat(false);
                mFxController.getFxCurrentTrick().update();

                gameInfo(true);
                playerViews(true);
                markerView(true);
                trickView();
                updateHandShelfs();
                break;

            case PLAYING_NOT_YOUR_MOVE:

                gameInfo(true);
                playerViews(true);
                markerView(true);
                trickView();
                updateHandShelfs();
                break;

            case GAME_ABORTED:

                mFxController.ImageViewBackground.setImage(backGroundBlank);
                for (AnchorPane pane : new AnchorPane[]{mFxController.AnchorPlayerhandShelfLeft, mFxController.AnchorPlayerhandShelfMid, mFxController.AnchorPlayerhandShelfRight}) {

                    for (var child : pane.getChildren()) {

                        child.setVisible(false);
                    }
                    pane.getChildren().removeAll();
                }

                newGameView(true);
                break;

            case GAME_FINISHED:

                updateHandShelfs();
                for (AnchorPane pane : new AnchorPane[]{mFxController.AnchorPlayerhandShelfLeft, mFxController.AnchorPlayerhandShelfMid, mFxController.AnchorPlayerhandShelfRight}) {

                    for (var child : pane.getChildren()) {

                        child.setVisible(false);
                    }
                    pane.getChildren().removeAll();
                }

                resultView(true);
                break;

            default: break;
        }

    }

    private static void updateAuction(GUIState state) {

        updateHandShelfs();
        playerViews(true);
        gameInfo(true);

        mFxController.ImageViewBackground.setImage(backGroundShelfs);

        var message = "";
        var controller = mFxController.getController();
        var auction = controller.getGame().getAuction();

        if (state == GUIState.AUCTION_ASKING) {

            var auctionValue = auction.getNextAuctionValue();

            if (controller.getGame().getAuction().getQuestioner() == controller.getGame().getPlayerAt(0)) {

                message = "Raise and play?";

            } else {

                var name = controller.getSkatSet().getPlayingPlayerName(auction.getHearer().getGameIndex());
                message = "Raise against " + name + "?";
            }

            mFxController.LabelAuctionValue.setText(Integer.toString(auctionValue));

        } else if (state == GUIState.AUCTION_HEARING) {

            var auctionValue = auction.getAuctionValue();
            var name = controller.getSkatSet().getPlayingPlayerName(auction.getQuestioner().getGameIndex());
            message = name + " raised. Call?";
            mFxController.LabelAuctionValue.setText(Integer.toString(auctionValue));

        } else {

            declareGameTypeView("Not your move", "Wait for you opponents.");
            mFxController.LabelAuctionValue.setVisible(true);

        }



        declareGameTypeView("Auction", message);
        mFxController.LabelAuctionValue.setVisible(true);
        buttonsAcceptCancel(); // call last
    }


    private static void declareGameTypeView(String title, String text) {


        mFxController.LabelAuctionViewTitle.setText(title);
        mFxController.LabelAuctionViewText.setText(text);
        auctionView(true);
        mFxController.LabelAuctionValue.setVisible(false);

    }

    /**
     * view for auction
     * @param visible boolean whether view is shown or not
     */
    private static void auctionView(boolean visible) {

        mFxController.AnchorAuctionDialog.setVisible(visible);
        mFxController.LabelAuctionValue.setVisible(visible);

    }

    /**
     * view for trump view
     * @param visible boolean whether view is shown or not
     */
    public static void markerView(boolean visible) {


        var game = mFxController.getController().getGame();
        var anchor = mFxController.AnchorMarker;
        var trick = game.getCurrentTrick();
        var color = trick.getColor();
        var gameTrump = game.getTrump();

        var imgView = (ImageView) mFxController.AnchorMarker.getChildren().get(0);
        var imgTrumpView = (ImageView) mFxController.AnchorMarker.getChildren().get(1);

        if (color != null && trick.getCardAt(0).getCardValue() != CardValue.JACK) {

            imgView.setImage( switch (color) {

                case CLUBS -> markerClubs;
                case SPADES -> markerSpades;
                case HEARTS -> markerHearts;
                case DIAMONDS -> markerDiamonds;

            } );

            imgTrumpView.setVisible(color == gameTrump.getColor());

            anchor.setVisible(visible);

        } else {

            anchor.setVisible(false);
            imgView.setVisible(true);

            if (gameTrump.getGameMode() == GameMode.SUIT && trick.getSize() > 0 ) {

                imgView.setImage( switch (gameTrump.getColor()) {

                    case CLUBS -> markerClubs;
                    case SPADES -> markerSpades;
                    case HEARTS -> markerHearts;
                    case DIAMONDS -> markerDiamonds;

                } );

                imgTrumpView.setVisible(true);
                anchor.setVisible(true);

            }

            if (gameTrump.getGameMode() == GameMode.GRAND && trick.getSize() > 0 && trick.getCardAt(0).getCardValue() == CardValue.JACK) {

                imgView.setVisible(false);
                imgTrumpView.setVisible(true);
                anchor.setVisible(true);

            }


        }



    }

    /**
     * hides everything
     */
    private static void hideAll() {

        resultView(false);
        newGameView(false);
        skatView(false);
        auctionView(false);
        trickView();
        playerViews(false);
        buttonSort(false);
        gameInfo(false);

        paButtonsHide();


        mFxController.anchorButtonsPlayActions.setVisible(false);
        mFxController.AnchorGameMessage.setVisible(false); // this will probably collide with fade out -> delete later


    }

    /**
     * hides buttons
     */
    private static void paButtonsHide() {

        var buttonDict = mFxController.mButtonDict;

        buttonDict.get("PA1").hide();
        buttonDict.get("PA2").hide();
        buttonDict.get("PA3").hide();
        buttonDict.get("PA4").hide();
        buttonDict.get("PA5").hide();

    }


    /**
     * view for info for current game
     * @param visible boolean whether view is shown or not
     */
    private static void gameInfo(boolean visible) {

        if (!visible) {

            mFxController.anchorGameInfoView.setVisible(false);
            return;

        }

        mFxController.anchorGameInfoView.setVisible(true);

        var game = mFxController.getController().getGame();
        var trump = game.getTrump();

        if ( trump != null  && trump.getGameMode() != null ) {

            mFxController.LabelGameInfoMode.setText(trump.getGameMode().toString().toUpperCase());
            mFxController.LabelGameInfoMode.setVisible(true);

            var iV = mFxController.IVGameInfoColor;

            if (trump.getGameMode() == GameMode.SUIT) {

                iV.setImage( switch (trump.getColor()) {

                  case CLUBS -> club;
                  case SPADES -> spade;
                  case HEARTS -> heart;
                  case DIAMONDS -> diamond;

              });

                iV.setVisible(true);

            } else {

               iV.setVisible(false);

            }


        } else {

            mFxController.IVGameInfoColor.setVisible(false);
            mFxController.LabelGameInfoMode.setVisible(false);

        }

        var set = mFxController.getController().getSkatSet();

        var gameAmount = Integer.toString(set.getGameAmount());

        if (gameAmount.equals("-1")) {
            gameAmount = "∞";
        }

        mFxController.LabelGameInfoRound.setText("Game\n" + Integer.toString(set.currentGameNo()+1) + " / " + gameAmount);


    }

    /**
     * view for infos about players
     * @param visible boolean whether view is shown or not
     */
    private static void playerViews(boolean visible) {

        var anchorPlayerView = new AnchorPane[]{mFxController.AnchorPlayerView1, mFxController.AnchorPlayerView2, mFxController.AnchorPlayerView3};
        var anchorPlayerIcon = new AnchorPane[]{mFxController.AnchorPlayerIcon1, mFxController.AnchorPlayerIcon2, mFxController.AnchorPlayerIcon3};
        var labelPlayerName = new Label[]{mFxController.LabelPlayerName1, mFxController.LabelPlayerName2, mFxController.LabelPlayerName3};
        var labelPlayerScore = new Label[]{mFxController.LabelPlayerPoints1, mFxController.LabelPlayerPoints2, mFxController.LabelPlayerPoints3};
        var labelPlayerActive = new Label[]{mFxController.LabelPlayerActive1, mFxController.LabelPlayerActive2, mFxController.LabelPlayerActive3};

        var set = mFxController.getController().getSkatSet();
        int activeSkatPlayerIndex;
        var game = mFxController.getController().getGame();

        if (game == null || game.getGameResult().isAborted()){
            activeSkatPlayerIndex = -1;
        } else {
            activeSkatPlayerIndex = game.getCurrentPlayer().getGameIndex() % 3;
        }



        for (var i = 0; i < 3; i++) {

            anchorPlayerView[i].setVisible(visible);
            if (!visible) { continue; }

            var index = mFxController.getPlayerGameIndex();
            index = (i + index)  % 3;
            var setIndex = mFxController.getSkatPlayerGameIndex();
            setIndex = (i + setIndex)  % 3;

            var skatSetPlayer = set.getSkatSetPlayerAt(setIndex);
            var skatPlayer = set.getSkatPlayerAt(index);

            var name = skatSetPlayer.getName();
            var score = skatSetPlayer.getTotalScore();
            var declarer = skatPlayer.isDeclarer();

            var iconImgView = (ImageView) anchorPlayerIcon[i].getChildren().get(0);

            labelPlayerActive[i].setVisible(false);
            if (index == activeSkatPlayerIndex && (mFxController.getState() == GUIState.PLAYING_NOT_YOUR_MOVE || mFxController.getState() == GUIState.PLAYING_YOUR_MOVE)) {
                labelPlayerActive[i].setVisible(true);
            }

            if (declarer) {

               iconImgView.setImage(iconDeclarer);

            } else {

                iconImgView.setImage(iconTogether);

            }

            labelPlayerName[i].setText(name);
            labelPlayerScore[i].setText(Integer.toString(score) + "P");

        }



    }


    /**
     * shows buttons for skat declaring
     */
    private static void buttonsAcceptSkat() {

        var buttonDict = mFxController.mButtonDict;

        paButtonsHide();
        mFxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA3").setImages(acceptButton3);
        buttonDict.get("PA3").show();


    }

    /**
     * shows buttons for auction
     */
    private static void buttonsAcceptCancel() {

        var buttonDict = mFxController.mButtonDict;

        paButtonsHide();
        mFxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA2").setImages(acceptButton2);
        buttonDict.get("PA2").show();


        buttonDict.get("PA4").setImages(cancelButton4);
        buttonDict.get("PA4").show();


    }

    /**
     * shows buttons for trump declaring
     */
    private static void buttonsChooseMode() {

        var buttonDict = mFxController.mButtonDict;

        paButtonsHide();
        mFxController.anchorButtonsPlayActions.setVisible(true);


        buttonDict.get("PA2").setImages(suitButton);
        buttonDict.get("PA2").show();

        buttonDict.get("PA3").setImages(grandButton);
        buttonDict.get("PA3").show();

        buttonDict.get("PA4").setImages(nullButton);
        buttonDict.get("PA4").show();



    }

    /**
     * handles show of sort button
     * @param visible boolean whether view is shown or not
     */
    private static void buttonSort(boolean visible) {

        var buttonDict = mFxController.mButtonDict;

        if (visible) {

            buttonDict.get("SORT").show();

        } else {

            buttonDict.get("SORT").hide();
        }
    }

    /**
     * shows buttons for declare trump color
     */
    private static void buttonsChooseColor() {
        var buttonDict = mFxController.mButtonDict;

        mFxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA1").setImages(clubsButton);
        buttonDict.get("PA1").show();

        buttonDict.get("PA2").setImages(spadesButton);
        buttonDict.get("PA2").show();

        buttonDict.get("PA3").setImages(greyButton);
        buttonDict.get("PA3").show();

        buttonDict.get("PA4").setImages(heartsButton);
        buttonDict.get("PA4").show();

        buttonDict.get("PA5").setImages(diamondsButton);
        buttonDict.get("PA5").show();

    }

    /**
     * shows view of current trick
     */
    private static void trickView() {

        if (mFxController.getFxCurrentTrick() != null){
            mFxController.getFxCurrentTrick().update();
        }

        mFxController.AnchorTrickOne.setVisible(true);
        mFxController.AnchorTrickTwo.setVisible(true);
        mFxController.AnchorTrickThree.setVisible(true);

    }

    /**
     * view of skat
     * @param visible boolean whether view is shown or not
     */
    private static void skatView(boolean visible) {

        mFxController.AnchorViewSkat.setVisible(visible);
        buttonsAcceptSkat();

    }

    /**
     * view for new game
     * @param visible boolean whether view is shown or not
     */
    private static void newGameView(boolean visible) {

        var set = mFxController.getController().getSkatSet();

        mFxController.AnchorWelcomeResultNewGameView.setVisible(visible);

        var labelArray = new Label[]{
            mFxController.LabelResultNewGame1,
            mFxController.LabelResultNewGame2,
            mFxController.LabelResultNewGame3,
            mFxController.LabelResultNewGame4,
            mFxController.LabelResultNewGame5,
        };

        if (visible) {

            var curGameNo = Integer.toString(set.currentGameNo() + 2);
            if (!set.getGameResults().isEmpty() && set.getGameResults().get(set.getGameResults().size() - 1).isAborted()) {

                curGameNo = Integer.toString(set.currentGameNo() + 1);
            }

            var gameAm = Integer.toString(set.getGameAmount()) + "  ";

            if (gameAm.equals("-1")) {

                gameAm = "∞";
            }

            mFxController.LabelGameNo.setText(curGameNo + "       " + gameAm);
        }

        mFxController.LabelGameNo.setVisible(visible);

        if (visible) {

            mFxController.mButtonDict.get("PLAY").show();

        } else {

            mFxController.mButtonDict.get("PLAY").hide();
        }


        mFxController.ImageViewWRNBackground.setImage(viewNewGame);

        var playerNo = set.getSkatSetPlayerAmount();

        for(var i = 0; i < playerNo; i++) {

            labelArray[4-i].setVisible(visible);

            if (visible) {  // change labels

                var player = set.getSkatSetPlayerAt(i);

                var score = player.getTotalScore();
                var scoreString = Integer.toString(score);
                scoreString = Print.times(9-scoreString.length(), "") + scoreString;

                var labelText = "P" + Integer.toString(i+1) + "   " + player.getName() + " " + Print.times(25 - player.getName().length(),".") + " " + scoreString + "P";

                labelArray[4-i].setText(labelText);

            }

        }


    }

    /**
     * view for result
     * @param visible boolean whether view is shown or not
     */
    private static void resultView(boolean visible) {

        var set = mFxController.getController().getSkatSet();

        mFxController.AnchorWelcomeResultNewGameView.setVisible(visible);
        mFxController.LabelWinner.setVisible(visible);

        var labelArray = new Label[] {
                mFxController.LabelResultNewGame1,
                mFxController.LabelResultNewGame2,
                mFxController.LabelResultNewGame3,
        };


        if (visible) {

            mFxController.mButtonDict.get("NEXT").show();

            var declarerIndex = mFxController.game().getDeclarer().getGameIndex();
            var name = mFxController.getController().getSkatSet().getPlayingPlayerName(declarerIndex);

            if (mFxController.game().getGameResult().declarerDidWin()) {

                mFxController.LabelWinner.setText(name + " wins!");

            } else {

                mFxController.LabelWinner.setText(name + " loses!");
            }


        } else {

            mFxController.mButtonDict.get("NEXT").hide();
        }


        mFxController.ImageViewWRNBackground.setImage(viewResult);

        for(var i = 0; i < 3; i++) {

            labelArray[2-i].setVisible(visible);

            if (visible) {  // change labels

                var gamePlayer = set.getSkatPlayerAt(i);
                var setPlayer = set.currentPlayingSkatSetPlayer()[i];

                var score = setPlayer.getTotalScore();
                var name = setPlayer.getName();
                var scoreString = Integer.toString(score);
                scoreString = Print.times(5-scoreString.length(), " ") + scoreString;

                var declarer = "  ";
                var gameScore = 0;

                if ( gamePlayer.isDeclarer() ) {
                    declarer = "D ";
                    gameScore = set.getCurrentGameResult().getGameValue();
                }

                var labelText = declarer + "P" + Integer.toString(i+1) + "  " + name + " " + Print.times(14 - name.length(),".") + " " + Integer.toString(gameScore) + "P" + "  -> " + scoreString + "P";

                labelArray[2-i].setText(labelText);

            }

        }

    }

    /**
     * updates handshelfs
     */
    public static void updateHandShelfs() {


        buttonSort(true);

        if ( mFxController.getFxHandShelfs()[0] == null) {
            return;
        }

        for (FXHandShelf shelf : mFxController.getFxHandShelfs()) {

            shelf.update();
        }

        if (mFxController.getState() == GUIState.NOT_STARTED || mFxController.getState() == GUIState.GAME_ABORTED
                || mFxController.getState() == GUIState.GAME_FINISHED || mFxController.getState() == GUIState.SET_FINISHED) {

            for (AnchorPane pane : new AnchorPane[]{mFxController.AnchorPlayerhandShelfLeft, mFxController.AnchorPlayerhandShelfMid, mFxController.AnchorPlayerhandShelfRight}) {

                pane.setVisible(false);
            }

            buttonSort(false);
        }
    }


}
