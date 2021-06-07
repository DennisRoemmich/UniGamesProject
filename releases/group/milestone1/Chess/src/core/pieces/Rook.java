package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Rook extends CastlingChessPiece  {

    public Rook(boolean isWhite){
        super(isWhite, ChessPieceType.ROOK);
    }
    
    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, board);

        //Rochade
        /* TODO */
        // J: Kann auch nur als Königszug angesehen werden, da sonst Mehrdeutigkeiten entstehen (Tf1 vs kurze Rochade)

        Direction[] rookDirections = new Direction[]{Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        return moveFinder.getReachableSquares(rookDirections);
    }
}
