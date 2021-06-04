package core.pieces;

import java.util.ArrayList;
import java.util.List;

import core.ChessBoard;
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
	
    public Square testSquare(Square squareToTest, int rankOffset, int fileOffset) {
    	
    	Rank newRank = Rank.valueOf(squareToTest.getRank().getIndex() + rankOffset);
        File newFile = File.valueOf(squareToTest.getFile().getIndex() + fileOffset);
        return new Square(newRank, newFile);
        
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

    protected List<Square> diagonalMoveD1() {
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.topRightNeighbour();
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.topRightNeighbour();
		}
		return checkLine(lineSquares);
    }

    protected List<Square> diagonalMoveD2() {
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.topLeftNeighbour();
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.topLeftNeighbour();
		}
		return checkLine(lineSquares);
    }

	protected List<Square> diagonalMoveD3() {
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.bottomLeftNeighbour();
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.bottomLeftNeighbour();
		}
		return checkLine(lineSquares);
	}

	protected List<Square> diagonalMoveD4() {
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.bottomRightNeighbour();
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.bottomRightNeighbour();
		}
		return checkLine(lineSquares);
	}

	protected List<Square> forwardMove(){
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.topNeighbour();
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.topNeighbour();
		}
		return checkLine(lineSquares);
	}

	protected List<Square> backwardMove() {
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.bottomNeighbour();
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.bottomNeighbour();
		}
		return checkLine(lineSquares);
	}

	protected List<Square> rightwardMove() {
	    List<Square> lineSquares = new ArrayList<>();
	    Square squareToAdd = origin.rightNeighbour();
	    while (squareToAdd != null) {
	    	lineSquares.add(squareToAdd);
	    	squareToAdd = squareToAdd.rightNeighbour();
		}
		return checkLine(lineSquares);
	}

	protected List<Square> leftwardMove() {
		List<Square> lineSquares = new ArrayList<>();
		Square squareToAdd = origin.leftNeighbour();
		while (squareToAdd != null) {
			lineSquares.add(squareToAdd);
			squareToAdd = squareToAdd.leftNeighbour();
		}
		return checkLine(lineSquares);
	}

	protected List<Square> checkLine(List<Square> lineSquares) {
		List<Square> reachableFields = new ArrayList<>();
		for(Square square : lineSquares){
			if (isSquareReachable(square)) {
				reachableFields.add(square);
			}
			if(breakLoop) {
				breakLoop = false;
				break;
			}
		}
		return reachableFields;
	}
   
}
