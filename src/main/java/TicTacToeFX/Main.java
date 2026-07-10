package TicTacToeFX;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    SampleController controller = new SampleController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gameGUI.fxml"));
        primaryStage.setTitle("TicTacTest");
        primaryStage.setScene(new Scene(root, 548, 684));
        primaryStage.show();

        var loader = new FXMLLoader(getClass().getResource(
                "gameGUI.fxml"));
       controller = loader.getController();

    }


    public static void main(String[] args) {

        /*
        var player1 = "Player 1";
        var player2 = "Player 2";

        // if application was started with arguments, they are used as player names

        if (args.length > 0){
            player1 = args[0];
        }

        if (args.length > 1){
            player2 = args[1];
        }

        var game = new TicTacToe(player1, player2);
        game.startGame();
        */

        launch(args)

        ;
    }
}
