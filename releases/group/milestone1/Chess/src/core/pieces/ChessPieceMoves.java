package core.pieces;

import java.util.List;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

public class ChessPieceMoves {
	
	private static boolean breakLoop = false;
	
	private ChessPieceMoves() {
		// Prevent initialization
	}
	
    public static Square testSquare(Square square, int rankOffset, int fileOffset) {
    	
    	Rank newRank = Rank.valueOf(square.getRank().getIndex() + rankOffset);
        File newFile = File.valueOf(square.getFile().getIndex() + fileOffset);
        return new Square(newRank, newFile);
        
    }

    private static void testMove(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
    	if (board.isFieldFree(square)) {
    		list.add(square);
    	} else {
    		if (board.isOccupiedByOpponent(square, piece.isWhite())) {
             	list.add(square);
        	}
        	breakLoop = true;
    	}
    }

    protected static void diagonalMoveD1(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
    	for (int rightDiagonal = 1; rightDiagonal < 8; rightDiagonal++) {
    		int rankOffset = rightDiagonal;
    		int fileOffset = rightDiagonal;
    		try {
    			Square posToTest = testSquare(square, rankOffset, fileOffset);
    			testMove(posToTest, board, list, piece);

    			if(breakLoop) {
    				breakLoop = false;
    				break;
    			}
    		} catch (Exception e) {

    		}
		}
    }

    protected static void diagonalMoveD2(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
    	for (int rightDiagonal = -1; rightDiagonal > -8; rightDiagonal--) {
    		int rowOffset = rightDiagonal;
    		int fileOffset = rightDiagonal;
    		try {
    			Square posToTest = testSquare(square, rowOffset, fileOffset);
    			testMove(posToTest, board, list, piece);
    			if(breakLoop) {
    				breakLoop = false;
    				break;
    			}
    		} catch (Exception e) {

    		}
    	}
    }

	protected static void diagonalMoveD3(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int leftDiagonal = -1; leftDiagonal > -8; leftDiagonal--) {
	        int rankOffset = leftDiagonal;
	        int fileOffset = -leftDiagonal;
	        try {
	        	Square posToTest = testSquare(square, rankOffset, fileOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	           
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	        }
	    }
	}

	protected static void diagonalMoveD4(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int leftDiagonal = 1; leftDiagonal < 8; leftDiagonal++) {
	        int rankOffset = leftDiagonal;
	        int fileOffset = -leftDiagonal;
	        try {
	        	Square posToTest = testSquare(square, rankOffset, fileOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void forwardMove(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int rankOffset = 1; rankOffset < 8; rankOffset++) {
	        int fileOffset = 0;
	        try {
	        	Square posToTest = testSquare(square, rankOffset, fileOffset);
	        	testMove(posToTest, board, list, piece);
	        	              
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void backwardMove(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int rankOffset = -1; rankOffset > -8; rankOffset--) {
	        int fileOffset = 0;
	        try {
	        	Square posToTest = testSquare(square, rankOffset, fileOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	           
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void rightwardMove(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int fileOffset = 1; fileOffset < 8; fileOffset++) {
	        int rankOffset = 0;
	        try {
	        	Square posToTest = testSquare(square, rankOffset, fileOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	           
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void leftwardMove(Square square, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int fileOffset = -1; fileOffset > -8; fileOffset--) {
	        int rankOffset = 0;
	        try {
	        	Square posToTest = testSquare(square, rankOffset, fileOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	           
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}
   
}
