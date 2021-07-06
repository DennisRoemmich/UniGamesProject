package javaFX;

import controller.SkatController;
import engine.SkatGame;
import framework.GameController;
import framework.Player;
import javafx.fxml.Initializable;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class FXController implements Player, Initializable {

    SkatController controller;




    private SkatGame game(){

       return controller.getGame();

    }


    /* OVERRIDE */

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        return null;
    }

    @Override
    public void setController(GameController controller) {

        this.controller = (SkatController) controller;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
