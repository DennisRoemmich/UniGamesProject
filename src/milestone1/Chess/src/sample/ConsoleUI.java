package sample;

import core.Chess;
import core.ChessMove;
import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;
import framework.Player;
import framework.Presenter;
import org.json.simple.JSONObject;
import java.util.List;
import java.util.Scanner;


/**
 * The console UI to interact with the chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleUI implements Presenter, Player {
    private Scanner mScanner = new Scanner(System.in);
    private Controller mController = new Controller(this);
    private Chess mGame; 

    public void startGame() {
    	PrintToConsole.println("Type \"exit\" to end the game.");
        mController.setPlayerA(this);
        mController.setPlayerB(this);
        mController.createGame();
        mGame = mController.getGame();
        mController.startGame();
    }

    public void printBoard() {
        PrintToConsole.println("   A  B  C  D  E  F  G  H   ");
        PrintToConsole.println(" ┌────────────────────────┐ ");
        for (Rank rank = Rank.M8; rank != null; rank = rank.getBottomNeighbour()) {
        	PrintToConsole.print(rank + "│");
            for (File file : File.values()) {
            	PrintToConsole.print(' ');
                ChessPiece piece = mController.getGame().getBoard().getPiece(new Square(rank, file));
                if (piece == null) {
                	PrintToConsole.print(' ');
                } else {
                	// .toChar() can be changed to .toSymbol() for Unicode symbols
                	PrintToConsole.print(piece.toChar());
                }
                PrintToConsole.print(' ');
            }
            PrintToConsole.println("│" + rank);
        }
        PrintToConsole.println(" └────────────────────────┘ ");
        PrintToConsole.println("   A  B  C  D  E  F  G  H   ");
    }

    private void printResult() {
        switch (mGame.getResult()) {
            case DRAW -> PrintToConsole.println("Draw");
            case CHECKMATE -> PrintToConsole.println("Checkmate");
            case STALEMATE -> PrintToConsole.println("Stalemate");
            case SURRENDER -> PrintToConsole.println("Surrender");
            case NONE -> PrintToConsole.println("The game isn't over.");
            default -> PrintToConsole.println("ERROR: Unknown game result");
        }
    }

    @Override
    public JSONObject requestMove(JSONObject dataType) {
    	
/* Diese Exception macht Probleme bei mir */
    	
//        if (dataType.get("type").equals("move")) {
//            throw new IllegalArgumentException();
//        }
    	
        PrintToConsole.println("Please enter your move (e.g. \"e4\" or \"Nf3\"):");
        String input = mScanner.nextLine();
        
        try {   	
        	if (!checkEndGame(input)) {
	            Square destination;
	            ChessPieceType pieceType;
	            switch (input.length()) {
	                case 2:
	                    pieceType = ChessPieceType.PAWN;
	                    destination = new Square(input);
	                    break;
	                case 3:
	                    pieceType = ChessPieceType.valueOf(input.charAt(0));
	                    destination = new Square(input.substring(1));
	                    break;
	                default:
	                	PrintToConsole.println("The given input couldn't be recognized.");
	                    return requestMove(dataType);
	            }
	            List<Square> possibleOrigins = mGame.getPossibleOrigins(destination, pieceType);
	            switch (possibleOrigins.size()) {
	                case 0:
	                	PrintToConsole.println("The entered input could mean different moves or is impossible.");
	                    return requestMove(dataType);
	                case 1:
	                    ChessMove move = new ChessMove(possibleOrigins.get(0), destination);
	                    return move.toJSon();
	                default:
	                	PrintToConsole.println("The entered input could mean different moves. Not supported yet");
	                    return requestMove(dataType);
	            }
        	} else {
        		return null;
        	}
        } catch (Exception e) {
        	PrintToConsole.println("Invalid input: " + input);
            return requestMove(dataType);
        }
        
    }

    public static boolean checkEndGame(String input) {
		if ("exit".equalsIgnoreCase(input)) {
			Controller.mEndedGame = true;
			return true;
		}
		return false;
    }

    @Override
    public void refreshOutput() {
        printBoard();
        if (!mGame.isGameRunning()) {
            printResult();
        }
    }
}
