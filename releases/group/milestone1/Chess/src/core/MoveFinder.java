package core;

import core.pieces.ChessPiece;
import core.pieces.Pawn;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class MoveFinder {

    public static List<Position> findMoves(Position pos, ChessBoard board) {

        ChessPiece piece = board.getPiece(pos);

        switch (piece.getType()) {
            case PAWN:
                return findPawnMoves(pos, board);
            case KNIGHT:
                return findKnightMoves(pos, board);
            case BISHOP:
                return findBishopMoves(pos, board);

            case ROOK:
                return findRookMoves(pos, board);

            case QUEEN:
                return findQueenMoves(pos, board);

            case KING:
                return findKingMoves(pos, board);

            default:
                return new ArrayList<Position>();
        }
    }

    private static List<Position> findKingMoves(Position pos, ChessBoard board) {
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

    private static List<Position> findQueenMoves(Position pos, ChessBoard board) {
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

    private static List<Position> findRookMoves(Position pos, ChessBoard board) {
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

    private static List<Position> findBishopMoves(Position pos, ChessBoard board) {
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

    private static List<Position> findKnightMoves(Position pos, ChessBoard board) {
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

    private static List<Position> findPawnMoves(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<Position>();

        ChessPiece piece = board.getPiece(pos);
        Row newRow = pos.getRow();
        Column newColumn = pos.getColumn();
        Position posToTest;

        int directionFactor = piece.isWhite() ? 1 : -1;

        //Einfacher Zug
        newRow = Row.valueOf(pos.getRow().getIndex() + 1 * directionFactor);
        posToTest = new Position(newRow, pos.getColumn());

        if (board.isFieldFree(posToTest)) {
            list.add(posToTest);
        }

        //Doppelzug
        try {
            Pawn pawn = (Pawn) piece;
            if (pawn.getNumberOfMoves() == 0) {
                newRow = Row.valueOf(pos.getRow().getIndex() + 2 * directionFactor);
                posToTest = new Position(newRow, newColumn);
                if (board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                }
            }
        } catch (Exception e){

        }

        //Schlagen
        for (int columnOffset : new int[]{-1, 1}) {
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
    }
}
