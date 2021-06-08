package core.pieces;

import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.Square;
import java.util.List;

public class Queen extends ChessPiece  {

    public Queen(boolean isWhite){
        super(isWhite, ChessPieceType.QUEEN);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        ChessPieceMoves moveFinder = new ChessPieceMoves(this, origin, board);
        return moveFinder.getReachableSquares(Direction.values());
    }
}
