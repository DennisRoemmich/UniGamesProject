package core.pieces;

import core.ChessBoard;
import core.MoveFinder;
import core.positioning.Position;

import java.util.List;

public class Knight extends ChessPiece  {

    public Knight(boolean isWhite){
        super(isWhite, ChessPieceType.KNIGHT);
    }

    @Override
    public List<Position> findMoves(Position pos, ChessBoard board) {
        return MoveFinder.findMoves(pos, board);
    }
}
