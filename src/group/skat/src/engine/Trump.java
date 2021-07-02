package engine;

import engine.enums.CardColor;
import engine.enums.GameMode;

public class Trump {

    private final GameMode gameMode;
    private CardColor cardColor;

    /* CONSTRUCTOR */

    public Trump(GameMode mode){
        this.gameMode = mode;
    }

    Trump(CardColor color){

        this.cardColor = color;
        this.gameMode = GameMode.SUIT;

    }

    /* GETTER */

    public GameMode getGameMode(){
        return gameMode;
    }

    public CardColor getCardColor(){
        return cardColor;
    }







}
