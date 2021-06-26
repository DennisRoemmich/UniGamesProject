package core;

import framework.Player;
import org.json.simple.JSONObject;

public class AiPlayer implements Player {
    @Override
    public JSONObject requestMove(JSONObject dataType) {
		if (dataType.get("type") != "move") {
			return new JSONObject();
		}

		try {
			//ChessMove move = ChessMove.valueOf(Ainput, mController);
			//return move.toJSon();
			return null;
		} catch (Exception e) {
			System.out.println("Unknown Issue.");
			return new JSONObject();
		}
    }
}
