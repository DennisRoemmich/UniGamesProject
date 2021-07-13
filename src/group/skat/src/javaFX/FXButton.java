package javaFX;

import console.Print;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class FXButton {

    private static FXController controller;

    ImageView imageView;
    ImageView imageViewOverlay = null;

    Image imgDefault;
    Image imgHighlighted;

    boolean highlighted = false;

    private boolean isHightlightable = true;

    String identifier;

    Node rootObject;

    Label label;

    /* STATIC */

    public static void setFXController(FXController controller){

        FXButton.controller = controller;

    }

    /* CONSTRUCTOR */

    public FXButton(String id, ImageView imgView, Image imgDefault, Image imgHighlighted){

        this.rootObject = imgView;

        this.identifier = id;
        this.imageView = imgView;
        this.imgDefault = imgDefault;
        this.imgHighlighted = imgHighlighted;

        setActions();

    }

    public FXButton(String id, ImageView imgView, Image imgDefault){

        this.rootObject = imgView;

        this.identifier = id;
        this.imageView = imgView;
        this.imgDefault = imgDefault;

        setActions();

    }

    public void setImage(Image image){

        imageView.setImage(image);


    }

    public FXButton(String id, AnchorPane anchor, Image imgDefault, Image imgHighlighted, boolean highlightWithOverlay){

        this.rootObject = anchor;

        this.identifier = id;

        ImageView imgView = null;

        if (highlightWithOverlay) {

            var firstChild = anchor.getChildren().get(0);
            var secondChild = anchor.getChildren().get(1);

            if ( firstChild.getId() != null && firstChild.getId().equals("Overlay") ) {
                this.imageViewOverlay = (ImageView) anchor.getChildren().get(0);
                imgView = (ImageView) anchor.getChildren().get(1);
            } else if ( secondChild.getId() != null && secondChild.getId().equals("Overlay") ) {
                imgView = (ImageView) anchor.getChildren().get(0);
                this.imageViewOverlay = (ImageView) anchor.getChildren().get(1);
            } else {
                Print.debug("WARNING", "Something went wrong here. None of two childs is with identifier Overlay");
            }

            imageViewOverlay.setImage(imgHighlighted);
            imageViewOverlay.setVisible(false);

        } else {

            imgView = (ImageView) anchor.getChildren().get(0);

        }

        this.imageView = imgView;
        this.imgDefault = imgDefault;
        this.imgHighlighted = imgHighlighted;

        setActions();


    }

    /* OTHER */


    private void setActions(){



        rootObject.setOnMouseClicked(mouseEvent -> controller.buttonClicked(identifier));

        rootObject.setOnMouseEntered(mouseEvent -> {

            if (!highlighted && isHightlightable) {

                if (imageViewOverlay != null) {

                    imageViewOverlay.setVisible(true);

                } else {

                    imageView.setImage(imgHighlighted);

                }

                highlighted = true;

            }

        });

        rootObject.setOnMouseExited(mouseEvent -> {

            if (highlighted) {

                if (imageViewOverlay != null) {

                    imageViewOverlay.setVisible(false);

                } else {

                    imageView.setImage(imgDefault);

                }

                highlighted = false;

            }
        });

    }

    public void hide(){
        rootObject.setVisible(false);
    }

    public void show(){
        rootObject.setVisible(true);
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










