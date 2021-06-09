package rummikub_game;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;


public class Rummikub {

    private static final int JOKER_AMOUNT = 2;
    private static final int START_TILES_AMOUNT = 14;
    private static final boolean ACCEPT_CHANGES_WITHOUT_PUTTING = false;

    private final Random rand = new Random();

    private Board board;
    private Board sketchBoard;

    private final RummikubPlayer[] players;
    private final int startPlayer;
    private int currentMove;
    private Tile[] tileStack;
    private int tilesOnStack = 0;

    /**
     * Constructor. Initializes board, players etc.
     * @param playerNumber number of players in the game
     * @param indexStartPlayer must be the arrayindex! (Player 1 means index 0)
     */
    public Rummikub(int playerNumber, int indexStartPlayer){


        this.players = new RummikubPlayer[playerNumber];

        /* initialize player array */
        for(var i = 0; i < playerNumber; i++){

           players[i] = new RummikubPlayer();

        }

        this.board = new Board();
        this.sketchBoard = new Board();
        this.startPlayer = indexStartPlayer;
        this.currentMove = 0;

        createTileStack();
        handOutTiles();

    }

    public RummikubPlayer getCurrentPlayer(){

        return players[ ( currentMove + startPlayer ) % players.length ];

    }

    public RummikubPlayer getPlayerAt (int index){

        return players[ index ];

    }

    public int getCurrentMove(){

        return this.currentMove;

    }

    public Board getBoard(){

        return this.board;

    }

    public Board getSketchBoard(){

        return this.sketchBoard;

    }

    /**
     * Moves tile from rack of the player with the current move to the game board.
     * Is always referring to sketchRack and sketchBoard!
     * @return true if movement is possible, false otherwise.
     */
    public boolean moveTileFromCurrentRackToBoard(Point rackPos, Point boardPos){

        var currentPlayersRack = getCurrentPlayer().getSketchRack();
        var rackGridTile = currentPlayersRack.pointToGridTile(rackPos);
        var boardGridTile = sketchBoard.getGridTileAt(boardPos);

        if ( rackGridTile.isEmpty() || !boardGridTile.isEmpty() ) {

            return false;

        }

        sketchBoard.addTile(boardPos, rackGridTile.getTile());
        currentPlayersRack.removeTile(rackPos);

        return true;

    }




    /**
     *
     * @return true if game is finished, false if not
     */
    public boolean isFinished(){

        return ( currentMove != 0 && getWinner() != null );

    }


    public void resetMove(){

        getCurrentPlayer().resetSketchRack();
        sketchBoard = board;

    }

    /**
     * call if move should be finished; Only finishes move if board is possible!
     * @return false if current board is invalid, otherwise true
     */
    public boolean finishMove(){

        if ( sketchBoard.isValid() ) {

            /* true if player didn't play tiles */
            if ( getCurrentPlayer().getRack().getSize() == getCurrentPlayer().getSketchRack().getSize() ){

                var nextTile = getRandomTileFromStack();

                if ( nextTile != null ) {
                    getCurrentPlayer().getRack().addTile( nextTile );
                }

                if ( ACCEPT_CHANGES_WITHOUT_PUTTING ){

                    System.arraycopy(sketchBoard.grid, 0, board.grid, 0, sketchBoard.grid.length);

                }

            } else {

                System.arraycopy(sketchBoard.grid, 0, board.grid, 0, sketchBoard.grid.length);

            }

            getCurrentPlayer().acceptSketchRack();
            currentMove++;

            if( isFinished() ){
                gameOver();
            }

            return true;
        }

        return false;

    }

    /**
     * Current player gets (random) Tile from tileStack
     * @return returns false if there weren't any tiles left on stack.
     */
    public boolean currentPlayerTakeTile(){

        var tile = getRandomTileFromStack();

        if (tile != null) {

            getCurrentPlayer().getRack().addTile(tile);
            return true;

        } else {

            return false;

        }

    }

    private void handOutTiles(){

        for ( RummikubPlayer player : players ) {

            for ( var i = 0; i < START_TILES_AMOUNT; i++ ) {

                var mytile = getRandomTileFromStack();
                player.getRack().addTile(mytile);

            }

            player.resetSketchRack();

        }
    }


    /**
     * call if game is over
     * evaluates stats
     */
    private void gameOver(){

        RummikubPlayer winner = getWinner();
        var totalSum = 0;

        for ( RummikubPlayer player : players ){

            if ( player != winner ){

                var sum = player.getRack().getSum();
                player.setScore(sum * -1);
                totalSum += sum;

            }

                winner.setScore(totalSum);

        }

    }

    /**
     * returns null if there are no tiles left! Otherwise random remaining tile on stack ist returned.
     */
    private Tile getRandomTileFromStack(){

        if ( tilesOnStack == 0 ) {

            return null;

        }

        //var randomIndex = rand.nextInt(tilesOnStack);        // <-- this one is right
         var randomIndex = tilesOnStack-1;                       // <-- For Testing *DELETE*

        var tile = tileStack[randomIndex];

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
                    tileStack[color.value*26+i] = new Tile(color, (i%13)+1);
                }
            }
        }
        for (var i = 0; i < JOKER_AMOUNT; i++){
            tileStack[104+i] = new Tile(TileColor.JOKER);
        }

        tilesOnStack = 104 + JOKER_AMOUNT;

    }

    private RummikubPlayer getWinner(){

        for ( RummikubPlayer player : players ){

            if ( player.getRack().isEmpty() ){

                return player;

            }
        }

        return null;
    }


}

