package rummikub_game;

import java.awt.*;

public class Rummikub {

    private Board board;
    private Board sketchBoard;
    private RummikubPlayer[] players;
    private int startPlayer;
    private int currentMove;
    private Tile[] tileStack;
    private int[] currentScores;


    /**
     * initialize board, games, everything
     * @param playerNumber
     * @param indexStartPlayer
     */
    public Rummikub(int playerNumber, int indexStartPlayer){
        players = new RummikubPlayer[playerNumber];
        currentScores = new int[playerNumber];
        this.startPlayer = indexStartPlayer;

        handOutTiles();

    }

    public RummikubPlayer getCurrentPlayer(){
        return new RummikubPlayer();
    }

    public int getCurrentMove(){
        return 1;
    }

    public Board getBoard(){
        return this.board;
    }

    public boolean moveTileFromRackToBoard(Point rackPos, Point boardPos){
        return false;
    }

    public void currentPlayerTakeTile(){

    }

    /**
     *
     * @return
     */
    public boolean isFinished(){

        // call gameOver() if game is over!

        return false;
    }

    /**
     * Zug ist zu Ende. Überprüfe board, next current player etc.
     * @return false if current board is invalid, otherwise true
     */
    public boolean finishMove(){
        return false;
    }

    public void resetMove(){

    }

    private void createTileStack(Tile[] tiles){

    }

    private void handOutTiles(){
        createTileStack(new Tile[0]);
    }

    private RummikubPlayer getWinner(){
        return new RummikubPlayer();
    }

    /**
     * call if game is over
     * evaluates stats
     */
    private void gameOver(){

    }




}

