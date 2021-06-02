package core;

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

    }

    public boolean makeMove(Position origin, Position destination) {
        if (getPossibleMoves(origin).contains(destination)) {
            board.movePiece(origin, destination, currentMove);
            if(isItWhitesTurn) {
                isItWhitesTurn = false;
            } else {
                isItWhitesTurn = true;
                currentMove++;
            }
            return true;
        } else {
            return false;
        }
    }

    public List<Position> getPossibleMoves(Position pos) {
        if(board.isOccupiedBySelf(pos, isItWhitesTurn)) {
            return MoveFinder.findMoves(pos, board);
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
