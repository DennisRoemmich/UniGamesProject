package core.pieces;

import core.ChessBoard;
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece  {

    public Knight(boolean isWhite){
        super(isWhite, ChessPieceType.KNIGHT);
    }

    @Override
    public List<Square> findMovesDisregardingCheck(Square pos, ChessBoard board) {
        List<Square> list = new ArrayList<Square>();

        ChessPiece piece = board.getPiece(pos);
        Rank newRank = pos.getRow();
        File newFile = pos.getColumn();
        Square squareToTest;
        for (int rowMirror : new int[]{1, -1}) {
            for (int columnMirror : new int[]{-1, 1}) {
                for (int diagonalMirror : new int[]{0, 1}) {
                    int rowOffset = (1 + diagonalMirror) * rowMirror;
                    int columnOffset = (2 - diagonalMirror) * columnMirror;
                    try {
                        newRank = Rank.valueOf(pos.getRow().getIndex() + rowOffset);
                        newFile = File.valueOf(pos.getColumn().getIndex() + columnOffset);
                        squareToTest = new Square(newRank, newFile);
                        if (board.isOccupiedByOpponentOrFree(squareToTest, piece.isWhite())) {
                            list.add(squareToTest);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        return list;
    }
}
