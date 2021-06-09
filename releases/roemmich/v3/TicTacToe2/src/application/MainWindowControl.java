package application;


import java.util.concurrent.TimeUnit;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import run.TicTacToe;
import simulation.GameBoard;

public class MainWindowControl  {
	
	GameBoard mGame = new GameBoard();
	
	//Views
	@FXML
	private Label mLabel;
	
	@FXML
	private Button mButton1;
	
	@FXML
	private Button mButton2;
	@FXML
	private Button mButton3;
	@FXML
	private Button mButton4;
	@FXML
	private Button mButton5;
	@FXML
	private Button mButton6;
	@FXML
	private Button mButton7;
	@FXML
	private Button mButton8;
	@FXML
	private Button mButton9;
	@FXML
	private Button mAiButton;
	
	@FXML
	private Button mReplay;
	
	@FXML
	private Button mPlayAgain;
	
	@FXML
	private TextField mReplayInput;
	

	//Parameter
	boolean mIsAi = false;
	boolean mPlayerXTurn = true;
	boolean mHumanTurn = true;
	
	private Main mMain;
	
	public void setMain(Main main) {
		this.mMain = main;
	}
	
	public Main getMain() {
		return this.mMain;
	}
	
	/**
	 * Clears the current game board
	 */
	public void clearBoard() {
		this.mHumanTurn = true;
		mPlayerXTurn = true;
		
		mLabel.setText("Game in progress...");
		
		mButton1.setText("");
		mButton2.setText("");
		mButton3.setText("");
		mButton4.setText("");
		mButton5.setText("");
		mButton6.setText("");
		mButton7.setText("");
		mButton8.setText("");
		mButton9.setText("");
		mButton1.setDisable(false);
		mButton2.setDisable(false);
		mButton3.setDisable(false);
		mButton4.setDisable(false);
		mButton5.setDisable(false);
		mButton6.setDisable(false);
		mButton7.setDisable(false);
		mButton8.setDisable(false);
		mButton9.setDisable(false);
	}
	
	/**
	 * Button that restarts the game with a new game board instance
	 */
	@FXML
	public void playAgain() {
		
		this.mGame = new GameBoard();
		clearBoard();

	}
	
	/**
	 * Button for showing a particular turn you can enter 
	 */
	@FXML
	public void replayButton() {
		
		clearBoard();
		guiReplay(mGame, getIntegerReplay());
		mLabel.setText("Showing previous turns");
	}

	/**
	 * Button that determines if you want to play vs AI or against a Hotseat player
	 */
	@FXML
	public void setMode() {
		
		if (mIsAi) {
		mAiButton.setText("Enable AI");
		mIsAi = false;
		} else {
			mAiButton.setText("Disable AI");
			mIsAi = true;
		}
		printWinner(mGame);
	}
	
