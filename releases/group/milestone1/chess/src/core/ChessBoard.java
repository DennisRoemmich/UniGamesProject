package core;

public class ChessBoard {

    final int numRows = 8;
    final int numColumns = 8;

    ChessPiece[][] pieces = new ChessPiece[numRows][numColumns];
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

    public static ChessBoard getStartBoard() {
        ChessBoard board = new ChessBoard();
        for(Column column : Column.values()) {
            board.placePiece(new Pawn(true), Row.M2, column);
            board.placePiece(new Pawn(false), Row.M7, column);
        }
        /*
        for(Column column : new Column[]{Column.A, Column.H}){
            board.placePiece(new ChessPiece(ChessPieceType.ROOK, true), Row.M1, column);
            board.placePiece(new ChessPiece(ChessPieceType.ROOK, false), Row.M8, column);
        }
        for(Column column : new Column[]{Column.B, Column.G}){
            board.placePiece(new ChessPiece(ChessPieceType.KNIGHT, true), Row.M1, column);
            board.placePiece(new ChessPiece(ChessPieceType.KNIGHT, false), Row.M8, column);
        }
        for(Column column : new Column[]{Column.C, Column.F}){
            board.placePiece(new ChessPiece(ChessPieceType.BISHOP, true), Row.M1, column);
            board.placePiece(new ChessPiece(ChessPieceType.BISHOP, false), Row.M8, column);
        }
        board.placePiece(new ChessPiece(ChessPieceType.QUEEN, true), Row.M1, Column.D);
        board.placePiece(new ChessPiece(ChessPieceType.QUEEN, false), Row.M8, Column.D);
        board.placePiece(new ChessPiece(ChessPieceType.KING, true), Row.M1, Column.E);
        board.placePiece(new ChessPiece(ChessPieceType.KING, false), Row.M8, Column.E);*/
        return board;
    }

}
