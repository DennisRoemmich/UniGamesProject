package core.pieces;

import core.ChessBoard;
import core.MoveFinder;
import core.positioning.Position;

import java.util.List;

public class Pawn extends ChessPiece {

    private int numberOfMoves = 0;
    private int lastMoved = 0;

    public Pawn(boolean isWhite){
        super(isWhite, ChessPieceType.PAWN);
    }

    @Override
    public List<Position> findMoves(Position pos, ChessBoard board) {
        return MoveFinder.findMoves(pos, board);
    }

    public void registerMove(int moveNumber) {
        numberOfMoves++;
        lastMoved = moveNumber;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getLastMoved() {
        return lastMoved;
    }
}
