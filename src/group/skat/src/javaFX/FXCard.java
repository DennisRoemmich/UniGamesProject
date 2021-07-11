package javaFX;

import engine.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class FXCard {

    private boolean isOpen;
    private boolean isHighlighted;
    private boolean isSelected;

    private int index;
    private Card card;

    public AnchorPane anchorCard;

    public ImageView imageCardBackground;
    public ImageView imageCardColor;
    public ImageView imageCardValue;
    public ImageView imageCardHighlighted;

    // Images

    private static final String TURNED_DOWN_URL = ".resources/images/cards/turned_down";
    private static final Image turnedDown = new Image(TURNED_DOWN_URL);

    private static final String HIGHLIGHT_URL = ".resources/images/cards/highlight";
    private static final Image highlight = new Image(HIGHLIGHT_URL);

    private static final String CLUBS_URL = ".resources/images/cards/clubs";
    private static final String SPADES_URL = ".resources/images/cards/spades";
    private static final String HEARTS_URL = ".resources/images/cards/hearts";
    private static final String DIAMONDS_URL = ".resources/images/cards/diamonds";
    private static final String ACE_URL = ".resources/images/cards/ace";
    private static final String KING_URL = ".resources/images/cards/king";
    private static final String QUEEN_URL = ".resources/images/cards/queen";
    private static final String JACK_URL = ".resources/images/cards/jack";
    private static final String TEN_URL = ".resources/images/cards/ten";
    private static final String NINE_URL = ".resources/images/cards/nine";
    private static final String EIGHT_URL = ".resources/images/cards/eight";
    private static final String SEVEN_URL = ".resources/images/cards/seven";

    private static final Image clubs = new Image(CLUBS_URL);
    private static final Image spades = new Image(SPADES_URL);
    private static final Image hearts = new Image(HEARTS_URL);
    private static final Image diamonds = new Image(DIAMONDS_URL);
    private static final Image ace = new Image(ACE_URL);
    private static final Image king = new Image(KING_URL);
    private static final Image queen = new Image(QUEEN_URL);
    private static final Image jack = new Image(JACK_URL);
    private static final Image ten = new Image(TEN_URL);
    private static final Image nine = new Image(NINE_URL);
    private static final Image eight = new Image(EIGHT_URL);
    private static final Image seven = new Image(SEVEN_URL);

    /* CONSTRUCTOR */

    public FXCard(Card card, int index) {

        this.index = index;
        this.card = card;

        isOpen = false;
        isHighlighted = false;
        isSelected = false;

        imageCardBackground = new ImageView(turnedDown);
        imageCardBackground.setVisible(false);
        setImageCardColor();
        setImageCardValue();
        imageCardHighlighted = new ImageView(highlight);
        imageCardHighlighted.setVisible(false);

        imageCardBackground.setFitWidth(119);
        imageCardBackground.setFitWidth(206);
        imageCardColor.setFitWidth(119);
        imageCardColor.setFitWidth(206);
        imageCardValue.setFitWidth(119);
        imageCardValue.setFitWidth(206);

        anchorCard.getChildren().addAll(imageCardBackground, imageCardColor, imageCardValue, imageCardHighlighted);
    }

    /* GETTER */

    public int getIndex() {

        return index;
    }

    public boolean isOpen() {

        return isOpen;
    }

    public boolean isHighlighted() {

        return isHighlighted;
    }

    public boolean isSelected() {

        return isSelected;
    }

    /* SETTER */

    private void setImageCardColor() {

        imageCardColor = new ImageView(

                switch (card.getCardValue()) {

                    case ACE -> ace;
                    case KING -> king;
                    case QUEEN -> queen;
                    case JACK -> jack;
                    case TEN -> ten;
                    case NINE -> nine;
                    case EIGHT -> eight;
                    case SEVEN -> seven;
                }
        );
    }

    private void setImageCardValue() {

        imageCardValue = new ImageView(

                switch (card.getCardColor()) {

                    case CLUBS -> clubs;
                    case SPADES -> spades;
                    case HEARTS -> hearts;
                    case DIAMONDS -> diamonds;
                }
        );
    }

    public void setOpen(boolean open) {

        isOpen = open;
    }

    public void setHighlighted(boolean highlighted) {

        isHighlighted = highlighted;
    }

    public void setSelected(boolean selected) {

        isSelected = selected;
    }

    /* OTHER */

    public void update() {

        if (isOpen) {

            imageCardBackground.setVisible(false);
            imageCardColor.setVisible(true);
            imageCardValue.setVisible(true);

        } else {

            imageCardBackground.setImage(turnedDown);
            imageCardColor.setVisible(false);
            imageCardValue.setVisible(false);

            isHighlighted = false;
            isSelected = false;
        }

        if (isHighlighted) {

            imageCardHighlighted.setVisible(true);
        }
    }

    public boolean isEqualTo(Card card) {

        return this.card == card;
    }
}
