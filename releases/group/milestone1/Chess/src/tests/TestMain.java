package tests;

public class TestMain {
    private static Test[] tests = new Test[0];

    public static void main(String args[]) {
        for(Test test : tests) {
            if(test.runTest()) {
                System.out.println("Test succeeded: " + test.getClass().getName());
            } else {
                System.out.println("Test failes: " + test.getClass().getName());
            }
        }
    }
}
