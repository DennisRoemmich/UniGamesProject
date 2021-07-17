package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import materials.MaterialType;
import siedlerController.Controller;
import streets.StreetType;

public class TradeEventHandler implements EventHandler {

	private Controller controller;
	private MaterialType sellType;
    	
	public TradeEventHandler(MaterialType type) {
		this.sellType = type;
	}

	@Override
    public void handle(Event event) {
		controller.setPurchaseType(sellType);
		controller.bankTrade();
    }
}
