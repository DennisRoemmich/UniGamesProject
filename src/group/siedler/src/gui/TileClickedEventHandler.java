package gui;

import javafx.event.Event;

import javafx.event.EventHandler;
import positions.TilePosition;

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
