package core.pieces;

import core.Chess;
import core.ChessBoard;
import core.Color;
import core.positioning.Direction;
import core.positioning.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Queen piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Queen extends ChessPiece  {

    public Queen(Color color) {
        super(color, ChessPieceType.QUEEN);
    }

    @Override
    public List<Square> findCoveredSquares(Chess game) {
        if(game.getBoard().getSquare(this).isEmpty()) return new ArrayList<>();
        Square origin = game.getBoard().getSquare(this).get();

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, game.getBoard());
        return moveFinder.getReachableSquares(Direction.values());
    }
}
