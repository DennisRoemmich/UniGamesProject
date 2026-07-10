package engine.pieces;

import engine.*;
import engine.analysis.CheckDetector;
import engine.board.ChessMove;
import engine.squares.Direction;
import engine.squares.File;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * King piece on the chess board.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class King extends ChessPiece {

    public King(PlayerColor playerColor) {
        super(playerColor, ChessPieceType.KING);
    }

    @Override
    public List<Square> findCoveredSquares(Chess game) {
        List<Square> list = new ArrayList<>();
    	Optional<Square> s = game.getBoard().getSquare(this);
    	if (!s.isPresent()) {
        	return new ArrayList<>();
        }
        Square origin = s.get();

        for (Direction direction : Direction.values()) {
            var squareToTest = origin.getNext(direction);
            if (squareToTest.isPresent()) {
                var piece = game.getBoard().getPiece(squareToTest.get());
                if (piece.isEmpty() || piece.get().getColor().equals(getColor().getContrary())) {
                    list.add(squareToTest.get());
                }
            }
        }
        return list;
    }

    @Override
    public List<ChessMove> findMoves(Chess game) {
        List<ChessMove> moves = super.findMoves(game);
        moves.addAll(findCastlingMoves(game));
        return moves;
    }

    public List<ChessMove> findCastlingMoves(Chess game) {
        List<ChessMove> castlingMoves = new ArrayList<>();

        // Check if King can castle
        if (getNumberOfMoves() != 0 || CheckDetector.isInCheck(game)) {
            return castlingMoves;
        }

        Square kingSquare = new Square(getColor().getBackrank(), File.E);

        for (PositionedPiece positionedRook : game.getBoard().getPositionedPieces(getColor(), ChessPieceType.ROOK)) {
            if (checkCastlingWithRook(game, positionedRook)) {
                Direction castlingDirection = positionedRook.getPosition()
                		.getFile().equals(File.A) ? Direction.LEFT : Direction.RIGHT;
                Square destination = new Square(kingSquare);
                for (int i = 0; i < 2; i++) {
                    var nextStep = destination.getNext(castlingDirection);
                    if (nextStep.isPresent()) {
                        destination = nextStep.get();
                    }
                }
                ChessMove move = new ChessMove(kingSquare, destination);
                castlingMoves.add(move);
            }

        }
        return castlingMoves;
    }

    private boolean checkCastlingWithRook(Chess game, PositionedPiece positionedRook) {
        Rook rook = (Rook) positionedRook.getPiece();

        if (rook.getNumberOfMoves() > 0) {
        	return false;
        }

        Square kingSquare = new Square(getColor().getBackrank(), File.E);
        Direction castlingDirection = positionedRook
        		.getPosition().getFile() == File.A ? Direction.LEFT : Direction.RIGHT;

        // Check if squares for king are not covered by the opponent
        List<Square> squaresToCheck = new ArrayList<>();
        var nextSquare = kingSquare.getNext(castlingDirection);
        if (nextSquare.isPresent()) {
            squaresToCheck.add(nextSquare.get());
            nextSquare = nextSquare.get().getNext(castlingDirection);
        }
        if (nextSquare.isPresent()) {
            squaresToCheck.add(nextSquare.get());
        }

        for (Square square : squaresToCheck) {
            if (CheckDetector.isSquareAttackedByOpponent(game, square)) {
                return false;
            }
        }

        // Check if those squares (+ the B-Square if castling is on queen side) are free
        if (castlingDirection == Direction.LEFT) {
            squaresToCheck.add(new Square(getColor().getBackrank(), File.B));
        }
        for (Square square : squaresToCheck) {
            if (game.getBoard().getPiece(square).isPresent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ChessPieceType.KING, getColor().isWhite(), getNumberOfMoves() == 0);
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
