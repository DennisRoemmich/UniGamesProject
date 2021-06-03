package core.pieces;

import core.ChessBoard;
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
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        List<Square> list = new ArrayList<Square>();

        ChessPiece piece = board.getPiece(pos);

        //Rochade
        /* TODO */
        // J: Kann auch nur als Königszug angesehen werden, da sonst Mehrdeutigkeiten entstehen (Tf1 vs kurze Rochade)

        ChessPieceMoves.forwardMove(pos, board, list, piece);
        ChessPieceMoves.backwardMove(pos, board, list, piece);
        ChessPieceMoves.rightwardMove(pos, board, list, piece);
        ChessPieceMoves.leftwardMove(pos, board, list, piece);
        
        return list;
    }
}
