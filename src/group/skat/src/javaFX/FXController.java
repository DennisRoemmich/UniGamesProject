package javaFX;

import framework.GameController;
import framework.Player;
import javafx.fxml.Initializable;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class FXController implements Player, Initializable {

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        return null;
    }

    @Override
    public void setController(GameController controller) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
