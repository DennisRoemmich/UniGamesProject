package main;

import model.*;
import ui.*;

public class Main {
	

	public static void main(String[] args) {

		ConsolePresenter presenter = new ConsolePresenter();
		TicTacToe game = new TicTacToe();
		
		game.initCells();
		presenter.game = game;

		
		while(true) {

			presenter.refreshOutput();
			presenter.getInput();
		}
	}

}
