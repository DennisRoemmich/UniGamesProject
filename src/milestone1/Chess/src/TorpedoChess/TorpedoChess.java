package TorpedoChess;

import core.Chess;
import core.ChessBoard;
import core.Color;
import core.pieces.ChessPiece;
import core.pieces.Pawn;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

public class TorpedoChess extends Chess {
    public TorpedoChess() {
        super();
        mBoard = getTorpedoBoard();
    }

    protected static ChessBoard getTorpedoBoard() {
        ChessBoard board = ChessBoard.getStartBoard();
        for(File file : File.values()) {
            board.placePiece(new TorpedoPawn(Color.WHITE), new Square(Rank.M2, file));
            board.placePiece(new TorpedoPawn(Color.BLACK), new Square(Rank.M7, file));
        }
        return board;
    }
}
