package rummikub_game;

import java.awt.*;
import java.util.ArrayList;
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

    private ArrayList<Tile> movedRackTiles = new ArrayList<>();

    /**
     * Constructor. Initializes board, players etc.
     * @param playerNumber number of players in the game
     * @param indexStartPlayer must be the arrayindex! (Player 1 means index 0)
     */
    public Rummikub(int playerNumber, int indexStartPlayer) {


        this.players = new RummikubPlayer[playerNumber];

        /* initialize player array */
        for (var i = 0; i < playerNumber; i++) {

           players[i] = new RummikubPlayer();
        }

        this.board = new Board();
        this.sketchBoard = new Board();
        this.startPlayer = indexStartPlayer;
        this.currentMove = 0;

        createTileStack();
        handOutTiles();
    }

    /**
     * @return currentPlayer
     */
    public RummikubPlayer getCurrentPlayer(){

        return players[ getCurrentPlayerIndex() ];
    }

    /**
     * @return currentPlayer index
     */
    public int getCurrentPlayerIndex(){

        return ( currentMove + startPlayer ) % players.length;
    }

    /**
     * @param index of player
     * @return player
     */
    public RummikubPlayer getPlayerAt (int index){

        return players[ index ];
    }

    /**
     * @return currentMove
     */
    public int getCurrentMove(){

        return this.currentMove;
    }

    /**
     * @return board
     */
    public Board getBoard(){

        return this.board;
    }

    /**
     * @return sketchboard
     */
    public Board getSketchBoard(){

        return this.sketchBoard;
    }

    /**
     * @return sketchrack of current player
     */
    public Rack getCurrentPlayersSketchRack() {

        return getCurrentPlayer().getSketchRack();
    }

    /**
     * @return amount of players
     */
    public int getPlayerAmount() {

        return players.length;
    }

    /**
     * moves a tile on current players's rack to an given position
     * @param toMove position of tile to be moved
     * @param target target of tile
     * @return true if successful, false if not
     */
    public boolean moveTileOnCurrentRack(Point toMove, Point target) {

        return getCurrentPlayersSketchRack().moveTile(toMove, target);
    }

    /**
     * moves a tile on the board to an given position
     * @param toMove position of tile to be moved
     * @param target target of tile
     * @return true if successful, false if not
     */
    public boolean moveTileOnBoard(Point toMove, Point target) {

        return getSketchBoard().moveTile(toMove, target);
    }

    /**
     * Moves tile from rack of the player with the current move to the game board.
     * Is always referring to sketchRack and sketchBoard!
     * @return true if movement is possible, false otherwise.
     */
    public boolean moveTileFromCurrentRackToBoard(Point rackPos, Point boardPos){

        var rackGridTile = getCurrentPlayersSketchRack().getGridTileAt(rackPos);
        var boardGridTile = sketchBoard.getGridTileAt(boardPos);

        if ( rackGridTile.isEmpty() || !boardGridTile.isEmpty() ) {

            return false;
        }

        if (sketchBoard.addTile(boardPos, rackGridTile.getTile())) {

            movedRackTiles.add(rackGridTile.getTile());
            getCurrentPlayersSketchRack().removeTile(rackPos);
            return true;
        }

        return false;
    }

    /**
     * Moves tile from board to rack of current player,
     * but only if this was already moved to the board in his move
     * @param boardPos position of tile to be moved
     * @param rackPos target of tile
     * @return true if successful, false if not
     */
    public boolean moveTileFromBoardToCurrentRack(Point boardPos, Point rackPos) {

        var boardGridTile = sketchBoard.getGridTileAt(boardPos);
        var rackGridTile = getCurrentPlayersSketchRack().getGridTileAt(rackPos);

        var i = boardRackMoveInvalid(boardGridTile.getTile());

        if (boardGridTile.isEmpty() || !rackGridTile.isEmpty() || i == -1) {

            return false;
        }

        if (getCurrentPlayersSketchRack().addTileAt(rackPos, boardGridTile.getTile())) {

            movedRackTiles.remove(i);
            sketchBoard.removeTile(boardPos);

            return true;
        }

        return false;
    }

    /**
     * checks whether tile was already moved in current move
     * @param tile to be checked
     * @return position of tile in arraylist which saves moved tiles, or -1 if not moved
     */
    private int boardRackMoveInvalid(Tile tile) {

        for (var i = 0; i < movedRackTiles.size(); i++) {

            if (movedRackTiles.get(i) == tile) {

                return i;
            }
        }
        return -1;
    }

    /**
     * sorts current player's rack for group
     * @return true
     */
    public boolean sortRackForGroup() {

        getCurrentPlayersSketchRack().sortForGroup();
        return true;
    }

    /**
     * sorts current player's rack for run
     * @return true
     */
    public boolean sortRackForRun() {

        getCurrentPlayersSketchRack().sortForRun();
        return true;
    }


    /**
     *
     * @return true if game is finished, false if not
     */
    public boolean isFinished() {

        return ( currentMove != 0 && getWinner() != null );
    }


    public void resetMove() {

        getCurrentPlayer().resetSketchRack();
        resetSketchBoard();

    }

    /**
     * call if move should be finished; Only finishes move if board is possible!
     * @return false if current board is invalid, otherwise true
     */
    public boolean finishMove(){

        if ( sketchBoard.isValid() ) {

            /* true if player didn't play tiles */
            if ( getCurrentPlayer().getRack().getSize() == getCurrentPlayersSketchRack().getSize() ){

                var nextTile = getRandomTileFromStack();

                if ( nextTile != null ) {

                    getCurrentPlayer().getRack().addTile( nextTile );
                }

                if ( ACCEPT_CHANGES_WITHOUT_PUTTING ){

                    acceptSketchBoard();

                }

            } else {

                acceptSketchBoard();
            }

            movedRackTiles.clear();
            getCurrentPlayer().acceptSketchRack();
            currentMove++;

            if( isFinished() ) {

                gameOver();
            }

            return true;
        }

        return false;

    }

    /**
     * Current player gets (random) Tile from tileStack
     * @return false if there weren't any tiles left on stack.
     * TODO: where used?? (only not grey bc used in test i guess)
     */
    public void currentPlayerTakeTile(){

        var tile = getRandomTileFromStack();

        if (tile != null) {

            getCurrentPlayer().getRack().addTile(tile);
        //    return true;

        } /* else {

            return false;

        }*/

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

        var winner = getWinner();
        var totalSum = 0;

        for ( RummikubPlayer player : players ){

            if ( player != winner ){

                var sum = player.getRack().getSum();
                player.setScore(player.getScore() - sum);
                totalSum += sum;

            }
        }
        winner.setScore(winner.getScore() + totalSum);
    }

    /**
     * returns null if there are no tiles left! Otherwise random remaining tile on stack ist returned.
     */
    private Tile getRandomTileFromStack(){

        if ( tilesOnStack == 0 ) {

            return null;

        }

        var randomIndex = rand.nextInt(tilesOnStack);        // <-- this one is right
        // var randomIndex = tilesOnStack-1;                       // <-- For Testing *DELETE*

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

    /**
     * Copies the Board into the sketchBoard (pass by value)
     */
    private void resetSketchBoard() {

        // PASS BY VALUE :
        for (var i = 0; i < sketchBoard.getBoardSize(); i++) {

            GridTile sourceGridTile = board.getBoard()[i / sketchBoard.GRID_WIDTH][i % sketchBoard.GRID_WIDTH];

            GridTile returnTile = new GridTile();

            if( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().color, sourceGridTile.getTile().value));

            }



            sketchBoard.getBoard()[i / sketchBoard.GRID_WIDTH][i % sketchBoard.GRID_WIDTH] = returnTile;

        }

    }

    /**
     * Copies the sketchBoard into the Board (pass by value)
     */
    private void acceptSketchBoard() {

    //    System.arraycopy(board.getBoard(), 0, sketchBoard.getBoard(), 0, board.grid.length);

        for (var i = 0; i < board.getBoardSize(); i++) {


            GridTile sourceGridTile = sketchBoard.getBoard()[i / sketchBoard.GRID_WIDTH][i % sketchBoard.GRID_WIDTH];

            GridTile returnTile = new GridTile();

            if( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().color, sourceGridTile.getTile().value));

            }

            board.getBoard()[i / sketchBoard.GRID_WIDTH][i % sketchBoard.GRID_WIDTH] = returnTile;
        }
    }

}

