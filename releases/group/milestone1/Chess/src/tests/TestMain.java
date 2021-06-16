package tests;

/**
 * Main class for testing.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class TestMain {
    
	private static Test[] tests = new Test[]{new CastlingTest(), new CheckMateTest(), new EnPassantTest()};

    public static void main(String[] args) {
        for (Test test : tests) {
            if (test.runTest()) {
                System.out.println("Test succeeded: " + test.getClass().getName());
            } else {
                System.out.println("Test failed: " + test.getClass().getName());
            }
        }
    }
}
