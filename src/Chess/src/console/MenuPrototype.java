package console;

import framework.PrintToConsole;

import java.util.Scanner;

public class MenuPrototype {

    private Scanner mScanner = new Scanner(System.in);

    public MenuPrototype() {
        printWelcomeMessage();
    }

    private void printWelcomeMessage() {
        PrintToConsole.println("Welcome to our game collection!");
    }

    public void startLoop() {
        printSelectGame();
        handleGameInput();
    }

    private void printSelectGame() {
    	PrintToConsole.println("Please chose a game to play:");
    	PrintToConsole.println("[C]hess, [R]umikub, [S]kat, Die Siedler von [K]onstanz, [Q]uit");
    }

    private void handleGameInput() {
        String input = mScanner.nextLine();
        switch (input) {
            case "C", "c":
                startChess();
            PrintToConsole.println("You finished the game.");
                break;
            case "R", "r":
            	PrintToConsole.println("This game isn't implemented yet :(");
                break;
            case "S", "s":
            	PrintToConsole.println("This game isn't implemented yet :(");
                break;
            case "K", "k":
            	PrintToConsole.println("This game isn't implemented yet :(");
                break;
            case "Q", "q":
                quitGame();
            	break;
            default:
            	PrintToConsole.println("The given input is invalid.");
        }
        printSelectGame();
        handleGameInput();
    }

    private void startChess() {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.run();
    }

    private void quitGame() {
    	PrintToConsole.println("See you again soon!");
        System.exit(1);
    }
}
