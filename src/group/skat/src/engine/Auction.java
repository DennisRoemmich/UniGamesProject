package engine;

public class Auction {

    private SkatPlayer foreHand;
    private SkatPlayer middleHand;
    private SkatPlayer rearHand;

    private int currentAuctioneerIndex;

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

        auctionLvL = new int[]{18, 20, 22, 23, 24, 27, 30, 33, 36, 40, 44, 45, 48, 50, 54, 55, 60,
                            63, 66, 70, 72, 77, 80, 81, 84, 88, 90, 96, 99, 100, 108, 110, 120, 121, 132};
        currentBidLvL = -1;
    }

    /* GETTER */

    public SkatPlayer getAuctionWinner() {

        return auctionWinner;
    }

    public boolean isRunning() {

        return auctionWinner == null;
    }

    public SkatPlayer getCurrentAuctioneer() {
        
        var p = currentAuctioneerIndex % 3;
        
        if ( p == 0 ) {

            return foreHand;

        } else if ( p == 1 ) {

            return middleHand;

        } else {

            return rearHand;
        }
    }

    /* ELSE */

    public void raiseOrAcceptBid() {

        if ( getCurrentAuctioneer().isAsking() ) {

            currentBidLvL++;
        }
        currentAuctioneerIndex++;
    }

    public void passBid() {

        if ( getCurrentAuctioneer() == foreHand ) {

            foreHand.setBidding(false);

            if ( rearHand.isAsking() ) {

                rearHand.setDeclarer(true);
                auctionWinner = rearHand;
            }

        } else if ( getCurrentAuctioneer() == middleHand ) {

            middleHand.setBidding(false);

            if ( foreHand.isBidding() ) {

                rearHand.setAsking(true);

            } else {

                rearHand.setDeclarer(true);
                auctionWinner = rearHand;
            }

        } else if ( getCurrentAuctioneer() == rearHand ) {

            rearHand.setBidding(false);

            if ( foreHand.isAsking() ) {

                foreHand.setDeclarer(true);
                auctionWinner = foreHand;

            } else if ( middleHand.isAsking() ) {

                middleHand.setDeclarer(true);
                auctionWinner = middleHand;
            }
        }
    }
}
