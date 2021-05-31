package core;

import java.util.ArrayList;
import java.util.List;

public class MoveFinder {
    public static List<Position> findMoves(Position pos, ChessBoard board) {
        ChessPiece piece = board.getPiece(pos);
        List<Position> list = new ArrayList<Position>();

        Row newRow = pos.getRow();
        Column newColumn = pos.getColumn();
        Position posToTest;

        switch (piece.getType()) {
            case PAWN:
                int directionFactor = piece.isWhite() ? 1 : -1;
                //Einfacher Zug
                newRow = Row.valueOf(pos.getRow().getIndex() + 1 * directionFactor);
                posToTest = new Position(newRow, pos.getColumn());
                if(board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                }

                //Doppelzug
                if(piece.getLastMove() == 0) {
                    newRow = Row.valueOf(pos.getRow().getIndex() + 2 * directionFactor);
                    posToTest = new Position(newRow, newColumn);
                    if(board.isFieldFree(posToTest)) {
                        list.add(posToTest);
                    }
                }

                //Schlagen
                for(int columnOffset : new int[]{-1, 1}) {
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + directionFactor);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                            list.add(posToTest);
                        }
                    } catch (Exception e) {

                    }
                }
                //En-passant
                /* TODO */
                //Verwandlung
                /* TODO */
                return list;
            case KNIGHT:
                for(int rowMirror : new int[]{1, -1}){
                    for(int columnMirror: new int[]{-1,1}){
                        for(int diagonalMirror: new int[]{0,1}) {
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
            case BISHOP:
                return list;
            case ROOK:
                return list;
            case QUEEN:
                return list;
            case KING:
                return list;
            default:
                return list;
        }
    }
}
