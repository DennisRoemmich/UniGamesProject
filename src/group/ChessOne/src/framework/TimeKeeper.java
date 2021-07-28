package framework;

public final class TimeKeeper {
	
    private static final long MOD = 100000000000L;
    private static final long DIVIDE = 1000000000L;
	
	private TimeKeeper() {
		//Unused
	}

    public static String timeToString() {
        return ((System.nanoTime() % MOD) / DIVIDE) + "us";
    }

    public static boolean isTimePointOver(long time) {
        long currentTime = System.currentTimeMillis();
        if (Math.abs(time - currentTime) > 10000000L) {
            return true;
        }
        return currentTime > time;
    }

    public static long remainingUntilTimePoint(long time) {
        long currentTime = System.currentTimeMillis();
        return time - currentTime;
    }

}
