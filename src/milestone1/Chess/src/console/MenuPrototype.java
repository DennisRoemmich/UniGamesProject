package console;

import TicTacToeFX.GUIStarter;
import TicTacToeFernanda.TicTacToe;
import framework.PrintToConsole;

import java.io.IOException;
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
    	PrintToConsole.printkn("Or play TicTacToe by entering \"T\" + the number of the team member (e.g. \"T1\"");
        PrintToConsole.printkn("The inputs are not case-sensitive.");
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
            case "T1", "t1":
                TicTacToe.main(new String[0]);
                break;
            case "T2", "t2":
                break;
            case "T3", "t3":
                GUIStarter.main(new String[0]);
                break;
            case "T4", "t4":
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
