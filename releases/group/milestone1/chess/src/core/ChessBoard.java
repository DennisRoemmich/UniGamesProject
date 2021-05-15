package core;

public class ChessBoard {

    final int numRows = 8;
    final int numColumns = 8;

    ChessPiece[][] pieces = new ChessPiece[numRows][numColumns];

    boolean placePiece(ChessPiece piece, int row, int column) {
        if (isPositionValid(row, column) && isFieldFree(row, column)) {
            pieces[row][column] = piece;
            return true;
        } else {
            return false;
        }
    }

    boolean removePiece(int row, int column) {
        if(!isPositionValid(row, column) || isFieldFree(row, column)) {
            return false;
        } else {
            pieces[row][column] = null;
            return true;
        }
    }

    private boolean isPositionValid(int row, int column){
        if (row < 0 || row >= numRows) {
            return false;
        }
        if (column < 0 || column >= numColumns) {
            return false;
        }
        return true;
    }

    private boolean isFieldFree(int row, int column) {
        if (!isPositionValid(row, column)) {
            return false;
        } else {
            return pieces[row][column] == null;
        }
    }

}
