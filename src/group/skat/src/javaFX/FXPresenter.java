package javaFX;


import javafx.scene.image.Image;

public class FXPresenter {

    private static FXController fxController;

    private static Image viewNewGame = new Image("ViewNewGame");



    public static void setFxController(FXController fxController){

        FXPresenter.fxController = fxController;

    }

    public static void update(){

        switch (fxController.getState()) {

            case NOT_STARTED -> {



            }
            case AUCTION_WATCHING -> {
            }
            case AUCTION_ASKING -> {
            }
            case AUCTION_HEARING -> {
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

    private static void newGameView(boolean visible){



        fxController.AnchorWelcomeResultNewGameView.setVisible(visible);

        if (visible) {
            fxController.ImageViewWRNBackground.setImage(viewNewGame);
        }

        fxController.LabelResultNewGame1.setVisible(visible);
        fxController.LabelResultNewGame2.setVisible(visible);
        fxController.LabelResultNewGame3.setVisible(visible);

        fxController.IVButtonNext.setVisible(visible);

    }

    private static void updatePlayerInfo(){


    }

    private static void updateHandShelfs(){


    }




}
