package javaFX;


import console.Print;
import javaFX.enums.GUIState;
import javafx.scene.control.Label;
import javafx.scene.image.Image;


public class FXPresenter {

    private static FXController fxController;

    private static Image viewNewGame = new Image("images/Views/ViewNewGame.png");
    private static Image viewResult = new Image("images/Views/ResultView.png");

    private static Image backGroundBlank = new Image("images/Views/ViewBackgroundBlank.png");
    private static Image backGroundShelfs = new Image("images/Views/ViewBackgroundShelfs.png");

    private static Image acceptButton = new Image("images/Buttons/Button2Accept.png");
    private static Image greyButton = new Image("images/Buttons/Button3GameColor.png");
    private static Image cancelButton = new Image("images/Buttons/Button4Cancel.png");
    private static Image suitButton = new Image("images/Buttons/Button2GameType.png");
    private static Image grandButton = new Image("images/Buttons/Button3GameType.png");
    private static Image nullButton = new Image("images/Buttons/Button4GameType.png");

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
                buttonsAcceptCancel();

                fxController.ImageViewBackground.setImage(backGroundShelfs);

                String message = "";
                var controller = fxController.getController();
                var auction = controller.getGame().getAuction();

                if (state == GUIState.AUCTION_ASKING){

                    var auctionValue = auction.getNextAuctionValue();
                    var name = controller.getSkatSet().getPlayingPlayerName(auction.getHearer().getGameIndex());
                    message = "Do you want to raise on " + Integer.toString(auctionValue) + " against " + name + "?";

                } else if (state == GUIState.AUCTION_HEARING) {

                    var auctionValue = auction.getAuctionValue();
                    var name = controller.getSkatSet().getPlayingPlayerName(auction.getQuestioner().getGameIndex());
                    message = name + " raised to " + Integer.toString(auctionValue) + ".\nDo you wanna accept?";

                } else {

                    Print.debug("WARNING", "FX: GUIState AUCTION_ASKING was demanded but there is no implementation yet.");

                }


                auctionView("Bidding", message);




            }


            case WAIT_FOR_DECLARER -> {

                updateHandShelfs();
                fxController.getFxSkat().update();
            }

            case DECLARE_SKAT -> {

                buttonsAccept();
                skatView(true);

                updateHandShelfs();
                fxController.getFxSkat().update();
            }

            case DECLARE_TRUMPTYPE -> {

                auctionView("Game Type", """
                Declare the type of the Game
                S = Suit    [Default mode - You can coose trump color next]
                G = Grand   [Only jacks are trump]
                N = Null    [You only win if you lose every single trick]
           
                        """);
                buttonsChooseMode();
                updateHandShelfs();

            }

            case DECLARE_TRUMPCOLOR -> {
                updateHandShelfs();
            }

            case PLAYING_YOUR_MOVE -> {
                updateHandShelfs();
            }

            case PLAYING_NOT_YOUR_MOVE -> {
                updateHandShelfs();
            }

            case GAME_ABORTED -> {
            }

            case GAME_FINISHED -> {
            }

            case SET_FINISHED -> {
            }
        }

    }


    private static void auctionView(String title, String text){


        fxController.LabelAuctionViewTitle.setText(title);
        fxController.LabelAuctionViewText.setText(text);
        auctionView(true);

    }

    private static void auctionView(boolean visible){

        fxController.AnchorAuctionDialog.setVisible(visible);

    }


    private static void hideAll(){

        resultView(false);
        newGameView(false);
        skatView(false);
        auctionView(false);

        buttonsHide();

        fxController.AnchorGameMessage.setVisible(false); // this will probably collide with fade out -> delete later


    }

    private static void buttonsHide(){

        fxController.anchorButtonsPlayActions.setVisible(false);
    }

    private static void buttonsAccept(){

        var buttonDict = fxController.buttonDict;

        fxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA1").hide();
        buttonDict.get("PA2").hide();

        buttonDict.get("PA3").show();
        buttonDict.get("PA3").setImage(acceptButton);

        buttonDict.get("PA4").hide();
        buttonDict.get("PA5").hide();

    }

    private static void buttonsAcceptCancel(){

        var buttonDict = fxController.buttonDict;

        fxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA1").hide();

        buttonDict.get("PA2").setImage(acceptButton);
        buttonDict.get("PA2").show();

        buttonDict.get("PA3").hide();

        buttonDict.get("PA4").setImage(cancelButton);
        buttonDict.get("PA4").show();

        buttonDict.get("PA5").hide();

    }

    private static void buttonsChooseMode(){

        var buttonDict = fxController.buttonDict;

        fxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA1").hide();

        buttonDict.get("PA2").setImage(suitButton);
        buttonDict.get("PA2").show();

        buttonDict.get("PA3").setImage(grandButton);
        buttonDict.get("PA3").show();

        buttonDict.get("PA4").setImage(nullButton);
        buttonDict.get("PA4").show();


        buttonDict.get("PA5").hide();

    }

    private static void buttonsChooseColor(){

    }

    private static void skatView(boolean visible){

        fxController.AnchorViewSkat.setVisible(visible);
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

            var curGameNo = Integer.toString(set.currentGameNo());
            var gameAm = Integer.toString(set.getGameAmount());

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

            labelArray[i].setVisible(visible);

            if (visible) {  // change labels

                var player = set.getSkatSetPlayerAt(i);

                var score = player.getTotalScore();
                var scoreString = Integer.toString(score);
                scoreString = Print.times(9-scoreString.length(), "") + scoreString;

                var labelText = "P" + Integer.toString(i+1) + " " + player.getName() + " " + Print.times(21 - player.getName().length(),".") + " " + scoreString;

                labelArray[i].setText(labelText);

            }

        }

        fxController.LabelResultNewGame1.setText("");

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

        } else {

            fxController.buttonDict.get("NEXT").hide();
        }


        fxController.ImageViewWRNBackground.setImage(viewResult);

        for(var i = 0; i < 3; i++){

            labelArray[i].setVisible(visible);

            if (visible) {  // change labels

                var gamePlayer = set.getSkatPlayerAt(i);
                var setPlayer = set.currentSkatSetPlayer()[i];

                var score = setPlayer.getTotalScore();
                var name = setPlayer.getName();
                var scoreString = Integer.toString(score);
                scoreString = Print.times(9-scoreString.length(), "") + scoreString;

                String declarer = "  ";
                int gameScore = 0;

                if ( gamePlayer.isDeclarer() ){
                    declarer = "S ";
                    gameScore = set.getCurrentGameResult().getGameValue();
                }

                var labelText = declarer + "P" + Integer.toString(i+1) + setPlayer.getName() + " " + Print.times(14 - setPlayer.getName().length(),"." + " " + Integer.toString(gameScore) + scoreString);

                labelArray[i].setText(labelText);

            }

        }

        fxController.LabelResultNewGame1.setText("");

    }

    private static void updatePlayerInfo(){


    }

    private static void updateHandShelfs(){

        for (FXHandShelf shelf : fxController.getFxHandShelfs()) {

            shelf.update();

            if (shelf.getSelectedCardIndex() != -1) {

                shelf.getFXCardAt(shelf.getSelectedCardIndex()).setSelected(false);
                shelf.setSelectedCardIndex(-1);
            }
        }
    }




}
