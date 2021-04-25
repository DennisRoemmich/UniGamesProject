package main;

import model.*;
import ui.*;

public class Main {
	

	public static void main(String[] args) {

		ConsolePresenter presenter = new ConsolePresenter();
		TicTacToe game = new TicTacToe();
		game.initCells();
		game.presenter = presenter;
		
		game.startGame();
	}

}
