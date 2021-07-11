package javaFX;

import engine.Card;
import engine.enums.CardValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseEvent;

public class FXCard {

    private FXController fxController;

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

    // TODO: update URL
    private static final String TURNED_DOWN_URL = ".resources/images/Cards/turned_down";
    private static final Image turnedDown = new Image(TURNED_DOWN_URL);

    // TODO: update URL
    private static final String HIGHLIGHT_URL = ".resources/images/Cards/highlight";
    private static final Image highlight = new Image(HIGHLIGHT_URL);

    // TODO: update URLs
    private static final String CLUBS_I_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String CLUBS_N_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String SPADES_I_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String SPADES_N_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String HEARTS_I_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String HEARTS_N_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String DIAMONDS_I_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String DIAMONDS_N_URL = ".resources/images/Cards/CardClubsColor.png";
    private static final String ACE_URL = ".resources/images/Cards/CardAce";
    private static final String KING_URL = ".resources/images/Cards/CardKing";
    private static final String QUEEN_URL = ".resources/images/Cards/CardQueen";
    private static final String JACK_URL = ".resources/images/Cards/CardJack";
    private static final String TEN_URL = ".resources/images/Cards/CardTen";
    private static final String NINE_URL = ".resources/images/Cards/CardNine";
    private static final String EIGHT_URL = ".resources/images/Cards/CardEight";
    private static final String SEVEN_URL = ".resources/images/Cards/CardSeven";

    private static final Image clubsI = new Image(CLUBS_I_URL);
    private static final Image clubsN = new Image(CLUBS_N_URL);
    private static final Image spadesI = new Image(SPADES_I_URL);
    private static final Image spadesN = new Image(SPADES_N_URL);
    private static final Image heartsI = new Image(HEARTS_I_URL);
    private static final Image heartsN = new Image(HEARTS_N_URL);
    private static final Image diamondsI = new Image(DIAMONDS_I_URL);
    private static final Image diamondsN = new Image(DIAMONDS_N_URL);
    private static final Image ace = new Image(ACE_URL);
    private static final Image king = new Image(KING_URL);
    private static final Image queen = new Image(QUEEN_URL);
    private static final Image jack = new Image(JACK_URL);
    private static final Image ten = new Image(TEN_URL);
    private static final Image nine = new Image(NINE_URL);
    private static final Image eight = new Image(EIGHT_URL);
    private static final Image seven = new Image(SEVEN_URL);

    /* CONSTRUCTOR */

    public FXCard(AnchorPane anchorCard, FXController fxcontroller) {

        fxController = fxcontroller;

        this.anchorCard = anchorCard;
    }

    public FXCard(Card card, int index, FXController fxcontroller) {

        fxController = fxcontroller;

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

        anchorCard.getChildren().addAll(imageCardBackground, imageCardColor, imageCardValue, imageCardHighlighted);

        for (ImageView pane : new ImageView[]{imageCardBackground, imageCardColor, imageCardColor, imageCardHighlighted}) {

            pane.setFitWidth(119);
            pane.setFitHeight(206);
            pane.setLayoutX(0);
            pane.setLayoutY(0);
        }
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

    public AnchorPane getAnchorCard() {

        return anchorCard;
    }

    /* SETTER */

    private void setImageCardColor() {

        if (card == null) {

            imageCardColor.setVisible(false);
            return;
        }

        var value = card.getCardValue();
        Image image;

        if (value == CardValue.KING || value == CardValue.QUEEN || value == CardValue.JACK) {

            image = switch (card.getCardColor()) {

                case CLUBS -> clubsI;
                case SPADES -> spadesI;
                case HEARTS -> heartsI;
                case DIAMONDS -> diamondsI;
            };

        } else {

            image = switch (card.getCardColor()) {

                case CLUBS -> clubsN;
                case SPADES -> spadesN;
                case HEARTS -> heartsN;
                case DIAMONDS -> diamondsN;
            };
        }

        imageCardColor = new ImageView(image);
    }

    private void setImageCardValue() {

        if (card == null) {

            imageCardValue.setVisible(false);
            return;
        }

        imageCardValue = new ImageView(

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

        imageCardBackground.setVisible(!isOpen);
        imageCardColor.setVisible(isOpen);
        imageCardValue.setVisible(isOpen);

        imageCardHighlighted.setVisible(isHighlighted);

        // TODO: selected
    }

    public boolean isEqualTo(Card card) {

        return this.card == card;
    }

    public void changeCard(Card card) {

        this.card = card;

        setImageCardColor();
        setImageCardValue();

        if (card == null) {

            anchorCard.setVisible(false);
        }
    }

    /*  */

    // TODO: mouseClick
    public void selectCard(MouseEvent e) {


    }
}
