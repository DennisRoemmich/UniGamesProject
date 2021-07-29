package gui;

import engine.Controller;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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

    private Controller mChessController = new Controller();
    private ChessBoardNode mBoardNode = new ChessBoardNode(mChessController, this);

    private Optional<Square> mOrigin = Optional.empty();

    public GuiController() {
    	//Unused
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AiPlayer aiPlayer = new AiPlayer(mChessController);
        mChessController.setPresenter(this);
        mChessController.setPlayerA(aiPlayer);
        mChessController.setPlayerB(aiPlayer);
        mBoardPane.getChildren().add(mBoardNode);
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

        var game = mChessController.getGame();

        if (game.isEmpty() || !game.get().isGameRunning()) {
            return;
        }
    	
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
		        refreshOutput();
		        mChessController.queueMove(move.toJSon());
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
                mChessController.queueMove(move.toJSon());
                refreshOutput();
            } catch (Exception e) {
                mInputField.setAccessibleHelp("Invalid input.");
            }
            mInputField.setText("");
        } else {
            mChessController.newGame();
            mChessController.startGame();
        }
    }
    
//    @FXML
//    public void aiButtonClicked() {
//    	mChessController.setPlayerB(new AiPlayer(mChessController));
//    }

}
