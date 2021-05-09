package model;

import java.util.Random;

public class AiPlayer implements Player {
	
	Random random = new Random();
	
	@Override
	public Position getNextInput(GameBoard gameBoard) {
		Position position;
		do {
			position = Position.valueOf(random.nextInt(9) + 1);
		} while (!gameBoard.isFree(position)); 
		return position;
	}
	
}
