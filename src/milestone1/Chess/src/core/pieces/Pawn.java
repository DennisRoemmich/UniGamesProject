package core.pieces;

import core.ChessBoard;
import core.Color;
import core.positioning.Direction;
import core.positioning.Square;
import framework.WriteError;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawn piece on the chess board.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Pawn extends ChessPiece {

    private boolean mCanBeCapturedEnPassant = false;

    public Pawn(Color color) {
        super(color, ChessPieceType.PAWN);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();

        Direction moveDirection = getColor().isWhite() ? Direction.UP : Direction.DOWN;

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
            squareToTest = origin.getNext(moveDirection);
            squareToTest = squareToTest.getNext(captureDirection);
            var piece = board.getPiece(squareToTest);
            if (piece.isEmpty() || piece.get().getColor().equals(getColor())) {
                list.add(squareToTest);
                continue;
            }
            Square neighbourSquare = origin.getNext(captureDirection);
            var possiblePiece = board.getPiece(neighbourSquare);
            if (possiblePiece.isPresent()) {
                Pawn pawn = (Pawn) possiblePiece.get();
                if (pawn.mCanBeCapturedEnPassant) {
                    list.add(squareToTest);
                }
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
