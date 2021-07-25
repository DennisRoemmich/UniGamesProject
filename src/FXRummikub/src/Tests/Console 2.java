package Tests;

import framework.GameController;
import framework.Player;
import framework.RequestType;
import org.json.simple.JSONObject;
import rummikub_controller.ActionType;
import rummikub_controller.GameMove;
import rummikub_controller.RummikubController;
import rummikub_game.Rummikub;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.util.Scanner;

public class Console implements Player {

    private Rummikub game;

        Console(){

    }





    public GameMove requestGameMove() {

        printBoard();
        System.out.print("\n\n");
        printRack();
        System.out.print("\n\n");
        String input = getInputString();

        if (input.equals("q")){
            return new GameMove(ActionType.QUIT);
        } else if (input.equals("f")){
            return new GameMove(ActionType.FINISHMOVE);
        }

        var move = inputToMove(input);

        return move;

    }


    private GameMove inputToMove(String input){


        boolean successful = false;
        String[] parts = input.split("->");

        String coordString = input.replaceAll("[^0-9]", ",");
        coordString = coordString.replaceAll(",+", ",");
        coordString = coordString.replaceAll(",+", ",");
        coordString = coordString.replaceAll(",+", ",");
        coordString = coordString.replaceFirst(",", "");
        String[] coordParts = coordString.split(",");

        var pointA = new Point(Integer.parseInt(coordParts[0]),Integer.parseInt(coordParts[1]));
        var pointB = new Point(Integer.parseInt(coordParts[2]),Integer.parseInt(coordParts[3]));
        ActionType type = null;

        if (parts[0].contains("R") && parts[1].contains("R")){
            type = ActionType.ONRACK;
        }
        if (parts[0].contains("R") && parts[1].contains("B")){
            type = ActionType.RACKTOBOARD;
        }
        if (parts[0].contains("B") && parts[1].contains("B")){
            type = ActionType.ONBOARD;
        }


        return new GameMove(type, pointA, pointB);

    }

    public String getInputString(){

        var isValid = false;

        while(!isValid){

            String inputMessage = "TYPE: 'q' - quit; 'f' - finish move; '?(x, y)->?(x,y)' With ? either B (Board) or R (Rack) to move\n: ";

            System.out.print(inputMessage);

            Scanner in = new Scanner(System.in);
            var input = in.nextLine();


            if ( input.matches("((R|B)\\(\\d{1,2},\\d{1,2}\\))->((R|B)\\(\\d{1,2},\\d{1,2}\\))|q|f") ) {

                return input;

            } else {
                System.out.print("Invalid input.\n");
            }

        }

        return null;

    }

    private void printBoard(){
        System.out.print("||||| BOARD |||||\n");
        System.out.print(game.getSketchBoard().toString(true));

    }

    private void printRack(){
        System.out.print("||||| RACK OF PLAYER " + (game.getCurrentPlayerIndex() + 1) + " |||||\n");
        System.out.print(game.getCurrentPlayer().getSketchRack().toString(true));

    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {

        printBoard();
        printRack();

        String input = getInputString();

        if (input.equals("q")){
            return new GameMove(ActionType.QUIT).toJSON();
        }

        var move = inputToMove(input);

        return move.toJSON();

    }

    @Override
    public void setController(GameController controller) {

            RummikubController rController = (RummikubController) controller;


    }



}









