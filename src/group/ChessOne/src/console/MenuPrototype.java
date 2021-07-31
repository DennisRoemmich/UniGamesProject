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
    	PrintToConsole.println("Please choose a game to play:");
    	PrintToConsole.println("[C]hess, [R]ummikub, [S]kat, Die Siedler von [K]onstanz, [Q]uit");
    }

    private void handleGameInput() {
        String input = mScanner.nextLine();
        String error = "This game isn't implemented yet :(";
        switch (input) {
            case "C", "c":
                startChess();
            PrintToConsole.println("You finished the game.");
                break;
            case "R", "r":
            	PrintToConsole.println(error);
                break;
            case "S", "s":
            	PrintToConsole.println(error);
                break;
            case "K", "k":
            	PrintToConsole.println(error);
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
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.run();
    }

    private void quitGame() {
    	PrintToConsole.println("See you again soon!");
        System.exit(1);
    }
}
