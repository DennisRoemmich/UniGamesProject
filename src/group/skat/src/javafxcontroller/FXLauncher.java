package javafxcontroller;

import controller.SkatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * fxLauncher starts the GUI
 */
public class FXLauncher extends Application {

    static final int MINWIDTH = 1365;
    static final int MINHEIGHT = 1024 - 200;


    static final int MAXWIDTH = 1365;
    static final int MAXHEIGHT = 1024 + 28;

    static SkatController mSkatController;

    private FXController mFxController;

    private static int mPlayerAmount;

    @Override
    public void start(Stage primaryStage) throws Exception{

        var classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("resources/SkatGUI.fxml");

        var loader = new FXMLLoader(resource);

        Parent root = loader.load();
        primaryStage.setTitle("Skat V0");
        primaryStage.setMinHeight(MINHEIGHT);
        primaryStage.setMinWidth(MINWIDTH);

        primaryStage.setMaxHeight(MAXHEIGHT);
        primaryStage.setMaxWidth(MAXWIDTH);

        var scene = new Scene(root, MINHEIGHT, MINWIDTH);
        primaryStage.setScene(scene);
        primaryStage.show();

        mFxController = (FXController) loader.getController();

        mFxController.setController(FXLauncher.mSkatController);
        mFxController.setScene(scene);
        mFxController.addPlayerWithAmount(mPlayerAmount);

    }

    /**
     * launch for 3 player
     * @param controller controller
     */
    public static void launchFX(SkatController controller) {

        FXLauncher.mSkatController = controller;
        launch();

    }

    /**
     * launch for variable players
     * @param controller controller
     * @param playerAmount playerAmount
     */
    public static void launchFX(SkatController controller, int playerAmount) {

        FXLauncher.mPlayerAmount = playerAmount;
        FXLauncher.mSkatController = controller;
        launch();

    }

    public FXController getFxController() {

        return mFxController;

    }

}