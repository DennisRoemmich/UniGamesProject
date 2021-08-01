package gui;

import console.ConsoleMenu;
import engine.board.ChessMove;
import framework.GameLog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class GuiMenuController implements Initializable {

    @FXML
    private Button topLeftButton, topRightButton, middleLeftButton, middleRightButton, bottomLeftButton, bottomRightButton;
    @FXML
    private Label topLabel, middleLabel, bottomLabel;
    @FXML
    protected Button mUniversalButton;
    @FXML
    protected AnchorPane mBoardPane, configPane;
    @FXML
    protected Label ratingLabel;
    @FXML
    protected TextField mInputField;

    /* Button Highlighting */

    private final Color highlightColor = Color.web("#C8DF52");
    private final Color standardColor = Color.web("#DBE8D8");
    private final BorderWidths highlightWidth = new BorderWidths(3);
    private final BorderWidths standardWidth = new BorderWidths(3);
    private final CornerRadii cornerRadii = new CornerRadii(3);
    private final BorderStroke selectedBorderStroke =
            new BorderStroke(highlightColor, BorderStrokeStyle.SOLID, cornerRadii, highlightWidth);
    private final BorderStroke unselectedBorderStroke =
            new BorderStroke(standardColor, BorderStrokeStyle.SOLID, cornerRadii, standardWidth);

    /* Game Configuration States */

    private boolean mIsInConfiguration = true;
    private boolean mIsNetworkGame = false;
    private boolean mIsHost = false;
    private boolean mIsAiGame = true;
    private boolean mIsClassicalChess = true;
    private Optional<GameLog> gameLog = Optional.empty();

    /* -   -   -   -   -   -   - */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshConfigurationView();
    }

    public abstract void startGame();

    /* Refresh Methods */

    public void refreshConfigurationView() {
        if (mIsInConfiguration) {
            configPane.setVisible(true);
            refreshText();
            refreshStrokes();
        } else {
            configPane.setVisible(false);
        }
    }

    private void refreshText() {
        if (mIsNetworkGame) {
            middleLabel.setText("Host or Client?");
            middleLeftButton.setText("Host");
            middleRightButton.setText("Client");
        } else {
            middleLabel.setText("Hotseat or AI?");
            middleLeftButton.setText("Hotseat");
            middleRightButton.setText("AI");
        }
    }

    private void refreshStrokes() {
        boolean middleState;
        if (mIsNetworkGame) {
            topLeftButton.setBorder(new Border(unselectedBorderStroke));
            topRightButton.setBorder(new Border(selectedBorderStroke));
            middleState = mIsHost;
        } else {
            topLeftButton.setBorder(new Border(selectedBorderStroke));
            topRightButton.setBorder(new Border(unselectedBorderStroke));
            middleState = !mIsAiGame;
        }
        if (middleState) {
            middleLeftButton.setBorder(new Border(selectedBorderStroke));
            middleRightButton.setBorder(new Border(unselectedBorderStroke));
        } else {
            middleLeftButton.setBorder(new Border(unselectedBorderStroke));
            middleRightButton.setBorder(new Border(selectedBorderStroke));
        }
        if (mIsClassicalChess) {
            bottomLeftButton.setBorder(new Border(selectedBorderStroke));
            bottomRightButton.setBorder(new Border(unselectedBorderStroke));
        } else {
            bottomLeftButton.setBorder(new Border(unselectedBorderStroke));
            bottomRightButton.setBorder(new Border(selectedBorderStroke));
        }
    }

    /* Button Events */

    @FXML
    public final void topLeftClicked() {
        mIsNetworkGame = false;
        refreshConfigurationView();
    }

    @FXML
    public final void topRightClicked() {
        mIsNetworkGame = true;
        refreshConfigurationView();
    }

    @FXML
    public final void middleLeftClicked() {
        if (mIsNetworkGame) {
            mIsHost = true;
        } else {
            mIsAiGame = false;
        }
        refreshConfigurationView();
    }

    @FXML
    public final void middleRightClicked() {
        if (mIsNetworkGame) {
            mIsHost = false;
        } else {
            mIsAiGame = true;
        }
        refreshConfigurationView();
    }

    @FXML
    public final void bottomLeftClicked() {
        mIsClassicalChess = true;
        refreshConfigurationView();
    }

    @FXML
    public final void bottomRightClicked() {
        mIsClassicalChess = false;
        refreshConfigurationView();
    }

    @FXML
    public final void universalButtonClicked() {
        if (mIsInConfiguration) {
            mIsInConfiguration = false;
            refreshConfigurationView();
            startGame();
        }
    }

    public boolean isNetworkGame() {
        return mIsNetworkGame;
    }

    public boolean isHost() {
        return mIsHost;
    }

    public boolean isAiGame() {
        return mIsAiGame;
    }

    public boolean isClassicalChess() {
        return mIsClassicalChess;
    }

    public Optional<GameLog> getGameLog() {
        return gameLog;
    }

    public boolean isInConfiguration() {
        return mIsInConfiguration;
    }

    public void setIsInConfiguration(boolean mIsInConfiguration) {
        this.mIsInConfiguration = mIsInConfiguration;
    }
}
