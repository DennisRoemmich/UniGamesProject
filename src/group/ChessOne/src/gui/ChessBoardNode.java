package gui;

import engine.board.ChessBoard;
import engine.pieces.PositionedPiece;
import engine.squares.Square;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.*;

/**
 * A node representing a square on the board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class ChessBoardNode extends Group {

    private static final double WIDTH = 100;
    private Optional<ChessBoard> mBoard = Optional.empty();
    private GuiEventHandler mEventHandler;

    private List<Square> mPlaceholders = new ArrayList<>();

    public ChessBoardNode(GuiEventHandler eventHandler) {
        this.mEventHandler = eventHandler;
        refreshNode();
    }

    public void refreshNode() {
        this.getChildren().clear();
        addBackground();
        if (mBoard.isPresent()) {
            addPieces(mBoard.get());
        }
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

    private void addPieces(ChessBoard board) {
        ChessBoard boardClone = new ChessBoard(board);
        for (PositionedPiece piece : boardClone.getPositionedPieces()) {
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

    public Optional<ChessBoard> getBoard() {
        return mBoard;
    }

    public void setBoard(Optional<ChessBoard> board) {
        this.mBoard = board;
    }
}
