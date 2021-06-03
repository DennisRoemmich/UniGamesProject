package core.pieces;

import java.util.List;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

public class ChessPieceMoves {
	
	private static boolean breakLoop = false;
	
	private ChessPieceMoves() {
		
	}
	
    public static Square testPosition(Square pos, int rowOffset, int columnOffset) {
    	
    	Rank newRow = Rank.valueOf(pos.getRank().getIndex() + rowOffset);
        File newColumn = File.valueOf(pos.getFile().getIndex() + columnOffset);
        return new Square(newRow, newColumn);     
        
    }


    private static void testMove(Square posToTest, ChessBoard board, List<Square> list, ChessPiece piece) {
    	if (board.isFieldFree(posToTest)) {
    		list.add(posToTest);
    	} else {
    	if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
             list.add(posToTest);
        }
        breakLoop = true;
    	}
    }

    protected static void diagonalMoveD1(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
    	for (int rightDiagonal = 1; rightDiagonal < 8; rightDiagonal++) {
    		int rowOffset = rightDiagonal;
    		int columnOffset = rightDiagonal;
    		try {
    			Square posToTest = testPosition(pos, rowOffset, columnOffset);
    			testMove(posToTest, board, list, piece);

    			if(breakLoop) {
    				breakLoop = false;
    				break;
    			}
    		} catch (Exception e) {

    		}
		}
    }

    protected static void diagonalMoveD2(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
    	for (int rightDiagonal = -1; rightDiagonal > -8; rightDiagonal--) {
    		int rowOffset = rightDiagonal;
    		int columnOffset = rightDiagonal;
    		try {
    			Square posToTest = testPosition(pos, rowOffset, columnOffset);
    			testMove(posToTest, board, list, piece);
        	
           
    			if(breakLoop) {
    				breakLoop = false;
    				break;
    			}
    		} catch (Exception e) {

    		}
    	}
    }

	protected static void diagonalMoveD3(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int leftDiagonal = -1; leftDiagonal > -8; leftDiagonal--) {
	        int rowOffset = leftDiagonal;
	        int columnOffset = -leftDiagonal;
	        try {
	        	Square posToTest = testPosition(pos, rowOffset, columnOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	           
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	        }
	    }
	}

	protected static void diagonalMoveD4(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int leftDiagonal = 1; leftDiagonal < 8; leftDiagonal++) {
	        int rowOffset = leftDiagonal;
	        int columnOffset = -leftDiagonal;
	        try {
	        	Square posToTest = testPosition(pos, rowOffset, columnOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void forwardMove(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int rowOffset = 1; rowOffset < 8; rowOffset++) {
	        int columnOffset = 0;
	        try {
	        	Square posToTest = testPosition(pos, rowOffset, columnOffset);
	        	testMove(posToTest, board, list, piece);
	        	              
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void backwardMove(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int rowOffset = -1; rowOffset > -8; rowOffset--) {
	        int columnOffset = 0;
	        try {
	        	Square posToTest = testPosition(pos, rowOffset, columnOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	           
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void rightwardMove(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int columnOffset = 1; columnOffset < 8; columnOffset++) {
	        int rowOffset = 0;
	        try {
	        	Square posToTest = testPosition(pos, rowOffset, columnOffset);
	        	testMove(posToTest, board, list, piece);
	        	
	           
	            if(breakLoop) {
	            	breakLoop = false;
	            	break;
	            }
	        } catch (Exception e) {
	
	        }
	    }
	}

	protected static void leftwardMove(Square pos, ChessBoard board, List<Square> list, ChessPiece piece) {
	    for (int columnOffset = -1; columnOffset > -8; columnOffset--) {
	        int rowOffset = 0;
	        try {
	        	Square posToTest = testPosition(pos, rowOffset, columnOffset);
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
