package JavaFX;

import framework.GameController;
import framework.Player;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import rummikub_controller.ActionType;
import rummikub_controller.GameMove;
import rummikub_controller.RummikubController;
import rummikub_game.GridTile;
import rummikub_game.Rummikub;
import rummikub_game.TileColor;

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


    private boolean finishButtonState = true;

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

    public void updateGUI() {

        // update board
        updateGUIBoard();

        // update rack
        updateGUIRack();

        // update players
        updateGUIPlayers();

        // update buttons
        updateGUIButtons();

    }

    private void updateGUIBoard() {

        var sketchboard = rummiGame.getSketchBoard();
        var gridWidth = sketchboard.GRID_WIDTH;

        for (var i = 0; i < sketchboard.getBoardSize(); i++) {

            var gridPoint = new Point(i / gridWidth, i % gridWidth);
            var gridTile = sketchboard.getGridTileAt(gridPoint);

            setGridCell(true, gridPoint, gridTile);
        }
        finishButtonState = rummiGame.getSketchBoard().isValid();
    }

    private void updateGUIRack() {

        var currentPlayersRack = rummiGame.getCurrentPlayer().getSketchRack();
        var rackWidth = currentPlayersRack.GRID_WIDTH;

        for (var i = 0; i < currentPlayersRack.getRackSize(); i++) {

            var gridPoint = new Point(i / rackWidth, i % rackWidth);
            var gridTile = currentPlayersRack.getGridTileAt(gridPoint);

            setGridCell(false, gridPoint, gridTile);
        }
    }

    private void setGridCell(boolean m, Point position, GridTile gridTile) {

        ImageView imageView;
        Label label;

        if (m) {

            imageView = getBoardViewAt(position);
            label = getBoardLabelAt(position);

        } else {

            imageView = getRackViewAt(position);
            label = getRackLabelAt(position);
        }

        var color = gridTile.getTile().getTileColor();
        var value = gridTile.getTile().getValue();

        // TODO: set corresponding images
        var nonempty = new Image("");
        var empty = new Image("");


        if (!gridTile.isEmpty()) {

            imageView.setImage(nonempty);

            if (color != TileColor.JOKER) {

                label.setText(Integer.toString(value));
            }
            switch (color) {
                case BLACK -> label.setTextFill(Color.web("#000000"));
                case BLUE -> label.setTextFill(Color.web("#0000ff"));
                case RED -> label.setTextFill(Color.web("#ff0000"));
                case YELLOW -> label.setTextFill(Color.web("#ffff00"));
                default -> label.setText("U+263B");
            }


        } else {

            imageView.setImage(empty);
            label.setText("");
        }
    }

    private ImageView getBoardViewAt(Point position) {

        return new ImageView();
    }

    private Label getBoardLabelAt(Point position) {

        return new Label();
    }

    private ImageView getRackViewAt(Point position) {

        return new ImageView();
    }

    private Label getRackLabelAt(Point position) {

        return new Label();
    }

    private void updateGUIPlayers() {

        // TODO: where are the names of the player?

        var p = rummiGame.getPlayerAmount();
        var tilesImage = " U+26C1";

        label_nameP1.setText("Player 1");
        label_rackP1.setText(rummiGame.getPlayerAt(0).getRack().getSize() + tilesImage);
        label_scoreP1.setText(Integer.toString(rummiGame.getPlayerAt(0).getScore()));

        label_nameP2.setText("Player 2");
        label_rackP2.setText(rummiGame.getPlayerAt(1).getRack().getSize() + tilesImage);
        label_scoreP2.setText(Integer.toString(rummiGame.getPlayerAt(1).getScore()));

        if (p >= 3) {

            label_nameP3.setText("Player 3");
            label_rackP3.setText(rummiGame.getPlayerAt(2).getRack().getSize() + tilesImage);
            label_scoreP3.setText(Integer.toString(rummiGame.getPlayerAt(2).getScore()));

            if (p == 4) {

                label_nameP4.setText("Player 4");
                label_rackP4.setText(rummiGame.getPlayerAt(3).getRack().getSize() + tilesImage);
                label_scoreP4.setText(Integer.toString(rummiGame.getPlayerAt(3).getScore()));

            } else {

                anchorPane_P4.setVisible(false);
            }
        } else {

            anchorPane_P3.setVisible(false);
        }
    }

    private void updateGUIButtons() {

        if (finishButtonState) {

            button_finishOrDraw.setImage(new Image(""));

        } else {

            button_finishOrDraw.setImage(new Image("../../resources/images/buttonDraw.png"));
        }
        anchorPane_contextMenu.setVisible(false);
        anchorPane_gameMessage.setVisible(false);
    }

}
