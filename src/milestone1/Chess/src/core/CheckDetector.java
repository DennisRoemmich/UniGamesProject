package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.pieces.King;
import core.positioning.Square;
import framework.PrintToConsole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Checks for check condition.
 *  @author Jan de Boer, Dennis Roemmich
 */
public final class CheckDetector {

	private CheckDetector() {
		// Prevent initialization
	}

	public static boolean isSquareAttacked(ChessBoard board, Square squareToTest, Color color) {
	    var piecesWithColor = board.getPositionedPieces(color);
        for (Square opponentSquare : piecesWithColor.stream().map(pP -> pP.getPosition()).collect(Collectors.toList())) {
            var opponentPiece = board.getPiece(opponentSquare).get();
            List<Square> opponentCoveredSquares = opponentPiece.findCoveredSquares(board);
            if (opponentCoveredSquares.contains(squareToTest)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInCheck(ChessBoard board, Color color) {
	    var king = board.getPositionedPieces(color, ChessPieceType.KING);
	    if(king.isEmpty()) {
	        return false;
        }
        return isSquareAttacked(board, king.get(0).getPosition(), color.getContrary());
    }

    public static boolean isInCheckAfterMove(ChessBoard board, Color color, ChessMove move) {
        ChessBoard testBoard = new ChessBoard(board);
        testBoard.movePiece(move);
        return isInCheck(testBoard, color);
    }
}
