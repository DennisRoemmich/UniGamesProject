package core.positioning;

/**
 * Squares represent the possible positions for chess pieces on a chess board.
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public class Square {

    private Rank mRank;
    private File mFile;

    public Square(Rank rank, File file) {
        if (rank == null || file == null) {
            throw new NullPointerException();
        }
        this.mRank = rank;
        this.mFile = file;
    }

    public Square(String name) {
        this.mRank = Rank.valueOf(name.charAt(1));
        this.mFile = File.valueOf(name.charAt(0));
    }

    public Square(Square square) {
        mRank = square.getRank();
        mFile = square.getFile();
    }

    public Rank getRank() {
        return mRank;
    }

    public File getFile() {
        return mFile;
    }

    public String toString() {
        return mFile.toString() + mRank.toString();
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
        return p.mFile == this.mFile && p.mRank == this.mRank;
    }
    
    @Override
    public int hashCode() {
    	return -1;
    }

    public static Square[] values() {
        Square[] squares = new Square[64];
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                squares[rank.getIndex() * 8 + file.getIndex()] = new Square(rank, file);
            }
        }
        return squares;
    }
    
    public Square getNext(Direction direction) {
        try {
            switch (direction) {
                case UP:
                    return new Square(mRank.getTopNeighbour(), mFile);
                case DOWN:
                    return new Square(mRank.getBottomNeighbour(), mFile);
                case LEFT:
                    return new Square(mRank, mFile.getLeftNeighbour());
                case RIGHT:
                    return new Square(mRank, mFile.getRightNeighbour());
                case UP_LEFT:
                    return new Square(mRank.getTopNeighbour(), mFile.getLeftNeighbour());
                case UP_RIGHT:
                    return new Square(mRank.getTopNeighbour(), mFile.getRightNeighbour());
                case DOWN_LEFT:
                    return new Square(mRank.getBottomNeighbour(), mFile.getLeftNeighbour());
                case DOWN_RIGHT:
                    return new Square(mRank.getBottomNeighbour(), mFile.getRightNeighbour());
                default:
                    throw new IndexOutOfBoundsException();
            }
        } catch (Exception e) {
            return null;
        }
    }

}
