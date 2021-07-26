package gui;

import javafx.event.Event;

import javafx.event.EventHandler;
import positions.NodePosition;

public class BuilderPlaceholderEventHandler implements EventHandler<Event> {
    private SiedlerEventHandler mEventHandler;
    private NodePosition mPosition;

    public BuilderPlaceholderEventHandler(SiedlerEventHandler eventHandler, NodePosition position) {
        this.mEventHandler = eventHandler;
        this.mPosition = position;
    }

    @Override
    public void handle(Event event) {
        mEventHandler.handleBuildingClick(mPosition);
    }
}
