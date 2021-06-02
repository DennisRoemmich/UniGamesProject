package core.positioning;

public enum Column {

    A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);

    private final int index;

    Column(int i) {
        index = i;
    }

    public static Column valueOf(char c) {
        c = Character.toUpperCase(c);
        try {
            return valueOf(String.valueOf(c));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static Column valueOf(int i) {
        char c = (char)(65 + i); // 65 is the decimal representation of the char 'A'
        return valueOf(c);
    }

    public int getIndex(){
        return index;
    }
}
