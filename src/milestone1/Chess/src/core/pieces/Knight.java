package core.pieces;

import core.ChessBoard;
import core.Color;
import core.positioning.File;
import core.positioning.Square;
import framework.WriteError;
import core.positioning.Rank;
import java.util.ArrayList;
import java.util.List;

/**
 * Knight piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Knight extends ChessPiece  {

    public Knight(Color color) {
        super(color, ChessPieceType.KNIGHT);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();

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
                        var piece = board.getPiece(squareToTest);
                        if (piece.isEmpty() || piece.get().getColor().equals(getColor().getContrary())) {
                            list.add(squareToTest);
                        }
                    } catch (Exception e) {
                    	WriteError.writeErrorLog("");
                    }
                }
            }
        }
        return list;
    }
}
