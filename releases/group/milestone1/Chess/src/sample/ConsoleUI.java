package sample;

import core.Chess;
import core.GameOverDetector;
import core.pieces.ChessPiece;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;
import org.json.simple.JSONObject;
import java.util.ArrayList;
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
        mController.setPlayerA(this);
        mController.setPlayerB(this);
        mController.createGame();
        mGame = mController.getGame();
        mController.startGame();
    }

    public void printBoard() {
    	println("   A  B  C  D  E  F  G  H   ");
        println(" ┌────────────────────────┐ ");
        for (Rank rank = Rank.M8; rank != null; rank = rank.getBottomNeighbour()) {
            print(rank + "│");
            for (File file : File.values()) {
                print(' ');
                ChessPiece piece = mController.getGame().getBoard().getPiece(new Square(rank, file));
                if (piece == null) {
                    print(' ');
                } else {
                    printPieceChar(piece);
                }
                print(' ');
            }
            println("│" + rank);
        }
        println(" └────────────────────────┘ ");
        println("   A  B  C  D  E  F  G  H   ");
    }

    private void printResult() {
        switch (mGame.getResult()) {
            case DRAW:
                println("Draw");
                break;
            case CHECKMATE:
                println("Checkmate");
                break;
            case STALEMATE:
                println("Stalemate");
                break;
            case SURRENDER:
                println("Surrender");
                break;
            default:
            	println("ERROR: Unknown game result");
            	break;
        }
    }

    private void printPieceChar(ChessPiece piece) {
        print(piece.toSymbol());
    }
    
    //TODO: Refactor this method to reduce its Cognitive Complexity from 21 to the 15 allowed.
    @Override
    public JSONObject requestMove() {

        println("Please enter the position of the piece, that you want to move:");

        Square origin = null;
        List<Square> availableDestinations = new ArrayList<>();

        do {
            String input = mScanner.nextLine();
            try {
                origin = new Square(input);             
                if (mController.getGame().getBoard().isFieldFree(origin)) {
                    println("This Field is empty.");
                    origin = null;
                } else {
                	if (mController.getGame().getBoard().
                	isOccupiedByOpponent(origin, mController.getGame().isItWhitesTurn())) {
                    println("This Field is occupied by the opponent.");
                    origin = null;
                	} else {
                		availableDestinations = mController.getGame().getPossibleMoves(origin);
                		if (availableDestinations.isEmpty()) {
                		println("The selected Piece can't move.");
                		}
                	}              	
                }      
            } catch (Exception e) {
                println("Invalid input: " + input);
            }
        } while (availableDestinations.isEmpty());

        println("Available Fields are: " + availableDestinations);
        print("Please enter the position of the field, ");
        print("where you want to place your piece or enter [r] to returnand choose another piece: \n" );
        
        Square destination = null;
        do {
            String input = mScanner.nextLine();
            if (input.charAt(0) == 'r') {
                return requestMove();
            }
            try {
                destination = new Square(input);
                if (!availableDestinations.contains(destination)) {
                    println("Field is unreachable! Enter another field or [r].");
                    destination = null;
                }
            } catch (Exception e) {
                println("Invalid input.");
            }
        } while (destination == null);
        JSONObject move = new JSONObject();
        
        //TODO: Type safety: The method put(Object, Object) belongs to the raw type HashMap. References to generic type HashMap<K,V> should be parameterized
        move.put("origin", origin);
        move.put("destination", destination);
        return move;
    }
    
	public static void println(String input) {
		System.out.println(input);		
	}
	
	public static void print(String input) {
		System.out.print(input);		
	}
	
	public static void print(char input) {
		System.out.print(input);		
	}
    
    @Override
    public void setController(Controller controller) {
        this.mController = controller;
    }

    @Override
    public void refreshOutput() {
        printBoard();
        if (!mGame.isGameRunning()) {
            printResult();
        }
    }
}
