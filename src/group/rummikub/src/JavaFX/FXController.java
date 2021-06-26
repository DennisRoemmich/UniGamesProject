package JavaFX;

import framework.GameController;
import framework.Player;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.simple.JSONObject;
import rummikub_controller.ActionType;
import rummikub_controller.GameMove;
import rummikub_controller.RummikubController;
import rummikub_game.Rummikub;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXController implements Player, Initializable {


    /* VARS */

    private Rummikub rummiGame;
    private RummikubController rummikubController;


    /* OUTLETS */

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
    public ImageView imageView_backGround;
    public AnchorPane rootAnchorPane;


    /* FUNCTIONS */


    Point originPoint = null;

    /**
     * Should be triggered when action on cell of grid is triggered
     * TODO: We need in-game Coordinates with the property Board / Rack
     */
    private void boardGridButtonEvent(Point point){


        if (originPoint  == null ){
            // if grid is empty -> return
            // ...
        }

    }

    private void rackGridButtonEvent(Point point){

    }

    private void makeMove(GameMove move){
        rummikubController.executeMove(move.toJSON());
    }


    /* GUI ACTIONS */



    public void acceptButtonClicked(MouseEvent mouseEvent) {

        System.out.print("hello world");

    }

    public void sortForGroupClicked(MouseEvent mouseEvent) {

        var move = new GameMove(ActionType.SORTGROUP);
        rummikubController.makeMove(move);

        //updateGUI()

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

    /* INTERFACE FUNCTIONS */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        imageView_backGround.fitWidthProperty().bind(rootAnchorPane.widthProperty());
        imageView_backGround.fitHeightProperty().bind(rootAnchorPane.heightProperty());

    }


    @Override
    public JSONObject requestMove(JSONObject inputType) {
        // we don't use that.
        return null;
    }

    @Override
    public void setController(GameController controller) {

        rummikubController = (RummikubController) controller;
        rummiGame = rummikubController.getGame();

    }

}
