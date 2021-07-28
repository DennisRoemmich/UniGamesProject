package gui;

import engine.Controller;
import engine.board.ChessBoard;
import engine.board.ChessMove;
import engine.pieces.PositionedPiece;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import engine.squares.Square;
import framework.Player;
import framework.Presenter;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import npc.AiPlayer;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GuiController implements Initializable, Player, Presenter, GuiEventHandler, EventHandler {

    @FXML
    private AnchorPane boardPane;
    @FXML
    private Button universalButton;
    @FXML
    private Label outputLabel;
    @FXML
    private TextField inputField;

    private Controller chessController = new Controller();
    private ChessBoardNode boardNode = new ChessBoardNode(chessController, this);

    private Optional<Square> origin = Optional.empty();

    public GuiController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AiPlayer aiPlayer = new AiPlayer(chessController);
        chessController.setPresenter(this);
        chessController.setPlayerA(this);
        chessController.setPlayerB(aiPlayer);
        boardPane.getChildren().add(boardNode);
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        refreshOutput();
        return null;
    }

    @Override
    public void refreshOutput() {
        boardNode.refreshNode();
    }

    @Override
    public void handlePieceClicked(PositionedPiece piece) {
        handleSquareClicked(piece.getPosition());
    }

    @Override
    public void handleSquareClicked(Square clickedSquare) {

        var possibleMoves = chessController.getGame().getPossibleMoves();

        if(origin.isEmpty()) {
            boardNode.resetPlaceholder();
            boolean possibleOriginFound = false;
            for(ChessMove move : possibleMoves) {
                if(move.getOrigin().equals(clickedSquare)) {
                    possibleOriginFound = true;
                    boardNode.addPlaceholder(move.getDestination());
                }
            }
            if(possibleOriginFound) {
                origin = Optional.of(clickedSquare);
            } else {
                boardNode.resetPlaceholder();
            }
            refreshOutput();
        } else {
            for(ChessMove move : possibleMoves) {
                boolean originEqual = move.getOrigin().equals(origin.get());
                boolean destinationEqual = move.getDestination().equals(clickedSquare);
                if(originEqual && destinationEqual) {
                    boardNode.resetPlaceholder();
                    refreshOutput();
                    chessController.executeMove(move.toJSon());
                    break;
                }
            }
            boardNode.resetPlaceholder();
            origin = Optional.empty();
        }
        refreshOutput();
    }

    @Override
    public void handle(Event event) {
    }

    public Controller getChessController() {
        return chessController;
    }

    @FXML
    public void buttonClicked() {
        if(chessController.getGame() != null && chessController.getGame().isGameRunning()) {
            try {
                ChessMove move = ChessMove.valueOf(inputField.getText(), chessController.getGame());
                chessController.executeMove(move.toJSon());
                refreshOutput();
            } catch (Exception e) {
                inputField.setAccessibleHelp("Invalid input.");
            }
            inputField.setText("");
        } else {
            chessController.newGame();
            chessController.startGame();
        }
    }

}
