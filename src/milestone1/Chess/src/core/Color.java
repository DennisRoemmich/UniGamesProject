package core;

public enum Color {
    BLACK(false), WHITE(true);

    private boolean value;

    Color(boolean value) {
        this.value = value;
    }

    public Color getContrary() {
        if(this == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public boolean isWhite() {
        return value;
    }
}
