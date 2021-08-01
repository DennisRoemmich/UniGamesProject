package siedler.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import siedler.controller.Main;
import siedler.framework.PrintToConsole;

public class FxMenuController {
	
	@FXML
	protected Button mKonstanzButton;
	@FXML
	protected Button mStandardButton;
	@FXML
	protected TextField mFieldHumanAmount;
	@FXML
	protected TextField mFieldAiAmount;
	@FXML
	protected TextField mFieldStartMaterial;
	
	
	ClassLoader mClassLoader;
	
    private static boolean mIsStandardMap;
    private static int mAiAmount;
    private static int mStartingAmount;
    
    private static int mHumanAmount;
    public static int getHumanAmount() {
		return mHumanAmount;
	}

	public static void setHumanAmount(int humanAmount) {
		FxMenuController.mHumanAmount = humanAmount;
	}

	
    
	public static int getAiAmount() {
		return mAiAmount;
	}

	public static void setAiAmount(int aiAmount) {
		FxMenuController.mAiAmount = aiAmount;
	}

	

    public static int getStartingAmount() {
		return mStartingAmount;
	}

	public static void setStartingAmount(int startingAmount) {
		FxMenuController.mStartingAmount = startingAmount;
	}

	public static boolean ismIsStandardMap() {
		return mIsStandardMap;
	}

	public static void setmIsStandardMap(boolean mIsStandardMap) {
		FxMenuController.mIsStandardMap = mIsStandardMap;
	}
	

	@FXML
    public void handleStandardButton(ActionEvent event) {
		setmIsStandardMap(true);
    	try {
    		setHumanAmount(Integer.parseInt(mFieldHumanAmount.getText()));
    		setAiAmount(Integer.parseInt(mFieldAiAmount.getText()));
    		setStartingAmount(Integer.parseInt(mFieldStartMaterial.getText()));
    	} catch (Exception e) {
    		PrintToConsole.println("Please input a number!");
    	}
    	mClassLoader = getClass().getClassLoader();
        var resourceMain = mClassLoader.getResource("resources/SiedlerGUI.fxml");
        if (resourceMain == null) {
            PrintToConsole.println("\n=== URL is null ===\n");
        }
        FXMLLoader loaderMain = new FXMLLoader(resourceMain);
        Parent rootMain;
		try {
			rootMain = loaderMain.load();
	        Scene scene = new Scene(rootMain, 1200, 900);
	        
	        scene.getRoot().requestFocus();
	        
	    	Main.getPrimaryStage().setScene(scene);
	    	Main.getPrimaryStage().setTitle("Die Siedler von Konstanz");
	    	Main.getPrimaryStage().show();
		} catch (IOException e) {
			e.printStackTrace();
		}


    }
    
    @FXML
    public void handleKonstanzButton(ActionEvent event) {

		try {
	    	setmIsStandardMap(false);
	    	
			setHumanAmount(Integer.parseInt(mFieldHumanAmount.getText()));
			setAiAmount(Integer.parseInt(mFieldAiAmount.getText()));
			setStartingAmount(Integer.parseInt(mFieldStartMaterial.getText()));
	    	
	    	mClassLoader = getClass().getClassLoader();
	        var resourceMain = mClassLoader.getResource("resources/SiedlerGUI.fxml");
	        if (resourceMain == null) {
	            PrintToConsole.println("\n=== URL is null ===\n");
	        }
	        FXMLLoader loaderMain = new FXMLLoader(resourceMain);
	        Parent rootMain;
			rootMain = loaderMain.load();
	        Scene scene = new Scene(rootMain, 1200, 900);
	    	
	    	scene.getRoot().requestFocus();
	    	
	    	Main.getPrimaryStage().setScene(scene);
	    	Main.getPrimaryStage().setTitle("Die Siedler von Konstanz");
	    	Main.getPrimaryStage().show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
