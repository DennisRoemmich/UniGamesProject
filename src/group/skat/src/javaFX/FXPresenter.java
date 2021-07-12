package javaFX;


import console.Print;
import javafx.scene.control.Label;
import javafx.scene.image.Image;


public class FXPresenter {

    private static FXController fxController;

    private static Image viewNewGame = new Image("./images/Views/ViewNewGame.png");
    private static Image viewResult = new Image("./images/Views/ResultView.png");

    private static Image backGroundBlank = new Image("./images/Views/ViewBackgroundBlank.png");
    private static Image backGroundShelfs = new Image("./images/Views/ViewBackgroundShelfs.png");

    private static Image acceptButton = new Image("./images/Buttons/Button2Accept.png");
    private static Image greyButton = new Image("./images/Buttons/Button3GameColor.png");
    private static Image cancelButton = new Image("./images/Buttons/Button4Cancel.png");

    public static void setFxController(FXController fxController){

        FXPresenter.fxController = fxController;

    }

    public static void update(){

        hideAll();

        switch (fxController.getState()) {

            case NOT_STARTED -> {

                fxController.ImageViewBackground.setImage(backGroundBlank);
                resultView(false);
                newGameView(true);

            }
            case AUCTION_WATCHING -> {

                fxController.ImageViewBackground.setImage(backGroundShelfs);
                fxController.AnchorGameMessage.setVisible(true);


            }
            case AUCTION_ASKING -> {

                fxController.ImageViewBackground.setImage(backGroundShelfs);
                fxController.AnchorGameMessage.setVisible(true);

                var auctionValue = fxController.getController().getGame().getAuction().getNextAuctionValue();
                String message = "Do you want to raise on " + Integer.toString(auctionValue) + "?";
                fxController.LabelGameMessage.setText(message);

                buttonsAcceptCancel();

            }
            case AUCTION_HEARING -> {

                fxController.ImageViewBackground.setImage(backGroundShelfs);
                fxController.AnchorGameMessage.setVisible(true);

                var auctionValue = fxController.getController().getGame().getAuction().getAuctionValue();
                String message = "Your opponenent raised to " + Integer.toString(auctionValue) + ".\nDo you wanna accept?";
                fxController.LabelGameMessage.setText(message);

                buttonsAcceptCancel();

            }
            case WAIT_FOR_DECLARER -> {
            }
            case DECLARE_SKAT -> {
            }
            case DECLARE_TRUMPTYPE -> {
            }
            case DECLARE_TRUMPCOLOR -> {
            }
            case PLAYING_YOUR_MOVE -> {
            }
            case PLAYING_NOT_YOUR_MOVE -> {
            }
            case GAME_ABORTED -> {
            }
            case GAME_FINISHED -> {
            }
            case SET_FINISHED -> {
            }
        }

    }


    private static void Auction(){

        resultView(false);
        newGameView(false);

    }

    private static void hideAll(){

        resultView(false);
        newGameView(false);

    }

    private static void buttonsHide(){

        fxController.anchorButtonsPlayActions.setVisible(false);

    }

    private static void buttonsAcceptCancel(){

        var buttonDict = fxController.buttonDict;

        fxController.anchorButtonsPlayActions.setVisible(true);

        buttonDict.get("PA1").hide();
        buttonDict.get("PA2").setImage(acceptButton);
        buttonDict.get("PA3").hide();
        buttonDict.get("PA4").setImage(cancelButton);
        buttonDict.get("PA5").hide();

    }

    private static void buttonsChooseMode(){

    }

    private static void buttonsChooseColor(){

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

        if ( visible ) {

            var curGameNo = Integer.toString(set.currentGameNo());
            var gameAm = Integer.toString(set.getGameAmount());

            if (gameAm.equals("-1")){
                gameAm = "∞";
            }

            fxController.LabelGameNo.setText(curGameNo + "       " + gameAm);

        }

        fxController.LabelGameNo.setVisible(visible);

        if( visible ){
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


        if( visible ){
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


    }




}
