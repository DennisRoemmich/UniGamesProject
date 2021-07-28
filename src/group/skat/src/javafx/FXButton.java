package javafx;

import console.Print;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * class for the buttons in GUI, with all its layouts, actions etc.
 */
public class FXButton {

    private static FXController mController;

    private ImageView mImageView;
    private ImageView mImageViewOverlay = null;

    private Image mImgDefault;
    private Image mImgHighlighted;

    private boolean mHighlighted = false;

    private boolean mIsHightlightable = true;

    private String mIdentifier;

    private Node mRootObject;


    /* STATIC */

    public static void setFXController(FXController controller) {

        FXButton.mController = controller;

    }

    /* CONSTRUCTOR */

    /**
     * the constructors handle the layouts and actions of the buttons
     */

    public FXButton(String id, ImageView imgView, Image imgDefault, Image imgHighlighted) {

        this.mRootObject = imgView;

        this.mIdentifier = id;
        this.mImageView = imgView;
        this.mImgDefault = imgDefault;
        this.mImgHighlighted = imgHighlighted;

        setActions();
        mImageView.setVisible(true);

    }

    public FXButton(String id, ImageView imgView, Image imgDefault) {

        this.mRootObject = imgView;

        this.mIdentifier = id;
        this.mImageView = imgView;
        this.mImgDefault = imgDefault;

        setActions();
        mImageView.setVisible(true);

    }

    public FXButton(String id, AnchorPane anchor, Image imgDefault, Image imgHighlighted, boolean highlightWithOverlay) {

        this.mRootObject = anchor;

        this.mIdentifier = id;

        ImageView imgView = null;

        if (highlightWithOverlay) {

            var firstChild = anchor.getChildren().get(0);
            var secondChild = anchor.getChildren().get(1);

            if ( firstChild.getId() != null && firstChild.getId().equals("Overlay") ) {
                this.mImageViewOverlay = (ImageView) anchor.getChildren().get(0);
                imgView = (ImageView) anchor.getChildren().get(1);
            } else if ( secondChild.getId() != null && secondChild.getId().equals("Overlay") ) {
                imgView = (ImageView) anchor.getChildren().get(0);
                this.mImageViewOverlay = (ImageView) anchor.getChildren().get(1);
            } else {
                Print.debug("WARNING", "Something went wrong here. None of two childs is with identifier Overlay");
            }

            mImageViewOverlay.setImage(imgHighlighted);
            mImageViewOverlay.setVisible(false);

        } else {

            imgView = (ImageView) anchor.getChildren().get(0);

        }

        this.mImageView = imgView;
        this.mImgDefault = imgDefault;
        this.mImgHighlighted = imgHighlighted;

        setActions();
        mImageView.setVisible(true);


    }

    /* OTHER */

    /**
     * 2 layers of images, one default and one highlighted, if a button is dragged over
     * @param images images
     */
    public void setImages(Image[] images) {

        mImgDefault = images[0];
        mImgHighlighted = images[1];

        if (mHighlighted) {

            mImageView.setImage(mImgHighlighted);

        } else {

            mImageView.setImage(mImgDefault);

        }



    }


    public void setImage(Image image) {

        mImageView.setImage(image);

    }

    /**
     * sets the actions of every button
     */
    private void setActions() {



        mRootObject.setOnMouseClicked(mouseEvent -> mController.buttonClicked(mIdentifier));

        mRootObject.setOnMouseEntered(mouseEvent -> {

            if (!mHighlighted && mIsHightlightable) {

                if (mImageViewOverlay != null) {

                    mImageViewOverlay.setVisible(true);

                } else {

                    mImageView.setImage(mImgHighlighted);

                }

                mHighlighted = true;

            }

        });

        mRootObject.setOnMouseExited(mouseEvent -> {

            if (mHighlighted) {

                if (mImageViewOverlay != null) {

                    mImageViewOverlay.setVisible(false);

                } else {

                    mImageView.setImage(mImgDefault);

                }

                mHighlighted = false;

            }
        });

    }

    /**
     * hides a button
     */
    public void hide() {

        mRootObject.setVisible(false);
    }

    /**
     * shows a button
     */
    public void show() {

        mRootObject.setVisible(true);
        mImageView.setVisible(true);
    }


    /* SETTER */

    public void setHighlight(boolean bool) {

        if (bool) {

            mImageView.setImage(mImgHighlighted);

        } else {

            mImageView.setImage(mImgDefault);

        }

    }


}










