package console;

import model.Result;

public class ConsoleMessages {
	
	public static void printWelcome() {
		System.out.println("Welcome to TicTacToe! I guess you know the rules...");
		System.out.println();
	}
	
	public static void printTicHelp() {
		System.out.println("To tic a cell, just enter it's number and press enter");
		System.out.println("These are the numbers of the cells:");
		System.out.println();
		System.out.println(" 1 | 2 | 3 ");
		System.out.println("---+---+---");
		System.out.println(" 4 | 5 | 6 ");
		System.out.println("---+---+---");
		System.out.println(" 7 | 8 | 9 ");
		System.out.println();
	}
	
	public static void print(String winnerName) {
		System.out.println("Congratulations! Player " + winnerName + " won!");
		System.out.println();
	}
	
	public static void print(Result result) {
		switch (result) {
			case IN_PROGRESS:
				System.out.println("The game couldn't be more exciting!");
				break;
			case X_WON, O_WON, TIE:
				System.out.println("Game Over! " + result.toString() + "!");
				break;
			case NOT_STARTED:
				System.out.println("The game hasn't started yet...");
			
			}
	}
	
	public static void printLetsGo() {
		System.out.println("Let's go! Player 0 starts");
		System.out.println();
	}
	
	public static void printLeaveMessage() {
		System.out.println("Cheers.");
	}
	
	public static void printTicACell() {
		System.out.println("Which cell do you want to tic?");
		System.out.println();
	}
	
	public static void printNotANumber(String text) {
		System.out.println("\"" + text + "\" doesn't seem to be a number :(");
		System.out.println("Try entering a number from 1 to 9");
		System.out.println();
	}
	
	public static void printNumberNotInRange(int number) {
		System.out.println("The enter number " + number + " isn't in the range from 1 to 9");
		System.out.println("Try entering a number from 1 to 9");
		System.out.println();
	}
	
	public static void printCellIsFilled() {
		System.out.println("The chosen Cell is already filled, urgh :(");
		System.out.println("Try choosing a free cell :)");
		System.out.println();
	}
}
