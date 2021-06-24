package TorpedoChess;

import core.Chess;
import core.ChessBoard;
import core.pieces.ChessPiece;
import core.pieces.Pawn;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

public class TorpedoChess extends Chess {
    public TorpedoChess() {
        mBoard = getTorpedoBoard();
        mCurrentMove = 1;
        mIsItWhitesTurn = true;
    }

    protected static ChessBoard getTorpedoBoard() {
        ChessBoard board = ChessBoard.getStartBoard();
        for(File file : File.values()) {
            board.placePiece(new Pawn(true), new Square(Rank.M2, file));
            board.placePiece(new Pawn(false), new Square(Rank.M7, file));
        }
        return board;
    }
}
