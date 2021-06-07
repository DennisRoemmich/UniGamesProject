package core;

import core.pieces.*;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

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
            board.movePiece(origin, destination);
            registerMove(destination);
            incrementMove();
            return true;
        } else {
            return false;
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
}
