package TicTacToeFX;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.Arrays;

public class SampleController {

    @FXML
    public ImageView upper_left;
    public ImageView background_image;

    public Pane pane;

    public ImageView tileUpperLeft;
    public ImageView tileUpperCenter;
    public ImageView tileUpperRight;
    public ImageView tileCenterLeft;
    public ImageView tileCenterCenter;
    public ImageView tileCenterRight;
    public ImageView tileLowerLeft;
    public ImageView tileLowerCenter;
    public ImageView tileLowerRight;

    public ImageView buttonQuit;
    public ImageView buttonGame;
    public ImageView buttonSettings;

    public Label player1Name;
    public Label player2Name;
    public Label player1Score;
    public Label player2Score;
    public ImageView player2underline;
    public ImageView player1underline;

    public boolean doWaitForKI = false;
    public boolean waitingForKI = false;

    private viewType currentViewType = viewType.GAME;

    private int[] gridState = new int[9];
    private ImageView[] imageViewArray;
    private TicTacToe game;



    private Image imgBackground = new Image("/Resources/Background.png");
    private Image imgBackgroundBlur = new Image("/Resources/Background_Aberrated.png");
    private Image imgBlackCross = new Image("/Resources/cross_black.png");
    private Image imgBlackCircle = new Image("/Resources/circle_black.png");
    private Image imgBlueCircle = new Image("/Resources/circle_blue.png");
    private Image imgRedCross = new Image("/Resources/cross_red.png");
    private Image imgKILabel = new Image("/Resources/KILabel.png");
    private Image imgModeLabel = new Image("/Resources/modeLabel.png");
    private Image imgPvKiLabel = new Image("/Resources/PvKiLabel.png");
    private Image imgPvKiLabelGrey = new Image("/Resources/PvKiLabelGrey.png");
    private Image imgPvPLabel = new Image("/Resources/PvPLabel.png");
    private Image imgPvPLabelGrey = new Image("/Resources/PvPLabelGrey.png");
    private Image imgRandomLabel = new Image("/Resources/randomLabel.png");
    private Image imgRandomLabelGrey = new Image("/Resources/randomLabelGrey.png");
    private Image imgStrongLabel = new Image("/Resources/strongLabel.png");
    private Image imgStrongLabelGrey = new Image("/Resources/strongLabelGrey.png");
    private Image imgGameOrange = new Image("/Resources/Game_orange.png");
    private Image imgGameGrey = new Image("/Resources/Game_grey.png");
    private Image imgSettingsOrange = new Image("/Resources/Settings_orange.png");
    private Image imgSettingsGrey = new Image("/Resources/Settings_grey.png");
    private Image imgApplyLabel = new Image("/Resources/applyLabel.png");
    /* INIT */

    public SampleController(){

        var player1 = "Mario";
        var player2 = "Luigi";

        Arrays.fill(gridState, 10); // 10 is empty box
        game = new TicTacToe(player1, player2);



    }

    @FXML
    public void initialize() {
        refreshGrid(false);
    }

    /* */

    private void switchView(viewType type){

        if(type == currentViewType) {
            return;
        }

        currentViewType = type;

        if(type == viewType.SETTINGS){

            // ermöglicht Einstellung erst beim verändern zu übernehmen
            diffChange = game.getBot().getDifficulty();
            modeKiChange = game.isKiActive();

            refreshSettings();

        }

        if(type == viewType.GAME){

            player1Name.setVisible(true);
            player2Name.setVisible(true);
            player1Score.setVisible(true);
            player2Score.setVisible(true);

            buttonGame.setImage(imgGameOrange);
            buttonSettings.setImage(imgSettingsGrey);

            clearTiles();
            refreshGrid(false);

        }
    }

    private void refreshSettings(){

        if(diffChange != game.getBot().getDifficulty() || modeKiChange != game.isKiActive()){
            buttonGame.setImage(imgApplyLabel);
        } else {
            buttonGame.setImage(imgGameGrey);
        }

        player1Name.setVisible(false);
        player2Name.setVisible(false);
        player1Score.setVisible(false);
        player2Score.setVisible(false);
        player1underline.setVisible(false);
        player2underline.setVisible(false);

        buttonSettings.setImage(imgSettingsOrange);

        tileUpperLeft.setImage(imgModeLabel);
        tileCenterLeft.setImage(imgKILabel);

        if(modeKiChange){
            tileUpperCenter.setImage(imgPvKiLabel);
            tileUpperRight.setImage(imgPvPLabelGrey);
        } else {
            tileUpperCenter.setImage(imgPvKiLabelGrey);
            tileUpperRight.setImage(imgPvPLabel);
        }

        if(diffChange == TicBot.Difficulty.STRONG){
            tileCenterCenter.setImage(imgStrongLabel);
            tileCenterRight.setImage(imgRandomLabelGrey);
        } else {
            tileCenterCenter.setImage(imgStrongLabelGrey);
            tileCenterRight.setImage(imgRandomLabel);
        }

    }

    private void clearTiles(){
        var tileArray = new ImageView[]{tileUpperLeft, tileUpperCenter, tileUpperRight, tileCenterLeft, tileCenterCenter, tileCenterRight, tileLowerLeft, tileLowerCenter, tileLowerRight};
        Arrays.fill(gridState, 10);

        for (ImageView tile : tileArray) {
            tile.setImage(null);
        }
    }

    private enum viewType {
        SETTINGS,
        GAME
    }


    public void refreshFXBoard(){

        refreshGrid(false);

    }

