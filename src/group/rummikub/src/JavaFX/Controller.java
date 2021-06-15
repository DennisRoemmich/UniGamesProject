package JavaFX;

import framework.Player;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONObject;
import rummikub_controller.GameMove;
import rummikub_game.Rummikub;

import java.awt.*;

public class Controller implements Player {

    private Rummikub rummiGame;

    public void acceptButtonClicked(MouseEvent mouseEvent) {

        System.out.print("hello world");

    }




    private void boardGridButton(Point point){

    }


    @Override
    public JSONObject requestJSONMove() {
        return null;
    }

    @Override
    public Object requestMove(Object type) {
        return null;
    }

    @Override
    public void setGameClass(Rummikub game) {

    }

    @Override
    public GameMove requestGameMove() {
        return null;
    }



}
