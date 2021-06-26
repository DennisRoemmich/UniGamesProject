package core.pieces;

import core.ChessBoard;
import core.ChessMove;
import core.positioning.Direction;
import core.positioning.Rank;
import core.positioning.Square;
import framework.WriteError;
import java.util.ArrayList;
import java.util.List;

/**
 * Pawn piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Pawn extends ChessPiece {

    private boolean mCanBeCapturedEnPassant = false;

    public Pawn(boolean isWhite) {
        super(isWhite, ChessPieceType.PAWN);
    }

    @Override
    public List<ChessMove> findMoves(ChessBoard board, Square origin) {
        List<ChessMove> moves = new ArrayList<>();

        // Regulär schlagen
        moves.addAll(super.findMoves(board, origin));

        // Einfacher Zug & Doppelzug
        Square squareToTest = origin.getNext(getMoveDirection());
        if (board.isFieldFree(squareToTest)) {
            if(squareToTest.getRank() == Rank.getBackRank(!isWhite())) {
                // Das ? steht dafür, dass die gewünschte Figur unbekannt ist
                moves.add(new ChessMove(origin, squareToTest, "promotion?"));
            }
            moves.add(new ChessMove(origin, squareToTest));
            if (isDoubleMovePossible()) {
                squareToTest = squareToTest.getNext(getMoveDirection());
                if (board.isFieldFree(squareToTest)) {
                    moves.add(new ChessMove(origin, squareToTest, "doublemove"));
                }
            }
        }
        return moves;
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();
        Square squareToTest;

        //Schlagen
        for (Direction captureDirection : new Direction[]{Direction.LEFT, Direction.RIGHT}) {
            try {
                squareToTest = origin.getNext(getMoveDirection());
                squareToTest = squareToTest.getNext(captureDirection);
                if (board.isOccupiedByOpponentOrFree(squareToTest, isWhite())) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {
            	WriteError.writeErrorLog("");
            }
        }
        return list;
    }

    protected boolean isDoubleMovePossible() {
        return getNumberOfMoves() == 0;
    }

    public void registerDoubleMove() {
        mCanBeCapturedEnPassant = true;
    }

    public void resetEnPassant() {
        mCanBeCapturedEnPassant = false;
    }

    public Direction getMoveDirection() {
        return isWhite() ? Direction.UP : Direction.DOWN;
    }
}
