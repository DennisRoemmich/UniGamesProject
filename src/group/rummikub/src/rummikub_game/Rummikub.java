package rummikub_game;

import java.awt.*;
import java.util.Random;

public class Rummikub {

    private static final int JOKER_AMOUNT = 2;
    private static final int START_TILES_AMOUNT = 14;
    private Board board;
    private Board sketchBoard;
    private RummikubPlayer[] players;
    private int startPlayer;
    private int currentMove;
    private Tile[] tileStack;
    private int[] currentScores;
    private int tilesOnStack = 0;


    /**
     * initialize board, games, everything
     * @param playerNumber
     * @param indexStartPlayer must be the arrayindex! (Player 1 means index 0)
     */
    public Rummikub(int playerNumber, int indexStartPlayer){

        this.board = new Board();
        this.players = new RummikubPlayer[playerNumber];

        for(int i = 0; i < playerNumber; i++){
           players[i] = new RummikubPlayer();
        }

        this.currentScores = new int[playerNumber];
        this.startPlayer = indexStartPlayer;

        createTileStack();
        handOutTiles();

    }

    public RummikubPlayer getCurrentPlayer(){
        return players[(currentMove + startPlayer) % players.length];
    }

    public RummikubPlayer getPlayerAt (int index){
        return players[index];
    }

    public int getCurrentMove(){
        return this.currentMove;
    }

    public Board getBoard(){
        return this.board;
    }

    /**
     * is always referring to sketchRack and sketchBoard
     * @param rackPos
     * @param boardPos
     * @return
     */
    public boolean moveTileFromCurrentRackToBoard(Point rackPos, Point boardPos){

        var currentPlayersRack = getCurrentPlayer().getRack();
        var rackGridTile = currentPlayersRack.pointToGridTile(rackPos);
        var boardGridTile = board.getGridTileAt(boardPos);

        if (rackGridTile.isEmpty() || !boardGridTile.isEmpty()) {
            return false;
        }

        board.addTile(boardPos, rackGridTile.getTile());
        currentPlayersRack.removeTile(rackPos);

        return true;
    }

    /**
     * Current player gets (random) Tile from tileStack
     */
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

    private void handOutTiles(){

        for(RummikubPlayer player : players){
            for(int i = 0; i < START_TILES_AMOUNT; i++){
                var mytile = getRandomTileFromStack();
                player.getRack().addTile(mytile);
            }
        }

    }

    /**
     * returns null if there are no tiles left! Otherwise random remaining tile on stack ist returned.
     * @return
     */
    private Tile getRandomTileFromStack(){

        if(tilesOnStack == 0){
            return null;
        }

        var rand = new Random();
        var randomIndex = rand.nextInt(tilesOnStack);
        Tile tile = tileStack[randomIndex];

        /* put top tile to position where the random tile was
           this is so the removed tiles are always at the end of the array */
        tileStack[randomIndex] = tileStack[tilesOnStack-1];
        tileStack[tilesOnStack-1] = null;

        tilesOnStack--;

        return tile;
    }

    /**
     * (re)creates tileStack, with all the 106 tiles. Tiles are ordered not random!
     */
    private void createTileStack(){

        tileStack = new Tile[104 + JOKER_AMOUNT];

        for(TileColor color : TileColor.values()){
            if(color != TileColor.JOKER) {
                for (var i = 0; i < 13*2; i++) {
                    tileStack[color.number*26+i] = new Tile(color, (i%13)+1);
                }
            }
        }
        for (var i = 0; i < JOKER_AMOUNT; i++){
            tileStack[104+i] = new Tile(TileColor.JOKER);
        }

        tilesOnStack = 104 + JOKER_AMOUNT;

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

