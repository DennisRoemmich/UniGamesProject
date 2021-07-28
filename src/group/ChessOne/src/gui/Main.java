package gui;

import gui.ChessBoardNode;
import gui.ChessGUI;
import gui.GuiController;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ChessGUI root = new ChessGUI();
        primaryStage.setTitle("Chess One");
        primaryStage.setScene(new Scene(root, 1400, 1000));
        primaryStage.show();
        GuiController guiController = new GuiController(root);
        root.setOnKeyPressed(guiController);
        root.setBoard(guiController.getChessController().getGame().getBoard());
        guiController.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
