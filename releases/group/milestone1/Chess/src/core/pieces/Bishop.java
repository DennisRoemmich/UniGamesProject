package core.pieces;

import core.ChessBoard;
import core.MoveFinder;
import core.positioning.Position;

import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite){
        super(isWhite, ChessPieceType.BISHOP);
    }

    @Override
    public List<Position> findMoves(Position pos, ChessBoard board) {
        return MoveFinder.findMoves(pos, board);
    }

    @Override
    public ChessPiece clone() {
        return new Bishop(isWhite);
    }
}
