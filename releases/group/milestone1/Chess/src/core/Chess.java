package core;

import core.pieces.CastlingChessPiece;
import core.pieces.ChessPiece;
import core.pieces.ChessPieceType;
import core.pieces.Pawn;
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
    	//Ununsed so far
    }

    public boolean makeMove(Square origin, Square destination) {
        if (getPossibleMoves(origin).contains(destination)) {
            board.movePiece(origin, destination);
            registerMove(destination);
            incrementMove();
            return true;
        } else {
            return false;
        }
    }

    private void registerMove(Square destination) {
        ChessPiece piece = board.getPiece(destination);
        if(piece.getType() == ChessPieceType.PAWN) {
            Pawn pawn = (Pawn) piece;
            pawn.registerMove(currentMove);
        }
        if(piece.getType() == ChessPieceType.KING || piece.getType() == ChessPieceType.ROOK) {
            CastlingChessPiece castlingPiece = (CastlingChessPiece) piece;
            castlingPiece.registerMove();
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
}
