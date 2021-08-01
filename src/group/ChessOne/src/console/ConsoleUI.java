package console;

import engine.Chess;
import engine.GameOwner;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import framework.*;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The console UI to interact with the chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleUI implements Runnable, Presenter, Player {


	private final Scanner mScanner = new Scanner(System.in);
    private GameOwner mGameOwner;
    private Chess mChessGame = null;
    private LinkedBlockingQueue<JSONObject> mRequestQueue = new LinkedBlockingQueue<>();
    private int mLastPrintedBoardHash = 0;

    private BlockingQueue<String> mSuperQueue;

    public ConsoleUI(BlockingQueue<String> superQueue) {
		this.mSuperQueue = superQueue;
	}

	@Override
	public void run() {
    	gameLoop();
	}

	public void gameLoop() {
    	boolean stopFlag = false;
    	while (!stopFlag) {

			while (mRequestQueue.peek() == null) {
				// Waiting for request
			}
			var request = mRequestQueue.poll();

			if (!request.containsKey("type")) {
				continue;
			} else if (request.containsKey("stopFlag")) {
				stopFlag = true;
			}

			if (request.get("type").toString().equals("quit")) {
				return;
			}

			PrintToConsole.println("Please enter your move (e.g. \"e4\" or \"Nf3\"):");
			String input = mScanner.nextLine();
			handleMoveInput(request, input);
		}
	}

	private void handleMoveInput(JSONObject request, String input) {
		if (!checkSpecialInput(input)) {
			try {
				ChessMove move = ChessMove.valueOf(input, mChessGame);
				for (ChessMove moveToCheck : mChessGame.getPossibleMoves()) {
					if (move.equals(moveToCheck)) {
						mGameOwner.addMoveToQueue(move.toJSon());
						break;
					}
				}
			} catch (Exception e) {
				PrintToConsole.println("The given input couldn't be resolved.");
				mRequestQueue.add(request);
			}
		}
	}

	public void requestMove(JSONObject dataType) {
    	refreshOutput();
    	mRequestQueue.add(dataType);
	}

    public boolean checkSpecialInput(String input) {

		if ("quit".equalsIgnoreCase(input)) {
			mSuperQueue.add("quit");
			return true;
		}
		if ("help".equalsIgnoreCase(input)) {
			GamePrinter.printHelp();
			return true;
		}
		if ("undo".equalsIgnoreCase(input)) {
			mSuperQueue.add("undo");
			return true;
		}
		if ("menu".equalsIgnoreCase(input)) {
			PrintToConsole.println("Settings:");
			PrintToConsole.println("Set Auto-[P]romotion on/off");
			PrintToConsole.println("Any input to continue the game");

			String newInput = mScanner.nextLine();
			if ("p".equalsIgnoreCase(newInput)) {
				autoPromotion();
				return true;
			}
		}
		return false;
    }

    private void autoPromotion() {
    	
    	if (mChessGame.getAutoPromotion()) {
    		mChessGame.setAutoPromotion(false);
    		PrintToConsole.println("Auto-Promotion turned off");
    	} else {
    		mChessGame.setAutoPromotion(true);
    		PrintToConsole.println("Auto-Promotion turned on");
    	}
    	
	}
    
    public char setPromotionPiece() {
    	PrintToConsole.println("Please enter the piece you wish to promote to or press any other key");
    	String input = mScanner.nextLine();
    	
    	switch (input.charAt(0)) {
    		case 'q', 'Q':
    			return 'Q';
    		case 'r', 'R':
    			return 'R';
    		case 'b', 'B':
    			return 'B';
    		case 'n', 'N':
    			return 'N';
    		default:
    			return 'Q';
    	}
    }

	@Override
    public void refreshOutput() {
    	var game = mGameOwner.getGame();
    	if (game.isPresent()) {
			mChessGame = game.get();
			if (mChessGame.getBoard().hashCode() != mLastPrintedBoardHash) {
				mLastPrintedBoardHash = mChessGame.getBoard().hashCode();
				GamePrinter.printBoard(mChessGame.getBoard());
				if (mChessGame.isGameOver()) {
					GamePrinter.printResult(GameOverDetector.checkForMate(game.get()));
				} else {
					PrintToConsole.println(mChessGame.getCurrentColor()
							.isWhite() ? "It's whites turn" : "It's blacks turn");
				}
			}
		}
    }

	public BlockingQueue<JSONObject> getRequestQueue() {
		return mRequestQueue;
	}

	public GameOwner getGameOwner() {
		return mGameOwner;
	}

	public void setGameOwner(GameOwner gameOwner) {
		this.mGameOwner = gameOwner;
	}
}
