package chessgui;

import engine.Controller;
import engine.GameOwner;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import engine.pieces.PositionedPiece;
import javafx.application.Platform;
import engine.squares.Square;
import chessframework.Player;
import chessframework.Presenter;
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

/**
 * Controller for the GUI engine.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class GuiController extends GuiMenuController implements Player, Presenter, GuiEventHandler {

    private GameOwner mGameOwner;
    private ChessBoardNode mBoardNode;

    private final ReadWriteLock mBoardNodeLock = new ReentrantReadWriteLock();

    private Optional<Square> mOrigin = Optional.empty();

    private LinkedBlockingQueue<JSONObject> mRequestQueue = new LinkedBlockingQueue<>();

    private Queue<Thread> mManagedThreads = new ConcurrentLinkedDeque<>();

    private boolean mAcceptMoveInput = false;

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
        Platform.runLater(new Refresher());
    }
    
    

    @Override
    public void handlePieceClicked(PositionedPiece piece) {
        handleSquareClicked(piece.getPosition());
    }

    @Override
    public void handleSquareClicked(Square clickedSquare) {

        var game = mGameOwner.getGame();

        if (!mAcceptMoveInput) {
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
        mBoardNodeLock.writeLock().lock();
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
        } finally {
            mBoardNodeLock.writeLock().unlock();
        }
        refreshOutput();
    }

    private void handleDestinationClicked(Square clickedSquare, List<ChessMove> possibleMoves) {
        for (ChessMove move : possibleMoves) {
            boolean originEqual = move.getOrigin().equals(mOrigin.get());
            boolean destinationEqual = move.getDestination().equals(clickedSquare);
            if (originEqual && destinationEqual) {
                mAcceptMoveInput = false;
                refreshOutput();
                makeMove(move.toJSon());
                break;
            }
        }
        mBoardNodeLock.writeLock().lock();
        try {
            mBoardNode.resetPlaceholder();
        } finally {
            mBoardNodeLock.writeLock().unlock();
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
            var game = mGameOwner.getGame();
            if (mAcceptMoveInput && game.isPresent()) {
                try {
                    ChessMove move = ChessMove.valueOf(mInputField.getText(), game.get());
                    makeMove(move.toJSon());
                } catch (Exception e) {
                    // Move invalid
                }
            }
        }
    }

    @Override
    public void startGame() {
        Thread controllerThread;

        if (isNetworkGame() && !isHost()) {
            ClientController clientController = new ClientController(new ConsoleNetworkClientIO(), this, this);
            mGameOwner = clientController;
            controllerThread = new Thread(clientController);
            clientController.setupConnection();
        } else {
            Controller controller = new Controller(this, this);
            mGameOwner = controller;

            controller.setPlayerB(createPlayerB(controller));

            var gameLog = getGameLog();
            if (gameLog.isPresent()) {
                controller.replayLog(gameLog.get());
            } else {
                controller.setGameMode(isClassicalChess());
                controller.newGame();
            }
            controllerThread = new Thread(controller);
        }
        setIsInConfiguration(false);
        mBoardNodeLock.writeLock().lock();
        try {
            var game = mGameOwner.getGame();
            if (game.isPresent()) {
                mBoardNode.setBoard(Optional.of(game.get().getBoard()));
            }
        } finally {
            mBoardNodeLock.writeLock().unlock();
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

    private void makeMove(JSONObject moveJSon) {
        mGameOwner.addMoveToQueue(moveJSon);
    }

    public void addThreadToManager(Thread thread) {
        if (!mManagedThreads.contains(thread)) {
            mManagedThreads.add(thread);
        }
    }

    public BlockingQueue<JSONObject> getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void requestMove(JSONObject moveType) {
        Platform.runLater(new MoveRequester());
    }

    private class MoveRequester implements Runnable {
        @Override
        public void run() {
            mAcceptMoveInput = true;
            refreshOutput();
        }
    }
    
    private class Refresher implements Runnable {
        @Override
        public void run() {
            mBoardNodeLock.writeLock().lock();
            try {
                mBoardNode.refreshNode();
            } finally {
                mBoardNodeLock.writeLock().unlock();
            }
            refreshGameState();
            refreshConfigurationView();
        }

        private void refreshGameState() {
            var game = mGameOwner.getGame();
            if (game.isPresent()) {
                switch (GameOverDetector.checkForMate(game.get())) {
                    case CHECKMATE:
                        mInfoLabel.setText("Checkmate.");
                        setIsInConfiguration(true);
                        return;
                    case STALEMATE:
                        mInfoLabel.setText("Stalemate.");
                        setIsInConfiguration(true);
                        return;
                    case DRAW:
                        mInfoLabel.setText("Draw.");
                        setIsInConfiguration(true);
                        return;
                    case NONE:
                        mInfoLabel.setText("It's " + game.get().getCurrentColor().toString() + "'s turn.");
                }
            } else {
                mInfoLabel.setText("No game is running.");
            }
        }
    }
}
