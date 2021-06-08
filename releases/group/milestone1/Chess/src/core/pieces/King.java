package core.pieces;

import core.CheckDetector;
import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Square;
import sample.PrintError;
import core.positioning.Rank;
import java.util.ArrayList;
import java.util.List;

public class King extends CastlingChessPiece  {

    public King(boolean isWhite) {
        super(isWhite, ChessPieceType.KING);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            try {
                Square squareToTest = origin.getNext(direction);
                if (board.isOccupiedByOpponentOrFree(squareToTest, isWhite())) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {
            	PrintError.writeErrorLog("");
            }
        }
        return list;
    }

    @Override
    public List<Square> findMoves(Square square, ChessBoard board) {
        List<Square> moves = super.findMoves(square, board);
        moves.addAll(findCastlingMoves(board));
        return moves;
    }
    
    //TODO: Reduce Complexity 17 -> 15 and the for-Loop should include only one continue/break statement.
    private List<Square> findCastlingMoves(ChessBoard board) {
        List<Square> castlingMoves = new ArrayList<>();
        if (this.hasMoved() || CheckDetector.isInCheck(board, isWhite())) {
            return castlingMoves;
        }
        Rank backRank = isWhite() ? Rank.M1 : Rank.M8;
        for (Square rookSquare : board.findSquaresOfPieces(ChessPieceType.ROOK, isWhite())) {
            Rook rook = (Rook) board.getPiece(rookSquare);
            if (rook.hasMoved()) {
                continue;
            }
            Square kingSquare = new Square(backRank, File.E);
            Direction kingMoveDirection = rookSquare.getFile() == File.A ? Direction.LEFT : Direction.RIGHT;
            List<Square> kingMovementSquares = new ArrayList<>();
            kingMovementSquares.add(kingSquare.getNext(kingMoveDirection));
            kingMovementSquares.add(kingSquare.getNext(kingMoveDirection).getNext(kingMoveDirection));
            for (Square square : kingMovementSquares) {
                if (!board.isFieldFree(square) || CheckDetector.isSquareAttacked(board, square, !isWhite())) {
                    continue;
                }
            }
            if (kingMoveDirection == Direction.LEFT && !board.isFieldFree(new Square(backRank, File.B))) {
                continue;
            }
            castlingMoves.add(kingSquare.getNext(kingMoveDirection).getNext(kingMoveDirection));
        }
        return  castlingMoves;
    }

}
