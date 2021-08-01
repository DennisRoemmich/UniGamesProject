package engine.pieces;

import engine.Chess;
import engine.squares.Direction;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Rook piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Rook extends ChessPiece  {

    public Rook(PlayerColor playerColor) {
        super(playerColor, ChessPieceType.ROOK);
    }
    
    @Override
    public List<Square> findCoveredSquares(Chess game) {
    	Optional<Square> s = game.getBoard().getSquare(this);
    	if (!s.isPresent()) {
        	return new ArrayList<>();
        }
        Square origin = s.get();

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, game.getBoard());
        Direction[] rookDirections = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        return moveFinder.getReachableSquares(rookDirections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ChessPieceType.ROOK, getColor().isWhite(), getNumberOfMoves() == 0);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return hashCode() == o.hashCode();
    }
}
