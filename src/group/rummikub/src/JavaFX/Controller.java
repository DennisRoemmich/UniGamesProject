package JavaFX;

import framework.Player;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import rummikub_controller.GameMove;
import rummikub_game.Rummikub;

import java.awt.*;

public class Controller implements Player {

    public AnchorPane anchorPane_contextMenu;
    private Rummikub rummiGame;

    public void acceptButtonClicked(MouseEvent mouseEvent) {

        System.out.print("hello world");

    }



    // private Point firstTouch =

    private void boardGridButtonEvent(Point point){

    }

    private void rackGridButtonEvent(Point point){

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


    public void closeContextMenu(MouseEvent mouseEvent) {
        anchorPane_contextMenu.setVisible(false);
    }

    public void openContextMenu(MouseEvent mouseEvent) {
        anchorPane_contextMenu.setVisible(true);
    }
}
