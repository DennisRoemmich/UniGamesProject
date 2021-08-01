package console;

import engine.Chess;
import engine.Controller;
import engine.GameOwner;
import engine.analysis.GameOverDetector;
import engine.board.ChessMove;
import engine.squares.File;
import engine.squares.Square;
import engine.squares.Rank;
import framework.*;
import network.ClientController;
import network.NetworkPlayer;
import network.NetworkState;
import npc.AiPlayer;
import org.json.simple.JSONObject;

import javax.swing.*;
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
    private GameOwner gameOwner;
    private Chess mChessGame = null;
    private NetworkState mNetworkState = NetworkState.UNDEFINED;
    private LinkedBlockingQueue<JSONObject> requestQueue = new LinkedBlockingQueue<>();
    private int lastPrintedBoardHash = 0;

    private BlockingQueue<String> superQueue;

    public ConsoleUI(BlockingQueue<String> superQueue) {
		this.superQueue = superQueue;
	}

	@Override
	public void run() {
    	gameLoop();
    	superQueue.add("quit");
	}

	public void gameLoop() {
    	boolean stopFlag = false;
    	while (!stopFlag) {

			while (requestQueue.peek() == null) {
			}
			var request = requestQueue.poll();

			if (!request.containsKey("type")) {
				continue;
			} else if (request.get("type").toString() == "quit"){
				stopFlag = true;
				break;
			}

			PrintToConsole.println("Please enter your move (e.g. \"e4\" or \"Nf3\"):");
			String input = mScanner.nextLine();
			if (checkSpecialInput(input)) {
				continue;
			}
			try {
				ChessMove move = ChessMove.valueOf(input, mChessGame);
				for (ChessMove moveToCheck: mChessGame.getPossibleMoves()) {
					if (move.equals(moveToCheck)) {
						gameOwner.getMoveQueue().add(move.toJSon());
						break;
					}
				}
			} catch (Exception e) {
				PrintToConsole.println("The given input couldn't be resolved.");
				requestQueue.add(request);
			}
		}
	}

    public void requestMove(JSONObject dataType) {
    	refreshOutput();
    	requestQueue.add(dataType);
	}

    public boolean checkSpecialInput(String input) {

		if ("quit".equalsIgnoreCase(input)) {
			superQueue.add("quit");
			return true;
		}
		if ("help".equalsIgnoreCase(input)) {
			GamePrinter.printHelp();
			return true;
		}
		if ("undo".equalsIgnoreCase(input)) {
			superQueue.add("undo");
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
    public void refreshOutput() {;
    	var game = gameOwner.getGame();
    	if (game.isPresent()) {
			mChessGame = game.get();
			if (mChessGame.getBoard().hashCode() != lastPrintedBoardHash) {
				lastPrintedBoardHash = mChessGame.getBoard().hashCode();
				GamePrinter.printBoard(mChessGame.getBoard());
				if (mChessGame.isGameOver()) {
					GamePrinter.printResult(GameOverDetector.checkForMate(game.get()));
				} else {
					PrintToConsole.println(mChessGame.getCurrentColor().isWhite() ? "It's whites turn" : "It's blacks turn");
				}
			}
		}
    }

	@Override
	public BlockingQueue<JSONObject> getRequestQueue() {
		return requestQueue;
	}

	public GameOwner getGameOwner() {
		return gameOwner;
	}

	public void setGameOwner(GameOwner gameOwner) {
		this.gameOwner = gameOwner;
	}
}
