package core;

import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.positioning.Square;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Checks if the game is over.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public final class GameOverDetector {
	
	private GameOverDetector() {
		
	}

    public static ChessResult checkForMate(Color color, ChessBoard board) {
        List<ChessPiece> pieces = board.getPieces(color);
        if (isDraw(board)) {
            return ChessResult.DRAW;
        }

        // Check if current Player can move
        if(pieces.stream().anyMatch(piece -> !piece.findMoves(board).isEmpty())) {
            return ChessResult.NONE;
        }

        return CheckDetector.isInCheck(board, color) ? ChessResult.CHECKMATE : ChessResult.STALEMATE;
    }

    private static boolean isDraw(ChessBoard board) {
        return !(canPlayerWin(board, Color.WHITE) || canPlayerWin(board, Color.BLACK));
    }

    private static boolean canPlayerWin(ChessBoard board, Color color) {
        int availableMinorPieces = 0; //Bishop & Knight are minor pieces, at least two are required for a checkmate

        for (ChessPiece piece : board.getPieces(color)) {
            ChessPieceType type = piece.getType();
            switch (type) {
                case PAWN, ROOK, QUEEN -> { return true; }
                case BISHOP, KNIGHT -> {
                    if(++availableMinorPieces >= 2) {
                        return true;
                    }
                }
            }
        }
        return availableMinorPieces >= 2;
    }
}
