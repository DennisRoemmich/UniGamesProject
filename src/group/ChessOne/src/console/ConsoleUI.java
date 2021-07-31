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
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The console UI to interact with the chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleUI implements Presenter, Player {

	private boolean mIsNetworkGame = false;
	private boolean mIsAiGame = false;

	private final Scanner mScanner = new Scanner(System.in);
    protected Controller mController;
    private ClientController mClientController;
    private GameOwner gameOwner;
    private Chess mChessGame = null;
    private NetworkState mNetworkState = NetworkState.UNDEFINED;
    private LinkedBlockingQueue<JSONObject> requestQueue = new LinkedBlockingQueue<>();
    private boolean endFlag = false;
    private int lastPrintedBoardHash = 0;

    public ConsoleUI() {
	}

    public void run() {
		PrintToConsole.println("Welcome to Chess!");

		askNetworkOrHotseat();

		if (mIsNetworkGame) {
			askHostOrClient();
		}

		if (mNetworkState == NetworkState.CLIENT) {
			mClientController = new ClientController(this, this);
			gameOwner = mClientController;
		} else {
			mController = new Controller();
			gameOwner = mController;
		}

		if (!mIsNetworkGame) {
			askGameMode();
		}

		startGame(mIsAiGame);
	}

	public void askGameMode() {
    	boolean correctInput = false;
		while (!correctInput) {

			GamePrinter.printGameModeQuestion(mIsNetworkGame);
			String input = mScanner.nextLine();

			correctInput = true;

			switch (input) {
				case "r", "R":
					loadGame();
					break;
				case "c", "C":
					mIsAiGame = false;
					break;
				case "a", "A":
					mIsAiGame = true;
					break;
				case "t", "T":
					mController.setGameMode(false);
					break;
				case "h", "H":
					GamePrinter.printHelp();
					correctInput = false;
					break;
				case "q", "Q":
					endFlag = true;
					break;
				default:
					PrintToConsole.println("Please try again");
					correctInput = false;
					break;
			}
		}
	}

    public void askNetworkOrHotseat() {
		boolean wrongMenuInput = true;
		while (wrongMenuInput) {
			PrintToConsole.println("");
			PrintToConsole.println("Play [H]otseat, Play via [N]etwork, [Q]uit");

			String input = mScanner.nextLine();

			switch (input) {
				case "h", "H":
					mIsNetworkGame = false;
					wrongMenuInput = false;
					break;
				case "n", "N":
					mIsNetworkGame = true;
					wrongMenuInput = false;
					break;
				case "q", "Q":
					endFlag = true;
					return;
				default:
					break;
			}
		}
	}

	public void askHostOrClient() {
    	boolean wrongMenuInput = true;
    	while (wrongMenuInput) {
			PrintToConsole.println("Do you want to be the [H]ost or [C]lient?");

			String input = mScanner.nextLine();

			switch (input) {
				case "h", "H":
					mNetworkState = NetworkState.HOST;
					wrongMenuInput = false;
					break;
				case "c", "C":
					mNetworkState = NetworkState.CLIENT;
					wrongMenuInput = false;
					PrintToConsole.print("Awaiting network confirmation... (Popup window)\n");
					break;
				case "q", "Q":
					endFlag = true;
					return;
				default:
					break;
			}
		}
	}
    
    public void loadGame() {
    	try {
    		PrintToConsole.println("Please enter the save file name or type [A] for Abort. ");
    		PrintToConsole.println("To replay your last game type the filename of your newest created save state");
    		String inputTwo = mScanner.nextLine();
    		if (!"a".equalsIgnoreCase(inputTwo)) {
    						
    				JSONObject loadedGame = FileController.loadJSon(inputTwo);
    				startGame(GameLog.valueOf(loadedGame), false);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

	public void startGame(boolean mAiGame) {

		PrintToConsole.println("Type \"help\" for information on how to play. \n");

		if (mIsNetworkGame && mNetworkState == NetworkState.CLIENT) {
			Thread clientThread = new Thread(mClientController);
			clientThread.start();

		} else {

			if (mAiGame) {
				mController.setPlayerA(this);
				mController.setPlayerB(new AiPlayer(mController));
			} else if (mIsNetworkGame) {
				mController.setPlayerA(this);
				mController.setPlayerB(new NetworkPlayer(mController));
			} else {
				mController.setPlayerA(this);
				mController.setPlayerB(this);
			}

			mController.setPresenter(this);
			mController.newGame();
			Optional<Chess> game = mController.getGame();
			if  (game.isPresent()) {
				mChessGame = game.get();
			}

			mChessGame = mController.getGame().get();
			Thread engineThread = new Thread(mController);
			engineThread.start();
		}

		gameLoop();
	}

	public void startGame(GameLog log, boolean aiGame) {
		mController.replayLog(log);
		startGame(aiGame);
	}

	public void gameLoop() {
    	boolean stopFlag = false;
    	while (!stopFlag) {

			while (requestQueue.peek() == null) {
			}
			var request = requestQueue.poll();

			if (!request.get("type").equals("move")) {
				continue;
			}

			PrintToConsole.println("Please enter your move (e.g. \"e4\" or \"Nf3\"):");
			String input = mScanner.nextLine();
			if (checkSpecialInput(input)) {
				//PrintToConsole("error2");
			}
			try {
				ChessMove move = ChessMove.valueOf(input, mChessGame);
				for (ChessMove moveToCheck: mChessGame.getPossibleMoves()) {
					if (move.equals(moveToCheck)) {
						if (mNetworkState == NetworkState.CLIENT) {
							mClientController.getMoveQueue().add(move.toJSon());
						} else {
							mController.getMoveQueue().add(move.toJSon());
						}
						break;
					}
				}
			} catch (Exception e) {
				PrintToConsole.println("The given input couldn't be resolved.");
				requestQueue.add(request);
			}
		}
	}


    private NetworkState hostOrClient() {

		var input = JOptionPane.showOptionDialog(null, "Client or Host?", "Network Connection",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null,
				new String[]{"HOST", "CLIENT"}, "HOST");

		if (input == 0) {

			mNetworkState = NetworkState.HOST;
			return NetworkState.HOST;

		}

		if (input == 1) {

			mNetworkState = NetworkState.CLIENT;
			return NetworkState.CLIENT;

		}

		return NetworkState.UNDEFINED;

	}

    public void requestMove(JSONObject dataType) {

    	refreshOutput();
    	requestQueue.add(dataType);
	}

    public boolean checkSpecialInput(String input) {
		if ("exit".equalsIgnoreCase(input)) {
			mController.quitGame();
			return true;
		}
		if ("help".equalsIgnoreCase(input)) {
			PrintToConsole.println("----------Commands----------");
			PrintToConsole.print("*help* Prints the current screen with ");
			PrintToConsole.println("information on commands and how to play the game ");
			PrintToConsole.println("*menu* Opens the game settings menu");
			PrintToConsole.println("*undo* Undoes the last move ");
			PrintToConsole.println("*exit* Closes the chess application ");
			PrintToConsole.println("");
			PrintToConsole.println("--------How to play---------");
			PrintToConsole.println("This chess app uses the official chess notation to register moves.");
			PrintToConsole.println("Simply type the square you want your piece to move to, e.g. e (column) 4 (row).");
			PrintToConsole.print("In case there are several possible moves (like BISHOP can move ");
			PrintToConsole.println("to e4 and PAWN can move to e4), you must define the moving piece. ");
			PrintToConsole.println("This can be done by typing Be4 (BISHOP to e4) ");
			PrintToConsole.print("For special moves like castling you may either move the king ");
			PrintToConsole.println("two squares or use the special castling notation (O-O or O-O-O) ");
			PrintToConsole.println("");
			PrintToConsole.print("For further information please visit: ");
			PrintToConsole.println("https://en.wikipedia.org/wiki/Algebraic_notation_(chess)");
			PrintToConsole.println("");
			return true;
		}
		if ("undo".equalsIgnoreCase(input)) {
			mController.undoLastMove();
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
    
    public Controller getController() {
    	return this.mController;
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
}
