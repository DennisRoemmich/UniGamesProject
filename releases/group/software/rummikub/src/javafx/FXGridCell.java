package javafx;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import rummikub_game.TileColor;

/**
 * this class is used for the cell on the board and on the racks
 */
public class FXGridCell {

    private ImageView mImgView;
    private Text mText;
    private int mValue;

    private TileColor mTileColor;

    private DoubleBinding mCellWidthProperty;


    private boolean mEmpty = true;

    /**
     * constructor
     */
    FXGridCell(ImageView view, Text text) {
        this.mImgView = view;
        this.mText = text;
        mEmpty = true;

        updateVisibility();
    }


    public void show() {

        updateVisibility();
    }

    public void clear() {

        mEmpty = true;

        updateVisibility();
    }


    /**
     * fills the cell corresponding to the given tile
     * @param tColor color of tile
     * @param value value of tile
     */
    public void fill(TileColor tColor, int value) {

        this.mValue = value;
        createText();

        mEmpty = false;
        mTileColor = tColor;

        updateVisibility();

        switch (tColor) {
            case BLACK -> mText.setFill(Color.web("#000000"));
            case BLUE -> mText.setFill(Color.web("#0000FF"));
            case RED -> mText.setFill(Color.web("#FF0000"));
            case YELLOW -> mText.setFill(Color.web("#FFA500"));
            default -> {
                mText.setText("♕"); // U+1F921
                mText.setFill(Color.web("#FF00FF"));
            }
        }

    }

    /**
     * sets width of cell
     * @param cellWidthProperty width
     */
    public void setCellWidthProperty(DoubleBinding cellWidthProperty) {

        this.mCellWidthProperty = cellWidthProperty;
    }

    /**
     * sets the value-string of the cell
     */
    private void createText() {

        mText.setText(Integer.toString(mValue));

        if(mValue >= 10 ) {
            mText.xProperty().bind(mCellWidthProperty.divide(2).subtract(mText.getLayoutBounds().getWidth()*0.45));
            mText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        } else {
            mText.xProperty().bind(mCellWidthProperty.divide(2).subtract(mText.getLayoutBounds().getWidth()*0.5));
            mText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        }

    }

    /**
     * handles visibility
     */
    public void updateVisibility() {

        if (mEmpty) {

            mImgView.setVisible(false);
            mText.setVisible(false);

        } else {

            mImgView.setVisible(true);
            mText.setVisible(true);
        }

    }

    public boolean isEmpty() {

        return mEmpty;
    }

    public int getValue() {

        return mValue;
    }

    public TileColor getTileColor() {

        return mTileColor;
    }

}
