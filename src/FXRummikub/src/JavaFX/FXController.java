package javafx;

import framework.GameController;
import framework.Player;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.json.simple.JSONObject;
import rummikub_controller.*;
import rummikub_game.Rummikub;
import javafx.scene.text.Font;


import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FXController implements Player, Initializable {

    /* CONSTANTS */

    private final int RACK_ROW_AMOUNT = 2;
    private final int RACK_COLUMN_AMOUNT = 16;
    private final int BOARD_COLUMN_AMOUNT = 16;
    private final int BOARD_ROW_AMOUNT = 7;
    public AnchorPane anchorPanePod;
    public ImageView imageView_startNewGame;



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
    public ImageView button_cancel;

    public AnchorPane anchorPane_P1;
    public Label label_nameP1;
    public Label label_rackP1;
    public Label label_scoreP1;
    public Rectangle rectanglePlayer_P1;
    public Circle circlePlayer_P1;
    public Label label_LetterP1;
    public AnchorPane anchorPane_P2;
    public Label label_nameP2;
    public Label label_rackP2;
    public Label label_scoreP2;
    public Rectangle rectanglePlayer_P2;
    public Circle circlePlayer_P2;
    public Label label_LetterP2;
    public AnchorPane anchorPane_P3;
    public Label label_nameP3;
    public Label label_rackP3;
    public Label label_scoreP3;
    public Rectangle rectanglePlayer_P3;
    public Circle circlePlayer_P3;
    public Label label_LetterP3;
    public AnchorPane anchorPane_P4;
    public Label label_nameP4;
    public Label label_rackP4;
    public Label label_scoreP4;
    public Rectangle rectanglePlayer_P4;
    public Circle circlePlayer_P4;
    public Label label_LetterP4;

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



    public AnchorPane anchorpane_pod1;
    public Label label_pod1name;
    public Label label_pod1score;
    public Label label_pod1totscore;
    public AnchorPane anchorpane_pod2;
    public Label label_pod2name;
    public Label label_pod2score;
    public Label label_pod2totscore;
    public AnchorPane anchorpane_pod3;
    public Label label_pod3name;
    public Label label_pod3score;
    public Label label_pod3totscore;
    public AnchorPane anchorpane_pod4;
    public Label label_pod4name;
    public Label label_pod4score;
    public Label label_pod4totscore;

    // true if board is valid, else false
    private boolean stateFinishButton = true;

    ClassLoader classLoader = getClass().getClassLoader();
    URL emptyTileURL = classLoader.getResource("RummikubTile.png");
    URL buttonDrawURL = classLoader.getResource("buttonDraw.jpg");
    URL buttonFinishURL = classLoader.getResource("buttonFinish.jpeg");
    URL buttonFinishNotPossibleURL = classLoader.getResource("resources/images/buttonFinishNotPossible.jpeg");


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

            }



            move.swapCoordinates(); // :(
            makeMove(move);
        }

    }

    private boolean makeMove(GameMove move){

        var suc = rummikubController.executeMove(move.toJSON());

        originPoint = null;

        updateGUI();

    //    return suc.get("successful").equals("true");
        return true;
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

    public void finishOrDrawClicked(MouseEvent mouseEvent) {

        anchorPane_contextMenu.setVisible(false);

        updateGUIButtons();

        if (!stateFinishButton) {

            if (!rummiGame.getSketchBoard().isValid()) {

                setGameMessage("Board is invalid!");

            } else if (!rummiGame.getCurrentPlayer().getCommingOut() && rummiGame.sumMovedRackTiles() <= 30) {

                setGameMessage("30 points needed to finish!");
            }
            return;
        }

        var move = new GameMove(ActionType.FINISHMOVE);

        makeMove(move);

        updateGUI();

        validFinishMove();
    }

    public void validFinishMove() {

        var p = (rummiGame.getCurrentPlayerIndex() + rummiGame.getPlayerAmount() - 1) % rummiGame.getPlayerAmount();

        if ( rummiGame.isFinished() ) {

            setGameMessage("Game over: " + rummikubController.getPlayerInfos().get(p).getName() + " wins!");
            setPodium();

        } else {

            setGameMessage(rummikubController.getPlayerInfos().get(p).getName() + " finished!");
        }
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

        setGameMessage("Main Menu is open");

    /*    var move = new GameMove(ActionType.FINISHMOVE);

        makeMove(move);*/

    }

    public void openSettings(MouseEvent mouseEvent) {

        setGameMessage("Settings are open");

        System.out.println(rummiGame.getBoard().toString(true));
        System.out.println(rummiGame.getSketchBoard().toString(true));


    }

    public void startNewGame(MouseEvent mouseEvent) {

        setGameMessage("New Game started");
    }

    public void quit(MouseEvent mouseEvent) {

        setGameMessage("You just quited");
    }

    public void closeContextMenu(MouseEvent mouseEvent) {

        anchorPane_contextMenu.setVisible(false);
    }

    public void setGameMessage(String s) {

        anchorPane_gameMessage.setOpacity(1);
        label_gameMessage.setText(s);
        anchorPane_gameMessage.setVisible(true);

        var kv0 = new KeyValue(anchorPane_gameMessage.opacityProperty(), 1, Interpolator.EASE_OUT);
        var kf0 = new KeyFrame(Duration.seconds(2), kv0);

        var timeline0 = new Timeline();
        timeline0.setOnFinished(e -> gameMessageFadeOut());

        timeline0.getKeyFrames().add(kf0);

        timeline0.play();
    }

    private void gameMessageFadeOut() {

        var kv1 = new KeyValue(anchorPane_gameMessage.opacityProperty(), 0, Interpolator.EASE_IN);
        var kf1 = new KeyFrame(Duration.seconds(2), kv1);

        var timeline1 = new Timeline();
        timeline1.getKeyFrames().add(kf1);

        timeline1.play();
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

        if(identifier.equals("cellTest") && false){

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
       // anchorPane.setStyle("-fx-background-color: green;");

        var imageView = new ImageView();

        var image = new Image(emptyTileURL.toString());
        imageView.setImage(image);

        var text = new Text();
        text.setText("9");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        /* BINDING AND LAYOUT*/

        anchorPane.prefHeightProperty().bind(cellHeightProperty);
        anchorPane.prefWidthProperty().bind(cellWidthProperty);

        if (gridName.equals("RACK")) {

            imageView.fitHeightProperty().bind(cellHeightProperty.subtract(2*padding).multiply(0.8));

        } else {

            imageView.fitHeightProperty().bind(cellHeightProperty.subtract(2*padding));
        }



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

        if(gridName.equals("RACK") && column_x == 2 && row_y == 0){
        //    currentCell.fill(TileColor.BLUE, 7);
        }


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

                if (gridName.equals("RACK")){
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

                    var image = new Image(emptyTileURL.toString(), cellWidthProperty.doubleValue(), cellHeightProperty.doubleValue(), false, false);
                    // Include Number here

                    cbContent.putImage(image);
                    //cbContent.put(DataFormat.)

                    db.setContent(cbContent);
                    imageView.setVisible(false);
                    text.setVisible(false);
                    mouseEvent.consume();

                    if (gridName.equals("RACK")){
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

                    if (gridName.equals("RACK")){
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


        /* Podium */

        imageView_startNewGame.fitWidthProperty().bind(rootAnchorPane.widthProperty().multiply(0.25));
        imageView_startNewGame.xProperty().bind(anchorPanePod.widthProperty().multiply(0.5).subtract(imageView_startNewGame.fitWidthProperty().multiply(0.5)));

        anchorpane_pod1.layoutXProperty().set(40);
        anchorpane_pod1.layoutYProperty().set(20);
        anchorpane_pod1.prefHeightProperty().bind(anchorPanePod.heightProperty().multiply(0.6));
        anchorpane_pod1.prefWidthProperty().bind(anchorPanePod.widthProperty().multiply(0.2));

        /* */

        AnchorPane[] playerAnchorPane = new AnchorPane[]{anchorPane_P1, anchorPane_P2, anchorPane_P3, anchorPane_P4};
        Label[] playerLabelName = new Label[]{label_nameP1, label_nameP2, label_nameP3, label_nameP4};
        Label[] playerLabelRack = new Label[]{label_rackP1, label_rackP2, label_rackP3, label_rackP4};
        Label[] playerLabelScore = new Label[]{label_scoreP1, label_scoreP2, label_scoreP3, label_scoreP4};
        Rectangle[] playerRectangle = new Rectangle[]{rectanglePlayer_P1, rectanglePlayer_P2, rectanglePlayer_P3, rectanglePlayer_P4};
        Circle[] playerCircle = new Circle[]{circlePlayer_P1, circlePlayer_P2, circlePlayer_P3, circlePlayer_P4};


        /* PLAYER BOX BINDINGS */

        for(int i = 0; i < 4; i++){

            playerAnchorPane[i].prefHeightProperty().bind(rootAnchorPane.heightProperty().multiply(0.25));
            playerAnchorPane[i].prefWidthProperty().bind(rootAnchorPane.widthProperty().multiply(0.12));

            playerRectangle[i].heightProperty().bind(playerAnchorPane[i].heightProperty());
            playerRectangle[i].widthProperty().bind(playerAnchorPane[i].widthProperty());

            playerCircle[i].radiusProperty().bind(playerAnchorPane[i].widthProperty().multiply(0.5).subtract(20));
            playerCircle[i].centerYProperty().bind(playerAnchorPane[i].heightProperty().multiply(0.4));
            playerCircle[i].centerXProperty().bind(playerAnchorPane[i].widthProperty().multiply(0.5).subtract(10));


        }




        // label_LetterP1.prefHeightProperty().bind(rootAnchorPane.widthProperty());







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

        updateGUI();

    }


    public void updateGUI() {


        // update environment
        updateGUIEnvironment();

        // update board
        updateGUIBoard();

        // update rack
        updateGUIRack();

        // update players
        updateGUIPlayers();

        // update buttons
        updateGUIButtons();

    }

    private void updateGUIFinishButton() {

        var noTilesMoved = rummiGame.getMovedRackTiles().isEmpty();

        var validBoard = rummiGame.getSketchBoard().isValid();

        var commingOutDone = rummiGame.getCurrentPlayer().getCommingOut();

        var movedTilesOver30 = rummiGame.sumMovedRackTiles() >= 30;

        stateFinishButton = noTilesMoved || (validBoard && (commingOutDone || movedTilesOver30));
    }

    private void updateGUIEnvironment() {

        var state = rummikubController.getGameState();

        state = GameState.FINISHED;

        if (state == GameState.STARTING) {

            anchorPanePod.setVisible(true);
            anchorpane_pod1.setVisible(false);
            anchorpane_pod2.setVisible(false);
            anchorpane_pod3.setVisible(false);
            anchorpane_pod4.setVisible(false);

        } else if (state == GameState.RUNNING) {

            anchorPanePod.setVisible(false);

        } else if (state == GameState.FINISHED) {

            anchorPanePod.setVisible(true);
            anchorpane_pod1.setVisible(true);
            anchorpane_pod2.setVisible(true);
            anchorpane_pod3.setVisible(true);
            anchorpane_pod4.setVisible(true);
        }
    }


    private void updateGUIBoard() {

        var sketchboard = rummiGame.getSketchBoard();

        for (var i = 0; i < BOARD_ROW_AMOUNT; i++) {

            for (var j = 0; j < BOARD_COLUMN_AMOUNT; j++) {

                var gridPoint = new Point(i, j);
                var gridTile = sketchboard.getGridTileAt(gridPoint);

                var boardTile = boardCells[j][i];

                boardTile.updateVisibility();

                if (gridTile.isEmpty()) {

                    boardTile.clear();

                } else {

                    if (gridTile.getTile().isEqualToFX(boardTile) || boardTile.isEmpty()) {

                        boardTile.fill(gridTile.getTile().getTileColor(), gridTile.getTile().getValue());
                    }
                }

            }
        }
        stateFinishButton = rummiGame.getSketchBoard().isValid() || rummiGame.sumMovedRackTiles() >= 30;
    }

    private void updateGUIRack() {

        var currentPlayersRack = rummiGame.getCurrentPlayersSketchRack();

        for (var i = 0; i < RACK_ROW_AMOUNT; i++) {

            for (var j = 0; j < RACK_COLUMN_AMOUNT; j++) {

                var gridPoint = new Point(i, j);
                var gridTile = currentPlayersRack.getGridTileAt(gridPoint);

                var rackTile = rackCells[j][i];

                rackTile.updateVisibility();

                if (gridTile.getTile() == null) {

                    rackTile.clear();

                } else {

                    if (gridTile.getTile().isEqualToFX(rackTile) || rackTile.isEmpty()) {

                        rackTile.fill(gridTile.getTile().getTileColor(), gridTile.getTile().getValue());
                    }
                }
            }
        }
    }

    private void updateGUIPlayers() {

        // TODO: where are the names of the player?
        // ANSWER: They are in the player class arry of rummikub-controller

        /* Highlight current Player  */

        label_nameP1.setStyle("-fx-background-color: black;");
        label_nameP2.setStyle("-fx-background-color: black;");
        label_nameP3.setStyle("-fx-background-color: black;");
        label_nameP4.setStyle("-fx-background-color: black;");

        switch (rummiGame.getCurrentPlayerIndex()){
            case 0 -> label_nameP1.setStyle("-fx-background-color: white;");
            case 1 -> label_nameP2.setStyle("-fx-background-color: white;");
            case 2 -> label_nameP3.setStyle("-fx-background-color: white;");
            case 3 -> label_nameP4.setStyle("-fx-background-color: white;");
        }

        var p = rummiGame.getPlayerAmount();
        var tilesImage = "";

        label_nameP1.setText(rummikubController.getPlayerInfos().get(0).getName());
        label_rackP1.setText(rummiGame.getPlayerAt(0).getRack().getSize() + tilesImage);
        label_scoreP1.setText(Integer.toString(rummiGame.getPlayerAt(0).getScore()));
        label_LetterP1.setText(Character.toString(label_nameP1.getText().charAt(0)));

        label_nameP2.setText(rummikubController.getPlayerInfos().get(1).getName());
        label_rackP2.setText(rummiGame.getPlayerAt(1).getRack().getSize() + tilesImage);
        label_scoreP2.setText(Integer.toString(rummiGame.getPlayerAt(1).getScore()));
        label_LetterP2.setText(Character.toString(label_nameP2.getText().charAt(0)));

        if (p >= 3) {

            label_nameP3.setText(rummikubController.getPlayerInfos().get(2).getName());
            label_rackP3.setText(rummiGame.getPlayerAt(2).getRack().getSize() + tilesImage);
            label_scoreP3.setText(Integer.toString(rummiGame.getPlayerAt(2).getScore()));
            label_LetterP3.setText(Character.toString(label_nameP3.getText().charAt(0)));

            if (p == 4) {

                label_nameP4.setText(rummikubController.getPlayerInfos().get(3).getName());
                label_rackP4.setText(rummiGame.getPlayerAt(3).getRack().getSize() + tilesImage);
                label_scoreP4.setText(Integer.toString(rummiGame.getPlayerAt(3).getScore()));
                label_LetterP4.setText(Character.toString(label_nameP4.getText().charAt(0)));

            } else {

                anchorPane_P4.setVisible(false);
            }
        } else {

            anchorPane_P3.setVisible(false);
        }
    }

    private void updateGUIButtons() {

        updateGUIFinishButton();

        if (stateFinishButton) {

            if (rummiGame.getMovedRackTiles().isEmpty()) {

                button_finishOrDraw.setImage(new Image(buttonDrawURL.toString()));

            } else {

                button_finishOrDraw.setImage(new Image(buttonFinishURL.toString()));
            }

        } else {

            button_finishOrDraw.setImage(new Image(buttonFinishNotPossibleURL.toString()));
        }

        anchorPane_contextMenu.setVisible(false);
        anchorPane_gameMessage.setVisible(false);
    }

    private void setPodium() {

        PlayerInfo[] podium = rummikubController.getPodium();

        for (var i = 0; i < 4; i++) {
            System.out.println(podium[i].getName() + ", " + podium[i].getLastScore() + ", " + podium[i].getTotalScore());
        }

        label_pod1name.setText(podium[0].getName());
        label_pod1score.setText(Integer.toString(podium[0].getLastScore()));
        label_pod1totscore.setText(Integer.toString(podium[0].getTotalScore()));

        label_pod2name.setText(podium[1].getName());
        label_pod2score.setText(Integer.toString(podium[1].getLastScore()));
        label_pod2totscore.setText(Integer.toString(podium[1].getTotalScore()));

        if (podium.length > 3) {

            label_pod1name.setText(podium[2].getName());
            label_pod1score.setText(Integer.toString(podium[2].getLastScore()));
            label_pod1totscore.setText(Integer.toString(podium[2].getTotalScore()));

            if (podium.length == 4) {

                label_pod1name.setText(podium[2].getName());
                label_pod1score.setText(Integer.toString(podium[2].getLastScore()));
                label_pod1totscore.setText(Integer.toString(podium[2].getTotalScore()));

            } else {

                anchorpane_pod4.setVisible(false);
            }
        } else {

            anchorpane_pod3.setVisible(false);
        }
    }

    public void undoButtonClicked(MouseEvent mouseEvent) {

        var move = new GameMove(ActionType.UNDOLASTMOVE);
        makeMove(move);

    }

    public void buttonStartNewGameClicked(MouseEvent mouseEvent) {

        var move = new GameMove(ActionType.STARTGAME);

        makeMove(move);

    }
}
