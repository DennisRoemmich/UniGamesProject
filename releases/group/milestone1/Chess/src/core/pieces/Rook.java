package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.Square;
import java.util.List;

/**
 * Rook piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Rook extends CastlingChessPiece  {

    public Rook(boolean isWhite) {
        super(isWhite, ChessPieceType.ROOK);
    }
    
    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, board);
        Direction[] rookDirections = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        return moveFinder.getReachableSquares(rookDirections);
    }
}
