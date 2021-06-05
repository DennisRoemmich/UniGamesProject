package core;

import core.pieces.ChessPieceType;
import core.positioning.Square;

public class CheckDetector {
	
	private CheckDetector() {
		// Prevent initialization
	}

    public static boolean isInCheck(ChessBoard board, boolean color) {
        Square kingPos = board.findPositionsOfPieces(ChessPieceType.KING, color).get(0);
        for (Square opponentPos : board.findPositionsOfPieces(!color)) {
            if(board.getPiece(opponentPos).findMovesDisregardingCheck(opponentPos, board).contains(kingPos)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInCheckAfterMove(ChessBoard board, boolean color, Square origin, Square destination) {
        ChessBoard testBoard = board.clone();
        testBoard.movePiece(origin, destination);
        return isInCheck(testBoard, color);
    }
}
