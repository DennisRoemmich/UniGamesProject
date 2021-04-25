package ui;

import java.io.Serial;
import java.util.Scanner;

import model.*;

public class ConsolePresenter extends TicTacToePresenter {
	
	private Scanner scanner = new Scanner(System.in);
	
	public ConsolePresenter() {
		ConsoleMessages.printWelcome();
		ConsoleMessages.printTicHelp();
		ConsoleMessages.printLetsGo();
	}
	
	private void printRow(Cell row[]) {
		for (Column column: Column.values()) {
			if (column != Column.LEFT) {
				printColumnSeperator();
			}
			printCell(row[column.toInt()]);
		}
		System.out.println();
	}
	
	private void printRowSeperator() {
		System.out.println("---+---+---");
	}
	
	private void printColumnSeperator() {
		System.out.print("|");
	}
	
	private void printCell(Cell cell) {
		System.out.print(" ");
		System.out.print(cell.toString());
		System.out.print(" ");
	}
	
	@Override
	public CellPosition getNextCellPosition() {
		String input = scanner.nextLine();
		System.out.println();
		int i;
		try {
			i = Integer.parseInt(input);
		} catch (Exception e) {
			ConsoleMessages.printNotANumber(input);
			return getNextCellPosition();
		}
		return positionFromInt(i);
	}
	
	private CellPosition positionFromInt(int i) {

		if(i < 1 || i > 9 ) {
			ConsoleMessages.printNumberNotInRange(i);
		}
		i--;
		
		Row row = Row.fromInt(i /3);
		Column column = Column.fromInt(i%3);
		
		return new CellPosition(row, column);
		
	}

	@Override
	public void displayBoard(Cell[][] cells) {
		for (Row row: Row.values()) {
			printRow(cells[row.toInt()]);
			if(row != Row.BOTTOM) {
				printRowSeperator();
			}
		}
		System.out.println();	
	}

	@Override
	public void displayWinner(Player winner) {
		ConsoleMessages.printWinner(winner.toString());
	}

	@Override
	protected void handleInvalidPosition() {
		ConsoleMessages.printCellIsFilled();
	}
}
