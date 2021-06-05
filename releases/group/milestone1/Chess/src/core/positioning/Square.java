package core.positioning;

import java.util.ArrayList;
import java.util.List;

public class Square {

    private Rank rank;
    private File file;

    public Square(Rank rank, File file) {
        if(rank == null || file == null) {
            throw new NullPointerException();
        }
        this.rank = rank;
        this.file = file;
    }

    public Square(String name) {
        this.rank = Rank.valueOf(name.charAt(1));
        this.file = File.valueOf(name.charAt(0));
    }

    public Rank getRank() {
        return rank;
    }

    public File getFile() {
        return file;
    }

    public String toString() {
        return file.toString() + rank.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Square)) {
            return false;
        }

        Square p = (Square) o;

        // Compare the data members and return accordingly
        return p.file == this.file && p.rank == this.rank;
    }
    
    @Override
    public int hashCode() {
    	return -1;
    }

    public static Square[] values() {
        Square[] squares = new Square[64];
        for(Rank rank : Rank.values()) {
            for(File file : File.values()) {
                squares[rank.getIndex() * 8 + file.getIndex()] = new Square(rank, file);
            }
        }
        return squares;
    }
    
    //Use super.clone() to create and seed the cloned instance to be returned.
    public Square clone() {
        return new Square(rank, file);
    }

    public Square getNext(Direction direction) {
        try {
            switch (direction) {
                case UP:
                    return new Square(rank.getTopNeighbour(), file);
                case DOWN:
                    return new Square(rank.getBottomNeighbour(), file);
                case LEFT:
                    return new Square(rank, file.getLeftNeighbour());
                case RIGHT:
                    return new Square(rank, file.getRightNeighbour());
                case UP_LEFT:
                    return new Square(rank.getTopNeighbour(), file.getLeftNeighbour());
                case UP_RIGHT:
                    return new Square(rank.getTopNeighbour(), file.getRightNeighbour());
                case DOWN_LEFT:
                    return new Square(rank.getBottomNeighbour(), file.getLeftNeighbour());
                case DOWN_RIGHT:
                    return new Square(rank.getBottomNeighbour(), file.getRightNeighbour());
            }
        } catch (Exception e) {

        }
        return null;
    }

}
