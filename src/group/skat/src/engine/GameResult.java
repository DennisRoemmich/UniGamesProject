package engine;

public class GameResult {

    private SkatPlayer[] players;
    private int[] points;

    private SkatPlayer declarer;
    private Trump trump;

    private boolean isAborted;
    private boolean isFinished;

    /* CONSTRUCTOR */

    public GameResult(SkatPlayer[] players, SkatPlayer declarer, Trump trump) {

        this.players = players;
        points = new int[3];

        this.declarer = declarer;
        this.trump = trump;

        isAborted = false;
        isFinished = false;
    }

    /* GETTER */

    public int getDeclarerIndex() {

        for (var i = 0; i < players.length; i++) {

            if (players[i] == declarer) {

                return i;
            }
        }
        return -1;
    }

    public Trump getTrump() {

        return trump;
    }

    public boolean isAborted() {

        return isAborted;
    }

    public boolean isFinished() {

        return isFinished;
    }

    /* ELSE */

    public void gameHasEnded() {

        for ( var i = 0; i < points.length; i++ ) {

            points[i] = players[i].getFinalScore();
        }
        isFinished = true;
    }

    public void setAborted(boolean isAborted) {

        this.isAborted = isAborted;
    }
}
