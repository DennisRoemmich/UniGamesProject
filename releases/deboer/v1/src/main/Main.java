package main;

import model.TicTacToe;
import ui.ConsolePresenter;

public class Main {

	public static void main(String[] args) {
		ConsolePresenter presenter = new ConsolePresenter();
		TicTacToe game = new TicTacToe();
		
		game.initCells();
		game.presenter = presenter;
		
		game.startGame();
	}

}