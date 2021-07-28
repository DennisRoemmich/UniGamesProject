package gui;

import engine.Controller;
import engine.board.ChessBoard;
import engine.pieces.PositionedPiece;
import engine.squares.Square;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardNode extends Group {

    private final double width = 100;
    private Controller controller;
    private GuiEventHandler eventHandler;

    private List<Square> placeholders = new ArrayList<>();

    public ChessBoardNode(Controller controller, GuiEventHandler eventHandler) {
        this.controller = controller;
        this.eventHandler = eventHandler;
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
        Color squareColor = ((r + f) % 2 == 0) ? Color.SADDLEBROWN : Color.WHITE;

        for(Square placeholderSquare : placeholders) {
            if(placeholderSquare.equals(square)) {
                squareColor = Color.DARKGREEN;
            }
        }

        rectangle.setFill(squareColor);

        rectangle.setLayoutX(f * width);
        rectangle.setLayoutY((7 - r) * width);

        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1);

        rectangle.setOnMouseClicked(new SquareEventHandler(eventHandler, square));

        this.getChildren().add(rectangle);
    }

    private void addPieces() {
        for (PositionedPiece piece : getPositionedPieces()) {
            addPiece(piece);
        }
    }

    private void addPiece(PositionedPiece piece) {
        var pieceNode = new ChessPieceNode(piece);

        if(eventHandler != null) {
            pieceNode.setOnMouseClicked(new PieceEventHandler(eventHandler, piece));
        }

        this.getChildren().add(pieceNode);
    }

    public void addPlaceholder(Square... squares) {
        for(Square square : squares) {
            placeholders.add(square);
        }
    }

    public void resetPlaceholder() {
        placeholders.clear();
    }

    private List<PositionedPiece> getPositionedPieces() {
        if(controller.getGame() != null) {
            return controller.getGame().getBoard().getPositionedPieces();
        } else {
            return new ArrayList<>();
        }
    }
}
