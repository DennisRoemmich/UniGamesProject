package gui;

import engine.board.ChessBoard;
import javafx.scene.layout.AnchorPane;

public class ChessGUI extends AnchorPane {
    private ChessBoardNode boardNode;

    public ChessGUI() {
        boardNode = new ChessBoardNode(ChessBoard.getStartBoard());
        boardNode.setLayoutX(20);
        boardNode.setLayoutY(25);
        this.getChildren().add(boardNode);
    }

    public void setBoard(ChessBoard board) {
        boardNode.setBoard(board);
    }
}
