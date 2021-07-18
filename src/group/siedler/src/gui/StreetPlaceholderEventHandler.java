package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import positions.EdgePosition;
import siedlerController.Controller;
import streets.StreetType;

public class StreetPlaceholderEventHandler implements EventHandler {
    private Controller controller;
    private EdgePosition position;

    public StreetPlaceholderEventHandler(Controller controller, EdgePosition position) {
        this.controller = controller;
        this.position = position;
    }

    @Override
    public void handle(Event event) {
        controller.placeStreet(position, StreetType.ROAD);
    }
}
