package console;

import engine.Chess;
import engine.Controller;
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
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The console UI to interact with the chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleUI implements Presenter, Player {

	private boolean mIsNetworkGame = true;


	private final Scanner mScanner = new Scanner(System.in);
    protected Controller mController = new Controller(new LinkedBlockingQueue<>());
    private ClientController mClientController;
    private Chess mChessGame = null;
    private NetworkState mNetworkState = NetworkState.UNDEFINED;

    public void run() {
    	PrintToConsole.println("Welcome to Chess!");
    	
    	boolean netWorkInput = true;
    	while (netWorkInput) {
    		PrintToConsole.println("");
        	PrintToConsole.println("Play [H]otseat, Play via [N]etwork, [Q]uit");
        	
        	String input = mScanner.nextLine();
        	
        	switch (input) {
			case "h", "H":
				mIsNetworkGame = false;
				netWorkInput = false;
				break;
			case "n", "N":
				mIsNetworkGame = true;
				netWorkInput = false;
				PrintToConsole.print("Awaiting network confirmation... (Popup window)\n");
				break;
			case "q", "Q":
				return;
			default:
				break;
        	}
    	}
    	boolean wrongMenuInput = true;

    	if (mIsNetworkGame) {
			mNetworkState = hostOrClient();

			if (mNetworkState == NetworkState.CLIENT) {
				mClientController = new ClientController(this, this);
			}

		}

    	while (wrongMenuInput) {

			PrintToConsole.print("Please choose your game mode: \n");
			if (!mIsNetworkGame) {
				PrintToConsole.print("[C]lassic Chess 1v1, Classic Chess vs [A]I, ");
			} else {
				PrintToConsole.print("[C]lassic Chess 1v1, ");
			}
			PrintToConsole.print("[T]orpedo Chess 1v1, [R]eplay from save file, [H]elp, [Q]uit \n");
			String input = mScanner.nextLine();

			wrongMenuInput = false;
			switch (input) {
				case "r", "R":
					loadGame();
					break;
    			case "c", "C":
    				startGame(false);
    				break;
				case "a", "A":
					if (!mIsNetworkGame) {
					startGame(true);
					}
					break;
				case "t", "T": 
					mController.setStandardChess();
					startGame(false);
					break;
				case "h", "H":
					PrintToConsole.println("--------Classic Chess 1v1---------");
					PrintToConsole.println("The classic chess game vs another player via hotseat.");
					PrintToConsole.println("");
					PrintToConsole.println("--------Classic Chess vs AI---------");
					PrintToConsole.println("Challenge vs the computer!");
					PrintToConsole.println("");
					PrintToConsole.println("--------Torpedo Chess 1v1---------");
					PrintToConsole.print("Alternative game mode where your pawns \n ");
					PrintToConsole.print("may always move 2 sqaures. En passant rule applies!");
					PrintToConsole.println("");
					break;
				case "q", "Q": 
					break;
				default:
					PrintToConsole.println("Please try again");
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

	public void startGame(GameLog log, boolean mAiGame) {
		PrintToConsole.println("Type \"help\" for information on how to play. \n");
		if (mAiGame) {
			mController.setPlayerA(this);
			mController.setPlayerB(new AiPlayer(mController));
		} else {
			mController.setPlayerA(this);
			mController.setPlayerB(this);
		}
		mController.setPresenter(this);
		mController.replayLog(log);
		mController.startGame();
	}

    public void startGame(boolean mAiGame) {

    	PrintToConsole.println("Type \"help\" for information on how to play. \n");
    
    	if (mIsNetworkGame && mNetworkState == NetworkState.CLIENT) {
    		Optional<Chess> game = Optional.of(mClientController.getGame());
    		if  (game.isPresent()) {
				mChessGame = game.get();
			}
    		mClientController.startGame();

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
			mClientController.startGame();
	        mController.startGame();

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

    public void printBoard() {
        PrintToConsole.println("   A   B   C   D   E   F   G   H  ");
        PrintToConsole.println(" ┌───────────────────────────────┐ ");
        for (Rank rank = Rank.M8; rank != null; rank = rank.getBottomNeighbour()) {
        	PrintToConsole.print(rank + "│");
            for (File file : File.values()) {
				PrintToConsole.print(' ');
                var piece = mChessGame.getBoard().getPiece(new Square(rank, file));
                if (piece.isEmpty()) {
                	PrintToConsole.print(' ');
                } else {
                	// .toChar() can be changed to .toSymbol() for Unicode symbols
                	PrintToConsole.print(piece.get().toSymbol());
                }
				PrintToConsole.print(" │");
            }
            PrintToConsole.println(rank.toString());
            if (rank != Rank.M1) {
        PrintToConsole.println(" ├───┼───┼───┼───┼───┼───┼───┼───┤ ");
			}
        }
        PrintToConsole.println(" └───────────────────────────────┘ ");
        PrintToConsole.println("   A   B   C   D   E   F   G   H  ");
    }

    private void printResult() {
        switch (mChessGame.getResult()) {
            case DRAW -> PrintToConsole.println("Draw");
            case CHECKMATE -> PrintToConsole.println("Checkmate");
            case STALEMATE -> PrintToConsole.println("Stalemate");
            case NONE -> PrintToConsole.println("The game isn't over.");
            default -> PrintToConsole.println("ERROR: Unknown game result");
        }
    }

    @Override
    public JSONObject requestMove(JSONObject dataType) {

    	refreshOutput();

		if (!dataType.get("type").equals("move")) {
			return new JSONObject();
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
					return move.toJSon();
				}
			}
		} catch (Exception e) {
			PrintToConsole.println("Unknown Issue.");
		}
		return requestMove(dataType);
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
    public void refreshOutput() {
        printBoard();
        if (mChessGame.isGameRunning()) {
			PrintToConsole.println(mChessGame.getCurrentColor().isWhite() ? "It's whites turn" : "It's blacks turn");
		} else {
            printResult();
        }
    }
}
