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
            mFxEngineController.showHelp();
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
                case C:
                	mFxEngineController.mController.getCurrentPlayerHand().addResources(MaterialType.WOOD, 1);
                	mFxEngineController.mController.getCurrentPlayerHand().addResources(MaterialType.CLAY, 1);
                	mFxEngineController.mController.getCurrentPlayerHand().addResources(MaterialType.ORE, 1);
                	mFxEngineController.mController.getCurrentPlayerHand().addResources(MaterialType.WOOL, 1);
                	mFxEngineController.mController.getCurrentPlayerHand().addResources(MaterialType.WHEAT, 1);
                	break;
                case D: 
                	mFxEngineController.getController().takeCard();
                	break;
			default:
				framework.PrintToConsole.println("Nothing happened!");
				break;
            }
        }
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
