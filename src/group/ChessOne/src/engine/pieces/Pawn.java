package engine.pieces;

import engine.Chess;
import engine.squares.Direction;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Pawn piece on the chess board.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Pawn extends ChessPiece {

    private boolean mCanBeCapturedEnPassant = false;

    public Pawn(PlayerColor playerColor) {
        super(playerColor, ChessPieceType.PAWN);
    }

    @Override
    public List<Square> findCoveredSquares(Chess game) {
    	Optional<Square> s = game.getBoard().getSquare(this);
    	if (!s.isPresent()) {
        	return new ArrayList<>();
        }
        Square origin = s.get();

        List<Square> list = new ArrayList<>();

        Direction moveDirection = getColor().getPawnMoveDirection();

        //Einfacher Zug & Doppelzug
        Square squareToTest = origin.getNext(moveDirection).get();

        if (game.getBoard().isFieldFree(squareToTest)) {
            list.add(squareToTest);
            if (isDoubleMovePossible() && squareToTest.getNext(moveDirection).isPresent()) {
                squareToTest = squareToTest.getNext(moveDirection).get();
                if (game.getBoard().isFieldFree(squareToTest)) {
                    list.add(squareToTest);
                }
            }
        }

        //Schlagen
        for (Direction captureDirection : new Direction[]{Direction.LEFT, Direction.RIGHT}) {
            squareToTest = origin.getNext(moveDirection).get();
            if(squareToTest.getNext(captureDirection).isEmpty()) continue;
            squareToTest = squareToTest.getNext(captureDirection).get();

            var piece = game.getBoard().getPiece(squareToTest);
            if(piece.isEmpty()) {
                // Capture En Passant
                var possiblePiece = game.getBoard().getPiece(origin.getNext(captureDirection).get());
                if (possiblePiece.isPresent() && possiblePiece.get().getType().equals(ChessPieceType.PAWN)) {
                    Pawn pawn = (Pawn) possiblePiece.get();
                    if (pawn.mCanBeCapturedEnPassant) {
                        list.add(squareToTest);
                    }
                }
            } else if (piece.get().getColor().equals(getColor().getContrary())) {
                // Capture regular
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

    @Override
    public int hashCode() {
        return Objects.hash(ChessPieceType.KING, getColor().isWhite(), mCanBeCapturedEnPassant);
    }
}
