package JavaFX;

import framework.GameController;
import framework.Player;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;
import rummikub_controller.ActionType;
import rummikub_controller.GameMove;
import rummikub_controller.RummikubController;
import rummikub_game.GridTile;
import rummikub_game.Rummikub;
import javafx.scene.text.Font;
import rummikub_game.TileColor;


import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXController implements Player, Initializable {

    /* CONSTANTS */

    private final int RACK_ROW_AMOUNT = 2;
    private final int RACK_COLUMN_AMOUNT = 15;
    private final int BOARD_COLUMN_AMOUNT = 15;
    private final int BOARD_ROW_AMOUNT = 7;

    /* VARS */

    FXGridCell[][] boardCells  = new FXGridCell[BOARD_COLUMN_AMOUNT][BOARD_ROW_AMOUNT];
    FXGridCell[][] rackCells  = new FXGridCell[RACK_COLUMN_AMOUNT][RACK_ROW_AMOUNT];

    private Rummikub rummiGame;
    private RummikubController rummikubController;


    /* OUTLETS */

    public AnchorPane anchorPaneBoard;

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

    public ImageView button_closeContextMenu;
    public GridPane gridPane_board;
    public GridPane gridPane_Rack;

    private boolean finishButtonState = true;

    /* FUNCTIONS */


    GamePoint originPoint = null;

    /**
     * Should be triggered when action on cell of grid is triggered
     * TODO: We need in-game Coordinates with the property Board / Rack
     */


    private void boardCellClicked(Point point){

        // valid start of a move
        if (originPoint == null && !boardCells[point.x][point.y].isEmpty()){
            originPoint = new GamePoint(GameArea.BOARD, point);
            return;
        }

        // valid end of a move
        if (originPoint != null && boardCells[point.x][point.y].isEmpty()){

            GameMove move;

            // move is board to board
            if ( originPoint.area == GameArea.BOARD ){
                move = new GameMove(ActionType.ONBOARD, originPoint.point, point);
            } else { // move is rack to board
                move = new GameMove(ActionType.RACKTOBOARD, originPoint.point, point);
            }

            rummikubController.executeMove(move.toJSON());

        }


    }

    private void rackCellClicked(Point point){

        // valid start of a move
        if (originPoint == null && !boardCells[point.x][point.y].isEmpty()){
            originPoint = new GamePoint(GameArea.RACK, point);
            return;
        }

        // valid end of a move
        if (originPoint != null && boardCells[point.x][point.y].isEmpty()){

            GameMove move;

            // move is board to board
            if ( originPoint.area == GameArea.RACK ){
                move = new GameMove(ActionType.ONRACK, originPoint.point, point);
            } else { // move is board to rack
                move = new GameMove(ActionType.BOARDTORACK, originPoint.point, point);
            }

            rummikubController.executeMove(move.toJSON());

        }

    }

    private void makeMove(GameMove move){
        rummikubController.executeMove(move.toJSON());
    }


    /* GUI ACTIONS */

    public void gridClicked(MouseEvent mouseEvent) {


    }

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

        var grid = rummiGame.getSketchBoard().grid;

        for(var x = 0; x < 5; x++){
            for(var y = 0; y < 5; y++){
                if (!grid[x][y].isEmpty()){
                    boardCells[x][y].clear();
                }
            }
        }
    }

    public void resetClicked(MouseEvent mouseEvent) {
    }

    public void openContextMenu(MouseEvent mouseEvent) {

        anchorPane_contextMenu.setVisible(true);
    }

    public void openMainMenu(MouseEvent mouseEvent) {

        anchorPane_gameMessage.setVisible(true);
        label_gameMessage.setText("Main Menu is open");

        var move = new GameMove(ActionType.FINISHMOVE);

        makeMove(move);

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


    FXGridCell dragActionHappening = null;

    private void setUpGrids(){


        // RACK

        var rackCellHeight = gridPane_Rack.heightProperty().divide(2);
        var rackCellWidth = gridPane_Rack.widthProperty().divide(15);

        var padding = 3; // in px

        for(var row_y = 0; row_y < RACK_ROW_AMOUNT; row_y++){
            for(var column_x = 0; column_x < RACK_COLUMN_AMOUNT; column_x++){

                addCellTo("RACK", column_x, row_y, padding, rackCellWidth, rackCellHeight);

            }
        }



        // BOARD

        var boardCellHeight = gridPane_board.heightProperty().divide(BOARD_ROW_AMOUNT);
        var boardCellWidth = gridPane_board.widthProperty().divide(BOARD_COLUMN_AMOUNT);

        padding = 2; // in px

        for(var y = 0; y < BOARD_ROW_AMOUNT; y++){
            for(var x = 0; x < BOARD_COLUMN_AMOUNT; x++){

                addCellTo("BOARD", x, y, padding, boardCellWidth, boardCellHeight);

            }
        }






    }

    // Helper Function for setUpGrids
    private void addCellTo(String gridName, int column_x, int row_y, int padding, DoubleBinding gridWidthProperty, DoubleBinding gridHeightProperty){

        GridPane gridPane;

        if ( gridName == "RACK"){
            gridPane = gridPane_Rack;
        } else {
            gridPane = gridPane_board;
        }

        /* UI ELEMENTS */

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: green;");

        ImageView imageView = new ImageView();
        imageView.setImage(new Image("@../../resources/images/RummikubTile.png"));

        Text text = new Text();
        text.setText("9");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        /* BINDING AND LAYOUT*/

        anchorPane.prefHeightProperty().bind(gridHeightProperty);
        anchorPane.prefWidthProperty().bind(gridWidthProperty);


        imageView.fitHeightProperty().bind(gridHeightProperty.subtract(2*padding));
        imageView.fitWidthProperty().bind(gridWidthProperty.subtract(2*padding));

        imageView.xProperty().set(padding);
        imageView.yProperty().set(padding);

        var textWidth = text.getLayoutBounds().getWidth();
        var textHeigth = text.getLayoutBounds().getHeight();

        text.xProperty().bind(gridWidthProperty.divide(2).subtract(textWidth/2));
        text.yProperty().bind(gridHeightProperty.divide(2));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(text);

        FXGridCell currentCell = new FXGridCell(imageView, text);
        // currentCell.fill(TileColor.BLUE, 7);


        System.out.print(row_y);

        if ( gridName == "RACK"){
            rackCells[column_x][row_y] = currentCell;
        } else {
            boardCells[column_x][row_y] = currentCell;
        }



        int finalY = row_y;
        int finalX = column_x;

        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if ( gridName == "RACK"){
                    rackCellClicked(new Point(finalY, finalX));
                } else {
                    boardCellClicked(new Point(finalY, finalX));
                }

            }
        });

        anchorPane.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if ( !currentCell.isEmpty() ) {

                    dragActionHappening = currentCell;

                    Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                    //Put ImageView on dragboard
                    ClipboardContent cbContent = new ClipboardContent();

                    var image = imageView.getImage();

                    cbContent.putImage(imageView.getImage());
                    cbContent.putString("9");
                    //cbContent.put(DataFormat.)

                    db.setContent(cbContent);
                    imageView.setVisible(false);
                    text.setVisible(false);
                    mouseEvent.consume();

                    if ( gridName == "RACK"){
                        rackCellClicked(new Point(finalY, finalX));
                    } else {
                        boardCellClicked(new Point(finalY, finalX));
                    }

                }

            }

        });

        anchorPane.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {

                // This if statement has to be removed if drag&push will be implemented
                if( currentCell.isEmpty() ){
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    event.consume();
                }

            }
        });

        anchorPane.setOnDragDropped(new EventHandler<DragEvent>() {


            @Override
            public void handle(DragEvent event) {
                // This if statement has to be removed if drag&push will be implemented
                if( currentCell.isEmpty() ) {
                   // System.out.print("Dragged finished: " + finalY + "," + finalX);

                    if ( gridName == "RACK"){
                        rackCellClicked(new Point(finalY, finalX));
                    } else {
                        boardCellClicked(new Point(finalY, finalX));
                    }

                    event.setDropCompleted(true);
                    event.consume();
                }
            }

        });

        anchorPane.setOnDragExited(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                dragActionHappening = null;
            }

        });


        gridPane.add(anchorPane, column_x, row_y);

    }

    /* INTERFACE FUNCTIONS */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        imageView_backGround.fitWidthProperty().bind(rootAnchorPane.widthProperty());
        imageView_backGround.fitHeightProperty().bind(rootAnchorPane.heightProperty());

        gridPane_board.prefHeightProperty().bind(rootAnchorPane.heightProperty().multiply(0.60));
        gridPane_Rack.prefHeightProperty().bind(rootAnchorPane.heightProperty().multiply(0.30));

        button_finishOrDraw.fitHeightProperty().bind(gridPane_Rack.heightProperty().multiply(0.5));
        button_reset.fitHeightProperty().bind(gridPane_Rack.prefHeightProperty().multiply(0.5));
        button_sortForGroup.fitHeightProperty().bind(gridPane_Rack.prefHeightProperty().multiply(0.5));
        button_sortForRun.fitHeightProperty().bind(gridPane_Rack.prefHeightProperty().multiply(0.5));
        button_reset.yProperty();

        button_reset.yProperty().bind(gridPane_Rack.heightProperty().multiply(0.90));

        setUpGrids();

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

