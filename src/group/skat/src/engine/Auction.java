package engine;

public class Auction {

    private SkatPlayer foreHand;
    private SkatPlayer middleHand;
    private SkatPlayer rearHand;

    private SkatPlayer currentAuctioneer;

    private int[] auctionLvL;
    private int currentBidLvL;

    private SkatPlayer auctionWinner;

    /* CONSTRUCTOR */

    public Auction(SkatPlayer[] players) {

        foreHand = players[0];
        middleHand = players[1];
        rearHand = players[2];

        foreHand.setAsking(false);
        middleHand.setAsking(true);
        rearHand.setAsking(false);

        currentAuctioneer = middleHand;

        auctionLvL = new int[]{18, 20, 22, 23, 24, 27, 30, 33, 36, 40, 44, 45, 48, 50, 54, 55, 60,
                            63, 66, 70, 72, 77, 80, 81, 84, 88, 90, 96, 99, 100, 108, 110, 120, 121, 132};
        currentBidLvL = -1;
    }

    /* GETTER */

    public SkatPlayer getCurrentAuctioneer() {

        return currentAuctioneer;
    }

    public SkatPlayer getAuctionWinner() {

        return auctionWinner;
    }

    public boolean isRunning() {

        return checkWinner() && !passedOut();
    }

    /* ELSE */

    public void nextTurn() {

        var last = currentAuctioneer;

        if ( last == foreHand ) {

            if ( middleHand.isBidding() ) {

                currentAuctioneer = middleHand;

            } else {

                currentAuctioneer = rearHand;
            }

        } else if ( last == middleHand ) {

            if ( foreHand.isBidding() ) {

                currentAuctioneer = foreHand;

            } else {

                currentAuctioneer = rearHand;
            }

        } else if ( last == rearHand ) {

            if ( middleHand.isBidding() ) {

                currentAuctioneer = middleHand;

            } else {

                currentAuctioneer = foreHand;
            }
        }
    }

    public void raiseOrAcceptBid() {

        if ( currentAuctioneer.isAsking() ) {

            currentBidLvL++;
        }

        if ( isRunning() ) {

            nextTurn();
        }
    }

    public void passBid() {

        if ( currentAuctioneer == foreHand ) {

            foreHand.setBidding(false);

            if ( isRunning() ) {

                nextTurn();
            }

        } else if ( currentAuctioneer == middleHand ) {

            middleHand.setBidding(false);

            rearHand.setAsking(true);

            if ( isRunning() ) {

                nextTurn();
            }

        } else if ( currentAuctioneer == rearHand ) {

            rearHand.setBidding(false);

            if ( foreHand.isBidding() ) {

                foreHand.setAsking(true);
            }

            if ( isRunning() ) {

                nextTurn();
            }
        }
    }

    private boolean checkWinner() {

        if ( isOnlyOneBidding(foreHand) && currentBidLvL != -1 ) {

            foreHand.setDeclarer(true);
            auctionWinner = foreHand;
            return true;

        } else if ( isOnlyOneBidding(middleHand) && currentBidLvL != -1 ) {

            middleHand.setDeclarer(true);
            auctionWinner = middleHand;
            return true;

        } else if ( isOnlyOneBidding(rearHand) && currentBidLvL != -1 ) {

            rearHand.setDeclarer(true);
            auctionWinner = rearHand;
            return true;
        }
        return false;
    }

    public boolean isOnlyOneBidding(SkatPlayer auctioneer) {

        if ( auctioneer == foreHand ) {

            return !middleHand.isBidding() && !rearHand.isBidding();

        } else if ( auctioneer == middleHand ) {

            return !foreHand.isBidding() && !rearHand.isBidding();

        } else if ( auctioneer == rearHand ) {

            return !foreHand.isBidding() && !middleHand.isBidding();
        }
        return false;
    }

    public boolean passedOut() {

        return !foreHand.isBidding() && !middleHand.isBidding() && !rearHand.isBidding();
    }
}
