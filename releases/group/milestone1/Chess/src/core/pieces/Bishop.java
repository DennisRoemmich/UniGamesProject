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
        List<Square> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);

        ChessPieceMoves.diagonalMoveD1(pos, board, list, piece);
        ChessPieceMoves.diagonalMoveD2(pos, board, list, piece);
        ChessPieceMoves.diagonalMoveD3(pos, board, list, piece);
        ChessPieceMoves.diagonalMoveD4(pos, board, list, piece);

        return list;
    }

    //SonarLint: Remove this "clone" implementation; use a copy constructor or copy factory instead.
    @Override
    public ChessPiece clone() {
        return new Bishop(isWhite);
    }
}
