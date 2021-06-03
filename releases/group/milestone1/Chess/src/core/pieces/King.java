package core.pieces;

import core.ChessBoard;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece  {

    private boolean hasMoved = false;

    public King(boolean isWhite){
        super(isWhite, ChessPieceType.KING);
    }

    @Override
    public List<Position> findMovesDisregardingCheck(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<Position>();

        ChessPiece piece = board.getPiece(pos);
        Row newRow = pos.getRow();
        Column newColumn = pos.getColumn();
        Position posToTest;

        //Rochade
        /* TODO */

        for (int rowOffset : new int[]{-1, 0, 1}) {
            for (int columnOffset : new int[]{-1, 0, 1}) {
                try {
                    newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                    newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                    posToTest = new Position(newRow, newColumn);
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
