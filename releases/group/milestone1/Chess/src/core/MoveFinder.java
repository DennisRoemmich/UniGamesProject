package core;

import core.pieces.ChessPiece;
import core.pieces.King;
import core.pieces.Pawn;
import core.pieces.Rook;
import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class MoveFinder {
	
	private static boolean breakLoop =false;
	
	private MoveFinder() {
		
	}
	

    public static List<Position> findMoves(Position pos, ChessBoard board) {
        List<Position> validDestinations = new ArrayList<>();
        ChessPiece piece = board.getPiece(pos);

        for (Position destination : findMovesDisregaringChess(pos, board)) {
            if(!CheckDetector.isInCheckAfterMove(board, piece.isWhite(), pos, destination)) {
                validDestinations.add(destination);
            }
        }
        return validDestinations;
    }

    public static List<Position> findMovesDisregaringChess(Position pos, ChessBoard board) {

        ChessPiece piece = board.getPiece(pos);

        switch (piece.getType()) {
            case PAWN:
                return findPawnMoves(pos, board);
            case KNIGHT:
                return findKnightMoves(pos, board);
            case BISHOP:
                return findBishopMoves(pos, board);
            case ROOK:
                return findRookMoves(pos, board);
            case QUEEN:
                return findQueenMoves(pos, board);
            case KING:
                return findKingMoves(pos, board);
            default:
                return new ArrayList<>();
        }
    }

    private static List<Position> findKingMoves(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<>();
        List<Position> temp = new ArrayList<>();
        
        ChessPiece piece = board.getPiece(pos);
        Row newRow;
        Column newColumn;
        Position posToTest;
        //Rochade
        
        leftwardMove(pos, board, temp, piece);
        if(temp.size()==2 && !King.getHasMoved() && !Rook.getHasMoved() ) {
        	list.add(temp.get(1));
        }

        for (int rowOffset : new int[]{-1, 0, 1}) {
            for (int columnOffset : new int[]{-1, 0, 1}) {
                try {
                    newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                    newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                    posToTest = new Position(newRow, newColumn);
                    if (board.isOccupiedByOpponentOrFree(posToTest, piece.isWhite())) {
                        list.add(posToTest);
                    }
                } catch (Exception e) {

                }
            }
        }
        return list;
    }
    
    private static Position testPosition(Position pos, int rowOffset, int columnOffset) {
    	
    	Row newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
        Column newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
        return new Position(newRow, newColumn);     
        
    }
    
    private static void testMove(Position posToTest, ChessBoard board, List<Position> list, ChessPiece piece) {
    	if (board.isFieldFree(posToTest)) {
            list.add(posToTest);
        } else {
            if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                 list.add(posToTest);
            }
            breakLoop = true;
            
        }
        
    }
    
    private static void diagonalMoveD1(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
    	for (int rightDiagonal = 1; rightDiagonal < 8; rightDiagonal++) {
            int rowOffset = rightDiagonal;
            int columnOffset = rightDiagonal;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	
               
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {

            }
    	}
    }
    
    private static void diagonalMoveD2(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
        for (int rightDiagonal = -1; rightDiagonal > -8; rightDiagonal--) {
            int rowOffset = rightDiagonal;
            int columnOffset = rightDiagonal;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	
               
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {

            }
        }
    }
    
    private static void diagonalMoveD3(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
        for (int leftDiagonal = -1; leftDiagonal > -8; leftDiagonal--) {
            int rowOffset = leftDiagonal;
            int columnOffset = -leftDiagonal;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	
               
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {
            	//System.out.println("Exception caught");
            }
        }
    }
    
    private static void diagonalMoveD4(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
        for (int leftDiagonal = 1; leftDiagonal < 8; leftDiagonal++) {
            int rowOffset = leftDiagonal;
            int columnOffset = -leftDiagonal;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {

            }
        }
    }
    private static void forwardMove(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
        for (int rowOffset = 1; rowOffset < 8; rowOffset++) {
            int columnOffset = 0;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	              
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {

            }
        }
    }
    
    private static void backwardMove(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
        for (int rowOffset = -1; rowOffset > -8; rowOffset--) {
            int columnOffset = 0;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	
               
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {

            }
        }
    }
    
    private static void leftwardMove(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
        for (int columnOffset = 1; columnOffset < 8; columnOffset++) {
            int rowOffset = 0;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	
               
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {

            }
        }
    }
    
    private static void rightwardMove(Position pos, ChessBoard board, List<Position> list, ChessPiece piece) {
        for (int columnOffset = -1; columnOffset > -8; columnOffset--) {
            int rowOffset = 0;
            try {
            	Position posToTest = testPosition(pos, rowOffset, columnOffset);
            	testMove(posToTest, board, list, piece);
            	
               
                if(breakLoop) {
                	breakLoop = false;
                	break;
                }
            } catch (Exception e) {

            }
        }
    }
    
    
    private static List<Position> findQueenMoves(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        
        diagonalMoveD1(pos, board, list, piece);
        diagonalMoveD2(pos, board, list, piece);
        diagonalMoveD3(pos, board, list, piece);
        diagonalMoveD4(pos, board, list, piece);
        
        forwardMove(pos, board, list, piece);
        backwardMove(pos, board, list, piece);
        leftwardMove(pos, board, list, piece);
        rightwardMove(pos, board, list, piece);

        return list;
    }

    private static List<Position> findRookMoves(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        
        forwardMove(pos, board, list, piece);
        backwardMove(pos, board, list, piece);
        leftwardMove(pos, board, list, piece);
        rightwardMove(pos, board, list, piece);

        //Rochade
        /* TODO */
        // J: Kann auch nur als Königszug angesehen werden, da sonst Mehrdeutigkeiten entstehen (Tf1 vs kurze Rochade)

        return list;
    }

    private static List<Position> findBishopMoves(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        
        diagonalMoveD1(pos, board, list, piece);
        diagonalMoveD2(pos, board, list, piece);
        diagonalMoveD3(pos, board, list, piece);
        diagonalMoveD4(pos, board, list, piece);
      
        return list;
    }

    private static List<Position> findKnightMoves(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        Row newRow;
        Column newColumn;
        Position posToTest;
        for (int rowMirror : new int[]{1, -1}) {
            for (int columnMirror : new int[]{-1, 1}) {
                for (int diagonalMirror : new int[]{0, 1}) {
                    int rowOffset = (1 + diagonalMirror) * rowMirror;
                    int columnOffset = (2 - diagonalMirror) * columnMirror;
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if (board.isOccupiedByOpponentOrFree(posToTest, piece.isWhite())) {
                            list.add(posToTest);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        return list;
    }

    private static List<Position> findPawnMoves(Position pos, ChessBoard board) {
        List<Position> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(pos);
        Row newRow;
        Column newColumn = pos.getColumn();
        Position posToTest;

        int directionFactor = piece.isWhite() ? 1 : -1;

        //Einfacher Zug
        newRow = Row.valueOf(pos.getRow().getIndex() + 1 * directionFactor);
        posToTest = new Position(newRow, pos.getColumn());

        if (board.isFieldFree(posToTest)) {
            list.add(posToTest);
        }

        //Doppelzug
        try {
            Pawn pawn = (Pawn) piece;
            if (pawn.getNumberOfMoves() == 0) {
                newRow = Row.valueOf(pos.getRow().getIndex() + 2 * directionFactor);
                posToTest = new Position(newRow, newColumn);
                if (board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                }
            }
        } catch (Exception e){

        }

        //Schlagen
        for (int columnOffset : new int[]{-1, 1}) {
            try {
                newRow = Row.valueOf(pos.getRow().getIndex() + directionFactor);
                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Position(newRow, newColumn);
                if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                    list.add(posToTest);
                }
            } catch (Exception e) {

            }
        }
        //En-passant
        try {
        	
        }catch (Exception e){
        
        }
        //Verwandlung
        /* TODO */
        return list;
    }
}
