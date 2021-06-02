package core.pieces;

import core.ChessBoard;
import core.MoveFinder;
import core.positioning.Position;

import java.util.List;

public class Queen extends ChessPiece  {

    public Queen(boolean isWhite){
        super(isWhite, ChessPieceType.QUEEN);
    }

    @Override
    public List<Position> findMoves(Position pos, ChessBoard board) {
        return MoveFinder.findMoves(pos, board);
    }
}
