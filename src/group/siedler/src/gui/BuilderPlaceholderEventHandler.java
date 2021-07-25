package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import org.w3c.dom.Node;
import positions.EdgePosition;
import positions.NodePosition;
import siedlerController.Controller;
import streets.StreetType;

public class BuilderPlaceholderEventHandler implements EventHandler {
    private SiedlerEventHandler eventHandler;
    private NodePosition position;

    public BuilderPlaceholderEventHandler(SiedlerEventHandler eventHandler, NodePosition position) {
        this.eventHandler = eventHandler;
        this.position = position;
    }

    @Override
    public void handle(Event event) {
        eventHandler.handleBuildingClick(position);
    }
}
