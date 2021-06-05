package core.pieces;

import java.util.ArrayList;
import java.util.List;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

public class ChessPieceMoves {
	
	private boolean breakLoop = false;
	private Square origin;
	private ChessPiece piece;
	private ChessBoard board;

	public ChessPieceMoves(ChessPiece piece, Square square, ChessBoard board) {
		this.origin = square;
		this.piece = piece;
		this.board = board;
	}

    private boolean isSquareReachable(Square squareToTest) {
    	if (board.isFieldFree(squareToTest)) {
    		return true;
    	} else {
			breakLoop = true;
    		if (board.isOccupiedByOpponent(squareToTest, piece.isWhite())) {
             	return true;
        	} else {
    			return false;
			}
    	}
    }

	public List<Square> getReachableSquares(Direction[] directions) {
		List<Square> reachableSquares = new ArrayList<>();
		for(Direction direction : directions) {
			reachableSquares.addAll(getReachableSquares(direction));
		}
		return reachableSquares;
	}

	public List<Square> getReachableSquares(Direction direction) {
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.getNext(direction);
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.getNext(direction);
		}
		return checkLine(lineSquares);
	}

	protected List<Square> checkLine(List<Square> lineSquares) {
		List<Square> reachableSquares = new ArrayList<>();
		for (Square square : lineSquares) {
			if (isSquareReachable(square)) {
				reachableSquares.add(square);
			}
			if (breakLoop) {
				breakLoop = false;
				break;
			}
		}
		return reachableSquares;
	}
}
