package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class King extends CastlingChessPiece  {

    public King(boolean isWhite){
        super(isWhite, ChessPieceType.KING);
    }

    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        List<Square> list = new ArrayList<Square>();

        ChessPiece piece = board.getPiece(pos);
        Rank newRank;
        File newFile;
        Square posToTest;

        //Rochade
        /* TODO */

        for (int rowOffset : new int[]{-1, 0, 1}) {
            for (int columnOffset : new int[]{-1, 0, 1}) {
                try {
                    newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                    newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
                    posToTest = new Square(newRank, newFile);
                    if (board.isOccupiedByOpponentOrFree(posToTest, piece.isWhite())) {
                        list.add(posToTest);
                    }
                } catch (Exception e) {

                }
            }
        }
        return list;
    }

}
