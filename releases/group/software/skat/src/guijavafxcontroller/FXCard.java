package guijavafxcontroller;

import guiengine.Card;
import guiengine.enums.CardValue;
import guijavafxcontroller.enums.FXCardPosition;
import guijavafxcontroller.enums.FXHandShelfPosition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * class for the cards in the GUI
 */
public class FXCard {

    private FXController mFxController;

    private boolean mIsOpen;
    private boolean mIsHighlighted;
    private boolean mIsSelected;

    private FXCardPosition mPosition;
    private int mIndex;
    private Card mCard;

    private AnchorPane mAnchorCard;

    private ImageView mImageCardBackground;
    private ImageView mImageCardColor;
    private ImageView mImageCardValue;
    private ImageView mImageCardHighlighted;

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

    /**
     * constructor for card at skat or current trick
     * @param anchorCard anchorPane for either skat or currentTrick
     * @param pos position of card
     * @param index index of card
     * @param fxcontroller fxcontroller
     */
    public FXCard(AnchorPane anchorCard, FXCardPosition pos, int index, FXController fxcontroller) {

        mFxController = fxcontroller;

        this.mAnchorCard = anchorCard;
        this.mAnchorCard.setVisible(true);

        mPosition = pos;
        this.mIndex = index;

        init();
        update();
    }

    /**
     * constructor for card at a handshelf
     * @param index index of card
     * @param fxcontroller fxcontrolle
     * @param pos position of card
     */
    public FXCard(int index, FXController fxcontroller, FXHandShelfPosition pos) {

        mFxController = fxcontroller;
        mAnchorCard = new AnchorPane();
        mAnchorCard.setVisible(true);

        mPosition = switch (pos) {

            case LEFT_PLAYER -> FXCardPosition.HANDSHELF_LEFT;
            case MID_PLAYER -> FXCardPosition.HANDSHELF_MID;
            case RIGHT_PLAYER -> FXCardPosition.HANDSHELF_RIGHT;
        };
        this.mIndex = index;
        this.mCard = null;

        mIsOpen = false;
        mIsHighlighted = false;
        mIsSelected = false;

        init();
        update();
    }

    /* GETTER */

    /**
     * @return index of card
     */
    public int getIndex() {

        return mIndex;
    }

    /**
     * @return true if card is open, false if front page is not visible (turned around)
     */
    public boolean isOpen() {

        return mIsOpen;
    }

    /**
     * @return true if highlighted, false if not
     */
    public boolean isHighlighted() {

        return mIsHighlighted;
    }

    /**
     * @return true if selected, false if not
     */
    public boolean isSelected() {

        return mIsSelected;
    }

    /**
     * @return anchorcard of card
     */
    public AnchorPane getAnchorCard() {

        return mAnchorCard;
    }

    /**
     * @return true if empty, false if not
     */
    public boolean isEmpty() {

        return mCard == null;
    }

    /**
     * @return card
     */
    public Card getCard() {

        return mCard;
    }

    /* SETTER */

    /**
     * sets image of card color
     */
    private void setImageCardColor() {

        if (mCard == null) {

            mImageCardColor.setVisible(false);
            return;
        }

        var value = mCard.getCardValue();
        Image image;

        if (value == CardValue.KING || value == CardValue.QUEEN || value == CardValue.JACK) {

            image = switch (mCard.getCardColor()) {

                case CLUBS -> clubsI;
                case SPADES -> spadesI;
                case HEARTS -> heartsI;
                case DIAMONDS -> diamondsI;
            };

        } else {

            image = switch (mCard.getCardColor()) {

                case CLUBS -> clubsN;
                case SPADES -> spadesN;
                case HEARTS -> heartsN;
                case DIAMONDS -> diamondsN;
            };
        }

        mImageCardColor.setImage(image);
        mImageCardColor.setVisible(true);
    }

    /**
     * sets image of card value
     */
    private void setImageCardValue() {

        if (mCard == null) {

            mImageCardValue.setVisible(false);
            return;
        }

        mImageCardValue.setImage(

                switch (mCard.getCardValue()) {

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

        mImageCardValue.setVisible(true);
    }

    public void setOpen(boolean open) {

        mIsOpen = open;
    }

    public void setHighlighted(boolean highlighted) {

        mIsHighlighted = highlighted;
    }

    public void setSelected(boolean selected) {

        mIsSelected = selected;
        update();
    }

    /* OTHER */

    /**
     * initialises the size, actions etc. of the card
     */
    private void init() {

        mImageCardColor = new ImageView();
        mImageCardValue = new ImageView();

        mImageCardBackground = new ImageView(turnedDown);
        mImageCardBackground.setVisible(false);
        mImageCardHighlighted = new ImageView(highlight);
        mImageCardHighlighted.setVisible(false);

        for (ImageView pane : new ImageView[]{mImageCardBackground, mImageCardColor, mImageCardValue, mImageCardHighlighted}) {

            mAnchorCard.getChildren().add(pane);

            if (mPosition == FXCardPosition.SKAT || mPosition == FXCardPosition.TRICK || mPosition == FXCardPosition.PREVIEW) {

                pane.setFitWidth(mAnchorCard.getWidth());
                pane.setFitHeight(mAnchorCard.getHeight());

            } else {
                pane.setFitWidth(119);
                pane.setFitHeight(206);
            }
            pane.setLayoutX(0);
            pane.setLayoutY(0);
        }

        mAnchorCard.setOnMouseClicked(mouseEvent -> mFxController.fxCardClicked(mPosition, mIndex));
    }

    /**
     * updates the card to current status
     */
    public void update() {

        mIsOpen = switch (mPosition) {

            case HANDSHELF_MID, SKAT, TRICK, PREVIEW -> true;
            case HANDSHELF_LEFT, HANDSHELF_RIGHT, TRICKS_DECLARER, TRICKS_OPPONENTS -> false;
        };

        var notnull = mCard != null;

        mImageCardBackground.setVisible(!mIsOpen && notnull);
        mImageCardColor.setVisible(mIsOpen && notnull);
        mImageCardValue.setVisible(mIsOpen && notnull);
        mImageCardHighlighted.setVisible(mIsSelected && notnull);

    }

    /**
     * @param card card
     * @return true if card has changed since last update, false if not
     */
    public boolean isEqualTo(Card card) {

        return this.mCard == card;
    }

    /**
     * changes card
     * @param card card
     */
    public void changeCard(Card card) {

        this.mCard = card;

        setImageCardColor();
        setImageCardValue();

        update();
    }

    public void removeCard() {

        mCard = null;
        update();
    }

}
