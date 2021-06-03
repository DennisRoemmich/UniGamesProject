package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Rook extends CastlingChessPiece  {

    public Rook(boolean isWhite){
        super(isWhite, ChessPieceType.ROOK);
    }
    
    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        List<Square> list = new ArrayList<Square>();

        ChessPiece piece = board.getPiece(pos);
        Rank newRank = pos.getRank();
        File newFile = pos.getFile();
        Square squareToTest;

        //Rochade
        /* TODO */
        // J: Kann auch nur als Königszug angesehen werden, da sonst Mehrdeutigkeiten entstehen (Tf1 vs kurze Rochade)

        for (int rowOffset = 1; rowOffset < 8; rowOffset++) {
            int columnOffset = 0;
            try {
                newRank = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
                squareToTest = new Square(newRank, newFile);
                if (board.isFieldFree(squareToTest)) {
                    list.add(squareToTest);
                } else {
                    if (board.isOccupiedByOpponent(squareToTest, piece.isWhite())) {
                        list.add(squareToTest);
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
                squareToTest = new Square(newRank, newFile);
                if (board.isFieldFree(squareToTest)) {
                    list.add(squareToTest);
                } else {
                    if (board.isOccupiedByOpponent(squareToTest, piece.isWhite())) {
                        list.add(squareToTest);
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
                squareToTest = new Square(newRank, newFile);
                if (board.isFieldFree(squareToTest)) {
                    list.add(squareToTest);
                } else {
                    if (board.isOccupiedByOpponent(squareToTest, piece.isWhite())) {
                        list.add(squareToTest);
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
                squareToTest = new Square(newRank, newFile);
                if (board.isFieldFree(squareToTest)) {
                    list.add(squareToTest);
                } else {
                    if (board.isOccupiedByOpponent(squareToTest, piece.isWhite())) {
                        list.add(squareToTest);
                    }
                    break;
                }
            } catch (Exception e) {

            }
        }
        return list;
    }
}
