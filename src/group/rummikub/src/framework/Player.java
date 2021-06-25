package framework;

import org.json.simple.JSONObject;
import rummikub_controller.GameMove;
import rummikub_controller.RummikubController;
import rummikub_game.Rummikub;

public interface Player<S,T> {

	JSONObject requestJSONMove();
	T requestMove(S type);
	void setGameClass(Rummikub game);
	void setGameController(RummikubController gameController);
	GameMove requestGameMove();

}