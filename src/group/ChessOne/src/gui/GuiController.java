package gui;

import engine.Controller;
import engine.GameOwner;
import engine.board.ChessMove;
import engine.pieces.PositionedPiece;
import javafx.application.Platform;
import javafx.scene.control.Button;
import engine.squares.Square;
import framework.Player;
import framework.Presenter;
import network.ClientController;
import network.ConsoleNetworkClientIO;
import network.NetworkPlayer;
import npc.AiPlayer;
import javafx.fxml.FXML;
import org.json.simple.JSONObject;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SuppressWarnings("rawtypes")
public class GuiController extends GuiMenuController implements Player, Presenter, GuiEventHandler {

    private GameOwner gameOwner;
    private ChessBoardNode mBoardNode;

    private final ReadWriteLock boardNodeLock = new ReentrantReadWriteLock();

    private Optional<Controller> mChessController;

    private Optional<Square> mOrigin = Optional.empty();

    private LinkedBlockingQueue<JSONObject> mRequestQueue = new LinkedBlockingQueue<>();

    private Queue<Thread> managedThreads = new ConcurrentLinkedDeque<>();

    private boolean acceptMoveInput = false;

    public GuiController() {
    	//Unused
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        mBoardNode = new ChessBoardNode(this);
        mBoardPane.getChildren().add(mBoardNode);
    }

    @Override
    public void refreshOutput() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                boardNodeLock.writeLock().lock();
                try {
                    mBoardNode.refreshNode();
                } finally {
                    boardNodeLock.writeLock().unlock();
                }
            }
        });
    }

    @Override
    public void handlePieceClicked(PositionedPiece piece) {
        handleSquareClicked(piece.getPosition());
    }

    @Override
    public void handleSquareClicked(Square clickedSquare) {

        var game = gameOwner.getGame();

        if (!acceptMoveInput) {
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
        boardNodeLock.writeLock().lock();
        try {
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
        finally {
            boardNodeLock.writeLock().unlock();
        }
        refreshOutput();
	}

	private void handleDestinationClicked(Square clickedSquare, List<ChessMove> possibleMoves) {
		for (ChessMove move : possibleMoves) {
		    boolean originEqual = move.getOrigin().equals(mOrigin.get());
		    boolean destinationEqual = move.getDestination().equals(clickedSquare);
		    if (originEqual && destinationEqual) {
                acceptMoveInput = false;
                refreshOutput();
                makeMove(move.toJSon());
		        break;
		    }
		}
		boardNodeLock.writeLock().lock();
		try {
            mBoardNode.resetPlaceholder();
        } finally {
            boardNodeLock.writeLock().unlock();
        }
		mOrigin = Optional.empty();
	}

    @FXML
    public final void universalButtonClicked() {
        if (isInConfiguration()) {
            setIsInConfiguration(false);
            refreshConfigurationView();
            startGame();
        } else {
            var game = gameOwner.getGame();
            if (acceptMoveInput && game.isPresent()) {
                try {
                    ChessMove move = ChessMove.valueOf(mInputField.getText(), game.get());
                    makeMove(move.toJSon());
                } catch (Exception e) {

                }
            }
        }
    }

	@Override
    public void startGame() {
        mChessController = Optional.empty();
        Thread controllerThread;

        if (isNetworkGame() && !isHost()) {
            ClientController clientController = new ClientController(new ConsoleNetworkClientIO(), this, this);
            gameOwner = clientController;
            controllerThread = new Thread(clientController);
            clientController.setupConnection();
        } else {
            mChessController = Optional.of(new Controller(this, this));
            gameOwner = mChessController.get();

            mChessController.get().setPlayerB(createPlayerB(mChessController.get()));

            var gameLog = getGameLog();
            if (gameLog.isPresent()) {
                mChessController.get().replayLog(gameLog.get());
            } else {
                mChessController.get().setGameMode(isClassicalChess());
                mChessController.get().newGame();
            }
            controllerThread = new Thread(mChessController.get());
        }
        setIsInConfiguration(false);
        boardNodeLock.writeLock().lock();
        try {
            mBoardNode.setBoard(Optional.of(gameOwner.getGame().get().getBoard()));
        }
        finally {
            boardNodeLock.writeLock().unlock();
        }
        addThreadToManager(controllerThread);
        controllerThread.start();
        refreshOutput();
    }

    private Player createPlayerB(Controller controller) {
        Player playerB;
        if (isNetworkGame()) {
            String gameModeName = isClassicalChess() ? "Classical" : "Torpedo";
            playerB = new NetworkPlayer(controller, gameModeName);
        } else if (isAiGame()) {
            playerB = new AiPlayer(controller);
        } else {
            playerB = this;
        }
        return playerB;
    }

    private void makeMove(JSONObject moveJSON) {
        gameOwner.addMoveToQueue(moveJSON);
    }

    public void addThreadToManager(Thread thread) {
        if (!managedThreads.contains(thread)) {
            managedThreads.add(thread);
        }
    }

    @Override
    public BlockingQueue<JSONObject> getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void requestMove(JSONObject moveType) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                acceptMoveInput = true;
                refreshOutput();
            }
        });
    }
}
