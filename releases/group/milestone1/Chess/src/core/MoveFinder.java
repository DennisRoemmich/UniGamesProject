package core;

import java.util.ArrayList;
import java.util.List;

public class MoveFinder {
	

	
	//needs currentMove fuer en-passant
    public static List<Position> findMoves(Position pos, ChessBoard board) {
       
    	ChessPiece piece = board.getPiece(pos);
        List<Position> list = new ArrayList<>();

        Row newRow;
        Column newColumn = pos.getColumn();
        Position posToTest;

        switch (piece.getType()) {
            case PAWN:
                int directionFactor = piece.isWhite() ? 1 : -1;
                //Einfacher Zug
                newRow = Row.valueOf(pos.getRow().getIndex() + 1 * directionFactor);
                posToTest = new Position(newRow, pos.getColumn());
               
                if(board.isFieldFree(posToTest)) {
                    list.add(posToTest);
                }

                //Doppelzug
                if(piece.getLastMove() == 0) {
                    newRow = Row.valueOf(pos.getRow().getIndex() + 2 * directionFactor);
                    posToTest = new Position(newRow, newColumn);
                    if(board.isFieldFree(posToTest)) {
                        list.add(posToTest);
                    }
                }

                //Schlagen
                for(int columnOffset : new int[]{-1, 1}) {
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
                /* TODO */
                //Verwandlung
                /* TODO */
                return list;
            case KNIGHT:
                for(int rowMirror : new int[]{1, -1}){
                    for(int columnMirror: new int[]{-1,1}){
                        for(int diagonalMirror: new int[]{0,1}) {
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
            case BISHOP:          	
            	for(int rightDiagonal = 1; rightDiagonal <8; rightDiagonal++){
                       
                            
            				int rowOffset = rightDiagonal;
                            int columnOffset = rightDiagonal;
                            try {
                                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                                posToTest = new Position(newRow, newColumn);
                                if(board.isFieldFree(posToTest)) {
                                	list.add(posToTest);
                                } else {
                                	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                         list.add(posToTest);
                                	 }
                                	break;
                                }
                            } catch (Exception e) {

                            }
                            	
           	 }
            	
            	for(int rightDiagonal = -1; rightDiagonal >-8; rightDiagonal--){
                    
                    
    				int rowOffset = rightDiagonal;
                    int columnOffset = rightDiagonal;
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if(board.isFieldFree(posToTest)) {
                        	list.add(posToTest);
                        } else {
                        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                 list.add(posToTest);
                        	 }
                        	break;
                        }
                    } catch (Exception e) {

                    }
                    	
   	 }
            	for(int leftDiagonal = 1; leftDiagonal <8; leftDiagonal++){
                    
                         
                              
              				int rowOffset = leftDiagonal;
                              int columnOffset = - leftDiagonal;
                              try {
                                  newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                                  newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                                  posToTest = new Position(newRow, newColumn);
                                  if(board.isFieldFree(posToTest)) {
                                  	list.add(posToTest);
                                  } else {
                                  	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                           list.add(posToTest);
                                  	 }
                                  	break;
                                  }
                              } catch (Exception e) {
 
                              }
            	}
            	for(int leftDiagonal = -1; leftDiagonal > -8; leftDiagonal--){
                    
                    
                    
      				int rowOffset =  leftDiagonal;
                      int columnOffset = - leftDiagonal;
                      try {
                          newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                          newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                          posToTest = new Position(newRow, newColumn);
                          if(board.isFieldFree(posToTest)) {
                          	list.add(posToTest);
                          } else {
                          	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                   list.add(posToTest);
                          	 }
                          	break;
                          }
                      } catch (Exception e) {

                      }
    	}
            	
                return list;          
            case ROOK:
            	
                //Rochade
                /* TODO */
            	for(int rowMirror = 1; rowMirror <8; rowMirror++){
             
                       
                            int rowOffset = rowMirror;
                            int columnOffset = 0;
                            try {
                                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                                posToTest = new Position(newRow, newColumn);
                                if(board.isFieldFree(posToTest)) {
                                	list.add(posToTest);
                                } else {
                                	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                         list.add(posToTest);
                                	 }
                                	break;
                                }
                            } catch (Exception e) {

                            }
           		
                        
                    
           	 }
            	for(int rowMirror = -1; rowMirror >-8; rowMirror--){
                    
                    
                    int rowOffset = rowMirror;
                    int columnOffset = 0;
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if(board.isFieldFree(posToTest)) {
                        	list.add(posToTest);
                        } else {
                        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                 list.add(posToTest);
                        	 }
                        	break;
                        }
                    } catch (Exception e) {

                    }
   		
                
            
   	 }
            	for(int columnMirror = 1; columnMirror <8; columnMirror++){
                    
                    
                    int rowOffset = 0;
                    int columnOffset = columnMirror;
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if(board.isFieldFree(posToTest)) {
                        	list.add(posToTest);
                        } else {
                        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                 list.add(posToTest);
                        	 }
                        	break;
                        }
                    } catch (Exception e) {

                    }
   		
                
            
   	 }
            	for(int columnMirror = -1; columnMirror > -8; columnMirror--){
                    
                    
                    int rowOffset = 0;
                    int columnOffset = columnMirror;
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if(board.isFieldFree(posToTest)) {
                        	list.add(posToTest);
                        } else {
                        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                 list.add(posToTest);
                        	 }
                        	break;
                        }
                    } catch (Exception e) {

                    }
   		
                
            
   	 }
                return list;
            case QUEEN:
            	for(int rightDiagonal = 1; rightDiagonal <8; rightDiagonal++){                                       
    				int rowOffset = rightDiagonal;
                    int columnOffset = rightDiagonal;
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if(board.isFieldFree(posToTest)) {
                        	list.add(posToTest);
                        } else {
                        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                 list.add(posToTest);
                        	 }
                        	break;
                        }
                    } catch (Exception e) {

                    }
                    	
   	 }
            	for(int rightDiagonal = -1; rightDiagonal >-8; rightDiagonal--){                                       
    				int rowOffset = rightDiagonal;
                    int columnOffset = rightDiagonal;
                    try {
                        newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                        posToTest = new Position(newRow, newColumn);
                        if(board.isFieldFree(posToTest)) {
                        	list.add(posToTest);
                        } else {
                        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                 list.add(posToTest);
                        	 }
                        	break;
                        }
                    } catch (Exception e) {

                    }
                    	
   	 }
    	for(int leftDiagonal = 1; leftDiagonal <8; leftDiagonal++){          
      				int rowOffset = leftDiagonal;
                      int columnOffset = - leftDiagonal;
                      try {
                          newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                          newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                          posToTest = new Position(newRow, newColumn);
                          if(board.isFieldFree(posToTest)) {
                          	list.add(posToTest);
                          } else {
                          	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                                   list.add(posToTest);
                          	 }
                          	break;
                          }
                      } catch (Exception e) {

                      }
    	}
    	
    	for(int leftDiagonal = -1; leftDiagonal >-8; leftDiagonal--){          
				int rowOffset = leftDiagonal;
              int columnOffset = - leftDiagonal;
              try {
                  newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                  newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                  posToTest = new Position(newRow, newColumn);
                  if(board.isFieldFree(posToTest)) {
                  	list.add(posToTest);
                  } else {
                  	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                           list.add(posToTest);
                  	 }
                  	break;
                  }
              } catch (Exception e) {

              }
}
    	for(int rowMirror = 1; rowMirror <8; rowMirror++){                        
            int rowOffset = rowMirror;
            int columnOffset = 0;
            try {
                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Position(newRow, newColumn);
                if(board.isFieldFree(posToTest)) {
                	list.add(posToTest);
                } else {
                	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                         list.add(posToTest);
                	 }
                	break;
                }
            } catch (Exception e) {

            }	        
    	}
    	for(int rowMirror = -1; rowMirror >-8; rowMirror--){                        
            int rowOffset = rowMirror;
            int columnOffset = 0;
            try {
                newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
                newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
                posToTest = new Position(newRow, newColumn);
                if(board.isFieldFree(posToTest)) {
                	list.add(posToTest);
                } else {
                	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                         list.add(posToTest);
                	 }
                	break;
                }
            } catch (Exception e) {

            }	        
    	}
    	for(int columnMirror = 1; columnMirror <8; columnMirror++){    
    		int rowOffset = 0;
    		int columnOffset = columnMirror;
    		try {
    			newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
        posToTest = new Position(newRow, newColumn);
        if(board.isFieldFree(posToTest)) {
        	list.add(posToTest);
        } else {
        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                 list.add(posToTest);
        	 }
        	break;
        }
    		} catch (Exception e) {

    		}

}
    	for(int columnMirror = -1; columnMirror>-8; columnMirror--){    
    		int rowOffset = 0;
    		int columnOffset = columnMirror;
    		try {
    			newRow = Row.valueOf(pos.getRow().getIndex() + rowOffset);
        newColumn = Column.valueOf(pos.getColumn().getIndex() + columnOffset);
        posToTest = new Position(newRow, newColumn);
        if(board.isFieldFree(posToTest)) {
        	list.add(posToTest);
        } else {
        	 if (board.isOccupiedByOpponent(posToTest, piece.isWhite())) {
                 list.add(posToTest);
        	 }
        	break;
        }
    		} catch (Exception e) {

    		}

}
                return list;
            case KING:
                //Rochade
                /* TODO */
                        for(int rowMirror: new int[]{-1, 0 ,1}) {
                        	for(int columnMirror: new int[]{-1, 0 ,1}) {
                            int rowOffset = rowMirror;
                            int columnOffset = columnMirror;
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
            default:
                return list;
        }
    }
    }
