package engine.pieces;

import engine.Chess;
import engine.squares.Direction;
import engine.squares.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Square> findCoveredSquares(Chess game) {
    	Optional<Square> s = game.getBoard().getSquare(this);
        if (!s.isPresent()) {
        	return new ArrayList<>();
        }
        Square origin = s.get();
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, game.getBoard());
        Direction[] bishopDirections =
        		new Direction[]{Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT};
        return moveFinder.getReachableSquares(bishopDirections);
    }
}
