package core.pieces;

import core.ChessBoard;
import core.MoveFinder;
import core.positioning.Position;

import java.util.List;

public class King extends ChessPiece  {

    private static boolean hasMoved = false;

    public King(boolean isWhite){
        super(isWhite, ChessPieceType.KING);
    }
    
    public static boolean getHasMoved() {
    	return hasMoved;
    }

    @Override
    public List<Position> findMoves(Position pos, ChessBoard board) {
        return MoveFinder.findMoves(pos, board);
    }
}
