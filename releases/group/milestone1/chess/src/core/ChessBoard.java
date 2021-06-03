package core;

import core.pieces.*;
<<<<<<< HEAD
import core.positioning.File;
import core.positioning.Square;
import core.positioning.Rank;
=======

import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;
>>>>>>> remotes/origin/chess01j

import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Cloneable {

    static final int NUMROWS = 8;
    static final int NUMCOLUMNS = 8;

    ChessPiece[][] pieces;
    void placePiece(ChessPiece piece, Square pos){
        placePiece(piece, pos.getRow(), pos.getColumn());
    }

    void placePiece(ChessPiece piece, Rank rank, File file) {
        pieces[rank.getIndex()][file.getIndex()] = piece;
    }

    void removePiece(Square pos) {
        removePiece(pos.getRow(), pos.getColumn());
    }

    void removePiece(Rank rank, File file) {
        pieces[rank.getIndex()][file.getIndex()] = null;
    }

    public ChessPiece getPiece(Square pos) {
        return  getPiece(pos.getRow(), pos.getColumn());
    }
    public ChessPiece getPiece(Rank rank, File file) {
        return pieces[rank.getIndex()][file.getIndex()];
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

    public boolean isFieldFree(Square pos) {
        return isFieldFree(pos.getRow(), pos.getColumn());
    }
    public boolean isFieldFree(Rank rank, File file) {
        return getPiece(rank, file) == null;
    }

    public boolean isOccupiedByOpponent(Square pos, boolean selfIsWhite) {
        return !isFieldFree(pos) && (getPiece(pos).isWhite() != selfIsWhite);
    }

    public boolean isOccupiedByOpponentOrFree(Square pos, boolean selfIsWhite) {
        return isFieldFree(pos) || isOccupiedByOpponent(pos, selfIsWhite);
    }

    public boolean isOccupiedBySelf(Square pos, boolean selfIsWhite) {
        return !isFieldFree(pos) && (getPiece(pos).isWhite() == selfIsWhite);
    }

    private ChessBoard(ChessPiece[][] pieces) {
        this.pieces = pieces;
    }

    public ChessBoard(){
        this.pieces = new ChessPiece[NUMROWS][NUMCOLUMNS];
    }

    public static ChessBoard getStartBoard() {
        ChessBoard board = new ChessBoard();
        for(File file : File.values()) {
            board.placePiece(new Pawn(true), Rank.M2, file);
            board.placePiece(new Pawn(false), Rank.M7, file);
        }
        for(File file : new File[]{File.A, File.H}){
            board.placePiece(new Rook(true), Rank.M1, file);
            board.placePiece(new Rook(false), Rank.M8, file);
        }
        for(File file : new File[]{File.B, File.G}){
            board.placePiece(new Knight(true), Rank.M1, file);
            board.placePiece(new Knight(false), Rank.M8, file);
        }
        for(File file : new File[]{File.C, File.F}){
            board.placePiece(new Bishop(true), Rank.M1, file);
            board.placePiece(new Bishop(false), Rank.M8, file);
        }
        board.placePiece(new Queen(true), Rank.M1, File.D);
        board.placePiece(new Queen(false), Rank.M8, File.D);
        board.placePiece(new King(true), Rank.M1, File.E);
        board.placePiece(new King(false), Rank.M8, File.E);
        return board;
    }

    public List<Square> findPositionsOfPieces(boolean color) {
        ArrayList<Square> list = new ArrayList<Square>();
        for(ChessPieceType type : ChessPieceType.values()) {
            list.addAll(findPositionsOfPieces(type, color));
        }
        return list;
    }

    public List<Square> findPositionsOfPieces(ChessPieceType type) {
        ArrayList<Square> list = new ArrayList<Square>();
        list.addAll(findPositionsOfPieces(type, true));
        list.addAll(findPositionsOfPieces(type, false));
        return list;
    }

    public List<Square> findPositionsOfPieces(ChessPieceType type, boolean color) {
        ArrayList<Square> list = new ArrayList<Square>();
        for(Square pos : Square.values()) {
            if(isFieldFree(pos)) continue;
            ChessPiece piece = getPiece(pos);
            if(piece.getType() == type && piece.isWhite() == color) {
                list.add(pos);
            }
        }
        return list;
    }

    @Override
    public ChessBoard clone() {
        ChessBoard clone = new ChessBoard();
        for(Square pos : Square.values()) {
            if(!isFieldFree(pos)) {
                clone.placePiece(getPiece(pos).clone(), pos);
            }
        }
        return clone;
    }
}
