package gui;

import developmentCards.CardType;
import diceRolling.DiceRolling;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import materials.MaterialType;
import siedlerController.Controller;
import siedlerController.GameState;

public class FXKeyEventController {
    private FXController fxController;

    public FXKeyEventController(FXController fxController) {
        this.fxController = fxController;
    }

    public void handleKeyInput(KeyEvent event) {

        if(event.getCode() == KeyCode.F1) {
            fxController.showHelp();
        }

        switch (event.getCode()) {
            case F1 -> fxController.showHelp();
            case T -> handleKeyInput(event);
            case K -> fxController.getController().playCard(CardType.KNIGHT, MaterialType.CLAY);
            case R -> fxController.getController().playCard(CardType.ROAD, MaterialType.CLAY);
            case I -> fxController.getController().playCard(CardType.INVENTION, MaterialType.CLAY);
            case M -> fxController.getController().playCard(CardType.MONOPOLY, MaterialType.CLAY);
            case DIGIT0 -> fxController.getController().takeCard();
        }

        fxController.refreshOutput();
    }
}
