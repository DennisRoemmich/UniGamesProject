package simulation;

/**
 * Represents the gameobject of a Cell
 * @author DR
 *
 */
public class Cell {
	
	//The status of a cell is represented by its enum value
	private ECellStatus status;
	
	/**
	 * Initializes and empty cell
	 */
	public Cell() {
		this.status = ECellStatus.EMPTY;
	}
	
	/**
	 * Initializes a cell with a current status
	 * @param status ECellStatus // Cell status which is either empty or contains X or O
	 */
	public Cell (ECellStatus status) {
		this.status = status;
	}
	
	/**
	 * Sets the status of a cell
	 * @param status ECellStatus // Cell status which is either empty or contains X or O
	 */
	public void setCell(ECellStatus status) {
		this.status = status;
	}
	
	/**
	 * Gets the status of a cell
	 * @return status ECellStatus // Cell status which is either empty or contains X or O
	 */
	public ECellStatus getStatus() {
		return this.status;
	}
	
	/**
	 * Returns the String represantation of the a cell
	 * @return String Value of a cell
	 */
	public String toString() {
		if (status == ECellStatus.O ) {
			return "O";
		} else if (status == ECellStatus.X ) {
			return "X";
		} else {
			return ".";
		}
	}
	
	/**
	 * Returns if the cell is empty or not
	 * @return boolean value
	 */
	public boolean isEmpty() {
		return (status == ECellStatus.EMPTY);
	}
	
	/**
	 * Returns if the cell has O value
	 * @return boolean value
	 */
	public boolean isO() {
		return (status == ECellStatus.O);	
	}
	
	/**
	 * Returns if the cell has X value 
	 * @return boolean value
	 */
	public boolean isX() {
		return (status == ECellStatus.X);
	}
}
