package src.gui;

import engine.Controller;
import engine.Chess;
import engine.board.ChessMove;
import engine.pieces.PositionedPiece;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.AnchorPane;
import engine.squares.Square;
import framework.Player;
import framework.Presenter;
import npc.AiPlayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import npc.AiRatingEngine;
import npc.AiRatingResult;
import org.json.simple.JSONObject;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("rawtypes")
public class GuiController implements Initializable, Player, Presenter, GuiEventHandler, EventHandler {

    @FXML
    private AnchorPane mBoardPane;
    @FXML
    private Label ratingLabel;
    @FXML
    private TextField mInputField;
    @FXML
    private Button mIsAi;

    private Controller mChessController;
    private ChessBoardNode mBoardNode;

    private Optional<Square> mOrigin = Optional.empty();
    private LinkedBlockingQueue<JSONObject> mRequestQueue = new LinkedBlockingQueue<>();

    private boolean acceptMoveInput = false;

    public GuiController() {
    	//Unused
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mChessController = new Controller();

        AiPlayer aiPlayerA = new AiPlayer(mChessController);
        AiPlayer aiPlayerB = new AiPlayer(mChessController);
        mBoardNode = new ChessBoardNode(mChessController, this);

        mChessController.setPresenter(this);
        mChessController.setPlayerA(this);
        mChessController.setPlayerB(this);
        mBoardPane.getChildren().add(mBoardNode);
        refreshOutput();
    }

    @Override
    public void refreshOutput() {
        mBoardNode.refreshNode();
        var game = mChessController.getGame();
        if (game.isPresent()) {
            ratingLabel.setText(String.valueOf(game.get().getBoard().hashCode()));
        }
    }

    @Override
    public void handlePieceClicked(PositionedPiece piece) {
        handleSquareClicked(piece.getPosition());
    }

    @Override
    public void handleSquareClicked(Square clickedSquare) {
        refreshOutput();

        var game = mChessController.getGame();

        if (!mChessController.isItMyTurn(this)) {
            return;
        }

        if (game.isPresent()) {
            var possibleMoves = game.get().getPossibleMoves();

            if (mOrigin.isEmpty()) {
                handleOriginClicked(clickedSquare, possibleMoves);
                refreshOutput();
            } else {
                handleDestinationClicked(clickedSquare, possibleMoves);
            }
        }
    }

	private void handleOriginClicked(Square clickedSquare, List<ChessMove> possibleMoves) {
		mBoardNode.resetPlaceholder();
		boolean possibleOriginFound = false;
		for (ChessMove move : possibleMoves) {
		    if (move.getOrigin().equals(clickedSquare)) {
		        possibleOriginFound = true;
		        mBoardNode.addPlaceholder(move.getDestination());
		    }
		}
		if (possibleOriginFound) {
		    mOrigin = Optional.of(clickedSquare);
		} else {
		    mBoardNode.resetPlaceholder();
		}
	}

	private void handleDestinationClicked(Square clickedSquare, List<ChessMove> possibleMoves) {
		for (ChessMove move : possibleMoves) {
		    boolean originEqual = move.getOrigin().equals(mOrigin.get());
		    boolean destinationEqual = move.getDestination().equals(clickedSquare);
		    if (originEqual && destinationEqual) {
		        mBoardNode.resetPlaceholder();
                makeMove(move.toJSon());
		        break;
		    }
		}
		mBoardNode.resetPlaceholder();
		mOrigin = Optional.empty();
	}

    @Override
    public void handle(Event event) {
    	//Unused
    }

    public Controller getChessController() {
        return mChessController;
    }

    @FXML
    public void buttonClicked() {
        var game = mChessController.getGame();
        if (game.isPresent() && !game.get().isGameOver()) {
            try {
                ChessMove move = ChessMove.valueOf(mInputField.getText(), game.get());
                makeMove(move.toJSon());
            } catch (Exception e) {
                mInputField.setAccessibleHelp("Invalid input.");
            }
            mInputField.setText("");
        } else if (game.isPresent()) {
            startGame();
        } else {
            mChessController.newGame();
            startGame();
        }
        refreshOutput();
    }

    private void makeMove(JSONObject moveJSON) {
        mChessController.getMoveQueue().add(moveJSON);
    }

    private void startGame() {
        if(mChessController.getGame().isPresent()) {
            Thread engineThread = new Thread(mChessController);
            engineThread.start();
        }
    }

    @Override
    public BlockingQueue<JSONObject> getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void requestMove(JSONObject previousMove) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                acceptMoveInput = true;
                refreshOutput();
            }
        });
    }

}
