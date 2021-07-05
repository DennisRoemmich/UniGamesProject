package javaFX;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXButton {

    ImageView imageView;

    Image imgDefault;
    Image imgHighlighted;

    /* CONSTRUCTOR */

    public FXButton(ImageView imgView, Image imgDefault, Image imgHighlighted){

        this.imageView = imgView;
        this.imgDefault = imgDefault;
        this.imgHighlighted = imgHighlighted;

    }

    public FXButton(ImageView imgView,Image imgDefault){

        this.imageView = imgView;
        this.imgDefault = imgDefault;

    }

    /* SETTER */

    public void setHighlight(boolean bool){

        if ( bool ){

            imageView.setImage(imgHighlighted);

        } else {

            imageView.setImage(imgDefault);

        }

    }


}










