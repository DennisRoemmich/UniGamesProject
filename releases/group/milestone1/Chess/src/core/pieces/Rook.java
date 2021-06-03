package core.pieces;

import core.ChessBoard;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece  {

    private boolean hasMoved = false;

    public Rook(boolean isWhite){
        super(isWhite, ChessPieceType.ROOK);
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
        // J: Kann auch nur als Königszug angesehen werden, da sonst Mehrdeutigkeiten entstehen (Tf1 vs kurze Rochade)

        for (int rowOffset = 1; rowOffset < 8; rowOffset++) {
            int columnOffset = 0;
            try {
                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Position(newRow, newColumn);
                if (board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                } else {
                    if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                        list.add(posToTest);
                    }
                    break;
                }
            } catch (Exception e) {

            }
        }

        for (int rowOffset = -1; rowOffset > -8; rowOffset--) {
            int columnOffset = 0;
            try {
                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Position(newRow, newColumn);
                if (board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                } else {
                    if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                        list.add(posToTest);
                    }
                    break;
                }
            } catch (Exception e) {

            }
        }

        for (int columnOffset = 1; columnOffset < 8; columnOffset++) {
            int rowOffset = 0;
            try {
                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Position(newRow, newColumn);
                if (board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                } else {
                    if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                        list.add(posToTest);
                    }
                    break;
                }
            } catch (Exception e) {

            }
        }

        for (int columnOffset = -1; columnOffset > -8; columnOffset--) {
            int rowOffset = 0;
            try {
                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Position(newRow, newColumn);
                if (board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                } else {
                    if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                        list.add(posToTest);
                    }
                    break;
                }
            } catch (Exception e) {

            }
        }
        return list;
    }
}
