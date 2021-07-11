package engine;

import console.Print;

import java.util.ArrayList;

public class Tricks {

    private ArrayList<Trick> tricks = new ArrayList<>();
    private Card[] skat;

    /* CONSTRUCTOR */

    public Tricks() {

        skat = null;
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

            sum += trick.getValue();
        }

        return sum;
    }

    public int getSize() {

        return tricks.size();
    }

    public boolean skatIsDropped() {

        return skat != null;
    }

    /* OTHER */

    public void addSkat(Card[] skat) {

        this.skat = skat;
    }

    public void addTrick(Trick trick) {

        tricks.add(trick);
    }

    public Trick getTrickAt(int index) {

        return tricks.get(index);
    }
}
