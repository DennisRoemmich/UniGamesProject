package simulation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game object of a game 
 * @author DR
 *
 */
public class GameBoard {
	
	static final int COLUMN = 3;
	static final int ROW = 3;
	
	private Random mRand = new Random();
	private Cell[][] mCellArray; 
	private List<Integer> mGamelog = new LinkedList<>();
		
	/**
	 * Initializes the internal Cell array
	 */
	public GameBoard() {
		this.mCellArray = buildCellArray(COLUMN, ROW);
	}
	
	/**
	 * Builds a new 2D cell array
	 * @param width Width of the array
	 * @param height Height of the array
	 * @return Cell[][] New 2D cell array of type Cell
	 */
	private static Cell[][] buildCellArray(int column, int row) {
		Cell[][] cellArray = new Cell[column][row];
			
		for (int columnIndex = 0; columnIndex < column; columnIndex++) {
			for (int rowIndex = 0; rowIndex < row; rowIndex++) {
				Cell cell = new Cell();
				cellArray[rowIndex][columnIndex] = cell;
			}
		}
		return cellArray;
	}
		
	/**
	 * Getter method for the gamelog list // I don't know how to fix this for SonarLint...
	 * @return LinkedList The gamelog list
	 */
	public List<Integer> getGameLog() {
		return this.mGamelog;
	}
	
	/**
	 * Setter method for the gamelog list
	 * @param newInput int Newest element in the list
	 */
	public void setGameLog(int newInput) {
		this.mGamelog.add(newInput);
	}
	
	/**
	 * Removes the last element in the gamelog list
	 * @param lastInput int last element
	 */
	public void removeLastGameLog(int lastInput) {
		this.mGamelog.remove(lastInput);
	}
	
	/**
	 * Getter method for the Cell object
	 * @param column x-position of the cell
	 * @param row y-position of the cell
	 * @return Cell gameobject
	 */
	public Cell getCell(int column, int row) {
		return this.mCellArray[column][row];
	}
	
	/**
	 * Sets a Cell to the O value
	 * @param column x-position of the cell
	 * @param row y-position of the cell
	 */
	public void setO(int column, int row) {
		 getCell(column, row).setCell(ECellStatus.O);
	}
	
	/**
	 * Sets a Cell to the X value
	 * @param column x-position of the cell
	 * @param row y-position of the cell
	 */
	public void setX(int column, int row) {
		 getCell(column, row).setCell(ECellStatus.X);
	}
	
	/**
	 * Sets a Cell to the empty value
	 * @param column x-position of the cell
	 * @param row y-position of the cell
	 */
	public void clearCell(int column, int row) {
		 getCell(column, row).setCell(ECellStatus.EMPTY);
	}
	
	/**
	 * Transforms the integer input to the corresponding row value
	 * @param input The input value (via e.g. the Scanner)
	 * @return int corresponding row value
	 */
	public int toRow(int input) {
		 return (input - 1) / 3;
	}
	
	/**
	 * Transforms the integer input to the corresponding column
	 * @param input The input value (via e.g. the Scanner)
	 * @return int corresponding column 
	 */
	public int toColumn(int input) {
		 return (input - 1) % 3;
	}
	
	/**
	 * Clears the game board
	 */
	public void clearGame() {
		for (int columnIndex = 0; columnIndex < COLUMN; columnIndex++) {
			for (int rowIndex = 0; rowIndex < ROW; rowIndex++) {
				clearCell(columnIndex, rowIndex);
			}
		}
	}
	
	/**
	 * Clears the game log
	 */
	public void clearGameLog() {
		for (int index = getGameLog().size() - 1; index >= 0; index--) {
		removeLastGameLog(index);
		}
	}
	
	/**
	 * Gets a random integer variable between 1 and 9
	 * @return Integer value
	 */
	public int getRandom() {
		return this.mRand.nextInt(9) + 1;
		}
	
	/**
	 * Saves the gameboard to a String value
	 * @return String Corresponding String value of the game board
	 */
	@Override
	public String toString()  {
		StringBuilder output = new StringBuilder();
		
		for (int rowIndex = 0; rowIndex < ROW; rowIndex++) {
			for (int columnIndex = 0; columnIndex < COLUMN; columnIndex++) {
				
				output.append(getCell(rowIndex, columnIndex).toString());
				output.append(" ");
			}
			output.append("\n");
		}
		return output.toString();
	}
}
