package engine;

import java.util.ArrayList;

public class Tricks {

    private ArrayList<Trick> tricks = new ArrayList<>();
    private Card[] skat;

    /* CONSTRUCTOR */

    public Tricks() {

    }

    public Tricks(Card[] skat) {

        this.skat = skat;
    }

    /* GETTER */

    public int getValue() {

        var sum = 0;

        if ( skat != null ) {

            sum += skat[0].getPoints() + skat[1].getPoints();
        }

        for ( Trick trick : tricks ) {

            sum += sum += trick.getValue();
        }

        return sum;
    }

    /* ELSE */

    public void addTrick(Trick trick) {

        tricks.add(trick);
    }
}
