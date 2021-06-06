package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
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

        //Rochade King side
        /*ChessPieceMoves.rightwardMove(pos, board, temp, this);
        if(temp.size()==2 && !hasMoved()) {
        	list.add(temp.get(1));
        }*/
        
        //Rochade Queen side
        /*ChessPieceMoves.leftwardMove(pos, board, temp, this);
        if(temp.size()==3 && !hasMoved()) {
        	list.add(temp.get(1));
        }*/

        for (Direction direction : Direction.values()) {
            try {
                Square squareToTest = pos.getNext(direction);
                if (board.isOccupiedByOpponentOrFree(squareToTest, isWhite)) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {

            }
        }
        return list;
    }

}
