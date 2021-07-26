package core.pieces;

import core.ChessBoard;
import core.ChessMove;
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
    public List<Square> findCoveredSquares(ChessBoard board) {
        if(board.getSquare(this).isEmpty()) return new ArrayList<>();
        Square origin = board.getSquare(this).get();

        List<Square> list = new ArrayList<>();

        Direction moveDirection = getColor().getPawnMoveDirection();

        //Einfacher Zug & Doppelzug
        Square squareToTest = origin.getNext(moveDirection).get();

        if (board.isFieldFree(squareToTest)) {
            list.add(squareToTest);
            if (isDoubleMovePossible() && squareToTest.getNext(moveDirection).isPresent()) {
                squareToTest = squareToTest.getNext(moveDirection).get();
                if (board.isFieldFree(squareToTest)) {
                    list.add(squareToTest);
                }
            }
        }

        //Schlagen
        for (Direction captureDirection : new Direction[]{Direction.LEFT, Direction.RIGHT}) {
            squareToTest = origin.getNext(moveDirection).get();
            if(squareToTest.getNext(captureDirection).isEmpty()) continue;
            squareToTest = squareToTest.getNext(captureDirection).get();
            var piece = board.getPiece(squareToTest);
            if(piece.isEmpty()) {
                var possiblePiece = board.getPiece(origin.getNext(captureDirection).get());
                if (possiblePiece.isPresent() && possiblePiece.get().getType().equals(ChessPieceType.PAWN)) {
                    Pawn pawn = (Pawn) possiblePiece.get();
                    if (pawn.mCanBeCapturedEnPassant) {
                        list.add(squareToTest);
                    }
                }
            } else if (piece.get().getColor().equals(getColor().getContrary())) {
                list.add(squareToTest);
                continue;
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

    public void resetDoubleMove() {
        mCanBeCapturedEnPassant = false;
    }

}
