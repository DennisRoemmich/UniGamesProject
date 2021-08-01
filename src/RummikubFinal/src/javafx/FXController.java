package javafx;

import framework.GameController;
import framework.Player;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
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

    private static final String CELLTEST = "cellTest";

    /* CONSTANTS */

    private static final int RACK_ROW_AMOUNT = 2;
    private static final int RACK_COLUMN_AMOUNT = 16;
    private static final int BOARD_COLUMN_AMOUNT = 16;
    private static final int BOARD_ROW_AMOUNT = 7;


    /* VARS */

    FXGridCell[][] mBoardCells = new FXGridCell[BOARD_COLUMN_AMOUNT][BOARD_ROW_AMOUNT];
    FXGridCell[][] mRackCells = new FXGridCell[RACK_COLUMN_AMOUNT][RACK_ROW_AMOUNT];

    private Rummikub mRummiGame;
    private RummikubController mRummikubController;


    /* OUTLETS */

    @FXML
    private AnchorPane anchorPanePod;
    @FXML
    private ImageView imageViewStartNewGame;
    @FXML
    private AnchorPane anchorPaneBoard;
    @FXML
    private ImageView buttonSortForGroup;
    @FXML
    private ImageView buttonFinishOrDraw;
    @FXML
    private ImageView buttonSortForRun;
    @FXML
    private ImageView buttonReset;
    @FXML
    private ImageView buttonCancel;
    @FXML
    private AnchorPane anchorPaneP1;
    @FXML
    private Label labelNameP1;
    @FXML
    private Label labelRackP1;
    @FXML
    private Label labelScoreP1;
    @FXML
    private Rectangle rectanglePlayerP1;
    @FXML
    private Circle circlePlayerP1;
    @FXML
    private Label labelLetterP1;
    @FXML
    private AnchorPane anchorPaneP2;
    @FXML
    private Label labelNameP2;
    @FXML
    private Label labelRackP2;
    @FXML
    private Label labelScoreP2;
    @FXML
    private Rectangle rectanglePlayerP2;
    @FXML
    private Circle circlePlayerP2;
    @FXML
    private Label labelLetterP2;
    @FXML
    private AnchorPane anchorPaneP3;
    @FXML
    private Label labelNameP3;
    @FXML
    private Label labelRackP3;
    @FXML
    private Label labelScoreP3;
    @FXML
    private Rectangle rectanglePlayerP3;
    @FXML
    private Circle circlePlayerP3;
    @FXML
    private Label labelLetterP3;
    @FXML
    private AnchorPane anchorPaneP4;
    @FXML
    private Label labelNameP4;
    @FXML
    private Label labelRackP4;
    @FXML
    private Label labelScoreP4;
    @FXML
    private Rectangle rectanglePlayerP4;
    @FXML
    private Circle circlePlayerP4;
    @FXML
    private Label labelLetterP4;
    @FXML
    private AnchorPane anchorPaneGameMessage;
    @FXML
    private Label labelGameMessage;
    @FXML
    private ImageView buttonOpenContextMenu;
    @FXML
    private AnchorPane anchorPaneContextMenu;
    @FXML
    private Label buttonToMainMenu;
    @FXML
    private Label buttonToSettings;
    @FXML
    private Label buttonStartNewGame;
    @FXML
    private Label buttonQuit;
    @FXML
    private ImageView imageViewBackGround;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private ImageView buttonCloseContextMenu;
    @FXML
    private GridPane gridPaneBoard;
    @FXML
    private GridPane gridPaneRack;
    @FXML
    private AnchorPane anchorpanePod1;
    @FXML
    private Label labelPod1Name;
    @FXML
    private Label labelPod1Score;
    @FXML
    private Label labelPod1Totscore;
    @FXML
    private AnchorPane anchorpanePod2;
    @FXML
    private Label labelPod2Name;
    @FXML
    private Label labelPod2Score;
    @FXML
    private Label labelPod2Totscore;
    @FXML
    private AnchorPane anchorpanePod3;
    @FXML
    private Label labelPod3Name;
    @FXML
    private Label labelPod3Score;
    @FXML
    private Label labelPod3Totscore;
    @FXML
    private AnchorPane anchorpanePod4;
    @FXML
    private Label labelPod4Name;
    @FXML
    private Label labelPod4Score;
    @FXML
    private Label labelPod4Totscore;

    // true if board is valid, else false
    private boolean stateFinishButton = true;


    // images
    String mEmptyTileURL = "resources/images/RummikubTile.png";
    String mButtonDrawURL = "resources/images/buttonDraw.jpg";
    String mButtonFinishURL = "resources/images/buttonFinish.jpeg";
    String mButtonFinishNotPossibleURL = "resources/images/buttonFinishNotPossible.jpeg";

    /* FUNCTIONS */


    GamePoint mOriginPoint = null;

    /**
     * Should be triggered when action on cell of grid is triggered
     */


    private void boardCellClicked(Point point) {

        debugPrint(CELLTEST, "boardCellClicked()");

        // valid start of a move
        if (mOriginPoint == null && !mBoardCells[point.x][point.y].isEmpty()) {
            debugPrint(CELLTEST, "boardCellClicked(): valid start");
            mOriginPoint = new GamePoint(GameArea.BOARD, point);
            return;
        }

        // valid end of a move
        if (mOriginPoint != null && mBoardCells[point.x][point.y].isEmpty()) {
            debugPrint(CELLTEST, "boardCellClicked(): valid end");
            GameMove move;

            // move is board to board
            if ( mOriginPoint.area == GameArea.BOARD ) {

                move = new GameMove(ActionType.ONBOARD, mOriginPoint.point, point);

            } else { // move is rack to board

                move = new GameMove(ActionType.RACKTOBOARD, mOriginPoint.point, point);
            }

            move.swapCoordinates(); // :(
            makeMove(move);

        }

    }

    private void rackCellClicked(Point point) {

        debugPrint(CELLTEST, "rackCellClicked()");

        // valid start of a move
        if (mOriginPoint == null && !mRackCells[point.x][point.y].isEmpty()) {
            debugPrint(CELLTEST, "rackCellClicked(): valid start");
            mOriginPoint = new GamePoint(GameArea.RACK, point);
            return;
        }

        // valid end of a move
        if (mOriginPoint != null && mRackCells[point.x][point.y].isEmpty()) {

            GameMove move;

            // move is board to board
            if ( mOriginPoint.area == GameArea.RACK ) {

                debugPrint(CELLTEST, "rackCellClicked(): valid end [" + mOriginPoint.point.x + "," + mOriginPoint.point.y + "] -> [" + point.x + "," + point.y + "]");
                move = new GameMove(ActionType.ONRACK, mOriginPoint.point, point);

            } else { // move is board to rack

                debugPrint(CELLTEST, "rackCellClicked(): valid end [" + mOriginPoint.point.x + "," + mOriginPoint.point.y + "] -> [" + point.x + "," + point.y + "]");
                move = new GameMove(ActionType.BOARDTORACK, mOriginPoint.point, point);

            }



            move.swapCoordinates(); // :(
            makeMove(move);
        }

    }

    private boolean makeMove(GameMove move) {

        mOriginPoint = null;

        mRummikubController.makeMove(move);

        updateGUI();

        return true;
    }


    /* GUI ACTIONS */

    public void sortForGroupClicked() {

        var move = new GameMove(ActionType.SORTGROUP);
        makeMove(move);

        updateGUI();
    }

    public void finishOrDrawClicked() {

        anchorPaneContextMenu.setVisible(false);

        updateGUIButtons();

        if (!stateFinishButton) {

            if (!mRummiGame.getSketchBoard().isValid()) {

                setGameMessage("Board is invalid!");

            } else if (!mRummiGame.getCurrentPlayer().getCommingOut() && mRummiGame.sumMovedRackTiles() <= 30) {

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

        var p = (mRummiGame.getCurrentPlayerIndex() + mRummiGame.getPlayerAmount() - 1) % mRummiGame.getPlayerAmount();

        if ( mRummiGame.isFinished() ) {

            setGameMessage("Game over: " + mRummikubController.getPlayerInfos().get(p).getName() + " wins!");
            setPodium();

        } else {

            setGameMessage(mRummikubController.getPlayerInfos().get(p).getName() + " finished!");
        }
    }

    public void sortForRunClicked() {

        var move = new GameMove(ActionType.SORTRUN);
        makeMove(move);

        updateGUI();
    }

    public void resetClicked() {

        var move = new GameMove(ActionType.RESET);
        makeMove(move);

        updateGUI();
    }


    public void openContextMenu() {

        anchorPaneContextMenu.setVisible(true);
    }

    public void openMainMenu() {

        setGameMessage("Main Menu is open");

    }

    public void openSettings() {

        setGameMessage("Settings are open");

    }

    public void startNewGame() {

        setGameMessage("New Game started");
    }

    public void quit() {

        setGameMessage("You just quited");
    }

    public void closeContextMenu() {

        anchorPaneContextMenu.setVisible(false);
    }

    public void setGameMessage(String s) {

        anchorPaneGameMessage.setOpacity(1);
        labelGameMessage.setText(s);
        anchorPaneGameMessage.setVisible(true);

        var kv0 = new KeyValue(anchorPaneGameMessage.opacityProperty(), 1, Interpolator.EASE_OUT);
        var kf0 = new KeyFrame(Duration.seconds(2), kv0);

        var timeline0 = new Timeline();
        timeline0.setOnFinished(e -> gameMessageFadeOut());

        timeline0.getKeyFrames().add(kf0);

        timeline0.play();
    }

    private void gameMessageFadeOut() {

        var kv1 = new KeyValue(anchorPaneGameMessage.opacityProperty(), 0, Interpolator.EASE_IN);
        var kf1 = new KeyFrame(Duration.seconds(2), kv1);

        var timeline1 = new Timeline();
        timeline1.getKeyFrames().add(kf1);

        timeline1.play();
    }


    FXGridCell dragActionHappening = null;

    private void setUpGrids() {


        // RACK

        var rackCellHeight = gridPaneRack.heightProperty().divide(RACK_ROW_AMOUNT);
        var rackCellWidth = gridPaneRack.widthProperty().divide(RACK_COLUMN_AMOUNT);

        var padding = 3; // in px

        for(var row_y = 0; row_y < RACK_ROW_AMOUNT; row_y++) {

            for(var column_x = 0; column_x < RACK_COLUMN_AMOUNT; column_x++) {

                addCellTo("RACK", column_x, row_y, padding, rackCellWidth, rackCellHeight);

            }
        }



        // BOARD

        var boardCellHeight = gridPaneBoard.heightProperty().divide(BOARD_ROW_AMOUNT);
        var boardCellWidth = gridPaneBoard.widthProperty().divide(BOARD_COLUMN_AMOUNT);

        padding = 2; // in px

        for(var y = 0; y < BOARD_ROW_AMOUNT; y++) {
            for(var x = 0; x < BOARD_COLUMN_AMOUNT; x++) {

                addCellTo("BOARD", x, y, padding, boardCellWidth, boardCellHeight);

            }
        }






    }

    // TODO delete this
    private void debugPrint(String identifier, String message) {

        if(identifier.equals(CELLTEST)) {

            System.out.println("CellTest: " + message);

        }

    }


    boolean eventHandlerActive = false;

    // Helper Function for setUpGrids
    private void addCellTo(String gridName, int columnX, int rowY, int padding, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty) {

        GridPane gridPane;

        if (gridName.equals("RACK")) {

            gridPane = gridPaneRack;

        } else {

            gridPane = gridPaneBoard;
        }

        /* UI ELEMENTS */

        var anchorPane = new AnchorPane();

        var imageView = new ImageView();

        var image = new Image(mEmptyTileURL);
        imageView.setImage(image);

        var text = new Text();
        text.setText("9");
        text.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        /* BINDING AND LAYOUT*/

        cellBindings(anchorPane, cellHeightProperty, cellWidthProperty, gridName, imageView, padding, text);

        var currentCell = new FXGridCell(imageView, text);
        currentCell.setCellWidthProperty(cellWidthProperty);

        if (gridName.equals("RACK")) {
            mRackCells[columnX][rowY] = currentCell;
        } else {
            mBoardCells[columnX][rowY] = currentCell;
        }

        cellActionsOne(anchorPane, columnX, rowY, gridName, currentCell, imageView, cellWidthProperty, cellHeightProperty, text);

        cellActions(columnX, rowY, anchorPane, gridName, currentCell);

        cellActionsTwo(anchorPane, columnX, rowY);

        gridPane.add(anchorPane, columnX, rowY);

    }

    private void cellBindings(AnchorPane anchorPane, DoubleBinding cellHeightProperty, DoubleBinding cellWidthProperty, String gridName, ImageView imageView, int padding, Text text) {

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

        text.xProperty().bind(cellWidthProperty.divide(2).subtract(textWidth/2));
        text.yProperty().bind(cellHeightProperty.divide(2));

        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(text);
    }
    
    public void cellActions(int finalX, int finalY, AnchorPane anchorPane, String gridName, FXGridCell currentCell) {


        anchorPane.setOnDragOver(event -> {

            // This if statement has to be removed if drag&push will be implemented
            if (currentCell.isEmpty()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }

        });

        anchorPane.setOnDragDropped(event -> {

            if(eventHandlerActive) {return;}
            eventHandlerActive = true;

            // This if statement has to be removed if drag&push will be implemented
            if( currentCell.isEmpty() ) {

                debugPrint(CELLTEST, "dragDropped: [" + finalX + "][" + finalY + "]");

                if (gridName.equals("RACK")) {
                    rackCellClicked(new Point(finalX, finalY));
                } else {
                    boardCellClicked(new Point(finalX, finalY));
                }

                event.setDropCompleted(true);
                event.consume();



            }

            eventHandlerActive = false;

        });

    }
    
    private void cellActionsOne(AnchorPane anchorPane, int finalX, int finalY, String gridName, FXGridCell currentCell, ImageView imageView, DoubleBinding cellWidthProperty, DoubleBinding cellHeightProperty, Text text) {

        anchorPane.setOnMouseClicked(mouseEvent -> {

            if(eventHandlerActive) {return;}
            eventHandlerActive = true;

            debugPrint(CELLTEST, "MouseClicked: [" + finalX + "][" + finalY + "]");

            if (gridName.equals("RACK")) {
                rackCellClicked(new Point(finalX, finalY));
            } else {
                boardCellClicked(new Point(finalX, finalY));
            }

            eventHandlerActive = false;

        });

        anchorPane.setOnDragDetected(mouseEvent -> {

            if(eventHandlerActive) {return;}
            eventHandlerActive = true;

            debugPrint(CELLTEST, "dragDetected: [" + finalX + "][" + finalY + "]");

            if ( !currentCell.isEmpty() ) {

                dragActionHappening = currentCell;

                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                //Put ImageView on dragboard
                var cbContent = new ClipboardContent();

                var image1 = new Image(mEmptyTileURL, cellWidthProperty.doubleValue(), cellHeightProperty.doubleValue(), false, false);
                // Include Number here

                cbContent.putImage(image1);
                //cbContent.put(DataFormat.)

                db.setContent(cbContent);
                imageView.setVisible(false);
                text.setVisible(false);
                mouseEvent.consume();

                if (gridName.equals("RACK")) {
                    rackCellClicked(new Point(finalX, finalY));
                } else {
                    boardCellClicked(new Point(finalX, finalY));
                }

            }

            eventHandlerActive = false;

        });
    }

    private void cellActionsTwo(AnchorPane anchorPane, int finalX, int finalY) {

        anchorPane.setOnDragDone(event -> {

            if(eventHandlerActive) {return;}
            eventHandlerActive = true;

            debugPrint(CELLTEST, "dragDone: [" + finalX + "][" + finalY + "]");

            dragActionHappening = null;

            if ( mOriginPoint != null ) {

                if ( mOriginPoint.area == GameArea.RACK ) {
                    mRackCells[finalX][finalY].show();
                } else {
                    mBoardCells[finalX][finalY].show();
                }

                mOriginPoint = null;

            }

            eventHandlerActive = false;

        });
    }

    /* INTERFACE FUNCTIONS */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        /* Podium */

        imageViewStartNewGame.fitWidthProperty().bind(rootAnchorPane.widthProperty().multiply(0.25));
        imageViewStartNewGame.xProperty().bind(anchorPanePod.widthProperty().multiply(0.5).subtract(imageViewStartNewGame.fitWidthProperty().multiply(0.5)));

        anchorpanePod1.layoutXProperty().set(35);
        anchorpanePod1.layoutYProperty().set(20);
        anchorpanePod2.layoutXProperty().set(220);
        anchorpanePod2.layoutYProperty().set(20);
        anchorpanePod3.layoutXProperty().set(425);
        anchorpanePod3.layoutYProperty().set(20);
        anchorpanePod4.layoutXProperty().set(620);
        anchorpanePod4.layoutYProperty().set(20);
        anchorpanePod1.prefHeightProperty().bind(anchorPanePod.heightProperty().multiply(0.6));
        anchorpanePod1.prefWidthProperty().bind(anchorPanePod.widthProperty().multiply(0.2));
        anchorpanePod2.prefHeightProperty().bind(anchorPanePod.heightProperty().multiply(0.6));
        anchorpanePod2.prefWidthProperty().bind(anchorPanePod.widthProperty().multiply(0.2));
        anchorpanePod3.prefHeightProperty().bind(anchorPanePod.heightProperty().multiply(0.6));
        anchorpanePod3.prefWidthProperty().bind(anchorPanePod.widthProperty().multiply(0.2));
        anchorpanePod4.prefHeightProperty().bind(anchorPanePod.heightProperty().multiply(0.6));
        anchorpanePod4.prefWidthProperty().bind(anchorPanePod.widthProperty().multiply(0.2));

        /* */

        var playerAnchorPane = new AnchorPane[]{anchorPaneP1, anchorPaneP2, anchorPaneP3, anchorPaneP4};
        var playerRectangle = new Rectangle[]{rectanglePlayerP1, rectanglePlayerP2, rectanglePlayerP3, rectanglePlayerP4};
        var playerCircle = new Circle[]{circlePlayerP1, circlePlayerP2, circlePlayerP3, circlePlayerP4};


        /* PLAYER BOX BINDINGS */

        for(var i = 1; i < 4; i++) {

            playerAnchorPane[i].prefHeightProperty().bind(rootAnchorPane.heightProperty().multiply(0.25));
            playerAnchorPane[i].prefWidthProperty().bind(rootAnchorPane.widthProperty().multiply(0.12));

            playerRectangle[i].heightProperty().bind(playerAnchorPane[i].heightProperty());
            playerRectangle[i].widthProperty().bind(playerAnchorPane[i].widthProperty());

            playerCircle[i].radiusProperty().bind(playerAnchorPane[i].widthProperty().multiply(0.5).subtract(20));
            playerCircle[i].centerYProperty().bind(playerAnchorPane[i].heightProperty().multiply(0.4));
            playerCircle[i].centerXProperty().bind(playerAnchorPane[i].widthProperty().multiply(0.5).subtract(10));


        }

        imageViewBackGround.fitWidthProperty().bind(rootAnchorPane.widthProperty());
        imageViewBackGround.fitHeightProperty().bind(rootAnchorPane.heightProperty());

        gridPaneBoard.prefHeightProperty().bind(rootAnchorPane.heightProperty().multiply(0.60));
        gridPaneRack.prefHeightProperty().bind(rootAnchorPane.heightProperty().multiply(0.30));

        buttonFinishOrDraw.fitHeightProperty().bind(gridPaneRack.heightProperty().multiply(0.5));
        buttonReset.fitHeightProperty().bind(gridPaneRack.prefHeightProperty().multiply(0.5));
        buttonSortForGroup.fitHeightProperty().bind(gridPaneRack.prefHeightProperty().multiply(0.5));
        buttonSortForRun.fitHeightProperty().bind(gridPaneRack.prefHeightProperty().multiply(0.5));
        buttonReset.yProperty();

        buttonReset.yProperty().bind(gridPaneRack.heightProperty().multiply(0.90));

        setUpGrids();

    }




    @Override
    public JSONObject requestMove(JSONObject inputType) {
        // we don't use that.
        return null;
    }

    @Override
    public void setController(GameController controller) {

        mRummikubController = (RummikubController) controller;
        mRummiGame = mRummikubController.getGame();

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

        var noTilesMoved = mRummiGame.getMovedRackTiles().isEmpty();

        var validBoard = mRummiGame.getSketchBoard().isValid();

        var commingOutDone = mRummiGame.getCurrentPlayer().getCommingOut();

        var movedTilesOver30 = mRummiGame.sumMovedRackTiles() >= 30;

        stateFinishButton = noTilesMoved || (validBoard && (commingOutDone || movedTilesOver30));
    }

    private void updateGUIEnvironment() {

        var state = mRummikubController.getGameState();

        if (state == GameState.STARTING) {

            anchorPanePod.setVisible(true);
            anchorpanePod1.setVisible(false);
            anchorpanePod2.setVisible(false);
            anchorpanePod3.setVisible(false);
            anchorpanePod4.setVisible(false);

        } else if (state == GameState.RUNNING) {

            anchorPanePod.setVisible(false);

        } else if (state == GameState.FINISHED) {

            anchorPanePod.setVisible(true);
            anchorpanePod1.setVisible(true);
            anchorpanePod2.setVisible(true);
            anchorpanePod3.setVisible(true);
            anchorpanePod4.setVisible(true);
        }
    }


    private void updateGUIBoard() {

        var sketchboard = mRummiGame.getSketchBoard();

        for (var i = 0; i < BOARD_ROW_AMOUNT; i++) {

            for (var j = 0; j < BOARD_COLUMN_AMOUNT; j++) {

                var gridPoint = new Point(i, j);
                var gridTile = sketchboard.getGridTileAt(gridPoint);

                var boardTile = mBoardCells[j][i];

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
        stateFinishButton = mRummiGame.getSketchBoard().isValid() || mRummiGame.sumMovedRackTiles() >= 30;
    }

    private void updateGUIRack() {

        var currentPlayersRack = mRummiGame.getCurrentPlayersSketchRack();

        for (var i = 0; i < RACK_ROW_AMOUNT; i++) {

            for (var j = 0; j < RACK_COLUMN_AMOUNT; j++) {

                var gridPoint = new Point(i, j);
                var gridTile = currentPlayersRack.getGridTileAt(gridPoint);

                var rackTile = mRackCells[j][i];

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

        var blackColor = "-fx-background-color: black;";
        var whiteColor = "-fx-background-color: white;";

        /* Highlight current Player  */

        labelNameP1.setStyle(blackColor);
        labelNameP2.setStyle(blackColor);
        labelNameP3.setStyle(blackColor);
        labelNameP4.setStyle(blackColor);

        switch (mRummiGame.getCurrentPlayerIndex()) {

            case 0 -> labelNameP1.setStyle(whiteColor);
            case 1 -> labelNameP2.setStyle(whiteColor);
            case 2 -> labelNameP3.setStyle(whiteColor);
            case 3 -> labelNameP4.setStyle(whiteColor);
            default -> { /* will never happen */ }
        }

        var p = mRummiGame.getPlayerAmount();
        var tilesImage = "";

        labelNameP1.setText(mRummikubController.getPlayerInfos().get(0).getName());
        labelRackP1.setText(mRummiGame.getPlayerAt(0).getRack().getSize() + tilesImage);
        labelScoreP1.setText(Integer.toString(mRummiGame.getPlayerAt(0).getScore()));
        labelLetterP1.setText(Character.toString(labelNameP1.getText().charAt(0)));

        labelNameP2.setText(mRummikubController.getPlayerInfos().get(1).getName());
        labelRackP2.setText(mRummiGame.getPlayerAt(1).getRack().getSize() + tilesImage);
        labelScoreP2.setText(Integer.toString(mRummiGame.getPlayerAt(1).getScore()));
        labelLetterP2.setText(Character.toString(labelNameP2.getText().charAt(0)));

        if (p >= 3) {

            labelNameP3.setText(mRummikubController.getPlayerInfos().get(2).getName());
            labelRackP3.setText(mRummiGame.getPlayerAt(2).getRack().getSize() + tilesImage);
            labelScoreP3.setText(Integer.toString(mRummiGame.getPlayerAt(2).getScore()));
            labelLetterP3.setText(Character.toString(labelNameP3.getText().charAt(0)));

            if (p == 4) {

                labelNameP4.setText(mRummikubController.getPlayerInfos().get(3).getName());
                labelRackP4.setText(mRummiGame.getPlayerAt(3).getRack().getSize() + tilesImage);
                labelScoreP4.setText(Integer.toString(mRummiGame.getPlayerAt(3).getScore()));
                labelLetterP4.setText(Character.toString(labelNameP4.getText().charAt(0)));

            } else {

                anchorPaneP4.setVisible(false);
            }
        } else {

            anchorPaneP3.setVisible(false);
        }
    }

    private void updateGUIButtons() {

        updateGUIFinishButton();

        if (stateFinishButton) {

            if (mRummiGame.getMovedRackTiles().isEmpty()) {

                buttonFinishOrDraw.setImage(new Image(mButtonDrawURL));

            } else {

                buttonFinishOrDraw.setImage(new Image(mButtonFinishURL));
            }

        } else {

            buttonFinishOrDraw.setImage(new Image(mButtonFinishNotPossibleURL));
        }

        anchorPaneContextMenu.setVisible(false);
        anchorPaneGameMessage.setVisible(false);
    }

    private void setPodium() {

        PlayerInfo[] podium = mRummikubController.getPodium();

        labelPod1Name.setText(podium[0].getName());
        labelPod1Score.setText(Integer.toString(podium[0].getLastScore()));
        labelPod1Totscore.setText(Integer.toString(podium[0].getTotalScore()));

        labelPod2Name.setText(podium[1].getName());
        labelPod2Score.setText(Integer.toString(podium[1].getLastScore()));
        labelPod2Totscore.setText(Integer.toString(podium[1].getTotalScore()));

        if (podium.length > 3) {

            labelPod3Name.setText(podium[2].getName());
            labelPod3Score.setText(Integer.toString(podium[2].getLastScore()));
            labelPod3Totscore.setText(Integer.toString(podium[2].getTotalScore()));

            if (podium.length == 4) {

                labelPod4Name.setText(podium[3].getName());
                labelPod4Score.setText(Integer.toString(podium[3].getLastScore()));
                labelPod4Totscore.setText(Integer.toString(podium[3].getTotalScore()));

            } else {

                anchorpanePod4.setVisible(false);
            }
        } else {

            anchorpanePod3.setVisible(false);
        }
    }

    public void undoButtonClicked() {

        var move = new GameMove(ActionType.UNDOLASTMOVE);
        makeMove(move);

    }

    public void buttonStartNewGameClicked() {

        var move = new GameMove(ActionType.STARTGAME);

        makeMove(move);

    }
}
