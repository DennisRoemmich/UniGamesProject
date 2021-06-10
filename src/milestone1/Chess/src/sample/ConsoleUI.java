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

import java.security.InvalidParameterException;
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
                    // .toChar() can be changed to .toSymbol() for Unicode symbols
                    System.out.print(piece.toChar());
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
            case DRAW -> System.out.println("Draw");
            case CHECKMATE -> System.out.println("Checkmate");
            case STALEMATE -> System.out.println("Stalemate");
            case SURRENDER -> System.out.println("Surrender");
            case NONE -> System.out.println("The game isn't over.");
            default -> System.out.println("ERROR: Unknown game result");
        }
    }

    @Override
    public JSONObject requestMove(JSONObject dataType) {

        if(dataType.get("type") != "move") {
            throw new IllegalArgumentException();
        }
        System.out.println("Please enter your move (e.g. \"e4\" or \"Nf3\"):");
        String input = mScanner.nextLine();
        try {
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
                    System.out.println("The given input couldn't be recognized.");
                    return requestMove(dataType);
            }
            List<Square> possibleOrigins = mGame.getPossibleOrigins(destination, pieceType);
            switch (possibleOrigins.size()){
                case 0:
                    System.out.println("This move is impossible.");
                    return requestMove(dataType);
                case 1:
                    ChessMove move = new ChessMove(possibleOrigins.get(0), destination);
                    return move.toJSON();
                default:
                    System.out.println("The entered input could mean different moves. This scenario isn't supported yet.");
                    return requestMove(dataType);
            }
        } catch (Exception e) {
            System.out.println("Invalid input: " + input);
            return requestMove(dataType);
        }
    }



    @Override
    public void refreshOutput() {
        printBoard();
        if (!mGame.isGameRunning()) {
            printResult();
        }
    }
}
