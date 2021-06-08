package sample;

import core.Chess;

import core.pieces.ChessPiece;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO: Eliminate println statements.

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
        System.out.println("   A  B  C  D  E  F  G  H   ");
        System.out.println(" ┌────────────────────────┐ ");
        for (Rank rank = Rank.M8; rank != null; rank = rank.getBottomNeighbour()) {
            System.out.print(rank + "│");
            for (File file : File.values()) {
                System.out.print(' ');
                ChessPiece piece = mController.getGame().getBoard().getPiece(new Square(rank, file));
                if (piece == null) {
                    System.out.print(' ');
                } else {
                    printPieceChar(piece);
                }
                System.out.print(' ');
            }
            System.out.println("│" + rank);
        }
        System.out.println(" └────────────────────────┘ ");
        System.out.println("   A  B  C  D  E  F  G  H   ");
    }

    private void printResult() {
        switch (mGame.getResult()) {
            case DRAW:
                System.out.println("Draw");
                break;
            case CHECKMATE:
                System.out.println("Checkmate");
                break;
            case STALEMATE:
                System.out.println("Stalemate");
                break;
            case SURRENDER:
                System.out.println("Surrender");
                break;
            default:
            	System.out.println("ERROR: Unknown game result");
            	break;
        }
    }

    private void printPieceChar(ChessPiece piece) {
        System.out.print(piece.toSymbol());
    }

    @Override
    public JSONObject requestMove() {

        System.out.println("Please enter the position of the piece, that you want to move:");
        
        Square origin = null;
        List<Square> availableDestinations = new ArrayList<>();
        
        validDestinations(origin, availableDestinations);

        System.out.println("Available Fields are: " + availableDestinations);
        System.out.print("Please enter the position of the field, ");
        System.out.print("where you want to place your piece or enter [r] to returnand choose another piece: \n" );
        
        Square destination = null;
        do {
            String input = mScanner.nextLine();
            if (input.charAt(0) == 'r') {
                return requestMove();
            }
            try {
                destination = new Square(input);
                if (!availableDestinations.contains(destination)) {
                    System.out.println("Field is unreachable! Enter another field or [r].");
                    destination = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        } while (destination == null);
        JSONObject move = new JSONObject();
        
        //TODO: Type safety: The method put(Object, Object) belongs to the raw type HashMap. References to generic type HashMap<K,V> should be parameterized
        move.put("origin", origin);
        move.put("destination", destination);
        return move;
    }
    
    public void validDestinations(Square origin, List<Square> availableDestinations ) {
    	
    	do {
            String input = mScanner.nextLine();
            try {
                origin = new Square(input);
                if (mController.getGame().getBoard().isFieldFree(origin)) {
                    System.out.println("This Field is empty.");
                    origin = null;
                } else {
                	if (mController.getGame().getBoard().
                	isOccupiedByOpponent(origin, mController.getGame().isItWhitesTurn())) {
                    System.out.println("This Field is occupied by the opponent.");
                    origin = null;
                	} else {
                		availableDestinations = mController.getGame().getPossibleMoves(origin);
                		if (availableDestinations.isEmpty()) {
                		System.out.println("The selected Piece can't move.");
                		}
                	}              	
                }      
            } catch (Exception e) {
                System.out.println("Invalid input: " + input);
            }
    	} while (availableDestinations.isEmpty());
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
