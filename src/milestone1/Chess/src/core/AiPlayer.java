package core;

import console.ConsoleMain;
import console.Controller;
import framework.Player;
import org.json.simple.JSONObject;

import java.util.Optional;

public class AiPlayer implements Player {

	protected Controller mController;

	public AiPlayer(Controller controller) {
		this.mController = controller;
	}

	@Override
    public JSONObject requestMove(JSONObject dataType) {
		if (dataType.get("type") != "move") {
			return new JSONObject();
		}
		try {
				
			return null;
		} catch (Exception e) {
			System.out.println("Unknown Issue.");
			return new JSONObject();
		}
    }
}
