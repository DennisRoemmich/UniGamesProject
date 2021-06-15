package sample;

import core.Chess;

import core.ChessMove;
import core.ChessResult;
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

    public void startGame() {
        Controller.addPlayerGlobal(this);
        Controller.addPlayerGlobal(this);
        Controller.getMain().setPresenter(this);
        Controller.getMain().startGame();
    }

    public void printBoard() {
        System.out.println("   A  B  C  D  E  F  G  H   ");
        System.out.println(" ┌────────────────────────┐ ");
        for (Rank rank = Rank.M8; rank != null; rank = rank.getBottomNeighbour()) {
            System.out.print(rank + "│");
            for (File file : File.values()) {
                System.out.print(' ');
                ChessPiece piece = Chess.getBoard().getPiece(new Square(rank, file));
                if (piece == null) {
                    System.out.print(' ');
                } else {
                    // .toChar() can be changed to .toSymbol() for Unicode symbols
                    System.out.print(piece.toSymbol());
                }
                System.out.print(' ');
            }
            System.out.println("│" + rank);
        }
        System.out.println(" └────────────────────────┘ ");
        System.out.println("   A  B  C  D  E  F  G  H   ");
    }

    private void printResult() {
        switch (Chess.getResult()) {
            case DRAW -> System.out.println("Draw");
            case CHECKMATE -> System.out.println("Checkmate");
            case STALEMATE -> System.out.println("Stalemate");
            case SURRENDER -> System.out.println("Surrender");
            case INGAME -> System.out.println("The game isn't over.");
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
            ChessMove move = ChessMove.valueOf(input);
            return move.toJSon();
        } catch (Exception e) {
            System.out.println("Unknown Issue.");
            return  requestMove(dataType);
        }
    }



    @Override
    public void refreshOutput() {
        printBoard();
        if (Chess.getResult() != ChessResult.INGAME) {
            printResult();
        }
    }
}
