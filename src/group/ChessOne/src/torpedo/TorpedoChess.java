package torpedo;

import engine.Chess;
import engine.board.ChessBoard;
import engine.pieces.PlayerColor;
import engine.squares.File;
import engine.squares.Rank;
import engine.squares.Square;

/**
 * Implements the new game mode Torpedo Chess!
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class TorpedoChess extends Chess {
    public TorpedoChess() {
        super();
        mBoard = getTorpedoBoard();
    }

    protected static ChessBoard getTorpedoBoard() {
        ChessBoard board = ChessBoard.getStartBoard();
        for (File file : File.values()) {
            board.placePiece(new TorpedoPawn(PlayerColor.WHITE), new Square(Rank.M2, file));
            board.placePiece(new TorpedoPawn(PlayerColor.BLACK), new Square(Rank.M7, file));
        }
        return board;
    }
}
