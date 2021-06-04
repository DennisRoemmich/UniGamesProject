package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite){
        super(isWhite, ChessPieceType.BISHOP);
    }

    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {

        ChessPieceMoves moveFinder = new ChessPieceMoves(this, pos, board);
        List<Square> list = new ArrayList<>();

        list.addAll(moveFinder.diagonalMoveD1());
        list.addAll(moveFinder.diagonalMoveD2());
        list.addAll(moveFinder.diagonalMoveD3());
        list.addAll(moveFinder.diagonalMoveD4());

        return list;
    }

    //SonarLint: Remove this "clone" implementation; use a copy constructor or copy factory instead.
    @Override
    public ChessPiece clone() {
        return new Bishop(isWhite);
    }
}
