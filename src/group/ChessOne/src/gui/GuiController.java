package gui;

import engine.Controller;
import engine.pieces.PositionedPiece;
import engine.squares.Square;
import framework.Player;
import framework.Presenter;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import npc.AiPlayer;
import org.json.simple.JSONObject;

public class GuiController implements Player, Presenter, GuiEventHandler, EventHandler<KeyEvent> {

    private ChessGUI gui;
    private Controller chessController;

    public GuiController(ChessGUI gui) {
        chessController = new Controller();
        chessController.newGame();
    }

    public void start() {
        AiPlayer aiPlayer = new AiPlayer(chessController);
        chessController.setPlayerA(aiPlayer);
        chessController.setPlayerB(aiPlayer);
        chessController.newGame();
        chessController.startGame();
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        return null;
    }

    @Override
    public void refreshOutput() {

    }

    @Override
    public void handlePieceClicked(PositionedPiece piece) {

    }

    @Override
    public void handleSquareClicked(Square square) {

    }

    @Override
    public void handle(KeyEvent event) {
        switch(event.getCode()) {
            case SPACE:
                start();
        }
    }

    public Controller getChessController() {
        return chessController;
    }
}
