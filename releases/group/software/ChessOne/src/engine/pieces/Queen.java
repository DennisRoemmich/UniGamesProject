package engine.pieces;

import engine.Chess;
import engine.squares.Direction;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Queen piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Queen extends ChessPiece  {

    public Queen(PlayerColor playerColor) {
        super(playerColor, ChessPieceType.QUEEN);
    }

    @Override
    public List<Square> findCoveredSquares(Chess game) {
    	Optional<Square> s = game.getBoard().getSquare(this);
    	if (!s.isPresent()) {
        	return new ArrayList<>();
        }
        Square origin = s.get();

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, game.getBoard());
        return moveFinder.getReachableSquares(Direction.values());
    }
}
