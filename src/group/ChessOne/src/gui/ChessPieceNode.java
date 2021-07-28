package gui;

import engine.pieces.PositionedPiece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class ChessPieceNode extends Region {
    private PositionedPiece positionedPiece;
    private ImageView imageView;
    private ClassLoader classLoader = getClass().getClassLoader();

    public ChessPieceNode(PositionedPiece positionedPiece) {
        this.positionedPiece = positionedPiece;
        setupImageView();
        refreshNode();
    }

    private void setupImageView() {
        Image image = new Image(classLoader.getResourceAsStream(positionedPiece.getPiece() + ".png"));
        imageView = new ImageView(image);
        imageView.setScaleX(2);
        imageView.setScaleY(2);
        this.getChildren().add(imageView);
    }

    public void refreshNode() {
        int x = 25 + 100 * positionedPiece.getPosition().getFile().getIndex();
        int y = 25 + 100 * (7 - positionedPiece.getPosition().getRank().getIndex());
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }
}
