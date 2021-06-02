package core.pieces;

import core.ChessBoard;
import core.MoveFinder;
import core.positioning.Position;

import java.util.List;

public class King extends ChessPiece  {

    private boolean hasMoved = false;

    public King(boolean isWhite){
        super(isWhite, ChessPieceType.KING);
    }

    @Override
    public List<Position> findMoves(Position pos, ChessBoard board) {
        return MoveFinder.findMoves(pos, board);
    }
}
