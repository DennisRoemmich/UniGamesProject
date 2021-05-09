package TicTacToeFX;

public class Settings {

    public static final int REPLAY_MOVE_DELAY = 600;   // Delay in ms between replay-frames
    public static final char EMPTY_BOX = '·';          // symbol for empty box
    public static final char SYMBOL_PLAYER_1 = 'O';
    public static final char SYMBOL_PLAYER_2 = 'X';

    public static final String BOT_NAME = "TICBOT";
    public static final int BOT_MOVE_DELAY = 2000;

    protected static final String[] PLAYER_NAMES = {"LUIGI", "MARIO"};

    // private constructor to make SonarLint happy
    private Settings(){

    }

}
