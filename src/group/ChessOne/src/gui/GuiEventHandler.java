package gui;

import engine.pieces.PositionedPiece;
import engine.squares.Square;

public interface GuiEventHandler {
    void handlePieceClicked(PositionedPiece piece);
    
    void handleSquareClicked(Square square);
}
