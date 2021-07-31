package npc;

import engine.pieces.PositionedPiece;
import engine.squares.Rank;
import engine.squares.Square;

public final class SquareRating {
	
    protected static final double[] PAWN_RATING_TABLE = {
            10, 10, 10, 10, 10, 10, 10, 10,
            4,  4,  4,  4,  4,  4,  4,  4,
            3,  3,  3,  3,  3,  3,  3,  3,
            2,  2,  2,  2,  2,  2,  2,  2,
            0,  0,  0,  5,  5,  0,  0,  0,
            2,  0,  0,  1,  1,  0,  0,  2,
            1,  2,  2, -2, -2,  2,  2,  1,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    protected static final double[] BISHOP_RATING_TABLE = {
            -2, -1,  0,  0,  0,  0, -1, -2,
            0,   1,  1,  1,  1,  1,  1,  0,
            0,   3,  3,  3,  3,  3,  3,  0,
            1,   3,  3,  3,  3,  3,  3,  1,
            1,   3,  3,  3,  3,  3,  3,  1,
            0,   1,  1,  2,  2,  1,  1,  0,
            0,   0,  0,  0,  0,  0,  0,  0,
            -2, -1, -3, -1, -1, -3, -1, -2
    };

    protected static final double[] KNIGHT_RATING_TABLE = {
            -2, -1, -1, -1, -1, -1, -1, -2,
            -1,  2,  2,  2,  2,  2,  2, -1,
            -1,  3,  3,  3,  3,  3,  3, -1,
            -1,  3,  3,  3,  3,  3,  3, -1,
            -1,  3,  3,  3,  3,  3,  3, -1,
            -1,  3,  3,  3,  3,  3,  3, -1,
            -1,  0,  0,  1,  1,  0,  0, -1,
            -2, -3, -1, -1, -1, -1, -3, -2
    };

    protected static final double[] ROOK_RATING_TABLE = {
            3, 3, 3, 3, 3, 3, 3, 3,
            5, 5, 5, 5, 5, 5, 5, 5,
            2, 2, 2, 2, 2, 2, 2, 2,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 3, 3, 0, 0, 0
    };

    protected static final double[] QUEEN_RATING_TABLE = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0
    };

    protected static final double[] KING_RATING_TABLE = {
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 1, 1, 1, 0,
            0, 1, 2, 2, 2, 2, 1, 0,
            0, 1, 2, 2, 2, 2, 1, 0,
            0, 1, 2, 2, 2, 2, 1, 0,
            0, 1, 2, 2, 2, 2, 1, 0,
            0, 1, 1, 1, 1, 1, 1, 0,
            0, 0, 15, 2, 4, 0, 15, 0
    };
    
    private SquareRating() {
    	//Unused
    }
	
    public static double rate(PositionedPiece piece) {
        var corrected = getCorrectedSquare(piece);
        int index = corrected.getIndex();

        var rating =  switch (piece.getPiece().getType()) {
            case PAWN   -> PAWN_RATING_TABLE[index];
            case BISHOP -> BISHOP_RATING_TABLE[index];
            case KNIGHT -> KNIGHT_RATING_TABLE[index];
            case ROOK   -> ROOK_RATING_TABLE[index];
            case KING   -> KING_RATING_TABLE[index];
            case QUEEN  -> QUEEN_RATING_TABLE[index];
            default -> 0;
        };
        return rating;
    }

    public static Square getCorrectedSquare(PositionedPiece piece) {
        if (!piece.getPiece().getColor().isWhite()) {
            return piece.getPosition();
        } else {
            Rank rank = piece.getPosition().getRank().getOpposite();
            return new Square(rank, piece.getPosition().getFile());
        }
    }

}
