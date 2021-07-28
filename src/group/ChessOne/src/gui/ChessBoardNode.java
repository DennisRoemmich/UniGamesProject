package gui;

import engine.Controller;
import engine.pieces.PositionedPiece;
import engine.squares.Square;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChessBoardNode extends Group {

    private static final double WIDTH = 100;
    private Controller mController;
    private GuiEventHandler mEventHandler;

    private List<Square> mPlaceholders = new ArrayList<>();

    public ChessBoardNode(Controller controller, GuiEventHandler eventHandler) {
        this.mController = controller;
        this.mEventHandler = eventHandler;
        refreshNode();
    }


    public void refreshNode() {
        this.getChildren().clear();
        addBackground();
        addPieces();
    }


    private void addBackground() {
        for (Square square : Square.values()) {
            addBackgroundSquare(square);
        }
    }

    @SuppressWarnings("unchecked")
	private void addBackgroundSquare(Square square) {
        int r = square.getRank().getIndex();
        int f = square.getFile().getIndex();

        Shape rectangle = new Rectangle(WIDTH, WIDTH);
        Color squareColor = ((r + f) % 2 == 0) ? Color.SADDLEBROWN : Color.WHITE;

        for (Square placeholderSquare : mPlaceholders) {
            if (placeholderSquare.equals(square)) {
                squareColor = Color.DARKGREEN;
            }
        }

        rectangle.setFill(squareColor);

        rectangle.setLayoutX(f * WIDTH);
        rectangle.setLayoutY((7 - r) * WIDTH);

        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(1);

        rectangle.setOnMouseClicked(new SquareEventHandler(mEventHandler, square));

        this.getChildren().add(rectangle);
    }

    private void addPieces() {
        for (PositionedPiece piece : getPositionedPieces()) {
            addPiece(piece);
        }
    }

    @SuppressWarnings("unchecked")
	private void addPiece(PositionedPiece piece) {
        var pieceNode = new ChessPieceNode(piece);

        if (mEventHandler != null) {
            pieceNode.setOnMouseClicked(new PieceEventHandler(mEventHandler, piece));
        }

        this.getChildren().add(pieceNode);
    }

    public void addPlaceholder(Square... squares) {
        squares = Arrays.copyOf(squares, squares.length);
        Collections.addAll(this.mPlaceholders, squares);
    }

    public void resetPlaceholder() {
        mPlaceholders.clear();
    }

    private List<PositionedPiece> getPositionedPieces() {
        if (mController.getGame() != null) {
            return mController.getGame().getBoard().getPositionedPieces();
        } else {
            return new ArrayList<>();
        }
    }
}
