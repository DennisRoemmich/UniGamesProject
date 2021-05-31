package sample;

import core.Chess;
import core.Column;
import core.Position;
import core.Row;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Chess game = new Chess();
    private Scanner scanner = new Scanner(System.in);

    public void test() {
        List<Position> positions = game.getPossibleMoves(new Position(Row._2_, Column.A));
        for (Position pos: positions) {
            System.out.println("row: " + pos.getRow() + ", column: " + pos.getColumn());
        }

    }

    public void startGame(){
        game = new Chess();
        System.out.println("Let's play some chess!");
        System.out.println("You start!");
        while(true) {
            getMove();
        }
    }

    public void getMove(){
        System.out.println("Please enter the position of the piece, that you want to move:");
        Position origin = null;
        do {
            String input = scanner.nextLine();
            try {
                origin = new Position(input);
            } catch (Exception e) {
                System.out.println("Invalid input.");
                getMove();
            }
        } while (origin == null);

        List<Position> availableDestinations = game.getPossibleMoves(origin);
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
