package gui;

import engine.Controller;
import engine.Chess;
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
import npc.AiPlayer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.simple.JSONObject;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("rawtypes")
public class GuiController implements Initializable, Player, Presenter, GuiEventHandler, EventHandler {

    @FXML
    private AnchorPane mBoardPane;
    @FXML
    private Button mUniversalButton;
    @FXML
    private Label mOutputLabel;
    @FXML
    private TextField mInputField;
    @FXML
    private Button mIsAi;

    private Controller mChessController;
    private ChessBoardNode mBoardNode;

    private Optional<Square> mOrigin = Optional.empty();
    private LinkedBlockingQueue<JSONObject> mEngineQueue = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<JSONObject> mReplyQueue = new LinkedBlockingQueue<>();
    private Thread mEngineThread;

    public GuiController() {
    	//Unused
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mChessController = new Controller(mEngineQueue);
        mEngineThread = new Thread(mChessController);

        AiPlayer aiPlayer = new AiPlayer(mChessController);
        mBoardNode = new ChessBoardNode(mChessController, this);

        mChessController.setPresenter(this);
        mChessController.setPlayerA(aiPlayer);
        mChessController.setPlayerB(this);
        mBoardPane.getChildren().add(mBoardNode);

        mEngineThread.start();
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        refreshOutput();
        return null;
    }

    @Override
    public void refreshOutput() {
        mBoardNode.refreshNode();
    }

    @Override
    public void handlePieceClicked(PositionedPiece piece) {
        handleSquareClicked(piece.getPosition());
    }

    @Override
    public void handleSquareClicked(Square clickedSquare) {
        refreshOutput();

        var game = mChessController.getGame();

        /*if (game.isEmpty() || !game.get().isGameRunning()) {
            return;
        }*/
    	
        var possibleMoves = game.get().getPossibleMoves();

        if (mOrigin.isEmpty()) {
            handleOriginClicked(clickedSquare, possibleMoves);
            refreshOutput();
        } else {
            handleDestinationClicked(clickedSquare, possibleMoves);
        }
        refreshOutput();
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
        if (game.isPresent() && game.get().isGameRunning()) {
            try {
                ChessMove move = ChessMove.valueOf(mInputField.getText(), game.get());
                makeMove(move.toJSon());

            } catch (Exception e) {
                mInputField.setAccessibleHelp("Invalid input.");
            }
            mInputField.setText("");
        } else {
            mChessController.newGame();
            mChessController.startGame();
        }
    }

    private void makeMove(JSONObject moveJSON) {
        mEngineQueue.add(moveJSON);
    }
    
//    @FXML
//    public void aiButtonClicked() {
//    	mChessController.setPlayerB(new AiPlayer(mChessController));
//    }

}
