package group;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class HelloGitLabCiTest extends TestCase {
    public void testSayHello() {
        assertEquals(HelloGitLabCi.sayHello(), "Hello GitLab CI! Fer");
    }
}
