package core.pieces;

import core.CheckDetector;
import core.ChessBoard;
import core.Color;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Square;
import framework.WriteError;
import core.positioning.Rank;
import java.util.ArrayList;
import java.util.List;

/**
 * King piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class King extends ChessPiece  {

    public King(Color color) {
        super(color, ChessPieceType.KING);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            try {
                Square squareToTest = origin.getNext(direction);
                var piece = board.getPiece(squareToTest);
                if (piece.isEmpty() || piece.get().getColor().equals(getColor().getContrary())) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {
            	WriteError.writeErrorLog("");
            }
        }
        return list;
    }

    @Override
    public List<Square> findMoves(ChessBoard board, Square square) {
        List<Square> moves = super.findMoves(board, square);
        moves.addAll(findCastlingMoves(board));
        return moves;
    }
    
    private List<Square> findCastlingMoves(ChessBoard board) {
        List<Square> castlingMoves = new ArrayList<>();
        if (getNumberOfMoves() != 0 || CheckDetector.isInCheck(board, getColor())) {
            return castlingMoves;
        }
        Rank backRank = getColor().isWhite() ? Rank.M1 : Rank.M8;
        var searchedRook = new Rook(getColor());
        for (Square rookSquare : board.getPositionedPieces().stream().filter(pP -> pP.getPiece().equals(searchedRook)).map(pP -> pP.getPosition()).toList()) {
            
        	Rook rook = (Rook) board.getPiece(rookSquare);
            
            if (rook.getNumberOfMoves() == 0) {
            	Square kingSquare = new Square(backRank, File.E);
            	Direction kingMoveDirection = rookSquare.getFile() == File.A ? Direction.LEFT : Direction.RIGHT;
            	List<Square> kingMovementSquares = new ArrayList<>();
            	
            	kingMovementSquares.add(kingSquare.getNext(kingMoveDirection));
            	kingMovementSquares.add(kingSquare.getNext(kingMoveDirection).getNext(kingMoveDirection));
            	
            	if (kingMoveDirection == Direction.LEFT && !board.isFieldFree(new Square(backRank, File.B))) {
            		continue;
            	}
            	castlingMoves.add(kingSquare.getNext(kingMoveDirection).getNext(kingMoveDirection));
            } 
        }
        return  castlingMoves;
    }

}
