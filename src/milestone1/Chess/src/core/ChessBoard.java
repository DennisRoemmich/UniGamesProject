package core;

import core.pieces.*;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;
import java.util.ArrayList;
import java.util.List;

/**
 * The chess board implementation of a chess game.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ChessBoard implements Cloneable {

    ChessPiece[][] mPieces;

    public ChessBoard() {
        this.mPieces = new ChessPiece[Rank.values().length][File.values().length];
    }

    public void placePiece(ChessPiece piece, Square square) {
        mPieces[square.getRank().getIndex()][square.getFile().getIndex()] = piece;
    }

    public void removePiece(Square square) {
        mPieces[square.getRank().getIndex()][square.getFile().getIndex()] = null;
    }

    public ChessPiece getPiece(Square square) {
        return mPieces[square.getRank().getIndex()][square.getFile().getIndex()];
    }

    public void movePiece(Square origin, Square destination) {
        ChessPiece piece = getPiece(origin);
        if (piece != null) {
            placePiece(piece, destination);
            removePiece(origin);
        }
    }

    public boolean isFieldFree(Square square) {
        return getPiece(square) == null;
    }

    public boolean isOccupiedByOpponent(Square square, boolean selfIsWhite) {
        return !isFieldFree(square) && (getPiece(square).isWhite() != selfIsWhite);
    }

    public boolean isOccupiedByOpponentOrFree(Square square, boolean selfIsWhite) {
        return isFieldFree(square) || isOccupiedByOpponent(square, selfIsWhite);
    }

    public boolean isOccupiedBySelf(Square square, boolean selfIsWhite) {
        return !isFieldFree(square) && (getPiece(square).isWhite() == selfIsWhite);
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

    public List<ChessPiece> findPieces(boolean color) {
        ArrayList<ChessPiece> piece = new ArrayList<>();
        for (Square squareOfPiece : findSquaresOfPieces(color)) {
            piece.add(getPiece(squareOfPiece));
        }
        return  piece;
    }

    public List<ChessPiece> findPieces(ChessPieceType type) {
        ArrayList<ChessPiece> piece = new ArrayList<>();
        for (Square squareOfPiece : findSquaresOfPieces(type)) {
            piece.add(getPiece(squareOfPiece));
        }
        return  piece;
    }

    public List<ChessPiece> findPieces(ChessPieceType type, boolean color) {
        ArrayList<ChessPiece> piece = new ArrayList<>();
        for (Square squareOfPiece : findSquaresOfPieces(type, color)) {
            piece.add(getPiece(squareOfPiece));
        }
        return  piece;
    }

    public List<Square> findSquaresOfPieces(boolean color) {
        ArrayList<Square> list = new ArrayList<>();
        for (ChessPieceType type : ChessPieceType.values()) {
            list.addAll(findSquaresOfPieces(type, color));
        }
        return list;
    }

    public List<Square> findSquaresOfPieces(ChessPieceType type) {
        ArrayList<Square> list = new ArrayList<>();
        list.addAll(findSquaresOfPieces(type, true));
        list.addAll(findSquaresOfPieces(type, false));
        return list;
    }

    public List<Square> findSquaresOfPieces(ChessPieceType type, boolean color) {
        ArrayList<Square> list = new ArrayList<>();
        for (Square square : Square.values()) {
            if (isFieldFree(square)) {
            	continue;
            }
            ChessPiece piece = getPiece(square);
            if (piece.getType() == type && piece.isWhite() == color) {
                list.add(square);
            }
        }
        return list;
    }

    public Square getSquare(ChessPiece piece) {
        for (Square square : Square.values()) {
            if (getPiece(square) == piece) {
                return square;
            }
        }
        return null;
    }

    //TODO: Eliminate clone method. Use copy constructor.
    @Override
    public ChessBoard clone() {
        ChessBoard clone = new ChessBoard();
        for (Square square : Square.values()) {
            if (!isFieldFree(square)) {
                clone.placePiece(getPiece(square).clone(), square);
            }
        }
        return clone;
    }
}
