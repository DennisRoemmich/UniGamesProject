package core.pieces;

import core.ChessBoard;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece  {

    public Knight(boolean isWhite){
        super(isWhite, ChessPieceType.KNIGHT);
    }

    @Override
    public List<Position> findMovesDisregardingCheck(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<Position>();

        ChessPiece piece = board.getPiece(pos);
        Row newRow = pos.getRow();
        Column newColumn = pos.getColumn();
        Position posToTest;
        for (int rowMirror : new int[]{1, -1}) {
            for (int columnMirror : new int[]{-1, 1}) {
                for (int diagonalMirror : new int[]{0, 1}) {
                    int rowOffset = (1 + diagonalMirror) * rowMirror;
                    int columnOffset = (2 - diagonalMirror) * columnMirror;
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
        }
        return list;
    }
}
