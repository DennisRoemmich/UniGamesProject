package gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the node on a tile where the burglar can be placed.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public class BurglarNode extends Group {
    private ImageView mImageView;

    public BurglarNode() {
        ClassLoader loader = getClass().getClassLoader();
        Image image = new Image(loader.getResourceAsStream("resources/BurglarIcon.png"));
        mImageView = new ImageView(image);
        double scaleFactor = 0.18;
        mImageView.setScaleX(scaleFactor);
        mImageView.setScaleY(scaleFactor);
        mImageView.setScaleZ(scaleFactor);
        mImageView.setLayoutX(-100);
        mImageView.setLayoutY(-22);
        getChildren().add(mImageView);
    }
}
