package core.pieces;

import core.ChessBoard;
import core.MoveFinder;
import core.positioning.Position;

import java.util.List;

public class Rook extends ChessPiece  {

    private boolean hasMoved = false;

    public Rook(boolean isWhite){
        super(isWhite, ChessPieceType.ROOK);
    }

    @Override
    public List<Position> findMoves(Position pos, ChessBoard board) {
        return MoveFinder.findMoves(pos, board);
    }
}
