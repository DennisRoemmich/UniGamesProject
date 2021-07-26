package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import positions.EdgePosition;

public class StreetPlaceholderEventHandler implements EventHandler<Event> {
    private SiedlerEventHandler mEventHandler;
    private EdgePosition mPosition;

    public StreetPlaceholderEventHandler(SiedlerEventHandler eventHandler, EdgePosition position) {
        this.mEventHandler = eventHandler;
        this.mPosition = position;
    }

    @Override
    public void handle(Event event) {
        mEventHandler.handleStreetClick(mPosition);
    }
}
