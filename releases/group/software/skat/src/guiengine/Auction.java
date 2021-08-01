package guiengine;

import guiconsole.Print;

/**
 * extra class for the whole auction
 */
public class Auction {

    private SkatPlayer[] mPlayers;
    private SkatPlayer mForeHand;
    private SkatPlayer mMiddleHand;
    private SkatPlayer mRearHand;

    private SkatPlayer mCurrentAuctioneer;

    private int[] mAuctionLvL;
    private int mCurrentBidLvL;

    private SkatPlayer mAuctionWinner;

    /* CONSTRUCTOR */

    public Auction(SkatPlayer[] players) {

        this.mPlayers = players;
        mForeHand = players[0];
        mMiddleHand = players[1];
        mRearHand = players[2];

        mForeHand.setAsking(false);
        mMiddleHand.setAsking(true);
        mRearHand.setAsking(false);

        mCurrentAuctioneer = mMiddleHand;

        mAuctionLvL = new int[]{18, 20, 22, 23, 24, 27, 30, 33, 36, 40, 44, 45, 48, 50, 54, 55, 60,
                            63, 66, 70, 72, 77, 80, 81, 84, 88, 90, 96, 99, 100, 108, 110, 120, 121, 132};
        mCurrentBidLvL = -1;
    }

    /* GETTER */

    /**
     * @return player to move in this moment
     */
    public SkatPlayer getCurrentAuctioneer() {

        return mCurrentAuctioneer;
    }

    /**
     * @return player who can raise or pass a bid
     */
    public SkatPlayer getQuestioner() {

        for (var player : mPlayers) {

            if (player.isAsking()) {

                return player;
            }
        }
        return null;
    }

    /**
     * @return player who can accept or pass a bid
     */
    public SkatPlayer getHearer() {

        if (mForeHand.isBidding() && !isOnlyOneBidding(mForeHand)) {

            return mForeHand;

        } else if (isOnlyOneBidding(mForeHand)) {

            return null;

        } else {

            return mRearHand;
        }
    }

    /**
     * @return player who has already passed or waits for the other two players to get finish with their bidding
     */
    public SkatPlayer getInactivePlayer() {

        for (var player : mPlayers) {

            if (player != getQuestioner() && player != getHearer()) {

                return player;
            }
        }
        return null;
    }

    /**
     * @return player who has won the auction and is now declarer
     */
    public SkatPlayer getAuctionWinner() {

        return mAuctionWinner;
    }

    /**
     * @return current BidLevel
     */
    public int getAuctionValue() {

        if(mCurrentBidLvL == -1){
            return 0;
        }

        return mAuctionLvL[mCurrentBidLvL];
    }

    public int getNextAuctionValue() {

        if(mCurrentBidLvL != -1 && mAuctionLvL[mCurrentBidLvL] == 132) {

            Print.debug("ERROR", "max auction level reached (getNextAuctionLevel)");

            return -1;
        }
        return mAuctionLvL[mCurrentBidLvL +1];
    }

    /**
     * @return true if auction is still running, false if not
     */
    public boolean isRunning() {

        return !checkWinner() && !passedOut();
    }

    /* ELSE */

    /**
     * handles the variable roles if a turn was made
     */
    public void nextTurn() {

        var last = mCurrentAuctioneer;

        if ( last == mForeHand) {

            if ( mMiddleHand.isBidding() && mForeHand.isBidding()) {

                mCurrentAuctioneer = mMiddleHand;

            } else {

                mCurrentAuctioneer = mRearHand;
            }

        } else if ( last == mMiddleHand) {

            if ( mForeHand.isBidding() && mMiddleHand.isBidding() ) {

                mCurrentAuctioneer = mForeHand;

            } else {

                mCurrentAuctioneer = mRearHand;
            }

        } else if ( last == mRearHand) {

            if ( mMiddleHand.isBidding() ) {

                mCurrentAuctioneer = mMiddleHand;

            } else {

                mCurrentAuctioneer = mForeHand;
            }
        }
    }

    /**
     * the current player has raised or accepted the current bid
     */
    public void raiseOrAcceptBid() {

        if (mCurrentAuctioneer.isAsking()) {

            mCurrentBidLvL++;
        }

        if (isRunning()) {

            nextTurn();
        }
    }

    /**
     * the current player has passed the current bid and is no longer in the auction
     */
    public void passBid() {

        if (mCurrentAuctioneer == mForeHand) {

            mForeHand.setBidding(false);
            mForeHand.setAsking(false);

            if (isRunning()) {

                nextTurn();
            }

        } else if (mCurrentAuctioneer == mMiddleHand) {

            mMiddleHand.setBidding(false);
            mMiddleHand.setAsking(false);
            mRearHand.setAsking(true);

            if (isRunning()) {

                nextTurn();
            }

        } else if (mCurrentAuctioneer == mRearHand) {

            mRearHand.setBidding(false);
            mRearHand.setAsking(false);

            if (mForeHand.isBidding()) {

                mForeHand.setAsking(true);
            }

            if (isRunning()) {

                nextTurn();
            }
        }
    }

    /**
     * @return true if there's an winnner, false if not
     */
    private boolean checkWinner() {

        if (mCurrentBidLvL == mAuctionLvL.length - 1 && !mCurrentAuctioneer.isAsking()) {

            mCurrentAuctioneer.setDeclarer(true);
            mAuctionWinner = mCurrentAuctioneer;
        }

        if (isOnlyOneBidding(mForeHand) && mCurrentBidLvL != -1) {

            mForeHand.setDeclarer(true);
            mAuctionWinner = mForeHand;
            return true;

        } else if (isOnlyOneBidding(mMiddleHand) && mCurrentBidLvL != -1) {

            mMiddleHand.setDeclarer(true);
            mAuctionWinner = mMiddleHand;
            return true;

        } else if (isOnlyOneBidding(mRearHand) && mCurrentBidLvL != -1) {

            mRearHand.setDeclarer(true);
            mAuctionWinner = mRearHand;
            return true;
        }
        return false;
    }

    /**
     * @param auctioneer player
     * @return true if player has won the auction, false if not (yet)
     */
    public boolean isOnlyOneBidding(SkatPlayer auctioneer) {

        if (auctioneer == mForeHand) {

            return !mMiddleHand.isBidding() && !mRearHand.isBidding();

        } else if (auctioneer == mMiddleHand) {

            return !mForeHand.isBidding() && !mRearHand.isBidding();

        } else if (auctioneer == mRearHand) {

            return !mForeHand.isBidding() && !mMiddleHand.isBidding();
        }
        return false;
    }

    /**
     * @return true if all players passed, false if not
     */
    public boolean passedOut() {

        return !mForeHand.isBidding() && !mMiddleHand.isBidding() && !mRearHand.isBidding();
    }
}
