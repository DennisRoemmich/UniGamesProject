package core.pieces;

import core.ChessBoard;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    private int numberOfMoves = 0;
    private int lastMoved = 0;

    public Pawn(boolean isWhite){
        super(isWhite, ChessPieceType.PAWN);
    }

    @Override
    public List<Position> findMovesDisregardingCheck(Position pos, ChessBoard board) {
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
