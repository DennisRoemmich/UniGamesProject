package core.positioning;

public class Square {

    private Rank rank;
    private File file;

    public Square(Rank rank, File file) {
        this.rank = rank;
        this.file = file;
    }

    public Square(String name) {
        try {
        File file = File.valueOf(name.charAt(0));
        Rank rank = Rank.valueOf(name.charAt(1));
            this.rank = rank;
            this.file = file;
        } catch (Exception e) {
            throw e;
        }
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

    public static Square[] values() {
        Square[] squares = new Square[64];
        for(Rank rank : Rank.values()) {
            for(File file : File.values()) {
                squares[rank.getIndex() * 8 + file.getIndex()] = new Square(rank, file);
            }
        }
        return squares;
    }

    public Square clone() {
        return new Square(rank, file);
    }

}