	/**
	 * Retrurns the textfield entry
	 * @return Integer
	 */
	public int getIntegerReplay() {
		try {
			return Integer.parseInt(mReplayInput.getText());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	/**
	 * Method that implements the replay logic
	 * @param game GameBoard
	 * @param input Integer
	 */
	public void guiReplay(GameBoard game, int input) {
		try {
			if (game.getGameLog().isEmpty()) {
				System.out.println("No replay data found!");
			}
			int turn = 0;
			int max = 0;
			
			if (input <= game.getGameLog().size() && input > 0) {
				max = input - 1; 
			} else {
				max = game.getGameLog().size() - 1;
			}
			while (turn <= max) {
				switch (game.getGameLog().get(turn)) {
				
					case 1:	setPlay1();
						break;
					case 2: setPlay2();
						break;
					case 3: setPlay3();
						break;
					case 4: setPlay4();
						break;
					case 5: setPlay5();
						break;
					case 6: setPlay6();
						break;
					case 7: setPlay7();
						break;
					case 8: setPlay8();
						break;
					case 9: setPlay9();
						break;
					default: 
						break;					
				}				
			turn++;			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls the checkWinner method in the TicTacToe class
	 * @return Integer
	 */
	public int winnerGui() {
		return TicTacToe.checkWinner(mGame);
	}
	

	/**
	 * Checks if AI mode is enabled and makes the random AI move
	 */
	public void checkAi() {
		if (mIsAi && mHumanTurn) {
			int random;
			random = mGame.getRandom();
			int index = 0;
			while (index < mGame.getGameLog().size()) {

				if (random == mGame.getGameLog().get(index)) {
					random = mGame.getRandom();
					
					//Otherwise it would count to 1 immediately skipping cell 0!!
					index = -1;
				}
				index++;
				mHumanTurn = false;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
			switch (random) {
			case 1: setPlay1();
				break;
			case 2: setPlay2();
				break;
			case 3: setPlay3();
				break;
			case 4: setPlay4();
				break;
			case 5: setPlay5();
				break;
			case 6: setPlay6();
				break;
			case 7: setPlay7();
				break;
			case 8: setPlay8();
				break;
			case 9: setPlay9();
				break;
			default: 
				break;
			
				
		}
			mHumanTurn = true;
		}
		
	}
	
	/**
	 * Logic for button 1
	 */
	@FXML
	public void setPlay1() {
		if (mPlayerXTurn) {
			mButton1.setText("X");
			mPlayerXTurn = false;
			mButton1.setDisable(true);
			mGame.setX(mGame.toRow(1), mGame.toColumn(1));
		} else {
			mButton1.setText("O");
			mPlayerXTurn = true;
			mButton1.setDisable(true);
			mGame.setO(mGame.toRow(1), mGame.toColumn(1));
		}
		printWinner(mGame);
		mGame.setGameLog(1);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
	}
	
	/**
	 * Logic for button 2
	 */
	@FXML
	public void setPlay2() {
		if (mPlayerXTurn) {
			mButton2.setText("X");
			mPlayerXTurn = false;
			mButton2.setDisable(true);
			mGame.setX(mGame.toRow(2), mGame.toColumn(2));
		} else {
			mButton2.setText("O");
			mPlayerXTurn = true;
			mButton2.setDisable(true);
			mGame.setO(mGame.toRow(2), mGame.toColumn(2));
		}
		printWinner(mGame);
		mGame.setGameLog(2);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
	}
	
	/**
	 * Logic for button 3
	 */
	@FXML
	public void setPlay3() {
		if (mPlayerXTurn) {
			mButton3.setText("X");
			mPlayerXTurn = false;
			mButton3.setDisable(true);
			mGame.setX(mGame.toRow(3), mGame.toColumn(3));
		} else {
			mButton3.setText("O");
			mPlayerXTurn = true;
			mButton3.setDisable(true);
			mGame.setO(mGame.toRow(3), mGame.toColumn(3));
		}
		printWinner(mGame);
		mGame.setGameLog(3);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
	}
	
	/**
	 * Logic for button 4
	 */
	@FXML
	public void setPlay4() {
		if (mPlayerXTurn) {
			mButton4.setText("X");
			mPlayerXTurn = false;
			mButton4.setDisable(true);
			mGame.setX(mGame.toRow(4), mGame.toColumn(4));
		} else {
			mButton4.setText("O");
			mPlayerXTurn = true;
			mButton4.setDisable(true);
			mGame.setO(mGame.toRow(4), mGame.toColumn(4));
		}
		printWinner(mGame);
		mGame.setGameLog(4);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
	}
	
	/**
	 * Logic for button 5
	 */
	@FXML
	public void setPlay5() {
		if (mPlayerXTurn) {
			mButton5.setText("X");
			mPlayerXTurn = false;
			mButton5.setDisable(true);
			mGame.setX(mGame.toRow(5), mGame.toColumn(5));
		} else {
			mButton5.setText("O");
			mPlayerXTurn = true;
			mButton5.setDisable(true);
			mGame.setO(mGame.toRow(5), mGame.toColumn(5));
		}
		printWinner(mGame);
		mGame.setGameLog(5);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
	}
	
	/**
	 * Logic for button 6
	 */
	@FXML
	public void setPlay6() {
		if (mPlayerXTurn) {
			mButton6.setText("X");
			mPlayerXTurn = false;
			mButton6.setDisable(true);
			mGame.setX(mGame.toRow(6), mGame.toColumn(6));
		} else {
			mButton6.setText("O");
			mPlayerXTurn = true;
			mButton6.setDisable(true);
			mGame.setO(mGame.toRow(6), mGame.toColumn(6));
		}
		printWinner(mGame);
		mGame.setGameLog(6);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
	}
	
	/**
	 * Logic for button 7
	 */
	@FXML
	public void setPlay7() {
		if (mPlayerXTurn) {
			mButton7.setText("X");
			mPlayerXTurn = false;
			mButton7.setDisable(true);
			mGame.setX(mGame.toRow(7), mGame.toColumn(7));
		} else {
			mButton7.setText("O");
			mPlayerXTurn = true;
			mButton7.setDisable(true);
			mGame.setO(mGame.toRow(7), mGame.toColumn(7));
		}
		printWinner(mGame);
		mGame.setGameLog(7);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
	}
	
	/**
	 * Logic for button 8
	 */
	@FXML
	public void setPlay8() {
		if (mPlayerXTurn) {
			mButton8.setText("X");
			mPlayerXTurn = false;
			mButton8.setDisable(true);
			mGame.setX(mGame.toRow(8), mGame.toColumn(8));
		} else {
			mButton8.setText("O");
			mPlayerXTurn = true;
			mButton8.setDisable(true);
			mGame.setO(mGame.toRow(8), mGame.toColumn(8));
		}
		printWinner(mGame);
		mGame.setGameLog(8);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
		checkAi();
		}
	}
	
	/**
	 * Logic for button 9
	 */
	@FXML
	public void setPlay9() {
		if (mPlayerXTurn) {
			mButton9.setText("X");
			mPlayerXTurn = false;
			mButton9.setDisable(true);
			mGame.setX(mGame.toRow(9), mGame.toColumn(9));
		} else {
			mButton9.setText("O");
			mPlayerXTurn = true;
			mButton9.setDisable(true);
			mGame.setO(mGame.toRow(9), mGame.toColumn(9));
		}
		printWinner(mGame);
		mGame.setGameLog(9);
		if (winnerGui() != 1 && winnerGui() != 2 && winnerGui() != 0) {
			checkAi();
			}
		
	}
	
	/**
	 * Prints the winner to the GUI and disables all buttons
	 * @param game GameBoard
	 */
	public void printWinner(GameBoard game) {
		if (TicTacToe.checkWinner(game) == 1) {
		mLabel.setText("X Won!");
		disableAllButtons();
		}
		if (TicTacToe.checkWinner(game) == 2) {
		mLabel.setText("O Won!");
		disableAllButtons();
		}
		if (TicTacToe.checkWinner(game) == 0) {
		mLabel.setText("Draw!");
		disableAllButtons();
		}
	}
	
	/**
	 * Helper function to disable all buttons
	 */
	public void disableAllButtons() {
		mButton1.setDisable(true);
		mButton2.setDisable(true);
		mButton3.setDisable(true);
		mButton4.setDisable(true);
		mButton5.setDisable(true);
		mButton6.setDisable(true);
		mButton7.setDisable(true);
		mButton8.setDisable(true);
		mButton9.setDisable(true);
	}
	
}
