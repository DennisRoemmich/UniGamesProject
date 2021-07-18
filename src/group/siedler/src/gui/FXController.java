package gui;

import buildings.Building;
import buildings.BuildingType;
import diceRolling.DiceRolling;
import helper.QuickJSON;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.event.ActionListener;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
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
    private final MapNode mapNode = new MapNode();
    
    private boolean tradeFlag = true;
    private MaterialType sellType;

    private final boolean animationStopFlag = false;
    

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
            //trade();
            
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

    private class Roller extends AnimationTimer{

        private final long FRAMES_PER_SEC = 10L;
        private final long INTERVAL = 1000000000L / FRAMES_PER_SEC;
        private final int MAX_ROLLS = 20;

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

        System.out.println(back.getWidth());
        System.out.println(back.getHeight());
        System.out.println(diceButton.getFitHeight());
    }

    public void setDiceImage(int n, ImageView dice){
        String diceImage = "./resources/Dice" + n + ".png";
        dice.setImage(new Image(diceImage));
    }

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
        setResources();


        MapFrame mapFrame = new MapFrame(mapNode);

        mapFrame.getMapNode().refreshOutput();
        mapFrame.setLayoutX(250);
        mapFrame.setLayoutY(150);

        back.getChildren().add(mapFrame);
        refreshOutput();
    }

    private void setupBindings() {
        background.fitHeightProperty().bind(back.heightProperty());
        background.fitWidthProperty().bind(back.widthProperty());

        diceButton.fitHeightProperty().bind(back.heightProperty().multiply(0.2));

        dice1.fitHeightProperty().bind(back.heightProperty().multiply(0.15));

        dice2.fitHeightProperty().bind(back.heightProperty().multiply(0.15));
        dice2.yProperty().bind(back.heightProperty().multiply(0.62));

        dice1.yProperty().bind(dice2.yProperty());
        dice1.xProperty().bind(back.widthProperty().multiply(0.79));

        playerMaterials.setImage(new Image("./resources/PlayerGreenMaterials.png"));
        playerMaterials.yProperty().bind(back.heightProperty().multiply(0.01));
        playerMaterials.xProperty().bind(back.widthProperty().multiply(0.7));
        playerMaterials.fitHeightProperty().bind(back.heightProperty().multiply(0.6));

        //woodLabel.setText("Wood");

        //Set Fonts
        woodLabel.setFont(Font.font("Arial", 15));
        wheatLabel.setFont(Font.font("Arial", 15));
        clayLabel.setFont(Font.font("Arial", 15));
        oreLabel.setFont(Font.font("Arial", 15));
        woolLabel.setFont(Font.font("Arial", 15));
        
        woodAmount.setFont(Font.font("Arial", 15));
        wheatAmount.setFont(Font.font("Arial", 15));
        clayAmount.setFont(Font.font("Arial", 15));
        oreAmount.setFont(Font.font("Arial", 15));
        woolAmount.setFont(Font.font("Arial", 15));
        
        currentPlayer.setFont(Font.font("Arial", 15));
    }

    private void setupPlayers() {
        AiPlayer aiPlayer = new AiPlayer(controller);
        controller.addPlayer(this, PlayerColor.BLUE);
        controller.addPlayer(aiPlayer, PlayerColor.RED);
        controller.addPlayer(this, PlayerColor.BLACK);
        controller.addPlayer(aiPlayer, PlayerColor.WHITE);
        controller.addPlayer(this, PlayerColor.PURPLE);
        controller.addPlayer(aiPlayer, PlayerColor.YELLOW);
    }
    
    public void setResources() {
    	
    	currentPlayer.setText(controller.getCurrentPlayerColor().name() + "'s turn");
    	woodAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOD)));
    	wheatAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WHEAT)));
    	woolAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.WOOL)));
    	oreAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.ORE)));
    	clayAmount.setText(String.valueOf(controller.getCurrentPlayerHand().getAmount(MaterialType.CLAY)));
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
    }
    
    //Trade functionality
    @FXML
    public void trade(KeyEvent event) {
    	if(controller.hasCurrentPlayerRolled())
    		if(tradeFlag) {
    			setFirstKeyStroke(event);
    			tradeFlag = false;
    		} else {
    			setSecondKeyStroke(event);
    			tradeFlag = true;
    		}
    	
    	
    	System.out.println("Trade accepted!");
    	refreshOutput();
    }


    public void rollAnimation(){
        clock.start();
    }
}
