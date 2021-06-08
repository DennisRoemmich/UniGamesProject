package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.Square;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite) {
        super(isWhite, ChessPieceType.BISHOP);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, board);
        Direction[] bishopDirections =
        		new Direction[]{Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT};
        return moveFinder.getReachableSquares(bishopDirections);
    }

    //TODO: Eliminate clone method. Use copy constructor.
    @Override
    public ChessPiece clone() {
        return new Bishop(isWhite());
    }
}
