package console;
/*
import org.json.simple.JSONObject;
import siedlerController.Controller;
import siedlerFramework.Player;
import siedlerFramework.PrintToConsole;

import java.util.Scanner;

public class ConsolePlayer implements Player {

    private Controller controller;
    private Scanner scanner = new Scanner(System.in);

    public ConsolePlayer(Controller controller) {
        this.controller = controller;
        PrintToConsole.print("Welcome to the Die Siedler von Konstanz");
    }

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if (!inputType.containsKey("move")) {
            return new JSONObject();
        }
        JSONObject reply = new JSONObject();
        switch (inputType.get("move").toString()) {
            case "rollDice":
                PrintToConsole.println("Press Enter to roll the dices!");
                scanner.nextLine();
                reply.put("accepted", "true");
            case "placeVillage":
                PrintToConsole.println("Please choose a position to place a Village: (seperated by spaces)");
                String position = scanner.nextLine();

        }
        return reply;
    }

    private JSONObject positionAnswer()
}
*/