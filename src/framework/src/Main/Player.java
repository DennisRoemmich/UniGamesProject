package Main;

import org.json.simple.JSONObject;

public interface Player {

	public void setSlot(Object aPlayer_Slot);

	public JSONObject requestMove();
}