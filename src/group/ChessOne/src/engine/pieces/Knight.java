package engine.pieces;

import engine.Chess;
import engine.squares.File;
import engine.squares.Square;
import chessframework.WriteError;
import engine.squares.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Knight piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 */
public class Knight extends ChessPiece {
	
    private static final int[] FILE_OFFSETS = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int[] RANK_OFFSETS = {2, 1, -1, -2, -2, -1, 1, 2};

    public Knight(PlayerColor playerColor) {
        super(playerColor, ChessPieceType.KNIGHT);
    }

    @Override
    public List<Square> findCoveredSquares(Chess game) {

        Optional<Square> s = game.getBoard().getSquare(this);
        if (s.isEmpty()) {
            return new ArrayList<>();
        }
        Square origin = s.get();

        List<Square> list = new ArrayList<>();

        Rank newRank;
        File newFile;
        Square squareToTest;
        for (int i = 0; i < 8; i++) {
            int rankOffset = RANK_OFFSETS[i];
            int fileOffset = FILE_OFFSETS[i];
            try {
                newRank = Rank.valueOf(origin.getRank().getIndex() + rankOffset);
                newFile = File.valueOf(origin.getFile().getIndex() + fileOffset);
                squareToTest = new Square(newRank, newFile);
                var piece = game.getBoard().getPiece(squareToTest);
                if (piece.isEmpty() || piece.get().getColor().equals(getColor().getContrary())) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {
                WriteError.writeErrorLog("");
            }
        }
        return list;
    }
}
