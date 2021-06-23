package JavaFX;

import framework.Player;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import rummikub_controller.GameMove;
import rummikub_game.Rummikub;

import java.awt.*;

public class Controller implements Player {

    public ImageView button_sortForGroup;
    public ImageView button_finishOrDraw;
    public ImageView button_sortForRun;
    public ImageView button_reset;

    public AnchorPane anchorPane_P1;
    public Label label_nameP1;
    public Label label_rackP1;
    public Label label_scoreP1;
    public AnchorPane anchorPane_P2;
    public Label label_nameP2;
    public Label label_rackP2;
    public Label label_scoreP2;
    public AnchorPane anchorPane_P3;
    public Label label_nameP3;
    public Label label_rackP3;
    public Label label_scoreP3;
    public AnchorPane anchorPane_P4;
    public Label label_nameP4;
    public Label label_rackP4;
    public Label label_scoreP4;

    public AnchorPane anchorPane_gameMessage;
    public Label label_gameMessage;

    public ImageView button_openContextMenu;
    public AnchorPane anchorPane_contextMenu;
    public Label button_toMainMenu;
    public Label button_toSettings;
    public Label button_startNewGame;
    public Label button_quit;

    public ImageView board_0201;
    public ImageView board_0204;


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


    public void sortForGroupClicked(MouseEvent mouseEvent) {
    }

    public void finsishOrDrawClicked(MouseEvent mouseEvent) {

        anchorPane_gameMessage.setVisible(false);
    }

    public void sortForRunClicked(MouseEvent mouseEvent) {
    }

    public void resetClicked(MouseEvent mouseEvent) {
    }

    public void openContextMenu(MouseEvent mouseEvent) {

        anchorPane_contextMenu.setVisible(true);
    }

    public void openMainMenu(MouseEvent mouseEvent) {

        anchorPane_gameMessage.setVisible(true);
        label_gameMessage.setText("Main Menu is open");
    }

    public void openSettings(MouseEvent mouseEvent) {

        anchorPane_gameMessage.setVisible(true);
        label_gameMessage.setText("Settings are open");
    }

    public void startNewGame(MouseEvent mouseEvent) {

        anchorPane_gameMessage.setVisible(true);
        label_gameMessage.setText("New Game started");
    }

    public void quit(MouseEvent mouseEvent) {

        anchorPane_gameMessage.setVisible(true);
        label_gameMessage.setText("You just quited");
    }

    public void closeContextMenu(MouseEvent mouseEvent) {

        anchorPane_contextMenu.setVisible(false);
    }

    public void board0201(MouseEvent mouseEvent) {

        board_0201.setVisible(false);
    }
}
