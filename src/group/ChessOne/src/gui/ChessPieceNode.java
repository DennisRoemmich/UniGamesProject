package gui;

import engine.pieces.PositionedPiece;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessPieceNode extends Group {
    private PositionedPiece mPositionedPiece;
    private ImageView mImageView;
    private ClassLoader mClassLoader = getClass().getClassLoader();

    public ChessPieceNode(PositionedPiece positionedPiece) {
        this.mPositionedPiece = positionedPiece;
        setupImageView();
        refreshNode();
    }

    private void setupImageView() {
        Image image = new Image(mClassLoader.getResourceAsStream("resources/" + mPositionedPiece.getPiece() + ".png"));
        mImageView = new ImageView(image);
        mImageView.setScaleX(2);
        mImageView.setScaleY(2);
        this.getChildren().add(mImageView);
    }

    public void refreshNode() {
        int x = 25 + 100 * mPositionedPiece.getPosition().getFile().getIndex();
        int y = 25 + 100 * (7 - mPositionedPiece.getPosition().getRank().getIndex());
        mImageView.setLayoutX(x);
        mImageView.setLayoutY(y);
    }

}