    public void refreshGrid(boolean blurr){

        System.out.println("refreshGrid() was called");
        if (tileUpperLeft == null){
            System.out.println("and tileUpperLeft is null");
        }

        imageViewArray = new ImageView[]{tileUpperLeft, tileUpperCenter, tileUpperRight, tileCenterLeft, tileCenterCenter, tileCenterRight, tileLowerLeft, tileLowerCenter, tileLowerRight};

        for(var i = 0; i < 9; i++){
            if (gridState[i] != game.getBoard().getIntBoard()[i]){
                gridState[i] = game.getBoard().getIntBoard()[i];
                switch (gridState[i]){
                    case 1 ->
                        imageViewArray[i].setImage(imgBlackCross);
                    case -1 ->
                        imageViewArray[i].setImage(imgBlackCircle);
                    case 2 ->     // this is the code for cross-winning-box (red)
                        imageViewArray[i].setImage(imgRedCross);
                    case -2 ->    // this is the code for circle-winning box (blue)
                        imageViewArray[i].setImage(imgBlueCircle);
                    default ->
                        imageViewArray[i].setImage(null);
                }
            }
        }


            if(game.getActivePlayer() == 2 && !game.isKiActive()){
                player1underline.setVisible(false);
                player2underline.setVisible(true);
            } else if(game.getActivePlayer() == 1 && !game.isKiActive()){
                player1underline.setVisible(true);
                player2underline.setVisible(false);
            } else {
                player1underline.setVisible(true);
                player2underline.setVisible(true);
            }

            if(blurr){
                    background_image.setImage(imgBackgroundBlur);
            } else {
                background_image.setImage(imgBackground);
            }

            player1Name.setText(game.getCurrentPlayerName1());
            player2Name.setText(game.getCurrentPlayerName2());

            if(game.isKiActive()){
                if(game.getKiPlayer() == 1){
                    System.out.print("this was called");
                    player1Score.setText(Integer.toString(game.getScoreVSKi()[1]));
                    player2Score.setText(Integer.toString(game.getScoreVSKi()[0]));
                } else {
                    player1Score.setText(Integer.toString(game.getScoreVSKi()[0]));
                    player2Score.setText(Integer.toString(game.getScoreVSKi()[1]));
                }
            } else {
                player1Score.setText(Integer.toString(game.getScorePlayers()[0]));
                player2Score.setText(Integer.toString(game.getScorePlayers()[1]));
            }


    }


    public void handleSubmitButtonAction(ActionEvent actionEvent) {

    }


    public void tileUpperLeftClicked(MouseEvent mouseEvent) {
        tileClicked(0);
    }

    public void tileUpperCenterClicked(MouseEvent mouseEvent) {
        tileClicked(1);
    }

    public void tileUpperRightClicked(MouseEvent mouseEvent) {
        tileClicked(2);
    }

    public void tileCenterLeftClicked(MouseEvent mouseEvent) {
        tileClicked(3);
    }

    public void tileCenterCenterClicked(MouseEvent mouseEvent) {
        tileClicked(4);
    }

    public void tileCenterRightClicked(MouseEvent mouseEvent) {
        tileClicked(5);
    }

    public void tileLowerLeftClicked(MouseEvent mouseEvent) {
        tileClicked(6);
    }

    public void tileLowerCenterClicked(MouseEvent mouseEvent) {
        tileClicked(7);
    }

    public void tileLowerRightClicked(MouseEvent mouseEvent) {
        tileClicked(8);
    }

    public void buttonQuitPressed(MouseEvent mouseEvent) {
        Platform.exit();
    }

    public void buttonGamePressed(MouseEvent mouseEvent) {
        if (currentViewType == viewType.SETTINGS){

            if(modeKiChange != game.isKiActive() || diffChange != game.getBot().getDifficulty()) {

                if (modeKiChange != game.isKiActive()) {
                    game.setKI(modeKiChange);
                }
                if (diffChange != game.getBot().getDifficulty()) {
                    game.getBot().setDifficulty(diffChange);
                }

                game.restartGame();
            }

            switchView(viewType.GAME);

        }
    }

    public void buttonSettingsPressed(MouseEvent mouseEvent) {
        if (currentViewType != viewType.SETTINGS){
            switchView(viewType.SETTINGS);
        }
    }

    private TicBot.Difficulty diffChange;
    private boolean modeKiChange;
    private boolean waitOneClick = true;

    private void tileClicked(int atIndex){

        // Click in den Settings
        if(currentViewType == viewType.SETTINGS){

            switch (atIndex) {
                case 1 -> {
                    modeKiChange = true;
                }
                case 2 -> {
                    modeKiChange = false;
                }
                case 4 -> {
                    diffChange = TicBot.Difficulty.STRONG;
                }
                case 5 -> {
                    diffChange = TicBot.Difficulty.RANDOM;
                }
                default -> {
                }
            }

            refreshSettings();

        } else if (!waitingForKI) {

            var blurr = false;
            int gameState = 0;

            if(!waitOneClick){
                waitOneClick = true;
                game.restartGame();
            } else {
                gameState = game.setFXMove(atIndex, this);
                if (gameState != 0) {
                        waitOneClick = false;
                        blurr = true;
                }
            }

            if(doWaitForKI){
                Task<Void> sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(450);
                        } catch (InterruptedException e) {
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        waitingForKI = false;
                        doWaitForKI = false;
                        refreshGrid(false);
                    }
                });
                new Thread(sleeper).start();
            } else {
                refreshGrid(blurr);
            }



            if (gameState == 3) {
                player1underline.setVisible(true);
                player2underline.setVisible(true);
            } else if (gameState == 2) {
                player1underline.setVisible(false);
                player2underline.setVisible(true);
            } else if (gameState == 1) {
                player1underline.setVisible(true);
                player2underline.setVisible(false);
            }

        }

    }






}
