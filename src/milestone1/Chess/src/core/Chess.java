package core;

import core.pieces.*;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import sample.WriteError;
import java.util.ArrayList;
import java.util.List;

/**
 * 	Main Chess class where the main chess game logic is being executed.
 *  @author Jan de Boer, Dennis Roemmich
 */
public class Chess {

    private static ChessBoard mBoard = ChessBoard.getStartBoard();
    private static int mCurrentMove = 1;
    private static boolean mIsItWhitesTurn = true;

    public static void resetGame() {
        mBoard = ChessBoard.getStartBoard();
        mCurrentMove = 1;
        mIsItWhitesTurn = true;
    }

    public static boolean makeMove(Square origin, Square destination) {
        ChessPiece piece = mBoard.getPiece(origin);
        if(piece == null) return false;
        ChessPieceType pieceType = piece.getType();
        if (getPossibleOrigins(destination, pieceType).contains(origin)) {
            checkForCastling(origin, destination);
            handleEnPassantCapture(origin, destination);
            mBoard.movePiece(origin, destination);
            checkForPromotion(destination, 'Q');
            registerMove(destination);
            resetEnPassant();
            checkForPawnDoubleMove(origin, destination);
            incrementMove();
            return true;
        } else {
            return false;
        }
    }

    private static void handleEnPassantCapture(Square origin, Square destination) {
        if (mBoard.getPiece(origin).getType() == ChessPieceType.PAWN && origin.getFile() != destination.getFile()
        	&& mBoard.getPiece(destination) == null) {
                    
                    Direction direction = mBoard.getPiece(origin).isWhite() ? Direction.DOWN : Direction.UP;
                    Square squareToRemove = destination.getNext(direction);
                    mBoard.removePiece(squareToRemove);
                }
            }
      
    private static void resetEnPassant() {
        for (ChessPiece piece : mBoard.findPieces(ChessPieceType.PAWN)) {
            Pawn pawn = (Pawn) piece;
            pawn.resetEnPassant();
        }
    }

    private static void registerMove(Square square) {
        ChessPiece piece = mBoard.getPiece(square);
        switch (piece.getType()) {
            case PAWN:
                ((Pawn) piece).registerMove(mCurrentMove);
                break;
            case KING, ROOK:
                ((CastlingChessPiece) piece).registerMove();
                break;
		default:
			break;
        }
    }

    private static void checkForPawnDoubleMove(Square origin, Square destination) {
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

    private static void checkForCastling(Square origin, Square destination) {
        Rank backRank = mIsItWhitesTurn ? Rank.M1 : Rank.M8;
        King king = (King) mBoard.findPieces(ChessPieceType.KING, mIsItWhitesTurn).get(0);
        if (king.hasMoved()) {
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
    
    private static void checkForPromotion(Square destination, char c) {
    	Rank topRank = mIsItWhitesTurn ? Rank.M8 : Rank.M1;   	
    	if (destination.getRank() != topRank) {
    		return;
    	}   	
    	ChessPiece piece = mBoard.getPiece(destination);    	
    	if (!piece.getType().equals(ChessPieceType.PAWN)) {
    		return;
    	}
    	Queen queen = new Queen(mIsItWhitesTurn);
    	
    	setPromotionPiece(c);
    	mBoard.placePiece(queen, destination);
    }
    
    private static ChessPiece setPromotionPiece(char c) {
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

    private static void incrementMove() {
        if (mIsItWhitesTurn) {
            mIsItWhitesTurn = false;
        } else {
            mIsItWhitesTurn = true;
            mCurrentMove++;
        }
    }

    public static List<Square> getPossibleOrigins(Square destination, ChessPieceType pieceType) {
        List<Square> squaresWithPiece = mBoard.findSquaresOfPieces(pieceType, isItWhitesTurn());
        List<Square> possibleOrigins = new ArrayList<>();
        for(Square origin : squaresWithPiece) {
            ChessPiece piece = mBoard.getPiece(origin);
            if(piece.findMoves(origin, mBoard).contains(destination)) {
                possibleOrigins.add(origin);
            }
        }
        return possibleOrigins;
    }

    public static ChessBoard getBoard() {
        return mBoard;
    }

    public static int getCurrentMove() {
        return mCurrentMove;
    }

    public static boolean isItWhitesTurn() {
        return mIsItWhitesTurn;
    }

    public static ChessResult getResult() {
        return GameOverDetector.checkForMate(mIsItWhitesTurn, mBoard);
    }

}
