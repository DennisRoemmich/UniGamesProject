package sample;

/**
 * Main console class that starts the chess game UI.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ConsoleMain {
    public static void main(String[] args) {
        MenuPrototype menu = new MenuPrototype();
        menu.startLoop();
    }
}
