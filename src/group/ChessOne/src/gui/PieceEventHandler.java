package gui;

import engine.pieces.PositionedPiece;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Eventhandler for initializing moving the pieces
 * @author Jan de Boer, Dennis Roemmich
 *
 */
@SuppressWarnings("rawtypes")
public class PieceEventHandler implements EventHandler {
    private PositionedPiece mPiece;
    private GuiEventHandler mEventHandler;

    public PieceEventHandler(GuiEventHandler eventHandler, PositionedPiece piece) {
        this.mEventHandler = eventHandler;
        this.mPiece = piece;
    }

    @Override
    public void handle(Event event) {
        mEventHandler.handlePieceClicked(mPiece);
    }

    public GuiEventHandler getEventHandler() {
        return mEventHandler;
    }

    public void setEventHandler(GuiEventHandler eventHandler) {
        this.mEventHandler = eventHandler;
    }

    public PositionedPiece getPiece() {
        return mPiece;
    }

    public void setPiece(PositionedPiece piece) {
        this.mPiece = piece;
    }
}
