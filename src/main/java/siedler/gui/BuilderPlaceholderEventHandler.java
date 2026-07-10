package siedler.gui;

import javafx.event.Event;

import javafx.event.EventHandler;
import positions.NodePosition;

/**
 * Handles mouse clicks onto the transparent (or villages to upgrade) Building placeholders.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
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
