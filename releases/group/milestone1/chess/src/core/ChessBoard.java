package core;


import core.pieces.*;
import core.positioning.File;
import core.positioning.Rank;
import core.positioning.Square;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Cloneable {

    ChessPiece[][] pieces;

    void placePiece(ChessPiece piece, Square square){
        pieces[square.getRank().getIndex()][square.getFile().getIndex()] = piece;
    }

    void removePiece(Square square) {
        pieces[square.getRank().getIndex()][square.getFile().getIndex()] = null;
    }

    public ChessPiece getPiece(Square square) {
        return pieces[square.getRank().getIndex()][square.getFile().getIndex()];
    }

    void movePiece(Square origin, Square destination) {
        ChessPiece piece = getPiece(origin);
        if(piece != null) {
            placePiece(piece, destination);
            removePiece(origin);
        }
        
        //Rochade
        //      if(piece.getType() )
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

    private ChessBoard(ChessPiece[][] pieces) {
        this.pieces = pieces;
    }

    public ChessBoard(){
        this.pieces = new ChessPiece[Rank.values().length][File.values().length];
    }

    public static ChessBoard getStartBoard() {
        ChessBoard board = new ChessBoard();
        for(File file : File.values()) {
            board.placePiece(new Pawn(true), new Square(Rank.M2, file));
            board.placePiece(new Pawn(false), new Square(Rank.M7, file));
        }
        for(File file : new File[]{File.A, File.H}){
            board.placePiece(new Rook(true), new Square(Rank.M1, file));
            board.placePiece(new Rook(false), new Square(Rank.M8, file));
        }
        for(File file : new File[]{File.B, File.G}){
            board.placePiece(new Knight(true), new Square(Rank.M1, file));
            board.placePiece(new Knight(false), new Square(Rank.M8, file));
        }
        for(File file : new File[]{File.C, File.F}){
            board.placePiece(new Bishop(true), new Square(Rank.M1, file));
            board.placePiece(new Bishop(false), new Square(Rank.M8, file));
        }
        board.placePiece(new Queen(true), new Square(Rank.M1, File.D));
        board.placePiece(new Queen(false), new Square(Rank.M8, File.D));
        board.placePiece(new King(true), new Square(Rank.M1, File.E));
        board.placePiece(new King(false), new Square(Rank.M8, File.E));
        return board;
    }

    public List<Square> findPositionsOfPieces(boolean color) {
        ArrayList<Square> list = new ArrayList<>();
        for(ChessPieceType type : ChessPieceType.values()) {
            list.addAll(findPositionsOfPieces(type, color));
        }
        return list;
    }

    public List<Square> findPositionsOfPieces(ChessPieceType type) {
        ArrayList<Square> list = new ArrayList<>();
        list.addAll(findPositionsOfPieces(type, true));
        list.addAll(findPositionsOfPieces(type, false));
        return list;
    }

    public List<Square> findPositionsOfPieces(ChessPieceType type, boolean color) {
        ArrayList<Square> list = new ArrayList<>();
        for(Square square : Square.values()) {
            if(isFieldFree(square)) continue;
            ChessPiece piece = getPiece(square);
            if(piece.getType() == type && piece.isWhite() == color) {
                list.add(square);
            }
        }
        return list;
    }

    //Use super.clone() to create and seed the cloned instance to be returned.
    @Override
    public ChessBoard clone() {
        ChessBoard clone = new ChessBoard();
        for(Square square : Square.values()) {
            if(!isFieldFree(square)) {
                clone.placePiece(getPiece(square).clone(), square);
            }
        }
        return clone;
    }
}
