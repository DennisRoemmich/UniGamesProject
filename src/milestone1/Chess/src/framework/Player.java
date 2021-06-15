package framework;

import org.json.simple.JSONObject;

import javax.swing.*;

public interface Player {
	JSONObject requestMove(JSONObject inputType);
}