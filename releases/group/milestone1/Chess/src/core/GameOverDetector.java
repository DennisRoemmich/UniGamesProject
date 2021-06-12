package core;

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

    public static ChessResult checkForMate(boolean currentPlayerIsWhite, ChessBoard board) {
        List<Square> squaresOfPlayer = board.findSquaresOfPieces(currentPlayerIsWhite);
        if (isDraw(board)) {
            return ChessResult.DRAW;
        }

        // Check if current Player can move
        for (Square square : squaresOfPlayer) {
            if (!board.getPiece(square).findMoves(square, board).isEmpty()) {
                return ChessResult.NONE;
            }
        }
        if (CheckDetector.isInCheck(board, currentPlayerIsWhite)) {
            return ChessResult.CHECKMATE;
        } else {
            return ChessResult.STALEMATE;
        }
    }

    private static boolean isDraw(ChessBoard board) {
        return !(canPlayerWin(board, true) || canPlayerWin(board, false)); 
    }

    private static boolean canPlayerWin(ChessBoard board, boolean isWhite) {
        int availableMinorPieces = 0; //Bishop & Knight are minor pieces, at least two are required for a checkmate
        for (Square square : board.findSquaresOfPieces(isWhite)) {
            ChessPieceType type = board.getPiece(square).getType();
            switch (type) {
                case PAWN, ROOK, QUEEN:
                    return true;
                case BISHOP, KNIGHT:
                    availableMinorPieces++;
                	break;
                default:
                	return true;
            }
        }
        return availableMinorPieces >= 2;
    }
}
