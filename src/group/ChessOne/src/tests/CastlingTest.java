package tests;

import engine.board.ChessBoard;
import engine.board.ChessMove;
import engine.pieces.ChessPieceType;
import engine.squares.File;
import engine.squares.Rank;
import engine.squares.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests the castling feature.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class CastlingTest extends Test {

    Square[][] mMoves = new Square[][]{{new Square("e2"), new Square("e4")},
            {new Square("e7"), new Square("e5")},
            {new Square("g1"), new Square("f3")},
            {new Square("b8"), new Square("c6")},
            {new Square("f1"), new Square("c4")},
            {new Square("f8"), new Square("c5")},
            {new Square("e1"), new Square("g1")}};


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
        ChessBoard board = mGame.getBoard();
        Square rookSquare = new Square(Rank.M1, File.F);
        var piece = board.getPiece(rookSquare);
        return piece.isPresent() && board.getPiece(rookSquare).get().getType() == ChessPieceType.ROOK;
    }
}
