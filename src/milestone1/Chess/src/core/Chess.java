package core;

import core.pieces.*;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import console.ConsoleUI;
import framework.WriteError;
import java.util.ArrayList;
import java.util.List;

/**
 * 	Main Chess class where the main chess game logic is being executed.
 *  @author Jan de Boer, Dennis Roemmich
 */
public class Chess {

    protected ChessBoard mBoard;
    protected int mCurrentMove;
    protected boolean mIsItWhitesTurn;
    protected char standardPromotionPiece = 'Q';
    private boolean autoPromotion = true;
    
    public Chess() {
    	mBoard = ChessBoard.getStartBoard();
    	mCurrentMove = 1;
    	mIsItWhitesTurn = true;
    }

    public boolean makeMove(ChessMove move) {
        return makeMove(move.getOrigin(), move.getDestination());
    }

    public boolean makeMove(Square origin, Square destination) {
        ChessPieceType pieceType = mBoard.getPiece(origin).getType();
        if (getPossibleOrigins(destination, pieceType).contains(origin)) {
            checkForCastling(origin, destination);
            handleEnPassantCapture(origin, destination);
            mBoard.movePiece(origin, destination);
            checkForPromotion(destination, standardPromotionPiece);
            registerMove(destination);
            resetEnPassant();
            checkForPawnDoubleMove(origin, destination);
            incrementMove();
            return true;
        } else {
            return false;
        }
    }

    protected void handleEnPassantCapture(Square origin, Square destination) {
        if (mBoard.getPiece(origin).getType() == ChessPieceType.PAWN && origin.getFile() != destination.getFile()
        	&& mBoard.getPiece(destination) == null) {
                    
                    Direction direction = mBoard.getPiece(origin).isWhite() ? Direction.DOWN : Direction.UP;
                    Square squareToRemove = destination.getNext(direction);
                    mBoard.removePiece(squareToRemove);
                }
            }
      
    protected void resetEnPassant() {
        for (ChessPiece piece : mBoard.findPieces(ChessPieceType.PAWN)) {
            Pawn pawn = (Pawn) piece;
            pawn.resetEnPassant();
        }
    }

    protected void registerMove(Square square) {
        ChessPiece piece = mBoard.getPiece(square);
        piece.registerMove();
    }

    protected void checkForPawnDoubleMove(Square origin, Square destination) {
        ChessPiece piece = mBoard.getPiece(destination);
        if (piece.getType() == ChessPieceType.PAWN) {
            Direction moveDirection = mIsItWhitesTurn ? Direction.UP : Direction.DOWN;
            try {
                Square doubleMoveDestination = origin.getNext(moveDirection).getNext(moveDirection);
                if (doubleMoveDestination.equals(destination)) {
                    Pawn pawn = (Pawn) piece;
                    pawn.registerDoubleMove();
                }
            } catch (Exception e) {
            	WriteError.writeErrorLog("");
            }
        }
    }

    protected void checkForCastling(Square origin, Square destination) {
        Rank backRank = mIsItWhitesTurn ? Rank.M1 : Rank.M8;
        King king = (King) mBoard.findPieces(ChessPieceType.KING, mIsItWhitesTurn).get(0);
        if (king.getNumberOfMoves() != 0) {
            return;
        }
        Square kingSquare = mBoard.getSquare(king);
        if (!kingSquare.equals(origin)) {
            // So far, castling is always triggered by the king. So only the square of the king is interesting
            return;
        }
        if (destination.getRank() != backRank) {
            return;
        }

        Square extraMoveOrigin;
        Square extraMoveDestination;
        switch (destination.getFile()) {
            case C:
                extraMoveOrigin = new Square(kingSquare.getRank(), File.A);
                extraMoveDestination = new Square(kingSquare.getRank(), File.D);
                break;
            case G:
                extraMoveOrigin = new Square(kingSquare.getRank(), File.H);
                extraMoveDestination = new Square(kingSquare.getRank(), File.F);
                break;
            default:
                return;
        }
        mBoard.movePiece(extraMoveOrigin, extraMoveDestination);
    }
    
    protected void checkForPromotion(Square destination, char c) {
    	Rank topRank = mIsItWhitesTurn ? Rank.M8 : Rank.M1;   	
    	if (destination.getRank() != topRank) {
    		return;
    	}   	
    	ChessPiece piece = mBoard.getPiece(destination);    	
    	if (!piece.getType().equals(ChessPieceType.PAWN)) {
    		return;
    	}
    	if(!autoPromotion) {
    		ChessPiece promotionPiece;
    		ConsoleUI newUI = new ConsoleUI();
    		promotionPiece = setPromotionPiece(newUI.setPromotionPiece());
    		mBoard.placePiece(promotionPiece, destination);
    		return;
    	}
    	Queen queen = new Queen(mIsItWhitesTurn);
    	mBoard.placePiece(queen, destination);
    }
    
    protected ChessPiece setPromotionPiece(char c) {
        switch (c) {

        case 'n', 'N':
        	return new Knight(mIsItWhitesTurn);
        case 'b', 'B':
        	return new Bishop(mIsItWhitesTurn);
        case 'r', 'R':
        	return new Rook(mIsItWhitesTurn);
        case 'q', 'Q':
        	return new Queen(mIsItWhitesTurn);
        default:
            throw new IllegalArgumentException();
        }  	
    }

    protected void incrementMove() {
        mIsItWhitesTurn = !mIsItWhitesTurn;
        if (mIsItWhitesTurn) {
            mCurrentMove++;
        }
    }

    public List<Square> getPossibleOrigins(Square destination, ChessPieceType pieceType) {
        List<Square> squaresWithPiece = mBoard.findSquaresOfPieces(pieceType, isItWhitesTurn());
        List<Square> possibleOrigins = new ArrayList<>();
        for (Square origin : squaresWithPiece) {
            ChessPiece piece = mBoard.getPiece(origin);
            if (piece.findMoves(mBoard, origin).contains(destination)) {
                possibleOrigins.add(origin);
            }
        }
        return possibleOrigins;
    }
    
//    public List<Square> getPossibleDestination(Square origin) {
//    	ChessPiece piece = mBoard.getPiece(origin);
//    	List<Square> possibleDestinations = new ArrayList<>();
//    	
//    	Rank rank;
//    	File file;
//    	
//    	for (int i = 0; i < 8; i++) {
//        	for (int j = 0; j < 8; j++) {
//        		
//        	}
//    	}
//    	//int i = (int) Math.floor(Math.random()*(7));
//    	//int j = (int) Math.floor(Math.random()*(7));
//    	
//    	Square destination = new Square(Rank.valueOf(i), File.valueOf(j));
//    	
//    	if (piece.findMoves(mBoard, destination).contains(origin)) {
//            possibleOrigins.add(origin);
//            
//        }
//        return possibleOrigins;
//    }

    public ChessBoard getBoard() {
        return mBoard;
    }

    public int getCurrentMove() {
        return mCurrentMove;
    }

    public boolean isItWhitesTurn() {
        return mIsItWhitesTurn;
    }

    public ChessResult getResult() {
        return GameOverDetector.checkForMate(mIsItWhitesTurn, mBoard);
    }

    public boolean isGameRunning() {
        return getResult() == ChessResult.NONE;
    }
    
    public boolean getAutoPromotion() {
    	return autoPromotion;
    }
    
    public void setAutoPromotion(boolean set) {
    	this.autoPromotion = set;
    }

}
