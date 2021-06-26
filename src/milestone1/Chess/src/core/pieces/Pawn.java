package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.Square;
import framework.WriteError;
import java.util.ArrayList;
import java.util.List;

/**
 * Pawn piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Pawn extends ChessPiece {

    private boolean mCanBeCapturedEnPassant = false;

    public Pawn(boolean isWhite) {
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
            if (isDoubleMovePossible()) {
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
                if (pawn.mCanBeCapturedEnPassant) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {
            	WriteError.writeErrorLog("");
            }
        }

        return list;
    }

    protected boolean isDoubleMovePossible() {
        return getNumberOfMoves() == 0;
    }

    public void registerDoubleMove() {
        mCanBeCapturedEnPassant = true;
    }

    public void resetEnPassant() {
        mCanBeCapturedEnPassant = false;
    }
}
