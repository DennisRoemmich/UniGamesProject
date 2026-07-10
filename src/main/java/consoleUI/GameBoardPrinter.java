package consoleUI;

import model.Column;
import model.GameBoard;
import model.Position;
import model.Row;
import model.Sign;

public final class GameBoardPrinter {
	
	private GameBoardPrinter() { /* Prevent Initialization */ };
	
	public static void print(GameBoard gameBoard) {
		for(Row row: Row.values()){
			for(Position position: row.getPositions()) {
				if(position.getColumn() != Column.LEFT) {
					printColumnSeperator();
				}
				printSign(gameBoard.getSign(position));
			}
			System.out.println();
			if(row != Row.BOTTOM) {
				printRowSeperator();
			} else {
				System.out.println();
			}
		}
	}
	
	private static void printRowSeperator() {
		System.out.println("---+---+---");
	}
	
	private static void printColumnSeperator() {
		System.out.print("|");
	}
	
	private static void printSign(Sign  sign) {
		System.out.print(" ");
		System.out.print(sign.toString());
		System.out.print(" ");
	}
}
