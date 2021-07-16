package javaFX;


import console.Print;
import engine.SkatPlayer;
import engine.enums.CardValue;
import engine.enums.GameMode;
import javaFX.enums.GUIState;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class FXPresenter {

    private static FXController fxController;

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




    public static void setFxController(FXController fxController){

        FXPresenter.fxController = fxController;


    }

    public static void update(){

        hideAll();

        var state = fxController.getState();

        switch (state) {

            case NOT_STARTED -> {

                fxController.ImageViewBackground.setImage(backGroundBlank);
                newGameView(true);

            }

            case AUCTION_WATCHING, AUCTION_ASKING, AUCTION_HEARING -> {

                updateHandShelfs();
                playerViews(true);
                gameInfo(true);

                fxController.ImageViewBackground.setImage(backGroundShelfs);

                String message = "";
                var controller = fxController.getController();
                var auction = controller.getGame().getAuction();

                if (state == GUIState.AUCTION_ASKING) {

                    var auctionValue = auction.getNextAuctionValue();

                    if (controller.getGame().getAuction().getQuestioner() == controller.getGame().getPlayerAt(0)) {

                        message = "Raise and play?";

                    } else {

                        var name = controller.getSkatSet().getPlayingPlayerName(auction.getHearer().getGameIndex());
                        message = "Raise against " + name + "?";
                    }

                    fxController.LabelAuctionValue.setText(Integer.toString(auctionValue));

                } else if (state == GUIState.AUCTION_HEARING) {

                    var auctionValue = auction.getAuctionValue();
                    var name = controller.getSkatSet().getPlayingPlayerName(auction.getQuestioner().getGameIndex());
                    message = name + " raised. Call?";
                    fxController.LabelAuctionValue.setText(Integer.toString(auctionValue));

                } else {


                    Print.debug("WARNING", "FX: GUIState AUCTION_ASKING was demanded but there is no implementation yet.");

                }



                declareGameTypeView("Auction", message);
                fxController.LabelAuctionValue.setVisible(true);
                buttonsAcceptCancel(); // call last


            }


            case WAIT_FOR_DECLARER -> {

                // TODO: egtl ja nich oder? nur wenn der declarer was macht das updaten
                playerViews(true);
                gameInfo(true);
                updateHandShelfs();
                fxController.getFxSkat().update();
            }

            case DECLARE_SKAT -> {

                skatView(true);
                playerViews(true);
                gameInfo(true);
                updateHandShelfs();
                fxController.getFxSkat().update();
                buttonsAcceptSkat();// call last
            }

            case DECLARE_TRUMPTYPE -> {

                declareGameTypeView("Game Type", """
                Declare Game Type.
                        """);

                updateHandShelfs();
                playerViews(true);
                gameInfo(true);
                fxController.setKeyPositionSkat(false);
                buttonsChooseMode(); // call last
            }

            case DECLARE_TRUMPCOLOR -> {

                playerViews(true);
                gameInfo(true);
                declareGameTypeView("Game Color", """
                Declare Game Color.
                        """);
                updateHandShelfs();
                buttonsChooseColor(); // call last
            }

            case PLAYING_YOUR_MOVE -> {

                fxController.setKeyPositionSkat(false);
                fxController.getFxCurrentTrick().update();

                gameInfo(true);
                playerViews(true);
                markerView(true);
                trickView(true);
                updateHandShelfs();
            }

            case PLAYING_NOT_YOUR_MOVE -> {

                gameInfo(true);
                playerViews(true);
                markerView(true);
                trickView(true);
                updateHandShelfs();
            }

            case GAME_ABORTED -> {

                Print.debug("maik", "game aborted");

                // TODO: show resultView? new game button anzeigen
                abortView(true);
            }

            case GAME_FINISHED -> {

                resultView(true);
                // TODO: new game button nicht vergessen
            }

            case SET_FINISHED -> {

            }
        }

    }


    private static void declareGameTypeView(String title, String text){


        fxController.LabelAuctionViewTitle.setText(title);
        fxController.LabelAuctionViewText.setText(text);
        auctionView(true);
        fxController.LabelAuctionValue.setVisible(false);

    }

    private static void auctionView(boolean visible){

        fxController.AnchorAuctionDialog.setVisible(visible);
        fxController.LabelAuctionValue.setVisible(visible);

    }

    public static void markerView(boolean visible){


        var game = fxController.getController().getGame();
        var anchor = fxController.AnchorMarker;
        var trick = game.getCurrentTrick();
        var color = trick.getColor();
        var gameTrump = game.getTrump();

        var imgView = (ImageView) fxController.AnchorMarker.getChildren().get(0);
        var imgTrumpView = (ImageView) fxController.AnchorMarker.getChildren().get(1);

        if (color != null && trick.getCardAt(0).getCardValue() != CardValue.JACK){

            imgView.setImage( switch (color){

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

                imgView.setImage( switch (gameTrump.getColor()){

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

    private static void hideAll(){

        resultView(false);
        newGameView(false);
        skatView(false);
        auctionView(false);
        trickView(false);
        playerViews(false);

        buttonsHide();

        fxController.AnchorGameMessage.setVisible(false); // this will probably collide with fade out -> delete later


    }

    private static void buttonsHide(){

        var buttonDict = fxController.buttonDict;

        buttonDict.get("PA1").hide();
        buttonDict.get("PA2").hide();
        buttonDict.get("PA3").hide();
        buttonDict.get("PA4").hide();
        buttonDict.get("PA5").hide();
        buttonDict.get("SORT").hide();
        fxController.anchorButtonsPlayActions.setVisible(false);
    }


    private static void gameInfo(boolean visible){

        if (!visible){

            fxController.anchorGameInfoView.setVisible(false);
            return;

        }

        fxController.anchorGameInfoView.setVisible(true);

        var game = fxController.getController().getGame();
        var trump = game.getTrump();

        if ( trump != null  && trump.getGameMode() != null ){

            fxController.LabelGameInfoMode.setText(trump.getGameMode().toString().toUpperCase());
            fxController.LabelGameInfoMode.setVisible(true);

            var IV = fxController.IVGameInfoColor;

            if (trump.getGameMode() == GameMode.SUIT){

                IV.setImage( switch (trump.getColor()){

                  case CLUBS -> club;
                  case SPADES -> spade;
                  case HEARTS -> heart;
                  case DIAMONDS -> diamond;

              });

                IV.setVisible(true);

            } else {

               IV.setVisible(false);

            }


        } else {

            fxController.IVGameInfoColor.setVisible(false);
            fxController.LabelGameInfoMode.setVisible(false);

        }

        var set = fxController.getController().getSkatSet();

        var gameAmount = Integer.toString(set.getGameAmount());

        if (gameAmount.equals("-1")){
            gameAmount = "∞";
        }

        fxController.LabelGameInfoRound.setText("Game\n" + Integer.toString(set.currentGameNo()+1) + " / " + gameAmount);


    }


    private static void playerViews(boolean visible){

        var anchorPlayerView = new AnchorPane[]{fxController.AnchorPlayerView1, fxController.AnchorPlayerView2, fxController.AnchorPlayerView3};
        var anchorPlayerIcon = new AnchorPane[]{fxController.AnchorPlayerIcon1, fxController.AnchorPlayerIcon2, fxController.AnchorPlayerIcon3};
        var anchorPlayerInfo = new AnchorPane[]{fxController.AnchorPlayerInfo1, fxController.AnchorPlayerInfo2, fxController.AnchorPlayerInfo3};
        var labelPlayerName = new Label[]{fxController.LabelPlayerName1, fxController.LabelPlayerName2, fxController.LabelPlayerName3};
        var labelPlayerScore = new Label[]{fxController.LabelPlayerPoints1, fxController.LabelPlayerPoints2, fxController.LabelPlayerPoints3};

        var set = fxController.getController().getSkatSet();

        for (var i = 0; i < 3; i++) {

            anchorPlayerView[i].setVisible(visible);
            if (!visible) { continue; }

            var index = fxController.getPlayerGameIndex();
            index = (i + index)  % 3;

            var skatSetPlayer = set.getSkatSetPlayerAt(index);
            var skatPlayer = set.getSkatPlayerAt(index);

            var name = skatSetPlayer.getName();
            var score = skatSetPlayer.getTotalScore();
            var declarer = skatPlayer.isDeclarer();

            var iconImgView = (ImageView) anchorPlayerIcon[i].getChildren().get(0);

            if (declarer) {

               iconImgView.setImage(iconDeclarer);

            } else {

                iconImgView.setImage(iconTogether);

            }

            labelPlayerName[i].setText(name);
            labelPlayerScore[i].setText(Integer.toString(score) + "P");

        }



    }


    private static void buttonsAcceptSkat(){

        var buttonDict = fxController.buttonDict;

        buttonsHide();
        fxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA3").setImages(acceptButton3);
        buttonDict.get("PA3").show();


    }

    private static void buttonsAcceptCancel(){

        var buttonDict = fxController.buttonDict;

        buttonsHide();
        fxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA2").setImages(acceptButton2);
        buttonDict.get("PA2").show();


        buttonDict.get("PA4").setImages(cancelButton4);
        buttonDict.get("PA4").show();


    }

    private static void buttonsChooseMode(){

        var buttonDict = fxController.buttonDict;

        buttonsHide();
        fxController.anchorButtonsPlayActions.setVisible(true);


        buttonDict.get("PA2").setImages(suitButton);
        buttonDict.get("PA2").show();

        buttonDict.get("PA3").setImages(grandButton);
        buttonDict.get("PA3").show();

        buttonDict.get("PA4").setImages(nullButton);
        buttonDict.get("PA4").show();



    }

    private static void buttonSort(){
        var buttonDict = fxController.buttonDict;
        buttonDict.get("SORT").show();
    }

    private static void buttonsChooseColor(){
        var buttonDict = fxController.buttonDict;

        fxController.anchorButtonsPlayActions.setVisible(true);

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

    private static void trickView(boolean visible){

        fxController.AnchorTrickOne.setVisible(true);
        fxController.AnchorTrickTwo.setVisible(true);
        fxController.AnchorTrickTwo.setVisible(true);

    }


    private static void skatView(boolean visible){

        fxController.AnchorViewSkat.setVisible(visible);
        buttonsAcceptSkat();

    }

    private static void newGameView(boolean visible){

        var set = fxController.getController().getSkatSet();

        fxController.AnchorWelcomeResultNewGameView.setVisible(visible);

        var labelArray = new Label[]{
            fxController.LabelResultNewGame1,
            fxController.LabelResultNewGame2,
            fxController.LabelResultNewGame3,
            fxController.LabelResultNewGame4,
            fxController.LabelResultNewGame5,
        };

        if (visible) {

            var curGameNo = Integer.toString(set.currentGameNo() + 2);
            var gameAm = Integer.toString(set.getGameAmount()) + "  ";

            if (gameAm.equals("-1")) {

                gameAm = "∞";
            }

            fxController.LabelGameNo.setText(curGameNo + "       " + gameAm);
        }

        fxController.LabelGameNo.setVisible(visible);

        if (visible) {

            fxController.buttonDict.get("PLAY").show();

        } else {

            fxController.buttonDict.get("PLAY").hide();
        }


        fxController.ImageViewWRNBackground.setImage(viewNewGame);

        var playerNo = set.getSkatSetPlayerAmount();

        for(var i = 0; i < playerNo; i++){

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

    private static void resultView(boolean visible){

        var set = fxController.getController().getSkatSet();

        fxController.AnchorWelcomeResultNewGameView.setVisible(visible);

        var labelArray = new Label[]{
                fxController.LabelResultNewGame1,
                fxController.LabelResultNewGame2,
                fxController.LabelResultNewGame3,
        };


        if (visible) {

            fxController.buttonDict.get("NEXT").show();
            fxController.LabelWinner.setText("Winner"); // TODO : @Maik Wie bekommt man hier den Gewinner?

        } else {

            fxController.buttonDict.get("NEXT").hide();
        }


        fxController.ImageViewWRNBackground.setImage(viewResult);

        for(var i = 0; i < 3; i++){

            labelArray[2-i].setVisible(visible);

            if (visible) {  // change labels

                var gamePlayer = set.getSkatPlayerAt(i);
                var setPlayer = set.currentSkatSetPlayer()[i];

                var score = setPlayer.getTotalScore();
                var name = setPlayer.getName();
                var scoreString = Integer.toString(score);
                scoreString = Print.times(9-scoreString.length(), " ") + scoreString;

                String declarer = "  ";
                int gameScore = 0;

                if ( gamePlayer.isDeclarer() ){
                    declarer = "D ";
                    gameScore = set.getCurrentGameResult().getGameValue();
                }

                var labelText = declarer + "P" + Integer.toString(i+1) + "  " + setPlayer.getName() + " " + Print.times(16 - setPlayer.getName().length(),".") + " " + Integer.toString(gameScore) + "P" + "  -> " + scoreString + "P";

                labelArray[2-i].setText(labelText);

            }

        }

        fxController.LabelResultNewGame1.setText("");

    }

    private static void abortView(boolean visible) {


    }

    private static void updatePlayerInfo(){


    }

    public static void updateHandShelfs(){

        buttonSort();

        for (FXHandShelf shelf : fxController.getFxHandShelfs()) {

            shelf.update();
        }
    }




}
