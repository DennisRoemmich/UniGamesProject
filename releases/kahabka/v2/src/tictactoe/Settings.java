package tictactoe;

public class Settings {

    public static final int REPLAY_MOVE_DELAY = 600;   // Delay in ms between replay-frames
    public static final char EMPTY_BOX = '·';          // symbol for empty box
    public static final char SYMBOL_PLAYER_1 = 'O';
    public static final char SYMBOL_PLAYER_2 = 'X';

    public static final String BOT_NAME = "TicBot";

    protected static final String[] PLAYER_NAMES = {"Player 1", "Player 2"};

    // private constructor to make SonarLint happy
    private Settings(){

    }

}
