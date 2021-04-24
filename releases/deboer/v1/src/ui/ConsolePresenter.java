package ui;

import java.util.Scanner;

import model.*;

public class ConsolePresenter extends TicTacToePresenter {
	
	private Scanner scanner = new Scanner(System.in);

	@Override
	public void refreshOutput() {
		for (Row row: Row.values()) {
			printRow(row);
			if(row != Row.BOTTOM) {
				printRowSeperator();
			}
		}
		System.out.println();	
	}
	
	private void printRow(Row row) {
		for (Column column: Column.values()) {
			
			if (column != Column.LEFT) {
				printColumnSeperator();
			}
			Cell cell = game.getCell(row, column);
			printCell(cell);
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
		switch(cell.player) {
		case NONE:
			System.out.print(" ");
			break;
		case O:
			System.out.print("O");
			break;
		case X:
			System.out.print("X");
			break;
		}
		System.out.print(" ");
	}
	
	public void getInput() {
		int i = scanner.nextInt();
		if(i < 1 || i > 9 ) return;
		i--;
		
		Row row = Row.fromInt(i /3);
		Column column = Column.fromInt(i%3);
		
		if(game.ticCell(row, column)) {
			//refreshOutput();
			checkForWinner();
		}
	}
	
	private void checkForWinner() {
		Player winner = game.getWinner();
		if (winner != Player.NONE) {
			System.out.println("Congratulations! Player " + winner.toString() + " won!");
			System.out.println();
		}
	}
}
