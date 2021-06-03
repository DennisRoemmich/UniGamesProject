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
        List<Square> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        
        ChessPieceMoves.diagonalMoveD1(pos, board, list, piece);
        ChessPieceMoves.diagonalMoveD2(pos, board, list, piece);
        ChessPieceMoves.diagonalMoveD3(pos, board, list, piece);
        ChessPieceMoves.diagonalMoveD4(pos, board, list, piece);
        
        ChessPieceMoves.forwardMove(pos, board, list, piece);
        ChessPieceMoves.backwardMove(pos, board, list, piece);
        ChessPieceMoves.rightwardMove(pos, board, list, piece);
        ChessPieceMoves.leftwardMove(pos, board, list, piece);

        return list;
    }
}
