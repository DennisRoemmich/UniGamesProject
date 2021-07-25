package javaFX;

import console.Print;
import engine.Card;
import engine.enums.CardValue;
import javaFX.enums.FXCardPosition;
import javaFX.enums.FXHandShelfPosition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FXCard {

    private FXController fxController;

    private boolean isOpen;
    private boolean isHighlighted;
    private boolean isSelected;

    private FXCardPosition position;
    private int index;
    private Card card;

    public AnchorPane anchorCard;

    public ImageView imageCardBackground;
    public ImageView imageCardColor;
    public ImageView imageCardValue;
    public ImageView imageCardHighlighted;

    // Images

    private static final String TURNED_DOWN_URL = "images/Cards/CardBack.png";
    private static final Image turnedDown = new Image(TURNED_DOWN_URL);

    private static final String HIGHLIGHT_URL = "images/Cards/CardHighlighted.png";
    private static final Image highlight = new Image(HIGHLIGHT_URL);

    private static final String CLUBS_N_URL = "images/Cards/CardClubsColor.png";
    private static final String CLUBS_I_URL = "images/Cards/CardClubsEmpty.png";
    private static final String SPADES_N_URL = "images/Cards/CardSpadesColor.png";
    private static final String SPADES_I_URL = "images/Cards/CardSpadesEmpty.png";
    private static final String HEARTS_N_URL = "images/Cards/CardHeartsColor.png";
    private static final String HEARTS_I_URL = "images/Cards/CardHeartsEmpty.png";
    private static final String DIAMONDS_N_URL = "images/Cards/CardDiamondsColor.png";
    private static final String DIAMONDS_I_URL = "images/Cards/CardDiamondsEmpty.png";
    private static final String ACE_URL = "images/Cards/CardAce.png";
    private static final String KING_URL = "images/Cards/CardKing.png";
    private static final String QUEEN_URL = "images/Cards/CardQueen.png";
    private static final String JACK_URL = "images/Cards/CardJack.png";
    private static final String TEN_URL = "images/Cards/Card10.png";
    private static final String NINE_URL = "images/Cards/Card9.png";
    private static final String EIGHT_URL = "images/Cards/Card8.png";
    private static final String SEVEN_URL = "images/Cards/Card7.png";

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

    public FXCard(AnchorPane anchorCard, FXCardPosition pos, int index, FXController fxcontroller) {

        fxController = fxcontroller;

        this.anchorCard = anchorCard;
        this.anchorCard.setVisible(true);

        position = pos;
        this.index = index;

        init();
        update();
    }

    public FXCard(int index, FXController fxcontroller, FXHandShelfPosition pos) {

        fxController = fxcontroller;
        anchorCard = new AnchorPane();
        anchorCard.setVisible(true);

        position = switch (pos) {

            case LEFT_PLAYER -> FXCardPosition.HANDSHELF_LEFT;
            case MID_PLAYER -> FXCardPosition.HANDSHELF_MID;
            case RIGHT_PLAYER -> FXCardPosition.HANDSHELF_RIGHT;
        };
        this.index = index;
        this.card = null;

        isOpen = false;
        isHighlighted = false;
        isSelected = false;

        init();
        update();
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

    public boolean isEmpty() {

        return card == null;
    }

    public Card getCard() {

        return card;
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

        imageCardColor.setImage(image);
        imageCardColor.setVisible(true);
    }

    private void setImageCardValue() {

        if (card == null) {

            imageCardValue.setVisible(false);
            return;
        }

        imageCardValue.setImage(

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

        imageCardValue.setVisible(true);
    }

    public void setOpen(boolean open) {

        isOpen = open;
    }

    public void setHighlighted(boolean highlighted) {

        isHighlighted = highlighted;
    }

    public void setSelected(boolean selected) {

        isSelected = selected;
        update();
    }

    /* OTHER */

    private void init() {

        imageCardColor = new ImageView();
        imageCardValue = new ImageView();

        imageCardBackground = new ImageView(turnedDown);
        imageCardBackground.setVisible(false);
        imageCardHighlighted = new ImageView(highlight);
        imageCardHighlighted.setVisible(false);

        for (ImageView pane : new ImageView[]{imageCardBackground, imageCardColor, imageCardValue, imageCardHighlighted}) {

            anchorCard.getChildren().add(pane);

            if (position == FXCardPosition.SKAT || position == FXCardPosition.TRICK || position == FXCardPosition.PREVIEW) {

                pane.setFitWidth(anchorCard.getWidth());
                pane.setFitHeight(anchorCard.getHeight());

            } else {
                pane.setFitWidth(119);
                pane.setFitHeight(206);
            }
            pane.setLayoutX(0);
            pane.setLayoutY(0);
        }

        anchorCard.setOnMouseClicked(mouseEvent -> fxController.fxCardClicked(position, index));
    }

    public void update() {

        isOpen = switch (position) {

            case HANDSHELF_MID, SKAT, TRICK, PREVIEW -> true;
            case HANDSHELF_LEFT, HANDSHELF_RIGHT, TRICKS_DECLARER, TRICKS_OPPONENTS -> false;
        };

        isOpen = position != FXCardPosition.HANDSHELF_RIGHT;
        var notnull = card != null;

        imageCardBackground.setVisible(!isOpen && notnull);
        imageCardColor.setVisible(isOpen && notnull);
        imageCardValue.setVisible(isOpen && notnull);
        imageCardHighlighted.setVisible(isSelected && notnull);


    //    imageCardHighlighted.setVisible(isHighlighted);

        // TODO: highlighted


    }

    public boolean isEqualTo(Card card) {

        return this.card == card;
    }

    public void changeCard(Card card) {

        this.card = card;

        setImageCardColor();
        setImageCardValue();

        update();
    }

    public void removeCard() {

        card = null;
        update();
    }

}
