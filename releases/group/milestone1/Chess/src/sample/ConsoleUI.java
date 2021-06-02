package sample;

import core.*;
import core.pieces.ChessPieceType;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Chess game = new Chess();
    private Scanner scanner = new Scanner(System.in);

    public void test() {
        List<Position> positions = game.getPossibleMoves(new Position(Row.M2, Column.A));
        for (Position pos: positions) {
            System.out.println("row: " + pos.getRow() + ", column: " + pos.getColumn());
        }

    }

    public void startGame(){
        printKing();
        game = new Chess();
        System.out.println("Let's play some chess!");
        System.out.println("You start!");
        while(true) {
            getMove();
        }
    }

    public void printKing() {
        Charset utf8 = Charset.forName("UTF-8");
        Charset def = Charset.defaultCharset();

        String charToPrint = "u0905";

        byte[] bytes = new byte[0];
        try {
            bytes = charToPrint.getBytes("UTF-8");
        String message = new String(bytes , def.name());

        PrintStream printStream = new PrintStream(System.out, true, utf8.name());
        printStream.println(message);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getQuickMove() {
        boolean isItWhitesTurn = game.isItWhitesTurn();

        ChessPieceType type;
        Row destinationRow;
        Column destinationColumn;



    }

    public void getMove(){
        System.out.println("Please enter the position of the piece, that you want to move:");

        Position origin = null;
        List<Position> availableDestinations = new ArrayList<Position>();

        do {
            String input = scanner.nextLine();
            try {
                origin = new Position(input);
                if(game.getBoard().isFieldFree(origin)){
                    System.out.println("This Field is empty.");
                    origin = null;
                    continue;
                }
                if(game.getBoard().isOccupiedByOpponent(origin, game.isItWhitesTurn())){
                    System.out.println("This Field is occupied by the opponent.");
                    origin = null;
                    continue;
                }
                availableDestinations = game.getPossibleMoves(origin);
                if(availableDestinations.isEmpty()) {
                    System.out.println("The selected Piece can't move.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                getMove();
            }
        } while (availableDestinations.isEmpty());

        System.out.println("Available Fields are: " + availableDestinations);
        System.out.println("Please enter the position of the field, where you want to place your piece or enter [r] to returnand choose another piece:");
        Position destination = null;
        do {
            String input = scanner.nextLine();
            if(input.charAt(0) == 'r') {
                getMove();
                return;
            }
            try {
                destination = new Position(input);
                if(!availableDestinations.contains(destination)){
                    System.out.println("Field is unreachable! Enter another field or [r].");
                    destination = null;
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                getMove();
            }
        } while (destination == null);
        game.makeMove(origin, destination);
        System.out.println("Move executed.");
    }
}
