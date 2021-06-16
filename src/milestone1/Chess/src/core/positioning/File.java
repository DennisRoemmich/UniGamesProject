package core.positioning;

/**
 * A file represents a column on a chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public enum File {

    A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);

    private final int mIndex;

    File(int index) {
        mIndex = index;
    }

    public static File valueOf(char c) {
        c = Character.toUpperCase(c);
        try {
            return valueOf(String.valueOf(c));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static File valueOf(int index) {
        char c = (char) (65 + index); // 65 is the decimal representation of the char 'A'
        return valueOf(c);
    }

    public int getIndex() {
        return mIndex;
    }

    public File getLeftNeighbour() {
        if (this != File.A) {
            return File.valueOf(this.getIndex() - 1);
        } else {
            return null;
        }
    }

    public File getRightNeighbour() {
        if (this != File.H) {
            return File.valueOf(this.getIndex() + 1);
        } else {
            return null;
        }
    }
}
