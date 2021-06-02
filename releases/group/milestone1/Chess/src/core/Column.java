package core;

import java.security.InvalidParameterException;

public enum Column {

    A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7);

    private final int index;

    Column(int i) {
        index = i;
    }

    public static Column valueOf(char c) {
        try {
            Column column = valueOf(String.valueOf(c));
            return column;
        } catch (Exception e) {
            try {
                char captialLetter = (char) (c - 32);
                Column column = valueOf(captialLetter);
                return column;
            } catch (Exception exception) {
                throw new IllegalArgumentException();

            }
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
