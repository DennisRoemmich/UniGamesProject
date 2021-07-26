package framework;

public class TimeKeeper {
    private static long startTime = System.nanoTime();

    public static void startMeasure() {
        startTime = System.nanoTime();
    }

    public static long getMeasure() {
        return System.nanoTime() - startTime;
    }

    public static void printCurrent() {
        System.out.println("Current durarion: " + getMeasure());
    }
}
