package controller;

/**
 * The different game states that handle the type of moves that can be played in each state.
 * @author Jan de Boer, Fernanda Maria Barrios, Dennis Roemmich
 *
 */
public enum GameState {
    NOT_RUNNING, ROLL_DICES, OPTIONAL_MOVES, MOVE_BURGLAR, SETUP_VILLAGE, SETUP_STREET;
}
