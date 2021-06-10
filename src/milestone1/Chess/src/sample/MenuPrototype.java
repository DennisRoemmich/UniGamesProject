package sample;

import java.util.Scanner;

public class MenuPrototype {

    private Scanner scanner = new Scanner(System.in);

    public MenuPrototype() {
        printWelcomeMessage();
    }

    private void printWelcomeMessage() {
        System.out.println("Welcome to our game collection!");
    }

    public void startLoop() {
        printSelectGame();
        handleGameInput();
    }

    private void printSelectGame() {
        System.out.println("Please chose a game to play:");
        System.out.println("[C]hess, [R]umikub, [S]kat, Die Siedler von [K]onstanz");
    }

    private void handleGameInput() {
        String input = scanner.nextLine();
        switch(input) {
            case "C", "c":
                startChess();
                break;
            case "R", "r":
                System.out.println("This game isn't implemented yet :(");
                break;
            case "S", "s":
                System.out.println("This game isn't implemented yet :(");
                break;
            case "K", "k":
                System.out.println("This game isn't implemented yet :(");
                break;
            case "Q", "q":
                quitGame();
            default:
                System.out.println("The given input is invalid.");
        }
        handleGameInput();
    }

    private void startChess() {
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.startGame();
    }

    private void quitGame() {
        System.out.println("See you again soon!");
        System.exit(1);
    }
}
