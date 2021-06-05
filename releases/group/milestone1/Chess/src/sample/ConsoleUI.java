package sample;

import core.pieces.ChessPiece;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements Presenter, Player {
    private Scanner scanner = new Scanner(System.in);
    private Controller controller = new Controller(this);

    public void startGame(){
        controller.setPlayerA(this);
        controller.setPlayerB(this);
        controller.newGame();
    }

    public void printBoard() {
        System.out.println("   A  B  C  D  E  F  G  H   ");
        System.out.println(" ┌────────────────────────┐ ");
        for(Rank rank : Rank.values()) {
            System.out.print(rank + "│");
            for(File file : File.values()){
                System.out.print(' ');
                ChessPiece piece = controller.getGame().getBoard().getPiece(new Square(rank, file));
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

    private void printPieceChar(ChessPiece piece){
        System.out.print(piece.toSymbol());
    }

    @Override
    public JSONObject requestMove() {
        System.out.println("Please enter the position of the piece, that you want to move:");

        Square origin = null;
        List<Square> availableDestinations = new ArrayList<>();

        do {
            String input = scanner.nextLine();
            try {
                origin = new Square(input);
                if(controller.getGame().getBoard().isFieldFree(origin)){
                    System.out.println("This Field is empty.");
                    origin = null;
                    continue;
                }
                if(controller.getGame().getBoard().isOccupiedByOpponent(origin, controller.getGame().isItWhitesTurn())){
                    System.out.println("This Field is occupied by the opponent.");
                    origin = null;
                    continue;
                }
                availableDestinations = controller.getGame().getPossibleMoves(origin);
                if(availableDestinations.isEmpty()) {
                    System.out.println("The selected Piece can't move.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input: " + input);
            }
        } while (availableDestinations.isEmpty());

        System.out.println("Available Fields are: " + availableDestinations);
        System.out.println("Please enter the position of the field, where you want to place your piece or enter [r] to returnand choose another piece:");
        Square destination = null;
        do {
            String input = scanner.nextLine();
            if(input.charAt(0) == 'r') {
                return requestMove();
            }
            try {
                destination = new Square(input);
                if(!availableDestinations.contains(destination)){
                    System.out.println("Field is unreachable! Enter another field or [r].");
                    destination = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        } while (destination == null);
        JSONObject move = new JSONObject();
        move.put("origin", origin);
        move.put("destination", destination);
        return move;
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void refreshOutput() {
        printBoard();
    }
}
