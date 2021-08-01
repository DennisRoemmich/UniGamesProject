package rummikub_game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Rummikub {

    private static final int JOKER_AMOUNT = 2;
    private static final int START_TILES_AMOUNT = 14;
    private static final boolean ACCEPT_CHANGES_WITHOUT_PUTTING = false;

    private final Random mRand;

    private final Board mBoard;
    private final Board mSketchBoard;

    private final RummikubPlayer[] mPlayers;
    private final int mStartPlayer;
    private int mCurrentMove;
    private Tile[] mTileStack;
    private int mTilesOnStack = 0;

    private final ArrayList<Tile> mMovedRackTiles = new ArrayList<>();

    /**
     * Constructor. Initializes board, players etc.
     * @param playerNumber number of players in the game
     * @param indexStartPlayer must be the arrayindex! (Player 1 means index 0)
     */
    public Rummikub(int playerNumber, int indexStartPlayer, int seed) {

        this.mRand = new Random(seed);

        this.mPlayers = new RummikubPlayer[playerNumber];

        /* initialize player array */
        for (var i = 0; i < playerNumber; i++) {

           mPlayers[i] = new RummikubPlayer();
        }

        this.mBoard = new Board();
        this.mSketchBoard = new Board();
        this.mStartPlayer = indexStartPlayer;
        this.mCurrentMove = 0;

        createTileStack();
        handOutTiles();
    }

    /**
     * @return currentPlayer
     */
    public RummikubPlayer getCurrentPlayer() {

        return mPlayers[ getCurrentPlayerIndex() ];
    }

    /**
     * @return currentPlayer index
     */
    public int getCurrentPlayerIndex() {

        return ( mCurrentMove + mStartPlayer) % mPlayers.length;
    }

    /**
     * @param index of player
     * @return player
     */
    public RummikubPlayer getPlayerAt (int index) {

        return mPlayers[ index ];
    }

    /**
     * @return currentMove
     */
    public int getCurrentMove() {

        return this.mCurrentMove;
    }

    /**
     * @return board
     */
    public Board getBoard() {

        return this.mBoard;
    }

    /**
     * @return sketchboard
     */
    public Board getSketchBoard() {

        return this.mSketchBoard;
    }

    /**
     * @return sketchrack of current player
     */
    public Rack getCurrentPlayersSketchRack() {

        return getCurrentPlayer().getSketchRack();
    }

    /**
     * @return ArrayList with movedRackTiles
     */
    public ArrayList<Tile> getMovedRackTiles() {

        return mMovedRackTiles;
    }

    /**
     * @return amount of players
     */
    public int getPlayerAmount() {

        return mPlayers.length;
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
    public boolean moveTileFromCurrentRackToBoard(Point rackPos, Point boardPos) {

        var rackGridTile = getCurrentPlayersSketchRack().getGridTileAt(rackPos);
        var boardGridTile = mSketchBoard.getGridTileAt(boardPos);

        if ( rackGridTile.isEmpty() || !boardGridTile.isEmpty() ) {

            return false;
        }

        if (mSketchBoard.addTile(boardPos, rackGridTile.getTile())) {

            mMovedRackTiles.add(rackGridTile.getTile());
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

        var boardGridTile = mSketchBoard.getGridTileAt(boardPos);
        var rackGridTile = getCurrentPlayersSketchRack().getGridTileAt(rackPos);

        var i = boardRackMoveInvalid(boardGridTile.getTile());

        if (boardGridTile.isEmpty() || !rackGridTile.isEmpty() || i == -1) {

            return false;
        }

        if (getCurrentPlayersSketchRack().addTileAt(rackPos, boardGridTile.getTile())) {

            mMovedRackTiles.remove(i);
            mSketchBoard.removeTile(boardPos);

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

        for (var i = 0; i < mMovedRackTiles.size(); i++) {

            if (mMovedRackTiles.get(i) == tile) {

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

        return ( mCurrentMove != 0 && getWinner() != null );
    }


    public boolean resetMove() {

        getCurrentPlayer().resetSketchRack();
        resetSketchBoard();
        mMovedRackTiles.clear();

        return true;

    }

    /**
     * call if move should be finished; Only finishes move if board is possible!
     * @return false if current board is invalid, otherwise true
     */
    public boolean finishMove() {

        if (mSketchBoard.isValid()) {

            /* true if player didn't play tiles */
            if (mMovedRackTiles.isEmpty()) {

                noTilesMoved();

            } else {

                if (!tilesMoved()) {

                    return false;
                }
            }

            mMovedRackTiles.clear();
            getCurrentPlayer().acceptSketchRack();
            mCurrentMove++;

            if( isFinished() ) {

                gameOver();
            }

            return true;
        }

        return false;

    }

    private void noTilesMoved() {

        var nextTile = getRandomTileFromStack();

        if ( nextTile != null ) {

            getCurrentPlayer().getSketchRack().addTile( nextTile );
        }

        if ( ACCEPT_CHANGES_WITHOUT_PUTTING ) {

            acceptSketchBoard();
        }
    }

    private boolean tilesMoved() {
        
        if (!getCurrentPlayer().getCommingOut()) {
            
            if (sumMovedRackTiles() >= 30) {

                getCurrentPlayer().setCommingOut(true);
                acceptSketchBoard();
            } else {

                return false;
            }
        }
        return true;
    }

    /**
     * Current player gets (random) Tile from tileStack
     * @return false if there weren't any tiles left on stack.
     */
    public void currentPlayerTakeTile() {

        var tile = getRandomTileFromStack();

        if (tile != null) {

            getCurrentPlayer().getRack().addTile(tile);

        }
    }

    private void handOutTiles() {

        for ( RummikubPlayer player : mPlayers) {

            for ( var i = 0; i < START_TILES_AMOUNT; i++ ) {

                var mytile = getRandomTileFromStack();
                player.getRack().addTile(mytile);

            }
            player.resetSketchRack();
        }
    }

    public int sumMovedRackTiles() {

        var sum = 0;
        for (Tile t : mMovedRackTiles) {

            sum += t.getValue();
        }

        return sum;
    }


    /**
     * call if game is over
     * evaluates stats
     */
    private void gameOver() {

        var winner = getWinner();
        var totalSum = 0;

        for ( RummikubPlayer player : mPlayers) {

            if ( player != winner ) {

                var sum = player.getRack().getSum();
                player.setScore(-sum);
                totalSum += sum;

            }
        }
        winner.setScore(totalSum);
    }

    /**
     * returns null if there are no tiles left! Otherwise random remaining tile on stack ist returned.
     */
    private Tile getRandomTileFromStack() {

        if ( mTilesOnStack == 0 ) {

            return null;

        }

         var randomIndex = mRand.nextInt(mTilesOnStack);        // <-- this one is right
        // var randomIndex = tilesOnStack-1;                       // <-- For Testing *DELETE*

        var tile = mTileStack[randomIndex];

        /* put top tile to position where the random tile was
           this is so the removed tiles are always at the end of the array */
        mTileStack[randomIndex] = mTileStack[mTilesOnStack -1];
        mTileStack[mTilesOnStack -1] = null;

        mTilesOnStack--;

        return tile;
    }

    /**
     * (re)creates tileStack, with all the 106 tiles. Tiles are ordered not random!
     */
    private void createTileStack() {

        mTileStack = new Tile[104 + JOKER_AMOUNT];

        for(TileColor color : TileColor.values()) {
            if(color != TileColor.JOKER) {
                for (var i = 0; i < 13*2; i++) {
                    mTileStack[color.mValue *26+i] = new Tile(color, (i%13)+1);
                }
            }
        }
        for (var i = 0; i < JOKER_AMOUNT; i++) {
            mTileStack[104+i] = new Tile(TileColor.JOKER);
        }

        mTilesOnStack = 104 + JOKER_AMOUNT;

    }

    public RummikubPlayer getWinner() {

        for ( RummikubPlayer player : mPlayers) {

            if ( player.getRack().isEmpty() ) {

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
        for (var i = 0; i < mSketchBoard.getBoardSize(); i++) {

            var sourceGridTile = mBoard.getBoard()[i / Board.GRID_WIDTH][i % Board.GRID_WIDTH];

            var returnTile = new GridTile();

            if( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().mColor, sourceGridTile.getTile().mValue));

            }



            mSketchBoard.getBoard()[i / Board.GRID_WIDTH][i % Board.GRID_WIDTH] = returnTile;

        }

    }

    /**
     * Copies the sketchBoard into the Board (pass by value)
     */
    private void acceptSketchBoard() {

        for (var i = 0; i < mBoard.getBoardSize(); i++) {


            var sourceGridTile = mSketchBoard.getBoard()[i / Board.GRID_WIDTH][i % Board.GRID_WIDTH];

            var returnTile = new GridTile();

            if( sourceGridTile.getTile() != null ) {

                returnTile.setTile(new Tile(sourceGridTile.getTile().mColor, sourceGridTile.getTile().mValue));

            }

            mBoard.getBoard()[i / Board.GRID_WIDTH][i % Board.GRID_WIDTH] = returnTile;
        }
    }

}

