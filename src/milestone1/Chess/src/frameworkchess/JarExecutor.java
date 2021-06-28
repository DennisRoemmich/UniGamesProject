package frameworkchess;

import java.io.File;
import java.io.IOException;

public class JarExecutor {
    public static void run(String fileName) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", getParentPathForFile(fileName) + "/" + fileName);
        pb.directory(new File(getParentPathForFile(fileName)));
        Process p = pb.start();
        StreamController lsr = new StreamController(p.getInputStream());
        Thread thread = new Thread(lsr, "LogStreamReader");
        thread.start();
    }

    private static String getParentPathForFile(String fileName) throws IOException {
        try {
            File jarFile = FileController.getFile(fileName);
            return jarFile.getParentFile().getAbsolutePath();
        } catch (Exception e) {
            PrintToConsole.println("Something didn't work.");
            throw new IOException();
        }
    }
}