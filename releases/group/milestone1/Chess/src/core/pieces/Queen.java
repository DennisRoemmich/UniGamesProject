package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece  {

    public Queen(boolean isWhite){
        super(isWhite, ChessPieceType.QUEEN);
    }

    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, pos, board);
        return moveFinder.getReachableSquares(Direction.values());
    }
}
