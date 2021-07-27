package core;

import core.pieces.ChessPieceType;
import core.positioning.Square;

/**
 * Checks for check condition.
 *  @author Jan de Boer, Dennis Roemmich
 */
public final class CheckDetector {

	private CheckDetector() {
		// Prevent initialization
	}

	public static boolean isSquareAttacked(ChessBoard board, Square squareToTest, Color color) {
	    var pieces = board.getPositionedPieces(color);
        for(PositionedPiece piece : pieces) {
            for(Square square : piece.getPiece().findCoveredSquares(board)) {
                if(square.equals(squareToTest)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInCheck(ChessBoard board, Color color) {
	    var king = board.getPositionedPieces(color, ChessPieceType.KING);
	    if(king.isEmpty()) return false;
        return isSquareAttacked(board, king.get(0).getPosition(), color.getContrary());
    }

    public static boolean isInCheckAfterMove(ChessBoard board, Color color, ChessMove move) {
        ChessBoard testBoard = new ChessBoard(board);
        testBoard.movePiece(move);
        return isInCheck(testBoard, color);
    }
}
