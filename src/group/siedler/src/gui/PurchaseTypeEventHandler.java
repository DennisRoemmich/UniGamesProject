package gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import materials.MaterialType;
import siedlerController.Controller;

public class PurchaseTypeEventHandler implements EventHandler {
	private Controller controller;
	private MaterialType purchaseType = MaterialType.WOOD;
	
//	public PurchaseTypeEventHandler(MaterialType type) {
//		this.purchaseType = type;
//	}

	@Override
    public void handle(Event event) {
       // controller.setPurchaseType(MaterialType.WOOD);
    }
}
