package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    private int numberOfMoves = 0;
    private int lastMoved = 0;

    public Pawn(boolean isWhite){
        super(isWhite, ChessPieceType.PAWN);
    }

    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        List<Square> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        Rank newRank;
        File newFile = pos.getFile();
        Square posToTest;

        int directionFactor = piece.isWhite() ? 1 : -1;

        //Einfacher Zug
        newRank = Rank.valueOf(pos.getRank().getIndex() + 1 * directionFactor);
        posToTest = new Square(newRank, pos.getFile());

        if (board.isFieldFree(posToTest)) {
            list.add(posToTest);
            if (numberOfMoves == 0) {
                newRank = Rank.valueOf(pos.getRank().getIndex() + 2 * directionFactor);
                posToTest = new Square(newRank, newFile);
                if (board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                }
            }
        }

        //Schlagen
        for (int columnOffset : new int[]{-1, 1}) {
            try {
                newRank = Rank.valueOf(pos.getRank().getIndex() + directionFactor);
                newFile = File.valueOf(pos.getFile().getIndex() + columnOffset);
                posToTest = new Square(newRank, newFile);
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

    public void registerMove(int moveNumber) {
        numberOfMoves++;
        lastMoved = moveNumber;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getLastMoved() {
        return lastMoved;
    }
}
