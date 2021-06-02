package core.positioning;

public enum Row {

    M1(0), M2(1), M3(2), M4(3), M5(4), M6(5), M7(6), M8(7);

    private final int index;

    Row(int i) {
        index = i;
    }

    public static Row valueOf(char c) {
        return valueOf("M" + c);
    }

    public static Row valueOf(int i) {
        char c = (char)(49 + i); // 49 is the decimal representation of the char '1'
        return valueOf(c);
    }

    public String toString() {
        return String.valueOf(name().charAt(1));
    }

    public int getIndex(){
        return index;
    }
}
