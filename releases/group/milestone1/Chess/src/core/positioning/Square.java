package core.positioning;

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
        try {
            this.rank = Rank.valueOf(name.charAt(1));
            this.file = File.valueOf(name.charAt(0));
        } catch (Exception e) {
        	//Add logic to this catch clause or eliminate it and rethrow the exception automatically.
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

    public Square rightNeighbour(){
        try {
            return new Square(rank, file.getRightNeighbour());
        } catch (Exception e){
            return null;
        }
    }

    public Square leftNeighbour(){
        try {
            return new Square(rank, file.getLeftNeighbour());
        } catch (Exception e){
            return null;
        }
    }

    public Square topNeighbour(){
        try {
            return new Square(rank.getTopNeighbour(), file);
        } catch (Exception e){
            return null;
        }
    }

    public Square bottomNeighbour(){
        try {
            return new Square(rank.getBottomNeighbour(), file);
        } catch (Exception e){
            return null;
        }
    }

    public Square topRightNeighbour(){
        try {
            return new Square(rank.getTopNeighbour(), file.getRightNeighbour());
        } catch (Exception e) {
            return null;
        }
    }

    public Square topLeftNeighbour(){
        try {
            return new Square(rank.getTopNeighbour(), file.getLeftNeighbour());
        } catch (Exception e) {
            return null;
        }
    }

    public Square bottomRightNeighbour(){
        try {
            return new Square(rank.getBottomNeighbour(), file.getRightNeighbour());
        } catch (Exception e) {
            return null;
        }
    }

    public Square bottomLeftNeighbour(){
        try {
            return new Square(rank.getBottomNeighbour(), file.getLeftNeighbour());
        } catch (Exception e) {
            return null;
        }
    }

}
