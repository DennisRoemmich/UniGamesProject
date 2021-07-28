package engine.analysis;

import engine.Chess;
import engine.board.ChessMove;
import engine.pieces.ChessPieceType;
import engine.pieces.PositionedPiece;
import engine.squares.Square;

/**
 * Checks for check condition.
 *  @author Jan de Boer, Dennis Roemmich
 */
public final class CheckDetector {

	private CheckDetector() {
		// Prevent initialization
	}

    public static boolean isSquareAttackedByOpponent(Chess game, Square squareToTest) {
        var pieces = game.getBoard().getPositionedPieces(game.getCurrentColor().getContrary());
        for(PositionedPiece piece : pieces) {
            for(Square square : piece.getPiece().findCoveredSquares(game)) {
                if(square.equals(squareToTest)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isSquareAttacked(Chess game, Square squareToTest) {
        var pieces = game.getBoard().getPositionedPieces(game.getCurrentColor());
        for(PositionedPiece piece : pieces) {
            for(Square square : piece.getPiece().findCoveredSquares(game)) {
                if(square.equals(squareToTest)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInCheck(Chess game) {
        var king = game.getBoard().getPositionedPieces(game.getCurrentColor(), ChessPieceType.KING);
        if(king.isEmpty()) return false;
        return isSquareAttackedByOpponent(game, king.get(0).getPosition());
    }

    public static boolean isInCheckAfterMove(Chess game, ChessMove move) {
        Chess testGame = new Chess(game);
        testGame.makeMoveForCheckChecking(move);
        return isInCheck(testGame);
    }
}
