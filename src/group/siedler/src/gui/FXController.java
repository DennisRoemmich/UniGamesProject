package gui;

import buildings.Building;
import buildings.BuildingType;
import developmentCards.CardType;
import diceRolling.DiceRolling;
import helper.QuickJSON;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import map.BuildRules;
import map.Map;
import map.MapGenerator;
import materials.MaterialType;

import org.json.simple.JSONObject;
import player.PlayerColor;
import positions.EdgePosition;
import positions.EdgePositionZCord;
import positions.NodePosition;
import siedlerController.AiPlayer;
import siedlerController.Controller;
import siedlerFramework.Player;
import siedlerFramework.Presenter;
import siedlerFramework.PrintToConsole;
import streets.Street;
import streets.StreetType;

import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class FXController implements Initializable, Player, Presenter {

    private Controller controller = new Controller();
    private MapNode mapNode = new MapNode();
    
    private boolean tradeFlag = true;
    private MaterialType sellType;
    private MaterialType chosen;


	private boolean animationStopFlag = false;

    private Stage stage;
    private Scene scene;
    private Parent root;
    

    @FXML
    private AnchorPane back;
    @FXML
    private ImageView background;
    @FXML
    private ImageView diceButton;
    @FXML
    private ImageView dice1;
    @FXML
    private ImageView dice2;
    @FXML
    private ImageView playerMaterials;
    @FXML
    private Label woodLabel;
    @FXML
    private Label wheatLabel;
    @FXML
    private Label woolLabel;
    @FXML
    private Label clayLabel;
    @FXML
    private Label oreLabel;   
    @FXML
    private Label woodAmount;
    @FXML
    private Label wheatAmount;
    @FXML
    private Label woolAmount;
    @FXML
    private Label clayAmount;
    @FXML
    private Label oreAmount;
    @FXML
    private Label currentPlayer;
    @FXML
   	private ImageView tradeWithBankButton;
    @FXML
    private Label winner;
    @FXML
    private Label tradeError;
    @FXML
    private Label points;
    @FXML
    private Label victoryPointCard;
    @FXML
    private Label knightCard;
    @FXML
    private Label roadCard;
    @FXML
    private Label inventionCard;
    @FXML
    private Label monopolyCard;
    @FXML
    private Label victoryAmount;
    @FXML
    private Label knightAmount;
    @FXML
    private Label roadAmount;
    @FXML
    private Label inventionAmount;
    @FXML
    private Label monopolyAmount;
    @FXML
    private Label consoleInfo;
    

    Roller clock = new Roller();

    public final static Image finishButtonImage = new Image("./resources/FinishButton.png");
    public final static Image diceButtonImage = new Image("./resources/DiceButton.png");

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if(!inputType.containsKey("type")) {
            return QuickJSON.create("reply", "invalid input");
        }
        switch (inputType.get("type").toString()) {
            case "rollDices":
                diceButton.setImage(diceButtonImage);
                diceButton.setVisible(true);
            case "optionalMove":
                refreshOutput();
        }
        return QuickJSON.create("reply", "valid");
    }

    @Override
    public void refreshOutput() {
        if(mapNode != null) {
            mapNode.refreshOutput();
            setResources();
            setWinner();
            setPoints();
            setCards();
            
            updateDiceViews();
            if(!controller.isRunning()) {
                diceButton.setVisible(false);
                dice1.setVisible(false);
                dice2.setVisible(false);
            } else {
                dice1.setVisible(true);
                dice2.setVisible(true);
                if(controller.isItMyTurn(this) && controller.hasCurrentPlayerRolled()) {
                    mapNode.addPlaceholderNodes(controller.getCurrentPlayerColor(), controller);
                }
            }
        }
    }

    public void switchToMainScene(MouseEvent mouseEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(new File("src/gui/SiedlerGUI.fxml").toURI().toURL());
//        root = loader.load();
//        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    public void switchToTradingWithBankScene(MouseEvent mouseEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(new File("src/gui/TradingWithBankGUI.fxml").toURI().toURL());
//        root = loader.load();
//        stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    private class Roller extends AnimationTimer{

        private long FRAMES_PER_SEC = 10L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private int MAX_ROLLS = 20;

        private long last = 0;
        private int count = 0;

        private boolean isRunning = false;

        @Override
        public void handle(long l) {
            isRunning = true;
            if(l - last > INTERVAL){
                int r = 2 + (int)(Math.random() * 5);
                setDiceImage(r, dice1);
                int j = 2 + (int)(Math.random() * 5);
                setDiceImage(j, dice2);
                last = l;
                count++;
                if (count > MAX_ROLLS || animationStopFlag){
                    clock.stop();
                    finishRoll();
                    count = 0;
                    isRunning = false;
                }
            }
        }

        public boolean isRunning() {
            return isRunning;
        }
    }

    public void updateDiceViews() {
        setDiceImage(DiceRolling.dice1, dice1);
        setDiceImage(DiceRolling.dice2, dice2);
    }

    public void finishRoll(){
        controller.handleRoll();
        refreshOutput();
    }

    public void diceButtonClicked(){
        if(controller.isItMyTurn(this)) {
            if(controller.hasCurrentPlayerRolled()) {
                diceButton.setVisible(false);
                controller.endMove();
                mapNode.clearPlaceholderNodes();
            } else {
                diceButton.setImage(finishButtonImage);
                clock.start();
            }
        }

        //System.out.println(back.getWidth());
        //System.out.println(back.getHeight());
        //System.out.println(diceButton.getFitHeight());
    }

    public void setDiceImage(int n, ImageView dice){
        String diceImage = "./resources/Dice" + n + ".png";
        dice.setImage(new Image(diceImage));
    }

    public void setPlayerMaterialsImage (String s, ImageView playerMaterials){

        String playerMaterialsImage = "./resources/Player" + s + "Materials.png";

        playerMaterials.setImage(new Image(playerMaterialsImage));
    }
