package tictactoe;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
	public Button sequence = new Button("Sequence");
	public TextField seq = new TextField();
	public Button seqcheck = new Button("Go!");

	public Button playPP = new Button("Play PvP");
	public Button playPE = new Button(("Play PvE"));
	public TextField p1 = new TextField();
	public TextField p2 = new TextField();

	Scene window;
	BorderPane borderPane = new BorderPane();
	GridPane gridPane = new GridPane();
	HBox topLine = new HBox();
	HBox bottomLine = new HBox();
	HBox bottomRight = new HBox();
	HBox bottomRL = new HBox();
	HBox bottomRC = new HBox();

	Scene menu;
	BorderPane borderPane2 = new BorderPane();

	Tictactoe ttt = new Tictactoe();

	int historyHelp;

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

		for (var i = 0; i < this.fields.size(); i++) {

			gridPane.add(this.fields.get(i), i % 3, i / 3);
		}
	}

	public void setTop() {

		var topLeft = new HBox();
		topLeft = setTopLeft(topLeft);

		var topCenter = new HBox();
		topCenter = setTopCenter(topCenter);

		var topRight = new HBox();
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

		var score = new Label(":");
		score.setFont(Font.font("Times New Roman", 24));

		var topCL = new HBox();
		topCL.setAlignment(Pos.CENTER);
		topCL.setPrefWidth(90);
		topCL.getChildren().add(this.scoreP1);

		var topCC = new HBox();
		topCC.setAlignment(Pos.CENTER);
		topCC.setPrefWidth(20);
		topCC.getChildren().add(score);

		var topCR = new HBox();
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

		var bottomLeft = new HBox();
		bottomLeft = setBottomLeft(bottomLeft);

		setBottomRight();

		this.bottomLine.setAlignment(Pos.CENTER);
		this.bottomLine.setPrefHeight(50);
		this.bottomLine.getChildren().addAll(bottomLeft, this.bottomRight);
	}

	public HBox setBottomLeft(HBox bottomLeft) {

		this.instruction.setFont(Font.font("Times New Roman", 24));

		bottomLeft.setAlignment(Pos.CENTER);
		bottomLeft.setPrefWidth(250);
		bottomLeft.getChildren().add(instruction);

		return bottomLeft;
	}

	public void setBottomRight() {

		this.replay.setFont(Font.font("Times New Roman", 22));
		this.replay.setVisible(false);
		this.replay.setFocusTraversable(false);

		this.sequence.setFont(Font.font("Times New Roman", 22));
		this.sequence.setVisible(true);
		this.sequence.setFocusTraversable(false);

		this.seq.setFont(Font.font("Times New Roman", 18));
		this.seq.setVisible(true);
		this.seq.setPromptText("Seq., e.g.: 5734");
		this.seq.setFocusTraversable(false);

		this.seqcheck.setFont(Font.font("Times New Roman", 22));
		this.seqcheck.setVisible(true);
		this.seqcheck.setFocusTraversable(false);

		this.playAgain.setFont(Font.font("Times New Roman", 22));
		this.playAgain.setVisible(false);
		this.playAgain.setFocusTraversable(false);

		this.quit.setFont(Font.font("Times New Roman", 22));
		this.quit.setVisible(false);
		this.quit.setFocusTraversable(false);

		this.bottomRL.setAlignment(Pos.CENTER);
		this.bottomRL.setPrefWidth(180);
		this.setBottomRL(1);

		this.bottomRC.setAlignment(Pos.CENTER);
		this.bottomRC.setPrefWidth(130);
		this.setBottomRC(0);

		var bottomRR = new HBox();
		bottomRR.setAlignment(Pos.CENTER);
		bottomRR.setPrefWidth(130);
		bottomRR.getChildren().add(this.quit);


		this.bottomRight.setAlignment(Pos.CENTER);
		this.bottomRight.setPrefWidth(450);
		this.bottomRight.getChildren().addAll(this.bottomRL, this.bottomRC, bottomRR);

	//	return bottomRight;
	}

	public void setBottomRL(int i) {

		this.bottomRL.getChildren().clear();
		if (i == 0) {
			this.bottomRL.getChildren().add(this.replay);

		} else if (i == 1) {

			this.bottomRL.getChildren().add(this.sequence);

		} else if (i == 2) {

			this.bottomRL.getChildren().addAll(this.seq);
		}
	}

	public void setBottomRC(int i) {

		this.bottomRC.getChildren().clear();
		if (i == 0) {

			this.bottomRC.getChildren().add(this.playAgain);

		} else if (i == 1) {

			this.bottomRC.getChildren().add(this.seqcheck);
		}
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

		var playPvP = new HBox();
		playPvP.setAlignment(Pos.CENTER_LEFT);
		playPvP.setPrefWidth(200);
		playPvP.getChildren().add(this.playPP);

		var playPvE = new HBox();
		playPvE.setAlignment(Pos.CENTER_RIGHT);
		playPvE.setPrefWidth(200);
		playPvE.getChildren().add(this.playPE);

		var playMode = new HBox();
		playMode.setAlignment(Pos.CENTER);
		playMode.setPrefWidth(500);
		playMode.getChildren().addAll(playPvP, playPvE);

		var playerField1 = new HBox();
		playerField1.setAlignment(Pos.TOP_CENTER);
		playerField1.setPrefWidth(250);
		playerField1.getChildren().add(p1);

		var playerField2 = new HBox();
		playerField2.setAlignment(Pos.TOP_CENTER);
		playerField2.setPrefWidth(250);
		playerField2.getChildren().add(p2);

		var players = new HBox();
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

			sequence.setOnAction(e -> enterSequence());
			seqcheck.setOnAction(e -> getSequence());

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

		var xi = new Image("tictactoe/X.png");
		var x = new ImageView(xi);
		x.setFitHeight(180);
		x.setFitWidth(180);

		return x;
	}

	public ImageView getO() {

		var oi = new Image("tictactoe/O.png");
		var o = new ImageView(oi);
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

		this.replay.setVisible(true);
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

				this.instruction.setText("Replay...");
				this.replayG();

			} else if (e.getSource() == playAgain) {

				ttt.resetGame();
				this.fields.forEach((b) -> b.setGraphic(null));
				this.setButtonState(0);
				this.setBottomRL(1);

				this.instruction.setText("");

				if (ttt.getMode() == 1 && ttt.getCurrentPlayer() == -1) {

					this.compMove();
				}

			}
		}
	}

	private void replayG() {

		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				try {

					Thread.sleep(550);

				} catch (InterruptedException ignored) {}

				return null;
			}
		};

		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
                        /* DO SOMETHING AFTER TIMER ENDS */
				replayMove();
			}
		});

		if (this.historyHelp < ttt.getHistoryLenght()) {

			new Thread(sleeper).start();

		}
	}

	private void replayMove() {

		int p = ttt.getCurrentPlayer();
		var i = this.historyHelp;

		if (ttt.getHistory(i) != 0) {

			ttt.moveG(ttt.getHistory(i));

			if (p == 1) {

				this.intToButton(ttt.getHistory(i)).setGraphic(this.getX());

			} else {

				this.intToButton(ttt.getHistory(i)).setGraphic(this.getO());
			}

			ttt.setCurrentPlayer(-p);
		}
		this.historyHelp++;
		if (this.historyHelp < ttt.getHistoryLenght()) {

			this.replayG();

		} else {

			this.historyHelp = 0;
			this.instruction.setText("Replay ended");
			this.setButtonState(1);
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

			this.replay.setVisible(true);
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
			this.setBottomRL(1);

		} else if (e.getSource() == this.playPE) {

			this.player1.setText(this.p1.getText());
			this.player2.setText("Computer");

			if ("".equals(this.p1.getText())) {
				this.player1.setText("Player 1");
			}

			ttt.setMode(1);

			this.setButtonState(0);
			this.setBottomRL(1);
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
	private void pvP(Button b, int i) {

		int p = ttt.getCurrentPlayer();

		if (ttt.checkG(i)) {

			this.setBottomRL(0);
			this.setBottomRC(0);

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

			this.setBottomRL(0);
			this.setBottomRC(0);

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
	private void seqMove(Button b, int i) {

		this.setBottomRL(0);
		this.setBottomRC(0);

		int p = ttt.getCurrentPlayer();

		ttt.moveG(i);

		if (p == 1) {

			b.setGraphic(this.getX());

		} else {

			b.setGraphic(this.getO());
		}

		ttt.setCurrentPlayer(-p);
	}

	//
	private void compMove() {

		Task<Void> sleeper = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				try {

					Thread.sleep(650);

				} catch (InterruptedException ignored) {}

				return null;
			}
		};

		sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {

				exeCompMove();
			}
		});

		new Thread(sleeper).start();
	}

	private void exeCompMove() {

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

	//
	private void enterSequence() {

		this.setBottomRL(2);
		this.setBottomRC(1);
	}

	//
	private void getSequence() {

		var s = this.seq.getText();

		setSequenceG(s);
	}

	//
	public void setSequenceG(String s) {

		int l = s.length();

		var c = new int[l];

		for (var i = 0; i < l; i++) {

			try {

				c[i] = Integer.parseInt(String.valueOf(s.charAt(i)));

			} catch (Exception e) {

				this.instruction.setText("No correct sequence!");
			}
		}

		if (!ttt.checkSequenceG(c)) {

			this.instruction.setText("No correct sequence!");

		} else {

			this.seq.setText("");
			this.instruction.setText("");

			for (var i = 0; i < l; i++) {

				this.seqMove(intToButton(c[i]), c[i]);

			}
			if (ttt.gamestatus() != 2) {

				this.setButtonState(1);

				resultG();

			} else if (ttt.getMode() == 1 && ttt.getCurrentPlayer() == -1) {

				this.compMove();

			}
		}
	}

}
