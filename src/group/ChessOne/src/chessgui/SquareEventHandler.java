package chessgui;

import engine.squares.Square;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Eventhandler for highlighting the squares
 * @author Jan de Boer, Dennis Roemmich
 *
 */
@SuppressWarnings("rawtypes")
public class SquareEventHandler implements EventHandler {
    private GuiEventHandler mEventHandler;
    private Square mSquare;

    public SquareEventHandler(GuiEventHandler eventHandler, Square square) {
        this.mEventHandler = eventHandler;
        this.mSquare = square;
    }

    @Override
    public void handle(Event event) {
        mEventHandler.handleSquareClicked(mSquare);
    }

    public GuiEventHandler getEventHandler() {
        return mEventHandler;
    }

    public void setEventHandler(GuiEventHandler eventHandler) {
        this.mEventHandler = eventHandler;
    }

    public Square getSquare() {
        return mSquare;
    }

    public void setSquare(Square square) {
        this.mSquare = square;
    }
}
