package tictactoe;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class GUI extends Application implements EventHandler<ActionEvent> {

	Button button1 = new Button();
	Button button2 = new Button();
	Button button3 = new Button();
	Button button4 = new Button();
	Button button5 = new Button();
	Button button6 = new Button();
	Button button7 = new Button();
	Button button8 = new Button();
	Button button9 = new Button();

	ArrayList<Button> fields = createButtons();

	public Label player1 = new Label("Player 1");
	public Label player2 = new Label("Player 2");
	public Label scoreP1 = new Label("0");
	public Label scoreP2 = new Label("0");

	public Label instruction = new Label("Welcome to Tictactoe!");
	public Button replay = new Button("Replay");
	public Button playAgain = new Button("Play again");
	public Button quit = new Button("Quit :(");

	public Button playPP = new Button("Play PvP");
	public Button playPE = new Button(("Play PvE"));
	public TextField p1 = new TextField();
	public TextField p2 = new TextField();

	Scene window;
	BorderPane borderPane = new BorderPane();
	GridPane gridPane = new GridPane();
	HBox topLine = new HBox();
	HBox bottomLine = new HBox();

	Scene menu;
	BorderPane borderPane2 = new BorderPane();

	Tictactoe ttt = new Tictactoe();

	boolean buttonState1 = true;
	boolean buttonState2 = false;

	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.setGridPane();
		this.setTop();
		this.setBottom();

		this.setBorderPane();

		window = new Scene(borderPane, 700, 700);

		this.setBorderPane2();

		menu = new Scene(borderPane2, 700, 700);

		playPP.setOnAction(e -> playG(e, primaryStage));
		playPE.setOnAction(e -> playG(e, primaryStage));

		quit.setOnAction(e -> quitG(primaryStage));

		primaryStage.setScene(menu);
		primaryStage.show();

		setButtonAction();
	}

	@Override
	public void handle(ActionEvent actionEvent) {

	}

	public ArrayList<Button> createButtons() {

		ArrayList<Button> fields = new ArrayList<>();
		fields.add(button1);
		fields.add(button2);
		fields.add(button3);
		fields.add(button4);
		fields.add(button5);
		fields.add(button6);
		fields.add(button7);
		fields.add(button8);
		fields.add(button9);

		for (Button b : fields) {
			b.setPrefSize(200, 200);
			b.setFocusTraversable(false);
		}

		return fields;
	}

	public void setBorderPane() {

		this.borderPane.setCenter(this.gridPane);
		this.borderPane.setTop(this.topLine);
		this.borderPane.setBottom(this.bottomLine);
	}

	public void setGridPane() {

		this.gridPane.getColumnConstraints().add(new ColumnConstraints(200));
		this.gridPane.getColumnConstraints().add(new ColumnConstraints(200));
		this.gridPane.getColumnConstraints().add(new ColumnConstraints(200));
		this.gridPane.setHgap(10);
		this.gridPane.setVgap(10);
		this.gridPane.setAlignment(Pos.CENTER);

		for (int i = 0; i < this.fields.size(); i++) {

			gridPane.add(this.fields.get(i), i % 3, i / 3);
		}
	}

	public void setTop() {

		HBox topLeft = new HBox();
		topLeft = setTopLeft(topLeft);

		HBox topCenter = new HBox();
		topCenter = setTopCenter(topCenter);

		HBox topRight = new HBox();
		topRight = setTopRight(topRight);

		this.topLine.setAlignment(Pos.CENTER);
		this.topLine.setPrefHeight(50);
		this.topLine.getChildren().addAll(topLeft, topCenter, topRight);
	}

	public HBox setTopLeft(HBox topLeft) {

		this.player1.setFont(Font.font("Times New Roman", 24));

		topLeft.setAlignment(Pos.CENTER);
		topLeft.setPrefWidth(250);
		topLeft.getChildren().add(player1);

		return topLeft;
	}

	public HBox setTopCenter(HBox topCenter) {

		this.scoreP1.setFont(Font.font("Times New Roman", 24));
		this.scoreP2.setFont(Font.font("Times New Roman", 24));

		Label score = new Label(":");
		score.setFont(Font.font("Times New Roman", 24));

		HBox topCL = new HBox();
		topCL.setAlignment(Pos.CENTER);
		topCL.setPrefWidth(90);
		topCL.getChildren().add(this.scoreP1);

		HBox topCC = new HBox();
		topCC.setAlignment(Pos.CENTER);
		topCC.setPrefWidth(20);
		topCC.getChildren().add(score);

		HBox topCR = new HBox();
		topCR.setAlignment(Pos.CENTER);
		topCR.setPrefWidth(90);
		topCR.getChildren().add(this.scoreP2);


		topCenter.setAlignment(Pos.CENTER);
		topCenter.setPrefWidth(200);
		topCenter.getChildren().addAll(topCL, topCC, topCR);

		return topCenter;
	}

	public HBox setTopRight(HBox topRight) {

		this.player2.setFont(Font.font("Times New Roman", 24));

		topRight.setAlignment(Pos.CENTER);
		topRight.setPrefWidth(250);
		topRight.getChildren().add(this.player2);

		return topRight;
	}

	public void setBottom() {

		HBox bottomLeft = new HBox();
		bottomLeft = setBottomLeft(bottomLeft);

		HBox bottomRight = new HBox();
		bottomRight = setBottomRight(bottomRight);

		this.bottomLine.setAlignment(Pos.CENTER);
		this.bottomLine.setPrefHeight(50);
		this.bottomLine.getChildren().addAll(bottomLeft, bottomRight);
	}

	public HBox setBottomLeft(HBox bottomLeft) {

		this.instruction.setFont(Font.font("Times New Roman", 24));

		bottomLeft.setAlignment(Pos.CENTER);
		bottomLeft.setPrefWidth(300);
		bottomLeft.getChildren().add(instruction);

		return bottomLeft;
	}

	public HBox setBottomRight(HBox bottomRight) {

		this.replay.setFont(Font.font("Times New Roman", 22));
		this.replay.setVisible(false);
		this.replay.setFocusTraversable(false);

		this.playAgain.setFont(Font.font("Times New Roman", 22));
		this.playAgain.setVisible(false);
		this.playAgain.setFocusTraversable(false);

		this.quit.setFont(Font.font("Times New Roman", 22));
		this.quit.setVisible(false);
		this.quit.setFocusTraversable(false);

		HBox bottomRL = new HBox();
		bottomRL.setAlignment(Pos.CENTER);
		bottomRL.setPrefWidth(130);
		bottomRL.getChildren().add(this.replay);

		HBox bottomRC = new HBox();
		bottomRC.setAlignment(Pos.CENTER);
		bottomRC.setPrefWidth(130);
		bottomRC.getChildren().add(this.playAgain);

		HBox bottomRR = new HBox();
		bottomRR.setAlignment(Pos.CENTER);
		bottomRR.setPrefWidth(130);
		bottomRR.getChildren().add(this.quit);


		bottomRight.setAlignment(Pos.CENTER);
		bottomRight.setPrefWidth(400);
		bottomRight.getChildren().addAll(bottomRL, bottomRC, bottomRR);

		return bottomRight;
	}

	public void setBorderPane2() {

		this.playPP.setFont(Font.font("Times New Roman", 24));
		this.playPP.setPrefSize(200, 100);
		this.playPP.setFocusTraversable(false);

		this.playPE.setFont(Font.font("Times New Roman", 24));
		this.playPE.setPrefSize(200, 100);
		this.playPE.setFocusTraversable(false);

		this.p1.setFont(Font.font("Times New Roman", 18));
		this.p1.setPromptText("Name Player 1");
		this.p1.setFocusTraversable(false);

		this.p2.setFont(Font.font("Times New Roman", 18));
		this.p2.setPromptText("Name Player 2");
		this.p2.setFocusTraversable(false);

		HBox playPvP = new HBox();
		playPvP.setAlignment(Pos.CENTER_LEFT);
		playPvP.setPrefWidth(200);
		playPvP.getChildren().add(this.playPP);

		HBox playPvE = new HBox();
		playPvE.setAlignment(Pos.CENTER_RIGHT);
		playPvE.setPrefWidth(200);
		playPvE.getChildren().add(this.playPE);

		HBox playMode = new HBox();
		playMode.setAlignment(Pos.CENTER);
		playMode.setPrefWidth(500);
		playMode.getChildren().addAll(playPvP, playPvE);

		HBox playerField1 = new HBox();
		playerField1.setAlignment(Pos.TOP_CENTER);
		playerField1.setPrefWidth(250);
		playerField1.getChildren().add(p1);

		HBox playerField2 = new HBox();
		playerField2.setAlignment(Pos.TOP_CENTER);
		playerField2.setPrefWidth(250);
		playerField2.getChildren().add(p2);

		HBox players = new HBox();
		players.setAlignment(Pos.CENTER);
		players.setPrefSize(500, 300);
		players.getChildren().addAll(playerField1, playerField2);

		this.borderPane2.setCenter(playMode);
		this.borderPane2.setBottom(players);
	}

	public void setButtonAction() {

			button1.setOnAction(e -> triggeredButton(button1, 1));
			button2.setOnAction(e -> triggeredButton(button2, 2));
			button3.setOnAction(e -> triggeredButton(button3, 3));
			button4.setOnAction(e -> triggeredButton(button4, 4));
			button5.setOnAction(e -> triggeredButton(button5, 5));
			button6.setOnAction(e -> triggeredButton(button6, 6));
			button7.setOnAction(e -> triggeredButton(button7, 7));
			button8.setOnAction(e -> triggeredButton(button8, 8));
			button9.setOnAction(e -> triggeredButton(button9, 9));

			replay.setOnAction(e -> resume(e));
			playAgain.setOnAction(e -> resume(e));

	}

	private Button intToButton(int i) {

		return switch (i) {
			case 1 -> this.button1;
			case 2 -> this.button2;
			case 3 -> this.button3;
			case 4 -> this.button4;
			case 5 -> this.button5;
			case 6 -> this.button6;
			case 7 -> this.button7;
			case 8 -> this.button8;
			default -> this.button9;
		};
	}

	public void triggeredButton(Button b, int i) {

		if (buttonState1) {

			if (ttt.getMode() == 0) {

				this.pvP(b, i);

			} else if (ttt.getMode() == 1) {

				this.pvE(b, i);
			}
		}
	}

	public ImageView getX() {

		Image xi = new Image("tictactoe/X.png");
		ImageView x = new ImageView(xi);
		x.setFitHeight(180);
		x.setFitWidth(180);

		return x;
	}

	public ImageView getO() {

		Image oi = new Image("tictactoe/O.png");
		ImageView o = new ImageView(oi);
		o.setFitHeight(180);
		o.setFitWidth(180);

		return o;
	}

	public void resultG() {

		int r = ttt.gamestatus();

		if (r == 1) {

			ttt.setScore(1, ttt.getScore(1)+1);

			this.updateScore(1);

			this.instruction.setText("Player 1 wins!");

		} if (r == -1) {

			ttt.setScore(-1, ttt.getScore(-1)+1);

			this.updateScore(-1);

			if (ttt.getMode() == 0) {

				this.instruction.setText("Player 2 wins!");

			} else if (ttt.getMode() == 1) {

				this.instruction.setText("Computer wins!");
			}


		} else if (r == 0) {

			this.instruction.setText("Draw! Nobody wins!");
		}

		this.playAgain.setVisible(true);
		this.quit.setVisible(true);
	}

	private void updateScore(int p) {

		if (p == 1) {

			int i = ttt.getScore(1);
			this.scoreP1.setText(Integer.toString(i));

		} else if (p == -1) {

			int i = ttt.getScore(-1);
			this.scoreP2.setText(Integer.toString(i));
		}
	}

	private void resume(ActionEvent e) {

		if (buttonState2) {

			if (e.getSource() == replay) {

				ttt.resetReplay();
				this.fields.forEach((b) -> b.setGraphic(null));
				this.setButtonState(2);

				this.replayG();
				this.setButtonState(1);

			} else if (e.getSource() == playAgain) {

				ttt.resetGame();
				this.fields.forEach((b) -> b.setGraphic(null));
				this.setButtonState(0);

				this.instruction.setText("");

				if (ttt.getMode() == 1 && ttt.getCurrentPlayer() == -1) {

					this.compMove();
				}

			}
		}
	}

	private void replayG() {

		this.instruction.setText("Replay...");

		for (int i = 0; i < ttt.getHistoryLenght(); i++) {

			int p = ttt.getCurrentPlayer();

			if (ttt.getHistory(i) != 0) {

				ttt.moveG(ttt.getHistory(i));

			//	ttt.wait(800);

				if (p == 1) {

					this.intToButton(ttt.getHistory(i)).setGraphic(this.getX());

				} else {

					this.intToButton(ttt.getHistory(i)).setGraphic(this.getO());
				}

				ttt.setCurrentPlayer(-p);
			}
		}
	}

	private void setButtonState(int i){

		if (i == 0) {
			this.buttonState1 = true;
			this.buttonState2 = false;

			this.replay.setVisible(false);
			this.playAgain.setVisible(false);
			this.quit.setVisible(false);

		} else if (i == 1) {

			this.buttonState1 = false;
			this.buttonState2 = true;

		//	this.replay.setVisible(true);
			this.playAgain.setVisible(true);
			this.quit.setVisible(true);

		} else if (i == 2) {

			this.buttonState1 = false;
			this.buttonState2 = false;

			this.replay.setVisible(false);
			this.playAgain.setVisible(false);
			this.quit.setVisible(false);
		}
	}

	// waits to execute the next order
	private static void wait(int ms) {

		try {

			Thread.sleep(ms);

		} catch (InterruptedException ex) {

			Thread.currentThread().interrupt();
		}
	}

	//
	private void playG(ActionEvent e, Stage stage) {

		if (e.getSource() == this.playPP) {

			this.player1.setText(this.p1.getText());
			this.player2.setText(this.p2.getText());

			if ("".equals(this.p1.getText())) {
				this.player1.setText("Player 1");
			}
			if ("".equals(this.p2.getText())) {
				this.player2.setText("Player 2");
			}

			ttt.setMode(0);

			this.setButtonState(0);

		} else if (e.getSource() == this.playPE) {

			this.player1.setText(this.p1.getText());
			this.player2.setText("Computer");

			if ("".equals(this.p1.getText())) {
				this.player1.setText("Player 1");
			}

			ttt.setMode(1);

			this.setButtonState(0);
		}

		stage.setScene(this.window);
	}

	//
	private void quitG(Stage stage) {

		ttt.resetGameG();
		this.updateScore(1);
		this.updateScore(-1);

		this.fields.forEach((b) -> b.setGraphic(null));
		this.instruction.setText("Welcome back!");
		this.setButtonState(0);

		stage.setScene(this.menu);
	}

	//
	private void pvP( Button b, int i) {

		int p = ttt.getCurrentPlayer();

		if (ttt.checkG(i)) {

			ttt.moveG(i);

			if (p == 1) {

				b.setGraphic(this.getX());

			} else {

				b.setGraphic(this.getO());
			}

			ttt.setCurrentPlayer(-p);

			if (ttt.gamestatus() != 2) {

				this.setButtonState(1);

				resultG();
			}
		}
	}

	//
	private void pvE (Button b, int i){

		int p = ttt.getCurrentPlayer();

		if (p == 1 && ttt.checkG(i)) {

			ttt.moveG(i);

			b.setGraphic(this.getX());

			ttt.setCurrentPlayer(-p);

			if (ttt.gamestatus() != 2) {

				this.setButtonState(1);

				resultG();

			} else {

				this.compMove();
			}
		}
	}

	//
	private void compMove() {

		int p = ttt.getCurrentPlayer();

		int i = ttt.getMoveG();

		while (!ttt.checkG(i)) {

			i = ttt.getMoveG();
		}

		ttt.moveG(i);

		this.intToButton(i).setGraphic(this.getO());

		ttt.setCurrentPlayer(-p);

		if (ttt.gamestatus() != 2) {

			this.setButtonState(1);

			resultG();
		}
	}

}
