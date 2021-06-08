package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.Square;
import sample.PrintError;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    private int numberOfMoves = 0;
    private int lastMoved = 0;

    private boolean canBeCapturedEnPassant = false;

    public Pawn(boolean isWhite){
        super(isWhite, ChessPieceType.PAWN);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();

        Direction moveDirection = isWhite() ? Direction.UP : Direction.DOWN;

        //Einfacher Zug & Doppelzug
        Square squareToTest = origin.getNext(moveDirection);

        if (board.isFieldFree(squareToTest)) {
            list.add(squareToTest);
            if (numberOfMoves == 0) {
                squareToTest = squareToTest.getNext(moveDirection);
                if (board.isFieldFree(squareToTest)) {
                    list.add(squareToTest);
                }
            }
        }

        //Schlagen
        for (Direction captureDirection : new Direction[]{Direction.LEFT, Direction.RIGHT}) {
            try {
                squareToTest = origin.getNext(moveDirection);
                squareToTest = squareToTest.getNext(captureDirection);
                if (board.isOccupiedByOpponent(squareToTest, isWhite())) {
                    list.add(squareToTest);
                    continue;
                }
                Square neighbourSquare = origin.getNext(captureDirection);
                Pawn pawn = (Pawn) board.getPiece(neighbourSquare);
                if(pawn.canBeCapturedEnPassant) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {
            	PrintError.writeErrorLog("");
            }
        }

        return list;
    }

    public void registerMove(int moveNumber) {
        numberOfMoves++;
        lastMoved = moveNumber;
    }

    public void registerDoubleMove() {
        canBeCapturedEnPassant = true;
    }

    public void resetEnPassant() {
        canBeCapturedEnPassant = false;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getLastMoved() {
        return lastMoved;
    }
}
