package core;

import core.pieces.ChessPieceType;
<<<<<<< HEAD
import core.positioning.Square;
=======
import core.positioning.Position;

>>>>>>> remotes/origin/chess01j

public class CheckDetector {
	
	private CheckDetector() {
		
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
