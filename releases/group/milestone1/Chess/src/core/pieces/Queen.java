package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece  {

    public Queen(boolean isWhite){
        super(isWhite, ChessPieceType.QUEEN);
    }

    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        List<Square> list = new ArrayList<Square>();

        ChessPiece piece = board.getPiece(pos);
        Rank newRank = pos.getRank();
        File newFile = pos.getFile();
        Square posToTest;
        for (int rightDiagonal = 1; rightDiagonal < 8; rightDiagonal++) {
            int rowOffset = rightDiagonal;
            int columnOffset = rightDiagonal;
            try {
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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

        for (int rowOffset = 1; rowOffset < 8; rowOffset++) {
            int columnOffset = 0;
            try {
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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

        for (int rowOffset = -1; rowOffset > -8; rowOffset--) {
            int columnOffset = 0;
            try {
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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

        for (int columnOffset = 1; columnOffset < 8; columnOffset++) {
            int rowOffset = 0;
            try {
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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

        for (int columnOffset = -1; columnOffset > -8; columnOffset--) {
            int rowOffset = 0;
            try {
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
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
}
