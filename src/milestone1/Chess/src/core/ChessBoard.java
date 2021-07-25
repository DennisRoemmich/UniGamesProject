package core;

import core.pieces.*;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The chess board implementation of a chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ChessBoard implements Cloneable {

    List<PositionedPiece> positionedPieces = new ArrayList<>();

    public ChessBoard() {

    }

    public ChessBoard(ChessBoard board) {
        for(PositionedPiece positionedPiece : board.positionedPieces) {
            this.positionedPieces.add(new PositionedPiece(positionedPiece));
        }
    }

    public void placePiece(ChessPiece piece, Square square) {
        PositionedPiece positionedPiece = new PositionedPiece(square, piece);
        addPiece(positionedPiece);
    }

    public void addPiece(PositionedPiece positionedPiece) {
        removePiece(positionedPiece.getPosition());
        positionedPieces.add(positionedPiece);
    }

    public void removePiece(Square square) {
        positionedPieces.removeIf(pP -> pP.getPosition().equals(square));
    }

    public void removePiece(ChessPiece piece) {
        positionedPieces.removeIf(pP -> pP.getPiece() == piece);
    }

    public Optional<ChessPiece> getPiece(Square square) {
        return positionedPieces.stream().filter(pP -> pP.getPosition().equals(square)).map(pP -> pP.getPiece()).findFirst();
    }

    public void movePiece(ChessPiece piece, Square newSquare) {
        removePiece(piece);
        placePiece(piece, newSquare);
    }

    public void movePiece(Square origin, Square destination) {
        var piece = getPiece(origin);
        if(piece.isPresent()) {
            removePiece(origin);
            placePiece(piece.get(), destination);
        }
    }

    public boolean isFieldFree(Square square) {
        return getPiece(square).isEmpty();
    }


    public static ChessBoard getStartBoard() {
        ChessBoard board = new ChessBoard();
        for (File file : File.values()) {
            board.placePiece(new Pawn(true), new Square(Rank.M2, file));
            board.placePiece(new Pawn(false), new Square(Rank.M7, file));
        }
        for (File file : new File[]{File.A, File.H}) {
            board.placePiece(new Rook(true), new Square(Rank.M1, file));
            board.placePiece(new Rook(false), new Square(Rank.M8, file));
        }
        for (File file : new File[]{File.B, File.G}) {
            board.placePiece(new Knight(true), new Square(Rank.M1, file));
            board.placePiece(new Knight(false), new Square(Rank.M8, file));
        }
        for (File file : new File[]{File.C, File.F}) {
            board.placePiece(new Bishop(true), new Square(Rank.M1, file));
            board.placePiece(new Bishop(false), new Square(Rank.M8, file));
        }
        board.placePiece(new Queen(true), new Square(Rank.M1, File.D));
        board.placePiece(new Queen(false), new Square(Rank.M8, File.D));
        board.placePiece(new King(true), new Square(Rank.M1, File.E));
        board.placePiece(new King(false), new Square(Rank.M8, File.E));
        return board;
    }

    public Optional<Square> getSquare(ChessPiece piece) {
        return positionedPieces.stream().filter(pP -> pP.getPiece() == piece).map(pP -> pP.getPosition()).findFirst();
    }

    public List<PositionedPiece> getPositionedPieces() {
        return positionedPieces;
    }
}
