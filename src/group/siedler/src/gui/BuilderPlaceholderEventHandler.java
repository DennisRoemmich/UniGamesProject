package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import org.w3c.dom.Node;
import positions.EdgePosition;
import positions.NodePosition;
import siedlerController.Controller;
import streets.StreetType;

public class BuilderPlaceholderEventHandler implements EventHandler {
    private final Controller controller;
    private final NodePosition position;

    public BuilderPlaceholderEventHandler(Controller controller, NodePosition position) {
        this.controller = controller;
        this.position = position;
    }

    @Override
    public void handle(Event event) {
        controller.placeBuilding(position);
    }
}
