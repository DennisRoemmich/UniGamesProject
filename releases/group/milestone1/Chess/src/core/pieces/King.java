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
        List<Square> list = new ArrayList<>();
        List<Square> temp = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        Rank newRank;
        File newFile;
        Square posToTest;

        //Rochade King side
        ChessPieceMoves.rightwardMove(pos, board, temp, piece);
        if(temp.size()==2 && !hasMoved()) {
        	list.add(temp.get(1));
        }
        
        //Rochade Queen side
        ChessPieceMoves.leftwardMove(pos, board, temp, piece);
        if(temp.size()==3 && !hasMoved()) {
        	list.add(temp.get(1));
        }


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
