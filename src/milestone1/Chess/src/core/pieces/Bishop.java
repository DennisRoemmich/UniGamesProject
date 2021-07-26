package core.pieces;

import core.ChessBoard;
import core.Color;
import core.positioning.Direction;
import core.positioning.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Bishop piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Bishop extends ChessPiece {

    public Bishop(Color color) {
        super(color, ChessPieceType.BISHOP);
    }
    
    public Bishop(Bishop bishop) {
    	super(bishop.getColor(), ChessPieceType.BISHOP);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board) {
        if(board.getSquare(this).isEmpty()) return new ArrayList<>();
        Square origin = board.getSquare(this).get();

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, board);
        Direction[] bishopDirections =
        		new Direction[]{Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT};
        return moveFinder.getReachableSquares(bishopDirections);
    }
}
