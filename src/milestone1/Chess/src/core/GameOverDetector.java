package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.positioning.Square;
import java.util.List;

/**
 * Checks if the game is over.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public final class GameOverDetector {
	
	private GameOverDetector() {
		
	}

    public static ChessResult checkForMate(Chess game) {

        if (isDraw(game.getBoard())) {
            return ChessResult.DRAW;
        }

        // Check if current Player can move
        if (game.getPossibleMoves().isEmpty()) {
            return CheckDetector.isInCheck(game.getBoard(), game.getCurrentColor()) ? ChessResult.CHECKMATE : ChessResult.STALEMATE;
        }

        // Check if for 50 moves no Pawn moved and no piece was captured
        if (game.getCurrentMove() - game.getLastPawnMoveOrCapture() >= 50) {
            return ChessResult.DRAW;
        }

        return ChessResult.NONE;
    }

    private static boolean isDraw(ChessBoard board) {
        return !(canPlayerWin(board, Color.WHITE) || canPlayerWin(board, Color.BLACK));
    }

    private static boolean canPlayerWin(ChessBoard board, Color color) {
        int availableMinorPieces = 0; //Bishop & Knight are minor pieces, at least two are required for a checkmate

        for (ChessPiece piece : board.getPieces(color)) {
            switch (piece.getType()) {
                case PAWN, ROOK, QUEEN -> { return true; }
                case BISHOP, KNIGHT -> {
                    if(++availableMinorPieces >= 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
