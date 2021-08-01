package menuMain;


import console.ConsoleMenu;
import main.SkatLauncher;
import java.util.Scanner;

public class MenuPrototype {

    private final Scanner mScanner = new Scanner(System.in);

    public MenuPrototype() {

        printWelcomeMessage();
        printSelectGame();
        handleGameInput();

    }

    private void printWelcomeMessage() {
        println("Welcome to our game collection!");
    }

    public void startLoop() {
        printSelectGame();
        handleGameInput();
    }

    private void printSelectGame() {
    	println("Please choose a game to play:");
    	print("[C]hess, [R]ummikub, [S]kat, Die Siedler von [K]onstanz, [T*]icTacToes, [Q]uit\n> ");
    }

    private void handleGameInput() {
        String input = mScanner.nextLine();
        var error = "This game isn't implemented yet :(";
        input = input.toUpperCase();
        switch (input) {
            case "C", "c":
                startChess();
            println("You finished the game.");
                break;
            case "R", "r":
            	startRummikub();
                break;
            case "S", "s":
            	startSkat();
                break;
            case "K", "k":
            	startSiedler();
                break;
            case "T1": // MARIA

                try {
                    game.TicTacToe.main(new String[0]);
                } catch (Exception e) {
                    println(">> Please restart the menu before opening another GUI-based application");
                }

            	break;
            case "T2": // JAN

                try {
                    graphicalUI.GUIStarter.main(new String[0]);
                } catch (Exception e) {
                    println(">> Please restart the menu before opening another GUI-based application");
                }

                break;
            case "T3": // ANDREAS

                try {
                    TicTacToeFX.GUIStarter.main(new String[0]);
                } catch (Exception e) {
                    println(">> Please restart the menu before opening another GUI-based application");
                }

                break;
            case "T4": // DENNIS

                try {
                    application.Main.main(new String[0]);
                } catch (Exception e) {
                    println(">> Please restart the menu before opening another GUI-based application");
                }


                break;
            case "T5": // MAIK

                try {
                    tictactoe.GUIStarter.main(new String[0]);
                } catch (Exception e) {
                    println(">> Please restart the menu before opening another GUI-based application");
                }

                break;
            default:
            	println("The given input is invalid.");
        }
        printSelectGame();
        handleGameInput();
    }

    private void startChess() {


        var invalidInput = false;

        do {


            print("Do you want to play the [C]onsole or the [G]ui version?\n> ");
            String input = mScanner.nextLine();
            input = input.toUpperCase();

            switch (input) {

                case "C":

                    ConsoleMenu consoleMenu = new ConsoleMenu();
                    consoleMenu.run();
                    break;

                case "G":

                    try {
                        chessgui.Main.main(new String[0]);
                    } catch (Exception e){
                        println(">> Please restart the menu before opening another GUI-based application");
                    }
                    break;

                case "Q":

                    break;

                default:
                    invalidInput = true;
                    println("invalid input");
                    break;

            }

        } while (invalidInput);


    }

    private void startSiedler(){

        siedler.controller.Main.main(new String[0]);

    }

    private void startRummikub() {

        var invalidInput = false;

        do {


            print("Do you want to run a test that disables randomness in order to reach the end of the game quickly? (y/n)\n> ");
            String input = mScanner.nextLine();
            input = input.toUpperCase();

            switch (input) {

                case "Y":

                    try {
                        rummikubmain.Main.main(new String[]{"test"});
                    } catch (Exception e) {
                        println(">> Please restart the menu before opening another GUI-based application");
                    }
                    break;

                case "N":

                    try {
                        rummikubmain.Main.main(new String[]{});
                    } catch (Exception e) {
                        println(">> Please restart the menu before opening another GUI-based application");
                    }


                    break;
                case "Q":

                    break;

                default:
                    invalidInput = true;
                    println("invalid input");
                    break;

            }

        } while (invalidInput);

    }

    private void startSkat(){

        var invalidInput = false;

        do {


            print("Do you want to play the [C]onsole or the [G]ui version?\n> ");
            String input = mScanner.nextLine();
            input = input.toUpperCase();

            switch (input) {

                case "C":

                    SkatLauncher.main(new String[]{"windows"});
                    break;

                case "G":

                    try {
                        guimain.GuiSkatLauncher.main(new String[]{});
                    } catch (Exception e){
                        println(">> Please restart the menu before opening another GUI-based application");
                    }
                    break;
                case "Q":

                    break;

                default:
                    invalidInput = true;
                    println("invalid input");
                    break;

            }

        } while (invalidInput);


    }

    private void quitGame() {
    	println("See you again soon!");
        System.exit(1);
    }

    private void println(String str){
        System.out.println(str);
    }
    private void print(String str){
        System.out.print(str);
    }


}
