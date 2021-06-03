package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(boolean isWhite){
        super(isWhite, ChessPieceType.BISHOP);
    }

    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        List<Square> list = new ArrayList<Square>();

        ChessPiece piece = board.getPiece(pos);
        Rank newRank = pos.getRow();
        File newFile = pos.getColumn();
        Square posToTest;
        for (int rightDiagonal = 1; rightDiagonal < 8; rightDiagonal++) {


            int rowOffset = rightDiagonal;
            int columnOffset = rightDiagonal;
            try {
                newRank = Rank.valueOf(pos.getRow().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Square(newRank, newFile);
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
                newRank = Rank.valueOf(pos.getRow().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Square(newRank, newFile);
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
                newRank = Rank.valueOf(pos.getRow().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Square(newRank, newFile);
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
                newRank = Rank.valueOf(pos.getRow().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Square(newRank, newFile);
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
