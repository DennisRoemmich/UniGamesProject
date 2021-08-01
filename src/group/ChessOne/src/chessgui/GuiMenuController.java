package chessgui;

import chessframework.GameLog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the GUI options interface
 * @author Jan de Boer, Dennis Roemmich
 *
 */
public abstract class GuiMenuController extends GuiElements implements Initializable {

    /* Button Highlighting */

    private final Color mHighlightColor = Color.web("#C8DF52");
    private final Color mStandardColor = Color.web("#DBE8D8");
    private final BorderWidths mHighlightWidth = new BorderWidths(3);
    private final BorderWidths mStandardWidth = new BorderWidths(3);
    private final CornerRadii mCornerRadii = new CornerRadii(3);
    private final BorderStroke mSelectedBorderStroke =
            new BorderStroke(mHighlightColor, BorderStrokeStyle.SOLID, mCornerRadii, mHighlightWidth);
    private final BorderStroke mUnselectedBorderStroke =
            new BorderStroke(mStandardColor, BorderStrokeStyle.SOLID, mCornerRadii, mStandardWidth);

    /* Game Configuration States */

    private boolean mIsInConfiguration = true;
    private boolean mIsNetworkGame = false;
    private boolean mIsHost = false;
    private boolean mIsAiGame = true;
    private boolean mIsClassicalChess = true;
    private Optional<GameLog> mGameLog = Optional.empty();

    /* -   -   -   -   -   -   - */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshConfigurationView();
    }

    public abstract void startGame();

    /* Refresh Methods */

    public void refreshConfigurationView() {
        if (mIsInConfiguration) {
            mConfigPane.setVisible(true);
            mUniversalButton.setText("Start Game");
            refreshText();
            refreshStrokes();
        } else {
            mConfigPane.setVisible(false);
            mUniversalButton.setText("Execute Move");
        }
    }

    private void refreshText() {
        if (mIsNetworkGame) {
            mMiddleLabel.setText("Host or Client?");
            mMiddleLeftButton.setText("Host");
            mMiddleRightButton.setText("Client");
        } else {
            mMiddleLabel.setText("Hotseat or AI?");
            mMiddleLeftButton.setText("Hotseat");
            mMiddleRightButton.setText("AI");
        }
    }

    private void refreshStrokes() {
        boolean middleState;
        if (mIsNetworkGame) {
            mTopLeftButton.setBorder(new Border(mUnselectedBorderStroke));
            mTopRightButton.setBorder(new Border(mSelectedBorderStroke));
            middleState = mIsHost;
        } else {
            mTopLeftButton.setBorder(new Border(mSelectedBorderStroke));
            mTopRightButton.setBorder(new Border(mUnselectedBorderStroke));
            middleState = !mIsAiGame;
        }
        if (middleState) {
            mMiddleLeftButton.setBorder(new Border(mSelectedBorderStroke));
            mMiddleRightButton.setBorder(new Border(mUnselectedBorderStroke));
        } else {
            mMiddleLeftButton.setBorder(new Border(mUnselectedBorderStroke));
            mMiddleRightButton.setBorder(new Border(mSelectedBorderStroke));
        }
        if (mIsClassicalChess) {
            mBottomLeftButton.setBorder(new Border(mSelectedBorderStroke));
            mBottomRightButton.setBorder(new Border(mUnselectedBorderStroke));
        } else {
            mBottomLeftButton.setBorder(new Border(mUnselectedBorderStroke));
            mBottomRightButton.setBorder(new Border(mSelectedBorderStroke));
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
        return mGameLog;
    }

    public boolean isInConfiguration() {
        return mIsInConfiguration;
    }

    public void setIsInConfiguration(boolean mIsInConfiguration) {
        this.mIsInConfiguration = mIsInConfiguration;
    }
}
