package engine.board;

import engine.pieces.*;
import engine.squares.File;
import engine.squares.Rank;
import engine.squares.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The chess board implementation of a chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ChessBoard {

    List<PositionedPiece> mPositionedPieces = new ArrayList<>();

    public ChessBoard() {

    }

    public ChessBoard(ChessBoard board) {
        for (PositionedPiece positionedPiece : board.mPositionedPieces) {
            this.mPositionedPieces.add(new PositionedPiece(positionedPiece));
        }
    }

    public void placePiece(ChessPiece piece, Square square) {
        PositionedPiece positionedPiece = new PositionedPiece(square, piece);
        addPiece(positionedPiece);
    }

    public void addPiece(PositionedPiece positionedPiece) {
        removePiece(positionedPiece.getPosition());
        mPositionedPieces.add(positionedPiece);
    }

    public void removePiece(Square square) {
        mPositionedPieces.removeIf(pP -> pP.getPosition().equals(square));
    }

    public void removePiece(ChessPiece piece) {
        mPositionedPieces.removeIf(pP -> pP.getPiece() == piece);
    }

    public Optional<ChessPiece> getPiece(Square square) {
        for (PositionedPiece piece : mPositionedPieces) {
            if (piece.getPosition().equals(square)) {
                return Optional.of(piece.getPiece());
            }
        }
        return Optional.empty();
    }

    public void movePiece(ChessMove move) {
        movePiece(move.getOrigin(), move.getDestination());
    }

    public void movePiece(Square origin, Square destination) {
        var piece = getPiece(origin);
        if (piece.isPresent()) {
            removePiece(origin);
            placePiece(piece.get(), destination);
        }
    }

    public boolean isFieldFree(Square square) {
        return getPiece(square).isEmpty();
    }

    public static ChessBoard getStartBoard() {
        ChessBoard board = new ChessBoard();
        for (Color color : Color.values()) {
            Rank bRank = color.getBackrank();
            for (File file : File.values()) {
                Optional<Square> s = new Square(bRank, file).getNext(color.getPawnMoveDirection());
                if (s.isPresent()) {
                    board.placePiece(new Pawn(color), s.get());
                }

            }
            for (File file : new File[]{File.A, File.H}) {
                board.placePiece(new Rook(color), new Square(bRank, file));
            }
            for (File file : new File[]{File.B, File.G}) {
                board.placePiece(new Knight(color), new Square(bRank, file));
            }
            for (File file : new File[]{File.C, File.F}) {
                board.placePiece(new Bishop(color), new Square(bRank, file));
            }
            board.placePiece(new Queen(color), new Square(bRank, File.D));
            board.placePiece(new King(color), new Square(bRank, File.E));
        }
        return board;
    }

    public Optional<Square> getSquare(ChessPiece piece) {
        for (PositionedPiece positionedPiece : mPositionedPieces) {
            if (positionedPiece.getPiece() == piece) {
                return Optional.of(positionedPiece.getPosition());
            }
        }
        return Optional.empty();
    }

    public List<ChessPiece> getPieces() {
        return piecesExtracted(getPositionedPieces());
    }

    public List<ChessPiece> getPieces(Color color) {
        return piecesExtracted(getPositionedPieces(color));
    }


    public List<PositionedPiece> getPositionedPieces() {
        return mPositionedPieces;
    }

    public List<PositionedPiece> getPositionedPieces(Color color) {
        List<PositionedPiece> filteredPieces = new ArrayList<>();
        for (PositionedPiece positionedPiece : mPositionedPieces) {
            if (positionedPiece.getPiece().getColor().equals(color)) {
                filteredPieces.add(positionedPiece);
            }
        }
        return filteredPieces;
    }

    public List<PositionedPiece> getPositionedPieces(Color color, ChessPieceType type) {
        List<PositionedPiece> filteredPieces = new ArrayList<>();
        for (PositionedPiece positionedPiece : mPositionedPieces) {
            ChessPiece piece = positionedPiece.getPiece();
            if (piece.getColor().equals(color) && piece.getType().equals(type) ) {
                filteredPieces.add(positionedPiece);
            }
        }
        return filteredPieces;
    }

    public static List<ChessPiece> piecesExtracted(List<PositionedPiece> piecesWithPosition) {
        List<ChessPiece> pieces = new ArrayList<>();
        for (PositionedPiece positionedPiece : piecesWithPosition) {
            pieces.add(positionedPiece.getPiece());
        }
        return pieces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        ChessBoard board = (ChessBoard) o;
        return Objects.equals(getPositionedPieces(), board.getPositionedPieces());
    }

    @Override
    public int hashCode() {
        int prehash = 0;
        for (PositionedPiece piece : mPositionedPieces) {
            prehash += piece.hashCode();
        }
        return Objects.hash(prehash);
    }
}
