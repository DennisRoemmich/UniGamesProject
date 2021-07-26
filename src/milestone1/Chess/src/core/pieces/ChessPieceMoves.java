package core.pieces;

import java.util.ArrayList;
import java.util.List;
import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.Square;

/**
 * Game moves for the pices Queen, Bishop and rook.
 * @author Jan de Boer, Dennis Roemmich
 * 
 */
public class ChessPieceMoves {
	
	private boolean mBreakLoop = false;
	private final Square mOrigin;
	private final ChessPiece mPiece;
	private final ChessBoard mBoard;

	public ChessPieceMoves(ChessPiece piece, Square square, ChessBoard board) {
		this.mOrigin = square;
		this.mPiece = piece;
		this.mBoard = board;
	}

    private boolean isSquareReachable(Square squareToTest) {
   	
    	if (mBoard.isFieldFree(squareToTest)) {
    		return true;
    	} else {
			mBreakLoop = true;
    		return !mBoard.getPiece(squareToTest).get().getColor().equals(mPiece.getColor());
    	}
    }

	public List<Square> getReachableSquares(Direction[] directions) {
		List<Square> reachableSquares = new ArrayList<>();
		for (Direction direction : directions) {
			reachableSquares.addAll(getReachableSquares(direction));
		}
		return reachableSquares;
	}

	public List<Square> getReachableSquares(Direction direction) {
		List<Square> lineSquares = new ArrayList<>();
		var squareToAdd = mOrigin.getNext(direction);
		while (squareToAdd.isPresent()) {
			lineSquares.add(squareToAdd.get());
			squareToAdd = squareToAdd.get().getNext(direction);
		}
		return checkLine(lineSquares);
	}

	protected List<Square> checkLine(List<Square> lineSquares) {
		List<Square> reachableSquares = new ArrayList<>();
		for (Square square : lineSquares) {
			if (isSquareReachable(square)) {
				reachableSquares.add(square);
			}
			if (mBreakLoop) {
				mBreakLoop = false;
				break;
			}
		}
		return reachableSquares;
	}
}
