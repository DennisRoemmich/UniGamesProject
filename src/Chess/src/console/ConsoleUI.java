package console;

import core.Chess;
import core.ChessMove;
import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;
import framework.*;
import org.json.simple.JSONObject;

import java.util.Scanner;
import java.util.concurrent.Callable;


/**
 * The console UI to interact with the chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleUI implements Presenter, Player {
    private Scanner mScanner = new Scanner(System.in);
    protected Controller mController = new Controller();
    private boolean mAiGame = false;

    public void run() {
    	PrintToConsole.println("Welcome to Chess!");
    	
    	boolean gameIsrunning = true;
    	//while(gameIsrunning) {
    	
    		PrintToConsole.println("Replay a save file? (Type [Y] for yes or any key to continue)");
    		String input = mScanner.nextLine();
    	
    		if ("y".equalsIgnoreCase(input)) {
    			loadGame();
    		}
    			else {
    	    		PrintToConsole.println("Play vs [A]I or any other key to play hotseat.");
    	    		String inputThree = mScanner.nextLine();
    	    		if("a".equalsIgnoreCase(input)) {
    	    			startGame(true);
    	    		} else {
    	    			startGame(false);
    	    		}
    				
    			}
    		}
	//}
    
    public void loadGame() {
    	try {
    		PrintToConsole.println("Please enter the save file name or type [A] for Abort. ");
    		PrintToConsole.println("To replay your last game type \"null\".");
    		String inputTwo = mScanner.nextLine();
    		if (!"a".equalsIgnoreCase(inputTwo)) {
    						
    				JSONObject loadedGame = FileController.loadJSon(inputTwo);
    				startGame(GameLog.valueOf(loadedGame), false);
    			}
    		} catch(Exception e) {
    			//will soon be used
    			}
    }

	public void startGame(GameLog log, boolean mAiGame) {
		PrintToConsole.println("Type \"help\" for information on how to play. \n");
		mController.setPlayerA(this);
		if(mAiGame) {
			mController.setPlayerB(mController.getAiPlayer());
		} else {
			mController.setPlayerB(this);
		}
		mController.setPresenter(this);
		mController.replayLog(log);
		mController.startGame();
	}

    public void startGame(boolean mAiGame) {
    	PrintToConsole.println("Type \"help\" for information on how to play. \n");
        mController.setPlayerA(this);
		if(mAiGame) {
			mController.setPlayerB(mController.getAiPlayer());
		} else {
			mController.setPlayerB(this);
		}
		mController.setPresenter(this);
        mController.newGame();
        mController.startGame();
    }

    public void printBoard() {
        PrintToConsole.println("   A   B   C   D   E   F   G   H  ");
        PrintToConsole.println(" ┌───────────────────────────────┐ ");
        for (Rank rank = Rank.M8; rank != null; rank = rank.getBottomNeighbour()) {
        	PrintToConsole.print(rank.toString() + "│");
            for (File file : File.values()) {
				PrintToConsole.print(' ');
                ChessPiece piece = mController.getGame().getBoard().getPiece(new Square(rank, file));
                if (piece == null) {
                	PrintToConsole.print(' ');
                } else {
                	// .toChar() can be changed to .toSymbol() for Unicode symbols
                	PrintToConsole.print(piece.toSymbol());
                }
				PrintToConsole.print(" │");
            }
            PrintToConsole.println(rank.toString());
            if(rank != Rank.M1) {
        PrintToConsole.println(" ├───┼───┼───┼───┼───┼───┼───┼───┤ ");
			}
        }
        PrintToConsole.println(" └───────────────────────────────┘ ");
        PrintToConsole.println("   A   B   C   D   E   F   G   H  ");
    }

    private void printResult() {
        switch (mController.getGame().getResult()) {
            case DRAW -> PrintToConsole.println("Draw");
            case CHECKMATE -> PrintToConsole.println("Checkmate");
            case STALEMATE -> PrintToConsole.println("Stalemate");
            case SURRENDER -> PrintToConsole.println("Surrender");
            case NONE -> PrintToConsole.println("The game isn't over.");
            default -> PrintToConsole.println("ERROR: Unknown game result");
        }
    }

    @Override
    public void requestMove(String moveType) {
    	if(mController == null) {
    		WriteError.writeErrorLog("Controller not specified.");
    		return;
		}
		switch (moveType) {
			case "move":
				mController.handleMove(requestPieceMove());
				return;
			case "promotionPiece":
				mController.handleMove(requestPromotionPiece());
				return;
		}
	}

	private JSONObject requestPieceMove() {
		PrintToConsole.println("Please enter your move (e.g. \"e4\" or \"Nf3\"):");
		String input = mScanner.nextLine();
		if(checkSpecialInput(input)) {
			return new JSONObject();
		}
		try {
			ChessMove move = ChessMove.valueOf(input, mController.getGame());
			return move.toJSon();
		} catch (Exception e) {
			System.out.println("Unknown Issue.");
			return new JSONObject();
		}
	}

	private JSONObject requestPromotionPiece() {
		JSONObject promotionAction = new JSONObject();
		PrintToConsole.println("Please enter the piece you wish to promote to or press any other key:");
		PrintToConsole.println("[Q]ueen, [R]ook, [B]ishop, K[n]ight");
		String input = mScanner.nextLine();
		if(input.length() != 1) {
			PrintToConsole.println("Please enter only one character.");
			return requestPieceMove();
		}
		ChessPieceType type;
		try{
			type = ChessPieceType.valueOf(input.charAt(0));
		} catch (IllegalArgumentException e) {
			PrintToConsole.println("Please enter one of the characters Q,R,B or N.");
			return requestPromotionPiece();
		}
		switch (type) {
			case KNIGHT, BISHOP, ROOK, QUEEN:
				promotionAction.put("promotion",type.toString());
				return promotionAction;
			case PAWN, KING:
				PrintToConsole.println("You can't promote to a King or a Pawn");
				return requestPromotionPiece();
			default:
				PrintToConsole.println("An error occurred. Please try again.");
				WriteError.writeErrorLog("Unexpected ChessPieceType in promotion request.");
				return requestPromotionPiece();
		}
	}

    public boolean checkSpecialInput(String input) {
		if("exit".equalsIgnoreCase(input)) {
			mController.quitGame();
			return true;
		}
		if("help".equalsIgnoreCase(input)) {
			PrintToConsole.println("----------Commands----------");
			PrintToConsole.println("*help* Prints the current screen with information on commands and how to play the game ");
			PrintToConsole.println("*menu* Opens the game options menu");
			PrintToConsole.println("*undo* Undoes the last move ");
			PrintToConsole.println("*exit* Closes the chess application ");
			PrintToConsole.println("");
			PrintToConsole.println("--------How to play---------");
			PrintToConsole.println("This chess app uses the official chess notation to register moves.");
			PrintToConsole.println("Simply type the square you want your piece to move to, e.g. e (column) 4 (row)\"");
			PrintToConsole.println("In case there are several possible moves (like BISHOP can move to e4 and PAWN can move to e4), you must define the moving piece. ");
			PrintToConsole.println("This can be done by typing Be4 (BISHOP to e4) ");
			PrintToConsole.println("For special moves like castling you may either move the king two squares or use the special castling notation (O-O or O-O-O)");
			PrintToConsole.println("");
			PrintToConsole.println("For further information please visit https://en.wikipedia.org/wiki/Algebraic_notation_(chess)");
			PrintToConsole.println("");
			return true;
		}
		if("undo".equalsIgnoreCase(input)) {
			mController.undoLastMove();
			return true;
		}
		if("menu".equalsIgnoreCase(input)) {
			PrintToConsole.println("Settings:");
			PrintToConsole.println("Set Auto-[P]romotion on/off");
			PrintToConsole.println("Any input to continue the game");
			
			String newInput = mScanner.nextLine();
			if("p".equalsIgnoreCase(newInput)) {
				autoPromotion();
				return true;
			}
		}
		return false;
    }

    private void autoPromotion() {
    	
    	if(mController.getGame().getAutoPromotion()) {
    		mController.getGame().setAutoPromotion(false);
    		PrintToConsole.println("Auto-Promotion turned off");
    	} else {
    		mController.getGame().setAutoPromotion(true);
    		PrintToConsole.println("Auto-Promotion turned on");
    	}
    	
	}
    
    public char setPromotionPiece() {
    }
    
    public Controller getController() {
    	return this.mController;
    }

	@Override
    public void refreshOutput() {
        printBoard();
        if (mController.getGame().isGameRunning()) {
			PrintToConsole.println(mController.getGame().isItWhitesTurn() ? "It's whites turn" : "It's blacks turn");
		} else {
            printResult();
        }
    }
}
