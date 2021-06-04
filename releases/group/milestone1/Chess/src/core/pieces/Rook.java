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
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, pos, board);
        List<Square> list = new ArrayList<>();

        //Rochade
        /* TODO */
        // J: Kann auch nur als Königszug angesehen werden, da sonst Mehrdeutigkeiten entstehen (Tf1 vs kurze Rochade)

        list.addAll(moveFinder.forwardMove());
        list.addAll(moveFinder.backwardMove());
        list.addAll(moveFinder.rightwardMove());
        list.addAll(moveFinder.leftwardMove());

        return list;
    }
}
