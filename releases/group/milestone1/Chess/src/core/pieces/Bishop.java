package core.pieces;

import core.ChessBoard;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite){
        super(isWhite, ChessPieceType.BISHOP);
    }

    @Override
    public List<Position> findMovesDisregardingCheck(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<Position>();

        ChessPiece piece = board.getPiece(pos);
        Row newRow = pos.getRow();
        Column newColumn = pos.getColumn();
        Position posToTest;
        for (int rightDiagonal = 1; rightDiagonal < 8; rightDiagonal++) {


            int rowOffset = rightDiagonal;
            int columnOffset = rightDiagonal;
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

        for (int rightDiagonal = -1; rightDiagonal > -8; rightDiagonal--) {


            int rowOffset = rightDiagonal;
            int columnOffset = rightDiagonal;
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

        for (int leftDiagonal = 1; leftDiagonal < 8; leftDiagonal++) {
            int rowOffset = leftDiagonal;
            int columnOffset = -leftDiagonal;
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

        for (int leftDiagonal = -1; leftDiagonal > -8; leftDiagonal--) {
            int rowOffset = leftDiagonal;
            int columnOffset = -leftDiagonal;
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

    @Override
    public ChessPiece clone() {
        return new Bishop(isWhite);
    }
}