/*
    public void tradeWithBankButtonClicked(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(new File("src/gui/TradingWithBankGUI.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200,900);

        Stage tradingStage = new Stage();
        tradingStage.setTitle("Trade with Bank");
        tradingStage.setScene(scene);
        tradingStage.show();
    }

*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new Controller();
        controller.setPresenter(this);


        int colorIndex = ThreadLocalRandom.current().nextInt(0, PlayerColor.values().length);
        var colors = PlayerColor.values();
        PlayerColor color = colors[colorIndex];

        /*DO NOT DELETE; BINDING IS BEING IMPLEMENTED HERE BUT IS A PAIN IN THE ASS*/

        setupBindings();
        setupPlayers();

        controller.newGame();
        mapNode.setMap(controller.getMap());
        mapNode.setScaleX(1.75);
        mapNode.setScaleY(1.75);
        mapNode.setScaleZ(1.75);
        setResources();


        MapFrame mapFrame = new MapFrame(mapNode);

        mapFrame.getMapNode().refreshOutput();
        mapFrame.setLayoutX(450);
        mapFrame.setLayoutY(400);

        back.getChildren().add(mapFrame);
        refreshOutput();
    }

    private void setupBindings() {
        /*
        background.fitHeightProperty().bind(back.heightProperty());
        background.fitWidthProperty().bind(back.widthProperty());

        diceButton.fitHeightProperty().bind(back.heightProperty().multiply(0.2));

        dice1.fitHeightProperty().bind(back.heightProperty().multiply(0.15));

        dice2.fitHeightProperty().bind(back.heightProperty().multiply(0.15));
        dice2.yProperty().bind(back.heightProperty().multiply(0.62));

        dice1.yProperty().bind(dice2.yProperty());
        dice1.xProperty().bind(back.widthProperty().multiply(0.79));

        //playerMaterials.setImage(new Image("./resources/PlayerGreenMaterials.png"));
        playerMaterials.yProperty().bind(back.heightProperty().multiply(0.01));
        playerMaterials.xProperty().bind(back.widthProperty().multiply(0.7));
        playerMaterials.fitHeightProperty().bind(back.heightProperty().multiply(0.6));

        //woodLabel.setText("Wood");

        //Set Fonts
        woodLabel.setFont(Font.font("Bauhaus 93", 27));
        wheatLabel.setFont(Font.font("Bauhaus 93", 27));
        clayLabel.setFont(Font.font("Arial", 15));
        oreLabel.setFont(Font.font("Arial", 15));
        woolLabel.setFont(Font.font("Arial", 15));
        
        woodAmount.setFont(Font.font("Arial", 15));
        wheatAmount.setFont(Font.font("Arial", 15));
        clayAmount.setFont(Font.font("Arial", 15));
        oreAmount.setFont(Font.font("Arial", 15));
        woolAmount.setFont(Font.font("Arial", 15));
        
        currentPlayer.setFont(Font.font("Arial", 15));
        */

    }

    private void setupPlayers() {
        AiPlayer aiPlayer = new AiPlayer(controller);
        controller.addPlayer(this, PlayerColor.BLUE);
        controller.addPlayer(aiPlayer, PlayerColor.WHITE);
        controller.addPlayer(this, PlayerColor.RED);
        controller.addPlayer(aiPlayer, PlayerColor.YELLOW);
        controller.addPlayer(this, PlayerColor.PURPLE);
        controller.addPlayer(aiPlayer, PlayerColor.BLACK);
    }
    
    public void setResources() {
    	
    	setPlayerMaterialsImage(controller.getCurrentPlayerColor().name(), playerMaterials);
    	currentPlayer.setText(controller.getCurrentPlayerColor().name() + "'s turn");
    	woodAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOD)));
    	wheatAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WHEAT)));
    	woolAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOL)));
    	oreAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.ORE)));
    	clayAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.CLAY)));
    }
    
    
    public MaterialType chooseResource(KeyEvent event) {
    	while(chosen == null) {
	    	if (event.getCode() == KeyCode.DIGIT1) {
	    		return MaterialType.WOOD;	    		
	    	}
	    	if (event.getCode() == KeyCode.DIGIT2) {
	    		return MaterialType.WHEAT;
	    		
	    	}
	    	if (event.getCode() == KeyCode.DIGIT3) {
	    		return MaterialType.WOOL;
	    		
	    	}
	    	if (event.getCode() == KeyCode.DIGIT4) {
	    		return MaterialType.ORE;
	    		
	    	}
	    	if (event.getCode() == KeyCode.DIGIT5) {
	    		return MaterialType.CLAY;
	    		
	    	}
    	}
    	return MaterialType.WOOD;
    }
    
    //Sets Sell MaterialType with the first keystroke
    public void setFirstKeyStroke(KeyEvent event) {
    	if (event.getCode() == KeyCode.DIGIT1) {
    		this.sellType = MaterialType.WOOD;
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT2) {
    		this.sellType = MaterialType.WHEAT;
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT3) {
    		this.sellType = MaterialType.WOOL;
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT4) {
    		this.sellType = MaterialType.ORE;
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT5) {
    		this.sellType = MaterialType.CLAY;
    		
    	}
    }
    
  //Sets the Purchase value for the 2nd keystroke
    public void setSecondKeyStroke(KeyEvent event) {
    	if (event.getCode() == KeyCode.DIGIT1) {
    		controller.bankTrade(MaterialType.WOOD, sellType);
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT2) {
    		controller.bankTrade(MaterialType.WHEAT, sellType);
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT3) {
    		controller.bankTrade(MaterialType.WOOL, sellType);
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT4) {
    		controller.bankTrade(MaterialType.ORE, sellType);
    		
    	}
    	if (event.getCode() == KeyCode.DIGIT5) {
    		controller.bankTrade(MaterialType.CLAY, sellType);
    		
    	}
    	if (event.getCode() == KeyCode.ENTER) {
    		
    		
    	}
    }
    
    public void setWinner() {
    	if(controller.isGameHasWinner()) {
    		
    		winner.setText("Player " +controller.getWinningColor() + " has won!");
    	}
    }
     public void setPoints() {
    	int winPoints = controller.getWinPoints();
    	points.setText("Victory Points:        " + winPoints);
     }
     
     public void setCards() {
//    	 int counterVP = 0;
//    	 int counterK = 0;
//    	 int counterR = 0;
//    	 int counterI = 0;
//    	 int counterM = 0;
//    	 victoryAmount.setText(String.valueOf(counterVP));
//    	 knightAmount.setText(String.valueOf(counterK));
//    	 roadAmount.setText(String.valueOf(counterR) );
//    	 inventionAmount.setText(String.valueOf(counterI) );
//    	 monopolyAmount.setText(String.valueOf(counterM) );
//    	 
//    	 for (int i = 0; i < controller.getPlayerCards().size(); i++) {
//    		 if(controller.getPlayerCards().get(i).getType() == CardType.VICTORY) {
//    			 counterVP++;
//    			 victoryAmount.setText(String.valueOf(counterVP) );
//    			
//    		 }
//    		 if(controller.getPlayerCards().get(i).getType() == CardType.KNIGHT) {
//    			 counterK++;
//    			 knightAmount.setText(String.valueOf(counterK));
//    			 
//    		 }
//    		 if(controller.getPlayerCards().get(i).getType() == CardType.ROAD) {
//    			 counterR++;
//    			 roadAmount.setText(String.valueOf(counterR) );
//    		 }
//    		 if(controller.getPlayerCards().get(i).getType() == CardType.INVENTION) {
//    			 counterI++;
//    			 inventionAmount.setText(String.valueOf(counterI) );
//    		 }
//    		 if(controller.getPlayerCards().get(i).getType() == CardType.MONOPOLY) {
//    			 counterM++;
//    			 monopolyAmount.setText(String.valueOf(counterM) );
//    		 }
//    	 }
//    	 
    	 knightAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.KNIGHT)));
    	 victoryAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.VICTORY)));
    	 roadAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.ROAD)));
    	 inventionAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.INVENTION)));
    	 monopolyAmount.setText(String.valueOf(controller.getCurrentPlayerCards().getAmount(CardType.MONOPOLY)));
     }
    
    //Trade functionality
    @FXML
    public void trade(KeyEvent event) {
		if (event.getCode() == KeyCode.F1) {
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
    		refreshOutput();
    		return;
    	}
    	if(controller.hasCurrentPlayerRolled()) {

    		String error ="You do not own this card!";
    		
    		if (event.getCode() == KeyCode.DIGIT0) {
        		controller.takeCard();
        		refreshOutput();
        		return;
        	}
        	if (event.getCode() == KeyCode.K) {
        		if(controller.getCurrentPlayerCards().getAmount(CardType.KNIGHT)>0) {
        			controller.playCard(CardType.KNIGHT, sellType);
        			refreshOutput();
        			return;
        		} else {
        			PrintToConsole.println(error);
        			return;
        		}
        	}	
        	if (event.getCode() == KeyCode.R) {
        		if(controller.getCurrentPlayerCards().getAmount(CardType.ROAD)>0) {
        			controller.playCard(CardType.ROAD, sellType);
        			refreshOutput();
        			return;
        		} else {
        			PrintToConsole.println(error);
        			return;
        		}
        	}	
        	if (event.getCode() == KeyCode.I ) {
        		if(controller.getCurrentPlayerCards().getAmount(CardType.INVENTION)>0) {
        			controller.playCard(CardType.INVENTION, sellType);
        			refreshOutput();
        			return;
        		} else {
        			PrintToConsole.println(error);
        			return;
        		}
        	}	
        	if (event.getCode() == KeyCode.M) {
        		if(controller.getCurrentPlayerCards().getAmount(CardType.MONOPOLY)>0) {
        		controller.playCard(CardType.MONOPOLY, sellType);
        		refreshOutput();
        		return;
        		} else {
        			PrintToConsole.println(error);
        			return;
        		}
        	}	
    		if(tradeFlag) {
    			setFirstKeyStroke(event);
    			tradeFlag = false;
    			PrintToConsole.println("1st input registered!");
    		} else {
    			tradeError.setText("");
    			setSecondKeyStroke(event);
    			if(controller.getCurrentPlayerHand().isTradeImpossible()) {
    				//tradeError.setText("Not enough ressources!");
    				PrintToConsole.println("Not enough ressources!");
    			} else {
    				PrintToConsole.println("2nd input registered!");
    			}
    			tradeFlag = true;
    		}
    	
    	}
    	refreshOutput();
    }


    public void rollAnimation(){
        clock.start();
    }
}
