package engine;

public class GameResult {

    private SkatPlayer[] players;
    private int[] points;

    private SkatPlayer declarer;
    private Trump trump;

    /* CONSTRUCTOR */

    public GameResult(SkatPlayer[] players, SkatPlayer declarer, Trump trump) {

        this.players = players;
        points = new int[3];

        this.declarer = declarer;
        this.trump = trump;

        calculateFinalScores();
        for ( var i = 0; i < points.length; i++ ) {

            points[i] = players[i].getFinalScore();
        }
    }

    /* GETTER */

    public int getDeclarerIndex() {

        for ( var i = 0; i < players.length; i++ ) {

            if ( players[i] == declarer ) {

                return i;
            }
        }
        return -1;
    }

    public Trump getTrump() {

        return trump;
    }

    /* ELSE */

    private void calculateFinalScores() {

        for ( SkatPlayer player : players ) {

            player.calculateFinalScore();
        }
    }

    private boolean declarerDidWin() {

        var declarerPoints = points[getDeclarerIndex()];
        var opponentsPoints = points[(getDeclarerIndex() + 1) % points.length];

        return declarerPoints > opponentsPoints;
    }
}
