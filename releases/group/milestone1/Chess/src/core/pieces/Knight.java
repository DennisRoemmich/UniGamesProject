package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import sample.PrintError;
import core.positioning.Rank;
import java.util.ArrayList;
import java.util.List;

/**
 * Knight piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Knight extends ChessPiece  {

    public Knight(boolean isWhite) {
        super(isWhite, ChessPieceType.KNIGHT);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();

        ChessPiece piece = board.getPiece(origin);
        Rank newRank;
        File newFile;
        Square squareToTest;
        for (int rowMirror : new int[]{1, -1}) {
            for (int columnMirror : new int[]{-1, 1}) {
                for (int diagonalMirror : new int[]{0, 1}) {
                    int rowOffset = (1 + diagonalMirror) * rowMirror;
                    int columnOffset = (2 - diagonalMirror) * columnMirror;
                    try {
                        newRank = Rank.valueOf(origin.getRank().getIndex() + rowOffset);
                        newFile = File.valueOf(origin.getFile().getIndex() + columnOffset);
                        squareToTest = new Square(newRank, newFile);
                        if (board.isOccupiedByOpponentOrFree(squareToTest, piece.isWhite())) {
                            list.add(squareToTest);
                        }
                    } catch (Exception e) {
                    	PrintError.writeErrorLog("");
                    }
                }
            }
        }
        return list;
    }
}
