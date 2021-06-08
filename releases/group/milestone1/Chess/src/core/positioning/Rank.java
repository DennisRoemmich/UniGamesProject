package core.positioning;

public enum Rank {

    M1(0), M2(1), M3(2), M4(3), M5(4), M6(5), M7(6), M8(7);

    private final int mIndex;

    Rank(int i) {
        mIndex = i;
    }

    public static Rank valueOf(char c) {
        return valueOf("M" + c);
    }

    public static Rank valueOf(int i) {
        char c = (char) (49 + i); // 49 is the decimal representation of the char '1'
        return valueOf(c);
    }

    public int getIndex() {
        return mIndex;
    }

    public Rank getBottomNeighbour() {
        if (this != Rank.M1) {
            return Rank.valueOf(this.getIndex() - 1);
        } else {
            return null;
        }
    }

    public Rank getTopNeighbour() {
        if (this != Rank.M8) {
            return Rank.valueOf(this.getIndex() + 1);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(name().charAt(1));
    }
}
