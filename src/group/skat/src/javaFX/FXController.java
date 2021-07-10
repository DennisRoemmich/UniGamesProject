package javaFX;

import controller.SkatController;
import engine.SkatGame;
import framework.GameController;
import framework.Player;
import javaFX.enums.GUIState;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FXController implements Player, Initializable {

    SkatController controller;
    Scene scene;
    Presenter presenter;

    Map<String, FXButton> buttonDict;

   /* INITIALIZE */

    /** This is called after initialize and after the initialization of the controller object*/
    private void init(){

        Presenter.setFxController(this);

        bindings();
        createButtons();
        
    }


    public void createButtons(){

        FXButton.controller = this;


        // work with dictionary of buttons

        buttonDict = new HashMap<String, FXButton>();

        buttonDict.put("DEBUG_VIEW", new FXButton(label_ShowHideDebugView));


    }

    private SkatGame game(){

       return controller.getGame();

    }

    private GUIState guiState(){ // zu Klasse machen?

        return GUIState.NOT_STARTED;

    }


    /* LAYOUT */

    private void bindings(){


    }


    /* SETTER */

    public void setController(GameController controller) {

        this.controller = (SkatController) controller;
        init();

    }

    public void setScene(Scene scene) {

        this.scene = scene;

    }

    /* GETTER */





    /* OVERRIDE */

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /* HELPER */

    Text newText(String label, int size){

        var text = new Text();
        text.setText(label);
        text.setFont(Font.font("Avenir", FontWeight.NORMAL, size));

        return text;

    }

    /* FX EVENTS */

    public void showHidedebugView(MouseEvent mouseEvent) {

       if( anchor_DebugView.isVisible() ){
           anchor_DebugView.setVisible(false);
           label_ShowHideDebugView.setText("⌗");
       } else {
           anchor_DebugView.setVisible(true);
           var text = new StringBuilder();
           text.append("ROOT :      height: ").append(String.format("%.0f", anchor_root.getHeight())).append("     width: ").append(String.format("%.0f", anchor_root.getWidth())).append("\n");
           text.append("WINDOW :    height: ").append(String.format("%.0f", scene.getHeight())).append("     width: ").append(String.format("%.0f", scene.getWidth()));
           label_WindowSize.setText(text.toString());
           label_ShowHideDebugView.setText("⤫");
       }



    }

    /* OUTLETS */

    public AnchorPane anchor_root;
    public AnchorPane anchor_DebugView;
    public Label label_ShowHideDebugView;
    public Label label_WindowSize;

}
