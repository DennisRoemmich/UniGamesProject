package core.pieces;

import core.ChessBoard;
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
        List<Square> list = new ArrayList<>();
        
        list.addAll(moveFinder.diagonalMoveD1());
        list.addAll(moveFinder.diagonalMoveD2());
        list.addAll(moveFinder.diagonalMoveD3());
        list.addAll(moveFinder.diagonalMoveD4());

        list.addAll(moveFinder.forwardMove());
        list.addAll(moveFinder.backwardMove());
        list.addAll(moveFinder.rightwardMove());
        list.addAll(moveFinder.leftwardMove());

        return list;
    }
}
