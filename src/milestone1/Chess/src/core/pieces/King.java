package core.pieces;

import core.CheckDetector;
import core.Chess;
import core.ChessBoard;
import core.positioning.Direction;
import core.positioning.File;
import core.positioning.Square;
import sample.PrintError;
import core.positioning.Rank;

import javax.swing.filechooser.FileView;
import java.util.ArrayList;
import java.util.List;

/**
 * King piece on the chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class King extends CastlingChessPiece  {

    public King(boolean isWhite) {
        super(isWhite, ChessPieceType.KING);
    }

    @Override
    public List<Square> findCoveredSquares(ChessBoard board, Square origin) {
        List<Square> list = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            try {
                Square squareToTest = origin.getNext(direction);
                if (board.isOccupiedByOpponentOrFree(squareToTest, isWhite())) {
                    list.add(squareToTest);
                }
            } catch (Exception e) {
            	PrintError.writeErrorLog("");
            }
        }
        return list;
    }

    @Override
    public List<Square> findMoves(Square square, ChessBoard board) {
        List<Square> moves = super.findMoves(square, board);
        moves.addAll(findCastlingMoves(board));
        return moves;
    }
    
    private List<Square> findCastlingMoves(ChessBoard board) {
        List<Square> castlingMoves = new ArrayList<>();
        if (this.hasMoved() || CheckDetector.isInCheck(board, isWhite())) {
            return castlingMoves;
        }
        Rank backRank = isWhite() ? Rank.M1 : Rank.M8;
        if(isQueenSideCastlingPossible(board)) {
            castlingMoves.add(new Square(backRank, File.C));
        }
        if(isKingSideCastlingPossible(board)) {
            castlingMoves.add(new Square(backRank, File.G));
        }
        return  castlingMoves;
    }

    private boolean isQueenSideCastlingPossible(ChessBoard board) {
        if(hasMoved()) return false;
        Rank backRank = isWhite() ? Rank.M1 : Rank.M8;
        Rook rook = (Rook) board.getPiece(new Square(backRank, File.A));
        if(rook == null || rook.hasMoved()) return false;
        List<Square> squaresToTest = new ArrayList<>();
        squaresToTest.add(new Square(backRank, File.C));
        squaresToTest.add(new Square(backRank, File.D));
        for(Square square : squaresToTest) {
            if(CheckDetector.isSquareAttacked(board, square, !isWhite())) return false;
        }
        squaresToTest.add(new Square(backRank, File.B));
        for(Square square : squaresToTest) {
            if(!board.isFieldFree(square)) return false;
        }
        return true;
    }

    private boolean isKingSideCastlingPossible(ChessBoard board) {
        if(hasMoved()) return false;
        Rank backRank = isWhite() ? Rank.M1 : Rank.M8;
        List<Square> squaresToTest = new ArrayList<>();
        squaresToTest.add(new Square(backRank, File.F));
        squaresToTest.add(new Square(backRank, File.G));
        for(Square square : squaresToTest) {
            if(CheckDetector.isSquareAttacked(board, square, !isWhite())) return false;
            if(!board.isFieldFree(square)) return false;
        }
        return true;
    }

}
