package gui;

import javafx.event.Event;

import javafx.event.EventHandler;
import positions.TilePosition;

/**
 * Represents the functionality of clicking onto a tile (e.g. moving the burglar)
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class TileClickedEventHandler implements EventHandler<Event> {

    private TilePosition mPosition;
    private SiedlerEventHandler mEventHandler;

    public TileClickedEventHandler(TilePosition position, SiedlerEventHandler eventHandler) {
        this.mPosition = position;
        this.mEventHandler = eventHandler;
    }

    @Override
    public void handle(Event event) {
        mEventHandler.handleTileCLick(mPosition);
    }
}
