package chessgui;

import engine.pieces.PositionedPiece;
import engine.squares.Square;

/**
 * Eventhandler interface for the GUI interactions
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public interface GuiEventHandler {
    void handlePieceClicked(PositionedPiece piece);
    
    void handleSquareClicked(Square square);
}
