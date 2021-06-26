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
import rummikub_game.Tile;
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

    // true if board is valid, else false
    private boolean stateFinishButton = true;

    /* FUNCTIONS */


    GamePoint originPoint = null;

    /**
     * Should be triggered when action on cell of grid is triggered
     * TODO: We need in-game Coordinates with the property Board / Rack
     */


    private void boardCellClicked(Point point){

        debugPrint("cellTest", "boardCellClicked()");

        // valid start of a move
        if (originPoint == null && !boardCells[point.x][point.y].isEmpty()) {
            debugPrint("cellTest", "boardCellClicked(): valid start");
            originPoint = new GamePoint(GameArea.BOARD, point);
            return;
        }

        // valid end of a move
        if (originPoint != null && boardCells[point.x][point.y].isEmpty()){
            debugPrint("cellTest", "boardCellClicked(): valid end");
            GameMove move;

            // move is board to board
            if ( originPoint.area == GameArea.BOARD ) {

                move = new GameMove(ActionType.ONBOARD, originPoint.point, point);

            } else { // move is rack to board

                move = new GameMove(ActionType.RACKTOBOARD, originPoint.point, point);
            }

            move.swapCoordinates(); // :(
            makeMove(move);

        }

    }

    private void rackCellClicked(Point point){

        debugPrint("cellTest", "rackCellClicked()");

        // valid start of a move
        if (originPoint == null && !rackCells[point.x][point.y].isEmpty()){
            debugPrint("cellTest", "rackCellClicked(): valid start");
            originPoint = new GamePoint(GameArea.RACK, point);
            return;
        }

        // valid end of a move
        if (originPoint != null && rackCells[point.x][point.y].isEmpty()){

            GameMove move;

            // move is board to board
            if ( originPoint.area == GameArea.RACK ) {

                debugPrint("cellTest", "rackCellClicked(): valid end [" + originPoint.point.x + "," + originPoint.point.y + "] -> [" + point.x + "," + point.y + "]");
                move = new GameMove(ActionType.ONRACK, originPoint.point, point);

            } else { // move is board to rack

                debugPrint("cellTest", "rackCellClicked(): valid end [" + originPoint.point.x + "," + originPoint.point.y + "] -> [" + point.x + "," + point.y + "]");
                move = new GameMove(ActionType.BOARDTORACK, originPoint.point, point);

                System.out.print("THIS IS CALLEED");


            }



            move.swapCoordinates(); // :(
            makeMove(move);
        }

    }

    private void makeMove(GameMove move){


        if(move.type == ActionType.ONBOARD){

            System.out.println("A move on board was send to execute");

        }

        rummikubController.executeMove(move.toJSON());

        originPoint = null;

        updateGUI();
    }


    /* GUI ACTIONS */

    public void gridClicked(MouseEvent mouseEvent) {


    }

    public void acceptButtonClicked(MouseEvent mouseEvent) {

        System.out.print("hello world");

    }

    public void sortForGroupClicked(MouseEvent mouseEvent) {

        var move = new GameMove(ActionType.SORTGROUP);
        makeMove(move);

        updateGUI();
    }

    public void finsishOrDrawClicked(MouseEvent mouseEvent) {

        var move = new GameMove(ActionType.FINISHMOVE);
        makeMove(move);



        updateGUI();
    }

    public void sortForRunClicked(MouseEvent mouseEvent) {

        var move = new GameMove(ActionType.SORTRUN);
        makeMove(move);

        updateGUI();
    }

    public void resetClicked(MouseEvent mouseEvent) {

        var move = new GameMove(ActionType.RESET);
        makeMove(move);

        updateGUI();
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

        var rackCellHeight = gridPane_Rack.heightProperty().divide(RACK_ROW_AMOUNT);
        var rackCellWidth = gridPane_Rack.widthProperty().divide(RACK_COLUMN_AMOUNT);

        var padding = 3; // in px

        for(var row_y = 0; row_y < RACK_ROW_AMOUNT; row_y++) {

            for(var column_x = 0; column_x < RACK_COLUMN_AMOUNT; column_x++) {

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

    private void debugPrint(String identifier, String message){

        if(identifier == "cellTest"){

            System.out.println("CellTest: " + message);

        }

    }


    boolean eventHandlerActive = false;

    // Helper Function for setUpGrids
    private void addCellTo(String gridName, int column_x, int row_y, int padding, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty){

        GridPane gridPane;

        if (gridName.equals("RACK")) {

            gridPane = gridPane_Rack;

        } else {

            gridPane = gridPane_board;
        }

        /* UI ELEMENTS */

        var anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: green;");

        var imageView = new ImageView();
        var image = new Image("@../../resources/images/RummikubTile.png");
        imageView.setImage(image);

        Text text = new Text();
        text.setText("9");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        /* BINDING AND LAYOUT*/

        anchorPane.prefHeightProperty().bind(cellHeightProperty);
        anchorPane.prefWidthProperty().bind(cellWidthProperty);


        imageView.fitHeightProperty().bind(cellHeightProperty.subtract(2*padding));
        imageView.fitWidthProperty().bind(cellWidthProperty.subtract(2*padding));

        imageView.xProperty().set(padding);
        imageView.yProperty().set(padding);

        var textWidth = text.getLayoutBounds().getWidth();
        var textHeigth = text.getLayoutBounds().getHeight();

        text.xProperty().bind(cellWidthProperty.divide(2).subtract(textWidth/2));
        text.yProperty().bind(cellHeightProperty.divide(2));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(text);

        FXGridCell currentCell = new FXGridCell(imageView, text);
        currentCell.setCellWidthProperty(cellWidthProperty);
        // currentCell.fill(TileColor.BLUE, 7);

        if(gridName=="RACK" && column_x == 2 && row_y == 0){
            currentCell.fill(TileColor.BLUE, 7);
        }


        System.out.print(row_y);

        if (gridName.equals("RACK")){
            rackCells[column_x][row_y] = currentCell;
        } else {
            boardCells[column_x][row_y] = currentCell;
        }


        int finalY = row_y;
        int finalX = column_x;

        anchorPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if(eventHandlerActive){return;}
                eventHandlerActive = true;

                debugPrint("cellTest", "MouseClicked: [" + finalX + "][" + finalY + "]");

                if ( gridName == "RACK"){
                    rackCellClicked(new Point(finalX, finalY));
                } else {
                    boardCellClicked(new Point(finalX, finalY));
                }

                eventHandlerActive = false;

            }
        });

        anchorPane.setOnDragDetected(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if(eventHandlerActive){return;}
                eventHandlerActive = true;

                debugPrint("cellTest", "dragDetected: [" + finalX + "][" + finalY + "]");

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
                        rackCellClicked(new Point(finalX, finalY));
                    } else {
                        boardCellClicked(new Point(finalX, finalY));
                    }

                }

                eventHandlerActive = false;

            }

        });

        anchorPane.setOnDragOver(new EventHandler<DragEvent>() {

            public void handle(DragEvent event) {

                // debugPrint("cellTest", "dragOver: [" + finalX + "][" + finalY + "]");

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

                if(eventHandlerActive){return;}
                eventHandlerActive = true;

                // This if statement has to be removed if drag&push will be implemented
                if( currentCell.isEmpty() ) {

                    debugPrint("cellTest", "dragDropped: [" + finalX + "][" + finalY + "]");

                    if ( gridName == "RACK"){
                        rackCellClicked(new Point(finalX, finalY));
                    } else {
                        boardCellClicked(new Point(finalX, finalY));
                    }

                    event.setDropCompleted(true);
                    event.consume();



                }

                eventHandlerActive = false;

            }

        });

        anchorPane.setOnDragDone(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {

                if(eventHandlerActive){return;}
                eventHandlerActive = true;

                debugPrint("cellTest", "dragDone: [" + finalX + "][" + finalY + "]");

                dragActionHappening = null;

                if ( originPoint != null ){

                    if ( originPoint.area == GameArea.RACK ){
                        rackCells[finalX][finalY].show();
                    } else {
                        boardCells[finalX][finalY].show();
                    }

                    originPoint = null;

                }

                eventHandlerActive = false;

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

        for (var i = 0; i < BOARD_ROW_AMOUNT; i++) {

            for (var j = 0; j < BOARD_COLUMN_AMOUNT; j++) {

                var gridPoint = new Point(i, j);
                var gridTile = sketchboard.getGridTileAt(gridPoint);

                var boardTile = boardCells[j][i];

                if (gridTile.isEmpty()) {

                    boardTile.clear();

                } else {

                    if (!gridTile.getTile().isEqualToFX(boardTile) || boardTile.isEmpty()) {

                        boardTile.fill(gridTile.getTile().getTileColor(), gridTile.getTile().getValue());
                    }
                }

            }
        }
        stateFinishButton = rummiGame.getSketchBoard().isValid();
    }

    private void updateGUIRack() {

        var currentPlayersRack = rummiGame.getCurrentPlayersSketchRack();

        for (var i = 0; i < RACK_ROW_AMOUNT; i++) {

            for (var j = 0; j < RACK_COLUMN_AMOUNT; j++) {

                var gridPoint = new Point(i, j);
                var gridTile = currentPlayersRack.getGridTileAt(gridPoint);

               // System.out.println(i + ", "+ j);

                var rackTile = rackCells[j][i];

                if (gridTile.getTile() == null) {

                    rackTile.clear();

                } else {

                    if (!gridTile.getTile().isEqualToFX(rackTile) || rackTile.isEmpty()) {

                        rackTile.fill(gridTile.getTile().getTileColor(), gridTile.getTile().getValue());
                    }
                }
            }
        }
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

        if (stateFinishButton) {

            button_finishOrDraw.setImage(new Image("@../../resources/images/buttonDraw.jpg"));

        } else {

            button_finishOrDraw.setImage(new Image("@../../resources/images/buttonDraw.jpg"));
            // var image = new Image("@../../resources/images/RummikubTile.png");
        }
        anchorPane_contextMenu.setVisible(false);
        anchorPane_gameMessage.setVisible(false);
    }


}

