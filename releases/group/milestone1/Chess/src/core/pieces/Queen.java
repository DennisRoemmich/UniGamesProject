package core.pieces;

import core.ChessBoard;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece  {

    public Queen(boolean isWhite){
        super(isWhite, ChessPieceType.QUEEN);
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
