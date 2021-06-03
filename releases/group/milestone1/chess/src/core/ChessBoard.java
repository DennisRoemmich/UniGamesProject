package core;

import core.pieces.*;

import core.positioning.Column;
import core.positioning.Position;
import core.positioning.Row;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard implements Cloneable {

    static final int NUMROWS = 8;
    static final int NUMCOLUMNS = 8;

    ChessPiece[][] pieces;
    void placePiece(ChessPiece piece, Position pos){
        placePiece(piece, pos.getRow(), pos.getColumn());
    }

    void placePiece(ChessPiece piece, Row row, Column column) {
        pieces[row.getIndex()][column.getIndex()] = piece;
    }

    void removePiece(Position pos) {
        removePiece(pos.getRow(), pos.getColumn());
    }

    void removePiece(Row row, Column column) {
        pieces[row.getIndex()][column.getIndex()] = null;
    }

    public ChessPiece getPiece(Position pos) {
        return  getPiece(pos.getRow(), pos.getColumn());
    }
    public ChessPiece getPiece(Row row, Column column) {
        return pieces[row.getIndex()][column.getIndex()];
    }

    void movePiece(Position origin, Position destination, int moveNumber) {
        ChessPiece piece = getPiece(origin);
        if(piece != null) {
            placePiece(piece, destination);
            removePiece(origin);
        }
        
        //Rochade
  //      if(piece.getType() )
    }

    public boolean isFieldFree(Position pos) {
        return isFieldFree(pos.getRow(), pos.getColumn());
    }
    public boolean isFieldFree(Row row, Column column) {
        return getPiece(row, column) == null;
    }

    public boolean isOccupiedByOpponent(Position pos, boolean selfIsWhite) {
        return !isFieldFree(pos) && (getPiece(pos).isWhite() != selfIsWhite);
    }

    public boolean isOccupiedByOpponentOrFree(Position pos, boolean selfIsWhite) {
        return isFieldFree(pos) || isOccupiedByOpponent(pos, selfIsWhite);
    }

    public boolean isOccupiedBySelf(Position pos, boolean selfIsWhite) {
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
        for(Column column : Column.values()) {
            board.placePiece(new Pawn(true), Row.M2, column);
            board.placePiece(new Pawn(false), Row.M7, column);
        }
        for(Column column : new Column[]{Column.A, Column.H}){
            board.placePiece(new Rook(true), Row.M1, column);
            board.placePiece(new Rook(false), Row.M8, column);
        }
        for(Column column : new Column[]{Column.B, Column.G}){
            board.placePiece(new Knight(true), Row.M1, column);
            board.placePiece(new Knight(false), Row.M8, column);
        }
        for(Column column : new Column[]{Column.C, Column.F}){
            board.placePiece(new Bishop(true), Row.M1, column);
            board.placePiece(new Bishop(false), Row.M8, column);
        }
        board.placePiece(new Queen(true), Row.M1, Column.D);
        board.placePiece(new Queen(false), Row.M8, Column.D);
        board.placePiece(new King(true), Row.M1, Column.E);
        board.placePiece(new King(false), Row.M8, Column.E);
        return board;
    }

    public List<Position> findPositionsOfPieces(boolean color) {
        ArrayList<Position> list = new ArrayList<Position>();
        for(ChessPieceType type : ChessPieceType.values()) {
            list.addAll(findPositionsOfPieces(type, color));
        }
        return list;
    }

    public List<Position> findPositionsOfPieces(ChessPieceType type) {
        ArrayList<Position> list = new ArrayList<Position>();
        list.addAll(findPositionsOfPieces(type, true));
        list.addAll(findPositionsOfPieces(type, false));
        return list;
    }

    public List<Position> findPositionsOfPieces(ChessPieceType type, boolean color) {
        ArrayList<Position> list = new ArrayList<Position>();
        for(Position pos : Position.values()) {
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
        for(Position pos : Position.values()) {
            if(!isFieldFree(pos)) {
                clone.placePiece(getPiece(pos).clone(), pos);
            }
        }
        return clone;
    }
}
