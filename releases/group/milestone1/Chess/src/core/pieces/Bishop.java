package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite){
        super(isWhite, ChessPieceType.BISHOP);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, board);
        Direction[] bishopDirections = new Direction[]{Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT};
        return moveFinder.getReachableSquares(bishopDirections);
    }

    //SonarLint: Remove this "clone" implementation; use a copy constructor or copy factory instead.
    @Override
    public ChessPiece clone() {
        return new Bishop(isWhite());
    }
}
