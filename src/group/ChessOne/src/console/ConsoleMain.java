package console;

import framework.PrintToConsole;

/**
 * Main console class that starts the chess game UI.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleMain {
    public static void main(String[] args) {
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.run();
    }
}
