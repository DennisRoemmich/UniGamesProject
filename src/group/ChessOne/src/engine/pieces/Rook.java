package engine.pieces;

import engine.Chess;
import engine.board.ChessBoard;
import engine.pieces.Color;
import engine.squares.Direction;
import engine.squares.Square;

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
    public List<Square> findCoveredSquares(Chess game) {
        if(game.getBoard().getSquare(this).isEmpty()) return new ArrayList<>();
        Square origin = game.getBoard().getSquare(this).get();

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, game.getBoard());
        Direction[] rookDirections = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        return moveFinder.getReachableSquares(rookDirections);
    }
}
