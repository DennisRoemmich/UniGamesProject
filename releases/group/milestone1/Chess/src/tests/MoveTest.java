package tests;

import core.positioning.Square;

public class MoveTest implements Test {
    Square[][] moves = new Square[][]{{new Square("e2"), new Square("e4")},
            {new Square("e7"), new Square("e5")},
            {new Square("b1"), new Square("c3")},
            {new Square("f8"), new Square("c4")},
            {new Square("a1"), new Square("b1")},
            {new Square("e8"), new Square("e7")},
            {new Square("d1"), new Square("g4")}};

    public boolean runTest() {
        return true;
    }
}
