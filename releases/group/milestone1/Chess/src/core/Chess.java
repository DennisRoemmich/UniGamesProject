package core;

import core.pieces.*;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import sample.PrintError;

import java.util.ArrayList;
import java.util.List;

public class Chess {

    private ChessBoard board = ChessBoard.getStartBoard();
    private int currentMove = 1;
    private boolean isItWhitesTurn = true;


    public void reset() {
        board = ChessBoard.getStartBoard();
    }

    public Chess() {
    	//Unused so far
    }

    public boolean makeMove(Square origin, Square destination) {
        if (getPossibleMoves(origin).contains(destination)) {
            checkForCastling(origin, destination);
            handleEnPassantCapture(origin, destination);
            board.movePiece(origin, destination);
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

    private void handleEnPassantCapture(Square origin, Square destination) {
        if(board.getPiece(origin).getType() == ChessPieceType.PAWN && origin.getFile() != destination.getFile() && board.getPiece(destination) == null) {
                    
                    Direction direction = board.getPiece(origin).isWhite() ? Direction.DOWN : Direction.UP;
                    Square squareToRemove = destination.getNext(direction);
                    board.removePiece(squareToRemove);
                }
            }
        
    

    private void resetEnPassant() {
        for(ChessPiece piece : board.findPieces(ChessPieceType.PAWN)){
            Pawn pawn = (Pawn) piece;
            pawn.resetEnPassant();
        }
    }

    private void registerMove(Square square) {
        ChessPiece piece = board.getPiece(square);
        switch (piece.getType()) {
            case PAWN:
                ((Pawn) piece).registerMove(currentMove);
                break;
            case KING, ROOK:
                ((CastlingChessPiece) piece).registerMove();
                break;
		default:
			break;
        }
    }

    private void checkForPawnDoubleMove(Square origin, Square destination) {
        ChessPiece piece = board.getPiece(destination);
        if(piece.getType() == ChessPieceType.PAWN) {
            Direction moveDirection = isItWhitesTurn ? Direction.UP : Direction.DOWN;
            try {
                Square doubleMoveDestination = origin.getNext(moveDirection).getNext(moveDirection);
                if(doubleMoveDestination.equals(destination)) {
                    Pawn pawn = (Pawn) piece;
                    pawn.registerDoubleMove();
                }
            } catch (Exception e) {
            	PrintError.writeErrorLog("");
            }
        }
    }

    private void checkForCastling(Square origin, Square destination) {
        Rank backRank = isItWhitesTurn ? Rank.M1 : Rank.M8;
        King king = (King)board.findPieces(ChessPieceType.KING, isItWhitesTurn).get(0);
        if(king.hasMoved()) {
            return;
        }
        Square kingSquare = board.getSquare((ChessPiece) king);
        if(!kingSquare.equals(origin)) {
            // So far, castling is always triggered by the king. So only the square of the king is interesting
            return;
        }
        if(destination.getRank() != backRank) {
            return;
        }

        Square extraMoveOrigin;
        Square extraMoveDestination;
        switch(destination.getFile()) {
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
        board.movePiece(extraMoveOrigin, extraMoveDestination);
    }
    
    private void checkForPromotion(Square destination, char c) {
    	Rank topRank = isItWhitesTurn ? Rank.M8 : Rank.M1;
    	
    	if(destination.getRank() != topRank) {
    		return;
    	}
    	
    	ChessPiece piece = board.getPiece(destination);
    	
    	if(!piece.getType().equals(ChessPieceType.PAWN)) {
    		return;
    	}
    	Queen queen = new Queen(isItWhitesTurn);
    	
    	setPromotionPiece(c);
    	board.placePiece(queen, destination);
    }
    
    private ChessPiece setPromotionPiece(char c) {
        switch (c) {

        case 'n','N':
        	Knight knight = new Knight(isItWhitesTurn);
        return knight;
        case 'b','B':
        	Bishop bishop = new Bishop(isItWhitesTurn);
        return bishop;
        case 'r','R':
        	Rook rook = new Rook(isItWhitesTurn);
        return rook;
        case 'q','Q':
        	Queen queen = new Queen(isItWhitesTurn);
        	return queen;
        default:
            throw new IllegalArgumentException();
        }  	
    }

    private void incrementMove() {
        if(isItWhitesTurn) {
            isItWhitesTurn = false;
        } else {
            isItWhitesTurn = true;
            currentMove++;
        }
    }

    public List<Square> getPossibleMoves(Square pos) {
        ChessPiece piece = board.getPiece(pos);
        if(piece != null && piece.isWhite() == isItWhitesTurn) {
            return piece.findMoves(pos, board);
        } else {
            return new ArrayList<>();
        }
    }

    public ChessBoard getBoard() {
        return board;
    }

    public int getCurrentMove() {
        return currentMove;
    }

    public boolean isItWhitesTurn() {
        return isItWhitesTurn;
    }

    public ChessResult getResult() {
        return GameOverDetector.checkForMate(isItWhitesTurn, board);
    }

    public boolean isGameRunning() {
        return getResult() == ChessResult.NONE;
    }
}
