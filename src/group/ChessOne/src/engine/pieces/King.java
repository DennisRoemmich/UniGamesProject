package engine.pieces;

import engine.*;
import engine.analysis.CheckDetector;
import engine.board.ChessMove;
import engine.squares.Direction;
import engine.squares.File;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        for (PositionedPiece positionedRook : game.getBoard().getPositionedPieces(getColor(), ChessPieceType.ROOK)) {

            Rook rook = (Rook) positionedRook.getPiece();

            if (rook.getNumberOfMoves() > 0) continue;

            Square kingSquare = new Square(getColor().getBackrank(), File.E);
            Direction kingMoveDirection = positionedRook.getPosition().getFile() == File.A ? Direction.LEFT : Direction.RIGHT;

            // Check if engine.squares for king are not covered by the opponent
            List<Square> squaresToCheck = new ArrayList<>();
            squaresToCheck.add(kingSquare.getNext(kingMoveDirection).get());
            squaresToCheck.add(squaresToCheck.get(0).getNext(kingMoveDirection).get());

            boolean stopFlag = false;
            for(Square square : squaresToCheck) {
                if(CheckDetector.isSquareAttackedByOpponent(game, square)) {
                    stopFlag = true;
                }
            }
            if(stopFlag) continue;


            // Check if those engine.squares (+ the B-Square if castling is on queen side) are free
            if (kingMoveDirection == Direction.LEFT) {
                squaresToCheck.add(new Square(getColor().getBackrank(), File.B));
            }
            for(Square square : squaresToCheck) {
                if(game.getBoard().getPiece(square).isPresent()) {
                    stopFlag = true;
                }
            }
            if(stopFlag) continue;

            // Add move to list
            Square destination = squaresToCheck.get(1);
            ChessMove move = new ChessMove(kingSquare, destination);
            castlingMoves.add(move);
        }
        return castlingMoves;
    }

}
