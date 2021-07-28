package gui;

import engine.pieces.PositionedPiece;
import javafx.event.Event;
import javafx.event.EventHandler;

public class PieceEventHandler implements EventHandler {
    private PositionedPiece piece;
    private GuiEventHandler eventHandler;

    public PieceEventHandler(GuiEventHandler eventHandler, PositionedPiece piece) {
        this.eventHandler = eventHandler;
        this.piece = piece;
    }

    @Override
    public void handle(Event event) {
        eventHandler.handlePieceClicked(piece);
    }

    public GuiEventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(GuiEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public PositionedPiece getPiece() {
        return piece;
    }

    public void setPiece(PositionedPiece piece) {
        this.piece = piece;
    }
}
