package consoleUI;

import java.util.Scanner;

import model.*;

public class ConsoleTicTacToeController implements Player {
	
	private ConsoleState state = ConsoleState.PREGAME;
	
	private TicTacToe game = new TicTacToe();
	private Scanner scanner = new Scanner(System.in);
	
	private Player playerA = this;
	private Player playerB = new AiPlayer();
	
	private Position lastEntered;
	private boolean lastEnteredisOld;
	
	private Player currentPlayer;
	
	public ConsoleTicTacToeController() {
		ConsoleMessages.printWelcome();
		ConsoleMessages.printTicHelp();
		newGame();
	}
	
	private void newGame() {
		game = new TicTacToe();
		state = ConsoleState.PREGAME;
		processInput();
	}
	
	private void nextMove() {
		GameBoardPrinter.print(game.getGameBoard());
		Position nextInput = currentPlayer.getNextInput(game.getGameBoard());
		if(game.getGameBoard().isFree(nextInput)) {
			game.ticCell(nextInput);
			if (game.getResult().isFinished()) {
				state = ConsoleState.POSTGAME;
				processInput();
			} else {
				togglePlayer();
				nextMove();
			}
		} else {
			nextMove();
		}
	}
	
	private void togglePlayer() {
		if(state != ConsoleState.INGAME) return;
		if(currentPlayer == playerA) {
			currentPlayer = playerB;
		} else {
			currentPlayer = playerA;
		}
	}
	
	private void processInput() {
		switch (state) {
		case PREGAME:
			processInputPreGame();
			break;
		case INGAME:
			processInputInGame();
			break;
		case POSTGAME:
			processInputPostGame();
			break;
		}
	}
	
	private void processInputPreGame() {
		System.out.println("You can start [s], change your opponent [o] or quit [q].\n");
		String input = scanner.next();
		switch(input) {
		case "q":
			quit();
			break;
		case "o":
			processPlayer();
			break;
		case "s":
			pickBeginner();
			state = ConsoleState.INGAME;
			nextMove();
			break;
		default:
			System.out.println("I didn't get that.");
			processInput();
		}
	}
	
	private void processInputInGame() {
		System.out.println("Enter a Position [1-9], start a new game [n] or quit[q].\n");
		String input = scanner.next();
		switch(input) {
		case "q":
			quit();
			break;
		case "n":
			newGame();
			break;
		default:
			processPosition(input);
		}
	}
	
	private void processInputPostGame() {
		System.out.println("--- Final Game Board ---\n");
		GameBoardPrinter.print(game.getGameBoard());
		ConsoleMessages.print(game.getResult());
		System.out.println("Do you want to start a new game [n], replay the game [r] or quit [q]?");
		String input = scanner.next();
		switch (input) {
		case "q": 
			quit();
			break;
		case "n":
			newGame();
			break;	
		case "r":
			replayGame();
			processInput();
			break;
		default:
			System.out.println("I didn't rocognize that...");
			processInput();
		}
	}
	
	private void replayGame() {
		for(GameBoard board: game.getHistory()) {
			if(board == null) return;
			GameBoardPrinter.print(board);
		}
	}
	
	private void processPlayer() {
		System.out.println();
		System.out.println("Möchtest du gegen einen Menschen oder den Computer spielen? [m/c]");
		String kind = scanner.next();
		switch(kind) {
		case "m":
			playerB = this;
			break;
		case "c":
			playerB = new AiPlayer();
			break;
		case "q": 
			quit();
			break;
		default:
			System.out.println("Das war keine zulässige Eingabe.");
		}
		processInput();
	}
	
	private void processPosition(String id) {
		try {
			lastEntered = Position.valueOf(id);
			lastEnteredisOld = false;
			return;
		} catch (Exception e) {
			try {
				int i = Integer.parseInt(id);
				lastEntered = Position.valueOf(i);
				lastEnteredisOld = false;
				return;
			} catch (Exception f) {
				processInputInGame();
			}
		}
	}
	
	private void pickBeginner() {
		System.out.println("Wer beginnt? [A/B]\n");
		String input = scanner.next();
		switch(input) {
		case "A":
			currentPlayer = playerA;
			break;
		case "B":
			currentPlayer = playerB;
			break;
		case "q": 
			quit();
			break;
		default:
			System.out.println("Das war nicht A oder B!");
			pickBeginner();
		}
	}
	
	private void quit() {
		ConsoleMessages.printLeaveMessage();
		System.exit(0);
	}

	public TicTacToe getGame() {
		return game;
	}

	@Override
	public Position getNextInput(GameBoard gameBoard) {
		lastEnteredisOld = true;
		while(lastEnteredisOld) {
			processInput();
		}
		return lastEntered;
	}
}
