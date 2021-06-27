package JavaFX;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import rummikub_game.TileColor;

public class FXGridCell {

    private ImageView imgView;
    private Text text;
    private int value;

    private TileColor tileColor;

    private DoubleBinding cellWidthProperty;


    private boolean empty = true;

    FXGridCell(ImageView view, Text text){
        this.imgView = view;
        this.text = text;
        empty = true;

        updateVisibility();
    }

    public void show() {

        updateVisibility();
    }

    public void clear() {

        empty = true;

        updateVisibility();
    }


    public void fill(TileColor tColor, int value){

        this.value = value;
        createText();

        empty = false;
        tileColor = tColor;

        updateVisibility();

        switch (tColor) {
            case BLACK -> text.setFill(Color.web("#000000"));
            case BLUE -> text.setFill(Color.web("#0000FF"));
            case RED -> text.setFill(Color.web("#FF0000"));
            case YELLOW -> text.setFill(Color.web("#FFA500"));
            default -> {
                text.setText("♕"); // U+1F921
            //    text.setText("/U1F921");
                text.setFill(Color.web("#FF00FF"));
            }
        }

    }

    public void setCellWidthProperty(DoubleBinding cellWidthProperty) {

        this.cellWidthProperty = cellWidthProperty;
    }

    private void createText() {

        text.setText(Integer.toString(value));

        if(value >= 10 ) {
            text.xProperty().bind(cellWidthProperty.divide(2).subtract(text.getLayoutBounds().getWidth()*0.45));
            text.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        } else {
            text.xProperty().bind(cellWidthProperty.divide(2).subtract(text.getLayoutBounds().getWidth()*0.5));
            text.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        }

    }

    public void updateVisibility() {

        if (empty) {

            imgView.setVisible(false);
            text.setVisible(false);

        } else {

            imgView.setVisible(true);
            text.setVisible(true);
        }

    }

    public boolean isEmpty() {

        return empty;
    }

    public int getValue() {

        return value;
    }

    public TileColor getTileColor() {

        return tileColor;
    }

}
