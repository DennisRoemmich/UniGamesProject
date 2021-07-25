package gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import materials.MaterialType;
import siedlerController.Controller;
import siedlerController.GameState;
import siedlerFramework.PrintToConsole;

import java.util.Optional;

import cards.CardType;
import dice.DiceRolling;

public class FXKeyEventController {
    private FXEngineController fxEngineController;

    private Optional<CardType> cardType = Optional.empty();
    private MaterialType materialBuffer;

    public FXKeyEventController(FXEngineController fxEngineController) {
        this.fxEngineController = fxEngineController;
    }

    public void handleKeyInput(KeyEvent event) {

        if (event.getCode() == KeyCode.F1) {
            showHelp();
            return;
        }

        if (fxEngineController.getMaterialsLeftToSelect() > 0) {
            handleMaterialInput(event);
        } else {
            handleOptionalMoveInput(event);
        }
        fxEngineController.refreshOutput();
    }

    private void handleMaterialInput(KeyEvent event) {
        var materialType = getMaterialType(event);
        if(materialType.isPresent()) {
            PrintToConsole.println("Selected material: " + materialType.get());
            if (fxEngineController.getMaterialsLeftToSelect() == 2) {
                materialBuffer = materialType.get();
                fxEngineController.setMaterialsLeftToSelect(1);
            } else {
                if(cardType.isPresent()) {
                    switch(cardType.get()) {
                        case MONOPOLY -> fxEngineController.getController().playCard(CardType.MONOPOLY, materialType.get());
                        case INVENTION -> fxEngineController.getController().playInventionCard(materialBuffer, materialType.get());
                    }
                } else {
                    fxEngineController.getController().bankTrade(materialType.get(), materialBuffer);
                }
                fxEngineController.setMaterialsLeftToSelect(0);
            }
        }
    }

    private void handleOptionalMoveInput(KeyEvent event) {
        if (fxEngineController.getController().getState() == GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("Oprional Move: " + event.getCode());
            switch (event.getCode()) {
                case K -> fxEngineController.getController().playCard(CardType.KNIGHT, MaterialType.CLAY);
                case R -> fxEngineController.getController().playCard(CardType.ROAD, MaterialType.CLAY);
                case T -> {
                    cardType = Optional.empty();
                    fxEngineController.setMaterialsLeftToSelect(2);
                }
                case I -> {
                    cardType = Optional.of(CardType.INVENTION);
                    fxEngineController.setMaterialsLeftToSelect(2);
                }
                case M -> {
                    cardType = Optional.of(CardType.MONOPOLY);
                    fxEngineController.setMaterialsLeftToSelect(1);
                }
                case ENTER, SPACE -> fxEngineController.diceButtonClicked();
                case DIGIT0 -> fxEngineController.getController().takeCard();
            }
        }
    }

    public void showHelp() {
        PrintToConsole.println("*---Welcome to Siedler!---*");
        PrintToConsole.println("");
        PrintToConsole.println("*---How to play:---*");
        PrintToConsole.println("Trading: Press a key from \"1\" to \"5\" on your keyboard for the resource to trade in and then another key from \"1\" to \"5\" to get the corresponding resource.");
        PrintToConsole.println("Take a Development Card: Press the \"0\" key on your keyboard");
        PrintToConsole.println("Play a Development Card: Press the \"K\", \"R\", \"I\", \"M\" key on your keyboard for the desired card to play");
        PrintToConsole.println("Important: If you like to play a development card, please choose the resource you want to get by pressing the desired number key on your keyboard BEFORE playing the card. Please press \"Enter\" afterwards such that no accidental trading occurs :)");
        PrintToConsole.println("");
        PrintToConsole.println("*---Differences to the standard game---*");
        PrintToConsole.println("The invention card gives you 5 of one resource instead of 2 of any. With 4:1 trading the result is the same. ");
        PrintToConsole.println("The Road building card gives you 2 Clay and Wood instead of letting you build two roads. With those resources you still may build those 2 roads or something else as you desire!");
    }


    public static Optional<MaterialType> getMaterialType(KeyEvent event) {
        return switch (event.getCode()) {
            case DIGIT1 -> Optional.of(MaterialType.WOOD);
            case DIGIT2 -> Optional.of(MaterialType.WHEAT);
            case DIGIT3 -> Optional.of(MaterialType.WOOL);
            case DIGIT4 -> Optional.of(MaterialType.ORE);
            case DIGIT5 -> Optional.of(MaterialType.CLAY);
            default -> Optional.empty();
        };
    }
}
