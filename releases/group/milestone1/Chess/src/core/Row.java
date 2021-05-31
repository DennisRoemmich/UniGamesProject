package core;

public enum Row {

    _1_(0), _2_(1), _3_(2), _4_(3), _5_(4), _6_(5), _7_(6), _8_(7);

    private final int index;

    Row(int i) {
        index = i;
    }

    public static Row valueOf(char c) {
        return valueOf("_" + c + "_");
    }

    public static Row valueOf(int i) {
        char c = (char)(49 + i); // 49 is the decimal representation of the char '1'
        return valueOf(c);
    }

    public int getIndex(){
        return index;
    }
}
