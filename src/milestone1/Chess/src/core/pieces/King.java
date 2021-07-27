package core.pieces;

import core.*;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Square;
import framework.WriteError;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

/**
 * King piece on the chess board.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class King extends ChessPiece {

    public King(Color color) {
        super(color, ChessPieceType.KING);
    }

    @Override
    public List<Square> findCoveredSquares(Chess game) {
        List<Square> list = new ArrayList<>();
        Square origin = game.getBoard().getSquare(this).get();

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

        for (PositionedPiece positionedRook : game.getBoard().getPositionedPieces(getColor(), ChessPieceType.ROOK)) {

            Rook rook = (Rook) positionedRook.getPiece();

            if (rook.getNumberOfMoves() > 0) continue;

            Square kingSquare = new Square(getColor().getBackrank(), File.E);
            Direction kingMoveDirection = positionedRook.getPosition().getFile() == File.A ? Direction.LEFT : Direction.RIGHT;

            // Check if squares for king are not covered by the opponent
            List<Square> squaresToCheck = new ArrayList<>();
            squaresToCheck.add(kingSquare.getNext(kingMoveDirection).get());
            squaresToCheck.add(squaresToCheck.get(0).getNext(kingMoveDirection).get());

            for(Square square : squaresToCheck) {
                if(CheckDetector.isSquareAttacked(game, square)) {
                    continue;
                }
            }

            // Check if those squares (+ the B-Square if castling is on queen side) are free
            if (kingMoveDirection == Direction.LEFT) {
                squaresToCheck.add(new Square(getColor().getBackrank(), File.B));
            }
            for(Square square : squaresToCheck) {
                if(game.getBoard().getPiece(square).isPresent()) {
                    continue;
                }
            }

            // Add move to list
            Square destination = squaresToCheck.get(1);
            ChessMove move = new ChessMove(kingSquare, destination);
            castlingMoves.add(move);
        }
        return castlingMoves;
    }

}
