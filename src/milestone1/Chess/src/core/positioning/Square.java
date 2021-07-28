package core.positioning;

import java.util.Objects;
import java.util.Optional;

/**
 * Squares represent the possible positions for chess pieces on a chess board.
 *
 * @author Jan de Boer, Dennis Roemmich
 */
public class Square {

    private final Rank mRank;
    private final File mFile;

    public Square(Rank rank, File file) {
        if (rank == null || file == null) {
            throw new NullPointerException();
        }
        this.mRank = rank;
        this.mFile = file;
    }

    public Square(Square square) {
        mRank = square.getRank();
        mFile = square.getFile();
    }


    // TODO : Replace usages by valueOf
    public Square(String name) {
        this.mRank = Rank.valueOf(name.charAt(1));
        this.mFile = File.valueOf(name.charAt(0));
    }

    public static Square valueOf(String name) {
        Rank rank = Rank.valueOf(name.charAt(1));
        File file = File.valueOf(name.charAt(0));
        return new Square(rank, file);
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

    public static Square[] values() {
        Square[] squares = new Square[64];
        for (Rank rank : Rank.values()) {
            for (File file : File.values()) {
                squares[rank.getIndex() * 8 + file.getIndex()] = new Square(rank, file);
            }
        }
        return squares;
    }

    public Optional<Square> getNext(Direction direction) {
        try {
            Square square = switch (direction) {
                case UP -> new Square(mRank.getTopNeighbour(), mFile);
                case DOWN -> new Square(mRank.getBottomNeighbour(), mFile);
                case LEFT -> new Square(mRank, mFile.getLeftNeighbour());
                case RIGHT -> new Square(mRank, mFile.getRightNeighbour());
                case UP_LEFT -> new Square(mRank.getTopNeighbour(), mFile.getLeftNeighbour());
                case UP_RIGHT -> new Square(mRank.getTopNeighbour(), mFile.getRightNeighbour());
                case DOWN_LEFT -> new Square(mRank.getBottomNeighbour(), mFile.getLeftNeighbour());
                case DOWN_RIGHT -> new Square(mRank.getBottomNeighbour(), mFile.getRightNeighbour());
                default -> throw new IllegalArgumentException();
            };
            return Optional.of(square);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int getIndex() {
        return mRank.getIndex() * 8 + mFile.getIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return mRank == square.mRank && mFile == square.mFile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mRank, mFile);
    }
}
