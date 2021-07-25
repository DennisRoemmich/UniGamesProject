package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.pieces.King;
import core.positioning.Square;
import java.util.List;

/**
 * Checks for check condition.
 *  @author Jan de Boer, Dennis Roemmich
 */
public final class CheckDetector {

	private CheckDetector() {
		// Prevent initialization
	}

	public static boolean isSquareAttacked(ChessBoard board, Square squareToTest, Color color) {
        for (Square opponentSquare : board.findSquaresOfPieces(color)) {
            ChessPiece opponentPiece = board.getPiece(opponentSquare);
            List<Square> opponentCoveredSquares = opponentPiece.findCoveredSquares(board, opponentSquare);
            if (opponentCoveredSquares.contains(squareToTest)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInCheck(ChessBoard board, Color color) {
	    ChessPiece exampleKing = new King(color);
        Square kingSquare = board.getPositionedPieces().stream().filter(pP -> pP.getPiece().equals(exampleKing)).findFirst().get().getPosition();
        return isSquareAttacked(board, kingSquare, color.getContrary());
    }

    public static boolean isInCheckAfterMove(ChessBoard board, Color color, Square origin, Square destination) {
        ChessBoard testBoard = board.clone();
        testBoard.movePiece(origin, destination);
        return isInCheck(testBoard, color);
    }
}
