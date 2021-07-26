package gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class BurglarNode extends Group {
    private ImageView mImageView;

    public BurglarNode() {
        ClassLoader loader = getClass().getClassLoader();
        Image image = new Image(loader.getResourceAsStream("resources/DaltonsSmall.png"));
        mImageView = new ImageView(image);
        double scaleFactor = 0.2;
        mImageView.setScaleX(scaleFactor);
        mImageView.setScaleY(scaleFactor);
        mImageView.setScaleZ(scaleFactor);
        mImageView.setLayoutX(-90);
        mImageView.setLayoutY(-65);
        getChildren().add(mImageView);
    }
}
