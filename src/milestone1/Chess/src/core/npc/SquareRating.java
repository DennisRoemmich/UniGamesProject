package core.npc;

import core.Chess;
import core.PositionedPiece;
import core.pieces.ChessPieceType;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

public final class SquareRating {
    public static double rate(PositionedPiece piece) {
        int index = getCorrectedSquare(piece).getIndex();

        return switch (piece.getPiece().getType()) {
            case PAWN   -> pawnRatingTable[index];
            case BISHOP -> bishopRatingTable[index];
            case KNIGHT -> knightRatingTable[index];
            case ROOK   -> rookRatingTable[index];
            case KING   -> kingRatingTable[index];
            case QUEEN  -> queenRatingTable[index];
            default -> 0;
        };
    }

    public static Square getCorrectedSquare(PositionedPiece piece) {
        if (!piece.getPiece().getColor().isWhite()) {
            return piece.getPosition();
        } else {
            Rank rank = piece.getPosition().getRank().getOpposite();
            return new Square(rank, piece.getPosition().getFile());
        }
    }

    public static final double[] pawnRatingTable = {
            10, 10, 10, 10, 10, 10, 10, 10,
            4,  4,  4,  4,  4,  4,  4,  4,
            3,  3,  3,  3,  3,  3,  3,  3,
            2,  2,  2,  2,  2,  2,  2,  2,
            0,  0,  0,  5,  5,  0,  0,  0,
            2,  0,  0,  1,  1,  0,  0,  2,
            1,  2,  2,  -2,  -2,  2,  2,  1,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    public static final double[] bishopRatingTable = {
            -2, -1,  0,  0,  0,  0, -1, -2,
            0,   1,  1,  1,  1,  1,  1,  0,
            0,   3,  3,  3,  3,  3,  3,  0,
            1,   3,  3,  3,  3,  3,  3,  1,
            1,   3,  3,  3,  3,  3,  3,  1,
            0,   1,  1,  2,  2,  1,  1,  0,
            0,   0,  0,  0,  0,  0,  0,  0,
            -2, -1, -3, -1, -1, -3, -1, -2
    };

    public static final double[] knightRatingTable = {
            -2, -1,  -1,  -1,  -1,  -1, -1, -2,
            -1,  2,  2,  2,  2,  2,  2,  -1,
            -1,   3,  3,  3,  3,  3,  3,  -1,
            -1,   3,  3,  3,  3,  3,  3,  -1,
            -1,   3,  3,  3,  3,  3,  3,  -1,
            -1,   3,  3,  3,  3,  3,  3,  -1,
            -1,   0,  0,  1,  1,  0,  0,  -1,
            -2, -3, -1, -1, -1, -1, -3, -2
    };

    public static final double[] rookRatingTable = {
            3,3,3,3,3,3,3,3,
            5,5,5,5,5,5,5,5,
            2,2,2,2,2,2,2,2,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,3,3,0,0,0
    };

    public static final double[] queenRatingTable = {
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0
    };

    public static final double[] kingRatingTable = {
            0,0,0,0,0,0,0,0,
            0,1,1,1,1,1,1,0,
            0,1,2,2,2,2,1,0,
            0,1,2,2,2,2,1,0,
            0,1,2,2,2,2,1,0,
            0,1,2,2,2,2,1,0,
            0,1,1,1,1,1,1,0,
            0,0,5,2,3,0,5,0
    };
}
