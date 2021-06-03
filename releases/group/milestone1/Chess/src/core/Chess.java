package core;

import core.pieces.ChessPiece;
import core.positioning.Position;

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

    public boolean makeMove(Position origin, Position destination) {
        if (getPossibleMoves(origin).contains(destination)) {
            board.movePiece(origin, destination, currentMove);
            incrementMove();
            return true;
        } else {
            return false;
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

    public List<Position> getPossibleMoves(Position pos) {
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
