package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

public class CheckDetector {

    public static boolean isInCheck(ChessBoard board, boolean color) {
        Position kingPos = board.findPositionsOfPieces(ChessPieceType.KING, color).get(0);
        for (Position opponentPos : board.findPositionsOfPieces(!color)) {
            if(MoveFinder.findMovesDisregaringChess(opponentPos, board).contains(kingPos)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInCheckAfterMove(ChessBoard board, boolean color, Position origin, Position destination) {
        ChessBoard testBoard = board.clone();
        testBoard.movePiece(origin, destination, 0);
        return isInCheck(testBoard, color);
    }
}
