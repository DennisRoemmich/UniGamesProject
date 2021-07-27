package framework;

public class TimeKeeper {
    private static final long mod = 100000000000L;
    private static final long divide = 1000000000L;

    public static String timeToString() {
        return ((System.nanoTime() % mod) / divide) + "us";
    }
}
