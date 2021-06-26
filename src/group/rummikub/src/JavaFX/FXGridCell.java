package JavaFX;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import rummikub_game.TileColor;

public class FXGridCell {

    private ImageView imgView;
    private Text text;
    private int value;

    private TileColor tileColor;


    private boolean empty = true;

    FXGridCell(ImageView view, Text text){
        this.imgView = view;
        this.text = text;
        empty = true;
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
            case YELLOW -> text.setFill(Color.web("#FFFF00"));
            default -> {
                text.setText("J");
                text.setFill(Color.web("#FF00FF"));
            }
        }

    }

    private void createText() {

        text.setText(Integer.toString(value));
    }

    private void updateVisibility() {

        if( empty ) {

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
