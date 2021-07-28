package framework;

public final class TimeKeeper {
    private static final long mod = 100000000000L;
    private static final long divide = 1000000000L;

    public static String timeToString() {
        return ((System.nanoTime() % mod) / divide) + "us";
    }

    public static boolean isTimePointOver(long time) {
        long currentTime = System.currentTimeMillis();
        if(Math.abs(time - currentTime) > 10000000L) {
            return true;
        }
        return currentTime > time;
    }

    public static long remainingUntilTimePoint(long time) {
        long currentTime = System.currentTimeMillis();
        return time - currentTime;
    }

}
