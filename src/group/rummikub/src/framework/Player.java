package framework;

import org.json.simple.JSONObject;
import rummikub_controller.GameMove;
import rummikub_game.Rummikub;

public interface Player {

	JSONObject getMove();
	GameMove getGameMove();
	void setGameClass(Rummikub game);
}