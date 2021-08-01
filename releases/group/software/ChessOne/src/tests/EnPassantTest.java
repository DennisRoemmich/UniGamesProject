package tests;

import engine.board.ChessMove;
import engine.squares.Square;
import engine.squares.File;
import engine.squares.Rank;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests the En-Passant moves in a chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class EnPassantTest extends Test {

    Square[][] mMoves = new Square[][]{{new Square("e2"), new Square("e4")},
            {new Square("c7"), new Square("c5")},
            {new Square("e4"), new Square("e5")},
            {new Square("f7"), new Square("f5")},
            {new Square("e5"), new Square("f6")}};

    protected List<ChessMove> getMoves() {
        List<ChessMove> list = new ArrayList<>();
        for (Square[] originDestinationPair : mMoves) {
        	ChessMove move = new ChessMove(originDestinationPair[0], originDestinationPair[1]);
            list.add(move);
        }
        return list;
    }

    public boolean runTest() {
        super.runMoves();
        Square capturedSquare = new Square(Rank.M5, File.F);
        return mGame.getBoard().getPiece(capturedSquare).isEmpty();
    }
}
