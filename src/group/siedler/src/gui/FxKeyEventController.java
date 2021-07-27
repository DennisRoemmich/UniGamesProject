package gui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import materials.MaterialType;

import java.util.Optional;
import cards.CardType;
import controller.GameState;
import framework.PrintToConsole;

public class FxKeyEventController {
    private FxEngineController mFxEngineController;

    private Optional<CardType> mCardType = Optional.empty();
    private MaterialType mMaterialBuffer;

    public FxKeyEventController(FxEngineController fxEngineController) {
        this.mFxEngineController = fxEngineController;
    }

    public void handleKeyInput(KeyEvent event) {

        if (event.getCode() == KeyCode.F1) {
            showHelp();
            return;
        }

        if (mFxEngineController.getMaterialsLeftToSelect() > 0) {
            handleMaterialInput(event);
        } else {
            handleOptionalMoveInput(event);
        }
        mFxEngineController.refreshOutput();
    }

    private void handleMaterialInput(KeyEvent event) {
        var materialType = getMaterialType(event);
        if (materialType.isPresent()) {
            PrintToConsole.println("Selected material: " + materialType.get());
            if (mFxEngineController.getMaterialsLeftToSelect() == 2) {
                mMaterialBuffer = materialType.get();
                mFxEngineController.setMaterialsLeftToSelect(1);
            } else {
                if (mCardType.isPresent()) {
                	checkCardTypeForMaterialInput(materialType);
                } else {
                    mFxEngineController.getController().bankTrade(materialType.get(), mMaterialBuffer);
                }
                mFxEngineController.setMaterialsLeftToSelect(0);
            }
        }
    }

	private void checkCardTypeForMaterialInput(Optional<MaterialType> materialType) {
		if (!materialType.isPresent()) {
			return;
		}
		if (mCardType.get() == CardType.MONOPOLY) {
			mFxEngineController.getController().playCard(CardType.MONOPOLY, materialType.get());
		}
		if (mCardType.get() == CardType.INVENTION) {	
			mFxEngineController.getController().playInventionCard(mMaterialBuffer, materialType.get());
		}
	}

    private void handleOptionalMoveInput(KeyEvent event) {
        if (mFxEngineController.getController().getState() == GameState.OPTIONAL_MOVES) {
            PrintToConsole.println("Oprional Move: " + event.getCode());
            switch (event.getCode()) {
                case K: 
                	mFxEngineController.getController().playCard(CardType.KNIGHT, MaterialType.CLAY);
                	break;
                case R: 
                	mFxEngineController.getController().playCard(CardType.ROAD, MaterialType.CLAY);
                	break;
                case T: 
                    mCardType = Optional.empty();
                    mFxEngineController.setMaterialsLeftToSelect(2);
                    break;
                case I:
                	if (mFxEngineController.mController.getCurrentPlayerCards().getAmount(CardType.INVENTION) > 0) {
                		mCardType = Optional.of(CardType.INVENTION);
                		mFxEngineController.setMaterialsLeftToSelect(2);
                	} else {
                		framework.PrintToConsole.println("You do not own this card!");
                	}
                	break;
                case M:
                	if (mFxEngineController.mController.getCurrentPlayerCards().getAmount(CardType.MONOPOLY) > 0) {
                		mCardType = Optional.of(CardType.MONOPOLY);
                		mFxEngineController.setMaterialsLeftToSelect(1);
                	} else {
                		framework.PrintToConsole.println("You do not own this card!");
                	}
                	break;
                case ENTER:
                	mFxEngineController.diceButtonClicked();
                	break;
                case SPACE:
                	mFxEngineController.diceButtonClicked();
                	break;
                case DIGIT0: 
                	mFxEngineController.getController().takeCard();
                	break;
			default:
				framework.PrintToConsole.println("Nothing happened!");
				break;
            }
        }
    }

    public void showHelp() {
        PrintToConsole.println("*---Welcome to Siedler!---*");
        PrintToConsole.println("");
        PrintToConsole.println("*---How to play:---*");
        PrintToConsole.print("Trading: Press the \"T\" key and the corresponding material key ");
        PrintToConsole.print("from \"1\" to \"5\" on your keyboard for the resource to ");
        PrintToConsole.print ("trade in and then another key from \"1\" to \"5\" to get a corresponding resource. \n");
        PrintToConsole.print("Take a Development Card: Press the \"0\" key on your keyboard \n");
        PrintToConsole.print("Play a Development Card: Press the \"K\", \"R\", \"I\", \"M\" ");
        PrintToConsole.print("key on your keyboard for the desired card to play. ");
        PrintToConsole.print("If you like to play a development card, ");
        PrintToConsole.print("you might need to choose the resource(s) you want to ");
        PrintToConsole.print("get by pressing the desired number key on your keyboard. \n");
        PrintToConsole.println("Dices: You may roll the dice by pressing the ENTER key or clicking on the image");
        PrintToConsole.println("");
        PrintToConsole.println("*---Differences to the standard game---*");
        PrintToConsole.print("The Road building card gives 2 Clay and Wood building roads. ");
        PrintToConsole.print("With those resources you may build those 2 roads or something else as you desire! \n");
        PrintToConsole.print("Since the players have grown suspicious on each other");
        PrintToConsole.print("in this version they have decided to not trade with each other \n");
        PrintToConsole.print("Thus player trading is not possible in this version of Siedler!");
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
