import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//starts game with option of choosing opponent and a new game option.
		try (Scanner in = new Scanner(System.in)){
			int replay = 1;
			do {
				TicTacToe game = new TicTacToe();
				System.out.println(
						"If you want to play against the computer enter 1, if you want to play with a friend enter 2.");
				int selection = in.nextInt();
				if(selection == 1) {
					game.startGameComputer();
				} else if (selection == 2) {
					game.startGame();
				}
				System.out.println("If you want to play again please type in 1, otherwise type in 0.");
				replay = in.nextInt();
			} while (replay == 1);
			
			System.out.println("Thanks for playing!");
			
		}



	}

}
