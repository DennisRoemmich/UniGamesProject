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
        List<Square> coveredSquared = new ArrayList<>();

    	Optional<Square> s = findSelf(game);

    	if (!s.isPresent()) {
        	return coveredSquared;
        }

        Square origin = s.get();


        Direction moveDirection = getColor().getPawnMoveDirection();

        //Einfacher Zug & Doppelzug
        addRegularMoves(game, coveredSquared, origin, moveDirection);

        //Schlagen
        addCaptureMoves(game, coveredSquared, origin, moveDirection);

        return coveredSquared;
    }

    private void addCaptureMoves(Chess game, List<Square> coveredSquared, Square origin, Direction moveDirection) {
        Optional<Square> squareToTest;
        for (Direction captureDirection : new Direction[]{Direction.LEFT, Direction.RIGHT}) {
            squareToTest = origin.getNext(moveDirection);
            squareToTest = getNextSquare(squareToTest, captureDirection);
            utilizeSquareToTest(game, coveredSquared, origin, squareToTest, captureDirection);
        }
    }

    private void utilizeSquareToTest(Chess game, List<Square> coveredSquared, 
    		Square origin, Optional<Square> squareToTest, Direction captureDirection) {
        if (squareToTest.isPresent()) {
            var piece = game.getBoard().getPiece(squareToTest.get());
            if (piece.isEmpty()) {
                // Capture En Passant
                addEnPassantMove(game, coveredSquared, origin, squareToTest.get(), captureDirection);
            } else if (piece.get().getColor().equals(getColor().getContrary())) {
                // Capture regular
                coveredSquared.add(squareToTest.get());
            }
        }
    }

    private void addEnPassantMove(Chess game, List<Square> coveredSquared, 
    		Square origin, Square destinationSquare, Direction captureDirection) {
        var enPassantVictimSquare = origin.getNext(captureDirection);
        if (enPassantVictimSquare.isPresent()) {
            var possiblePiece = game.getBoard().getPiece(enPassantVictimSquare.get());
            if (possiblePiece.isPresent() && possiblePiece.get().getType().equals(ChessPieceType.PAWN)) {
                Pawn pawn = (Pawn) possiblePiece.get();
                if (pawn.mCanBeCapturedEnPassant) {
                    coveredSquared.add(destinationSquare);
                }
            }
        }
    }

    private Optional<Square> getNextSquare(Optional<Square> squareToTest, Direction captureDirection) {
        if (squareToTest.isPresent()) {
            if (squareToTest.get().getNext(captureDirection).isEmpty()) {
                return Optional.empty();
            } else {
                squareToTest = squareToTest.get().getNext(captureDirection);
            }
        }
        return squareToTest;
    }

    private void addRegularMoves(Chess game, List<Square> coveredSquared, Square origin, Direction moveDirection) {
        var squareToTest = origin.getNext(moveDirection);

        if (squareToTest.isPresent() && game.getBoard().isFieldFree(squareToTest.get())) {
            coveredSquared.add(squareToTest.get());
            var nextSquare = squareToTest.get().getNext(moveDirection);
            if (isDoubleMovePossible() && nextSquare.isPresent() && game.getBoard().isFieldFree(nextSquare.get())) {
                coveredSquared.add(nextSquare.get());
            }
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return hashCode() == o.hashCode();
    }
}
