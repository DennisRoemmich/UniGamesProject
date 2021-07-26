package core.pieces;

import core.ChessBoard;
import core.Color;
import core.positioning.Direction;
import core.positioning.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Rook piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Rook extends ChessPiece  {

    public Rook(Color color) {
        super(color, ChessPieceType.ROOK);
    }
    
    @Override
    public List<Square> findCoveredSquares(ChessBoard board) {
        if(board.getSquare(this).isEmpty()) return new ArrayList<>();
        Square origin = board.getSquare(this).get();

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, board);
        Direction[] rookDirections = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        return moveFinder.getReachableSquares(rookDirections);
    }
}
