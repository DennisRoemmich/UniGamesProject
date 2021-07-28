package engine.pieces;

import engine.Chess;
import engine.board.ChessBoard;
import engine.pieces.Color;
import engine.squares.Direction;
import engine.squares.Square;

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
    public List<Square> findCoveredSquares(Chess game) {
        if(game.getBoard().getSquare(this).isEmpty()) return new ArrayList<>();
        Square origin = game.getBoard().getSquare(this).get();

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, game.getBoard());
        Direction[] bishopDirections =
        		new Direction[]{Direction.UP_LEFT, Direction.UP_RIGHT, Direction.DOWN_LEFT, Direction.DOWN_RIGHT};
        return moveFinder.getReachableSquares(bishopDirections);
    }
}
