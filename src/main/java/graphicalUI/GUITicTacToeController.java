package graphicalUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.*;

public class GUITicTacToeController {

    private TicTacToe game = new TicTacToe();

    private Player ai = new AiPlayer();
    private boolean aiIsPlaying = true;

    @FXML
    private Button quitButton;

    @FXML
    private Button newGameButton;

    @FXML
    private Button switchOpponentButton;

    @FXML
    private Label titleBar;
    @FXML
    private Label subtitleBar;

    @FXML
    private Rectangle topLeft;

    @FXML
    private Rectangle topCenter;

    @FXML
    private Rectangle topRight;

    @FXML
    private Rectangle centerLeft;
    @FXML
    private Rectangle centerCenter;
    @FXML
    private Rectangle centerRight;
    @FXML
    private Rectangle bottomLeft;
    @FXML
    private Rectangle bottomCenter;
    @FXML
    private Rectangle bottomRight;

    @FXML
    protected void onQuitPressed() {
        System.exit(0);
    }

    @FXML
    protected void onNewGamePressed() {
        game.reset();
        updateAll();
    }

    @FXML
    protected void onSwitchOpponentPressed() {
        if(game.getResult() == Result.NOT_STARTED) {
            aiIsPlaying = !aiIsPlaying;
        }
        updateAll();
    }

    private void updateLabels () {
        switch(game.getResult()) {
            case TIE:
                titleBar.setText("It's a tie! Nice game!");
                subtitleBar.setText("Start a new game by pressing the button");
                break;
            case X_WON:
                titleBar.setText("Blue did it! Congratulation!");
                subtitleBar.setText("Start a new game by pressing the button");
                break;
            case O_WON:
                titleBar.setText("Red won! Well played!");
                subtitleBar.setText("Start a new game by pressing the button");
                break;
            case NOT_STARTED:
                titleBar.setText("Start you're game against " + (aiIsPlaying ? "the computer!" : "another human!"));
                subtitleBar.setText("Just go ahead and tic a cell :)");
                break;
            case IN_PROGRESS:
                titleBar.setText("It's your move!");
                subtitleBar.setText("Just go ahead and tic a cell :)");
                break;
        }
    }

    @FXML
    protected void tappedTopLeft() {
        if(game.isRunning()) {
            game.ticCell(Position.TOPLEFT);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedTopCenter() {
        if(game.isRunning()) {
            game.ticCell(Position.TOPCENTER);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedTopRight() {
        if(game.isRunning()) {
            game.ticCell(Position.TOPRIGHT);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedCenterLeft() {
        if (game.isRunning()) {
            game.ticCell(Position.CENTERLEFT);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedCenterCenter() {
        if(game.isRunning()) {
            game.ticCell(Position.CENTERCENTER);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedCenterRight() {
        if(game.isRunning()) {
            game.ticCell(Position.CENTERRIGHT);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedBottomLeft() {
        if(game.isRunning()) {
            game.ticCell(Position.BOTTOMLEFT);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedBottomCenter() {
        if(game.isRunning()) {
            game.ticCell(Position.BOTTOMCENTER);
        }
        postMoveEvaluation();
    }
    @FXML
    protected void tappedBottomRight() {
        if(game.isRunning()) {
            game.ticCell(Position.BOTTOMRIGHT);
        }
        postMoveEvaluation();
    }

    private void postMoveEvaluation() {
        switchOpponentButton.setVisible(false);
        if(aiIsPlaying && game.isRunning()) {
            game.ticCell(ai.getNextInput(game.getGameBoard()));
        }
        updateAll();
    }

    private void updateAll() {
        updateBoard();
        updateLabels();
        updateButtonVisibility();
    }

    private void updateBoard() {
        GameBoard board = game.getGameBoard();
        topLeft.setFill(fromSign(board.getSign(Position.TOPLEFT)));
        topCenter.setFill(fromSign(board.getSign(Position.TOPCENTER)));
        topRight.setFill(fromSign(board.getSign(Position.TOPRIGHT)));
        centerLeft.setFill(fromSign(board.getSign(Position.CENTERLEFT)));
        centerCenter.setFill(fromSign(board.getSign(Position.CENTERCENTER)));
        centerRight.setFill(fromSign(board.getSign(Position.CENTERRIGHT)));
        bottomLeft.setFill(fromSign(board.getSign(Position.BOTTOMLEFT)));
        bottomCenter.setFill(fromSign(board.getSign(Position.BOTTOMCENTER)));
        bottomRight.setFill(fromSign(board.getSign(Position.BOTTOMRIGHT)));
    }

    public void updateButtonVisibility() {
        if(game.getResult() == Result.NOT_STARTED) {
            switchOpponentButton.setVisible(true);
            newGameButton.setVisible(false);
        } else {
            switchOpponentButton.setVisible(false);
            newGameButton.setVisible(true);
        }
    }
    private Color fromSign(Sign sign) {
        switch (sign) {
            case O:
                return Color.RED;
            case X:
                return Color.BLUE;
            case NONE:
                return Color.WHITE;
            default:
                return Color.WHITE;
        }
    }
}
