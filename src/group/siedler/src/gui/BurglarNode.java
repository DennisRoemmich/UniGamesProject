package gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import map.BuildRules;


public class BurglarNode extends Group {
    private ImageView imageView;

    public BurglarNode() {
        Image image = new Image(getClass().getResourceAsStream("/resources/DaltonsSmall.png"));
        imageView = new ImageView(image);
        double scaleFactor = 0.2;
        imageView.setScaleX(scaleFactor);
        imageView.setScaleY(scaleFactor);
        imageView.setScaleZ(scaleFactor);
        imageView.setLayoutX(-90);
        imageView.setLayoutY(-65);
        getChildren().add(imageView);
    }
}
