package javaFX;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class FXButton {

    public static FXController controller;

    ImageView imageView;

    Image imgDefault;
    Image imgHighlighted;

    Label label;

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

    public FXButton(Label label){



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










