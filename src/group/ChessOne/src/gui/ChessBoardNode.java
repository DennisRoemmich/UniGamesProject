package gui;

import engine.board.ChessBoard;
import engine.pieces.PositionedPiece;
import engine.squares.Square;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ChessBoardNode extends Group {

    private final double width = 100;
    private ChessBoard chessBoard;

    public ChessBoardNode(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        refreshNode();
    }


    public void refreshNode() {
        this.getChildren().clear();
        addBackground();
        addPieces();
    }


    private void addBackground() {
        for(Square square : Square.values()) {
            addBackgroundSquare(square);
        }
    }

    private void addBackgroundSquare(Square square) {
        int r = square.getRank().getIndex();
        int f = square.getFile().getIndex();

        Shape rectangle = new Rectangle(width, width);
        Color squareColor = ((r + f) % 2 == 0) ? Color.BLACK : Color.WHITE;
        rectangle.setFill(squareColor);

        rectangle.setLayoutX(f * width);
        rectangle.setLayoutY(r * width);

        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1);
        this.getChildren().add(rectangle);
    }

    private void addPieces() {
        for (PositionedPiece piece : chessBoard.getPositionedPieces()) {
            addPiece(piece);
        }
    }

    private void addPiece(PositionedPiece piece) {
        var pieceNode = new ChessPieceNode(piece);
        this.getChildren().add(pieceNode);
    }

    public void setBoard(ChessBoard board) {
        this.chessBoard = chessBoard;
        refreshNode();
    }
}
