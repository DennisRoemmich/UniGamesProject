package simulation;

/**
 * Represents the gameobject of a Cell
 * @author DR
 *
 */
public class Cell {
	
	//The status of a cell is represented by its enum value
	private ECellStatus mStatus;
	
	/**
	 * Initializes and empty cell
	 */
	public Cell() {
		this.mStatus = ECellStatus.EMPTY;
	}
	
	/**
	 * Initializes a cell with a current status
	 * @param status ECellStatus // Cell status which is either empty or contains X or O
	 */
	public Cell (ECellStatus status) {
		this.mStatus = status;
	}
	
	/**
	 * Sets the status of a cell
	 * @param status ECellStatus // Cell status which is either empty or contains X or O
	 */
	public void setCell(ECellStatus status) {
		this.mStatus = status;
	}
	
	/**
	 * Gets the status of a cell
	 * @return status ECellStatus // Cell status which is either empty or contains X or O
	 */
	public ECellStatus getStatus() {
		return this.mStatus;
	}
	
	/**
	 * Returns the String represantation of the a cell
	 * @return String Value of a cell
	 */
	public String toString() {
		if (mStatus == ECellStatus.O ) {
			return "O";
		} else if (mStatus == ECellStatus.X ) {
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
		return (mStatus == ECellStatus.EMPTY);
	}
	
	/**
	 * Returns if the cell has O value
	 * @return boolean value
	 */
	public boolean isO() {
		return (mStatus == ECellStatus.O);	
	}
	
	/**
	 * Returns if the cell has X value 
	 * @return boolean value
	 */
	public boolean isX() {
		return (mStatus == ECellStatus.X);
	}
}
