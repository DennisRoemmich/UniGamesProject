package core;

import core.positioning.Square;

import java.util.List;

public class MateDetector {
    public static ChessResult checkForMate(boolean currentPlayerIsWhite, ChessBoard board) {
        List<Square> squaresOfPlayer= board.findPositionsOfPieces(currentPlayerIsWhite);
        for(Square square : squaresOfPlayer) {
            if(!board.getPiece(square).findMoves(square, board).isEmpty()) {
                return ChessResult.NONE;
            }
        }
        if (CheckDetector.isInCheck(board, currentPlayerIsWhite)) {
            return ChessResult.CHECKMATE;
        } else {
            return ChessResult.STALEMATE;
        }
    }
}
