package oldstuff;

public enum Direction {
    LEFT(0), UPLEFT(1), UPRIGHT(2), RIGHT(3), DOWNRIGHT(4), DOWNLEFT(5);

    private int index;

    Direction(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Direction getOpposite() {
        switch (this) {
            case LEFT -> {
                return RIGHT;
            }
            case UPLEFT -> {
                return DOWNRIGHT;
            }
            case UPRIGHT -> {
                return DOWNLEFT;
            }
            case RIGHT -> {
                return LEFT;
            }
            case DOWNRIGHT -> {
                return UPLEFT;
            }
            case DOWNLEFT -> {
                return UPRIGHT;
            }
        }
        throw new IllegalStateException();
    }
}
