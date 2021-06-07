package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.positioning.Square;

import java.util.List;

public class CheckDetector {

    ChessBoard board;
	
	private CheckDetector() {
		// Prevent initialization
	}

	public static boolean isSquareAttacked(ChessBoard board, Square squareToTest, boolean color) {
        for (Square opponentSquare : board.findSquaresOfPieces(color)) {
            ChessPiece opponentPiece = board.getPiece(opponentSquare);
            List<Square> opponentCoveredSquares = opponentPiece.findCoveredSquares(board, opponentSquare);
            if(opponentCoveredSquares.contains(squareToTest)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInCheck(ChessBoard board, boolean color) {
        Square kingSquare = board.findSquaresOfPieces(ChessPieceType.KING, color).get(0);
        return isSquareAttacked(board, kingSquare, !color);
    }

    public static boolean isInCheckAfterMove(ChessBoard board, boolean color, Square origin, Square destination) {
        ChessBoard testBoard = board.clone();
        testBoard.movePiece(origin, destination);
        return isInCheck(testBoard, color);
    }
}
