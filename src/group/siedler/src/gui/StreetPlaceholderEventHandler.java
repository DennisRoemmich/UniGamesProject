package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import positions.EdgePosition;
import siedlerController.Controller;
import streets.StreetType;

public class StreetPlaceholderEventHandler implements EventHandler {
    private SiedlerEventHandler eventHandler;
    private EdgePosition position;

    public StreetPlaceholderEventHandler(SiedlerEventHandler eventHandler, EdgePosition position) {
        this.eventHandler = eventHandler;
        this.position = position;
    }

    @Override
    public void handle(Event event) {
        eventHandler.handleStreetClick(position);
    }
}
