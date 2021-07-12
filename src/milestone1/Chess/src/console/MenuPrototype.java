package console;

import frameworkchess.PrintToConsole;
import main.GUIStarter;
import main.SkatLauncher;

import java.util.Scanner;

public class MenuPrototype {

    private Scanner mScanner = new Scanner(System.in);
    private Object main;

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
        PrintToConsole.println("You can access a console version of Skat by entering \"SC\"");
        PrintToConsole.println("Or play TicTacToe by entering \"T\" + the first letter of the team member ([F],[J],[A],[D],[M])");
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
                GUIStarter.main(new String[0]);
                break;
            case "S", "s":
                SkatLauncher.main(new String[]{"gui"});
                break;
            case "Sc", "SC", "sc", "sC":
                SkatLauncher.main(new String[]{"windows"});
                break;
            case "K", "k":
                PrintToConsole.println("This game isn't implemented yet :(");
                break;
            case "tf", "Tf", "TF", "tF":
                game.TicTacToe.main(new String[0]);
                break;
            case "tj", "Tj", "TJ", "tJ":
                graphicalUI.GUIStarter.main(new String[0]);
                break;
            case "ta", "Ta", "TA", "tA":
                TicTacToeFX.GUIStarter.main(new String[0]);
                break;
            case "tm", "Tm", "TM", "tM":
                tictactoe.GUIStarter.main(new String[0]);
                break;
            case "td", "Td", "TD", "tD":
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
