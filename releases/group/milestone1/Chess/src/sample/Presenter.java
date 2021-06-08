package sample;

/**
 * Main interface for the surrounding logic.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public interface Presenter {
    void setController(Controller controller);
    
    void refreshOutput();
}
