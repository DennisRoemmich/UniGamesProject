package console;

import framework.FileController;
import framework.JarExecutor;
import framework.OSDetector;
import framework.PrintToConsole;
import framework.StreamController;
import main.Main;
import main.SkatLauncher;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;

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
        PrintToConsole.println("Or play TicTacToe by entering \"T\" + the number of the team member (e.g. \"T1\")");
        PrintToConsole.println("The inputs are not case-sensitive.");
    }

    private void handleGameInput() {
        String input = mScanner.nextLine();
        switch (input) {
            case "C", "c":
                startChess();
                PrintToConsole.println("You finished the game.");
                break;
            case "R", "r":
                main.Main.main(new String[0]);
                break;
            case "S", "s":
                SkatLauncher.main(new String[0]);
                break;
            case "Sg", "sg", "SG":
                String[] args = new String[1];
                args[0] = "gui";
                SkatLauncher.main(args);
                break;
            case "K", "k":
                siedlerController.Main.main(new String[0]);
                break;
            case "T1", "t1":
                game.TicTacToe.main(new String[0]);
                break;
            case "T2", "t2":
                graphicalUI.GUIStarter.main(new String[0]);
                break;
            case "T3", "t3":
                TicTacToeFX.GUIStarter.main(new String[0]);
                break;
            case "T4", "t4":
                tictactoe.GUIStarter.main(new String[0]);
                break;
            case "T5", "t5":
                application.Main.main(new String[0]);
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
