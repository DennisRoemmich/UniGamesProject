package gui;

import diceRolling.DiceRolling;
import helper.QuickJSON;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import materials.MaterialType;

import org.json.simple.JSONObject;
import player.PlayerColor;
import siedlerController.AiPlayer;
import siedlerController.Controller;
import siedlerFramework.Player;
import siedlerFramework.Presenter;

import javax.sound.sampled.*;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class FXController implements Initializable, Player, Presenter {
    @FXML
    private ImageView diceButton;
    @FXML
    private ImageView tradeWithBankButton;
    @FXML
    private AnchorPane back;
    @FXML
    private ImageView background;
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
    private ImageView message;


    private Controller controller = new Controller();
    private MapNode mapNode = new MapNode();
    
    private boolean tradeFlag = true;
    private MaterialType sellType;

    private boolean animationStopFlag = false;

    private String inMaterial;
    private String receiveMaterial;

    private AudioInputStream diceClipNameAIS;
    private Clip diceClipName;

    private AudioInputStream buttonClipNameAIS;
    private Clip buttonClipName;


    Roller clock = new Roller();

    ClassLoader classLoader = getClass().getClassLoader();

    public final static String finishButtonImageName = "resources/FinishButton.png";
    public final static String diceButtonImageName = "resources/DiceButton.png";

    @Override
    public JSONObject requestMove(JSONObject inputType) {
        if(!inputType.containsKey("type")) {
            return QuickJSON.create("reply", "invalid input");
        }
        switch (inputType.get("type").toString()) {
            case "rollDices":
                diceButton.setImage(new Image(classLoader.getResourceAsStream(diceButtonImageName)));
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

    public void sayHi(){
        System.out.println("hello");
    }

    public void updateDiceViews() {
        setDiceImage(DiceRolling.dice1, dice1);
        setDiceImage(DiceRolling.dice2, dice2);
    }

    public void finishRoll(){
        controller.handleRoll();
        refreshOutput();
    }

    public void diceButtonClicked() throws UnsupportedAudioFileException, IOException {
        buttonClipName.setFramePosition(0);
        buttonClipName.start();
        if(controller.isItMyTurn(this)) {
            if(controller.hasCurrentPlayerRolled()) {
                diceButton.setVisible(false);
                controller.endMove();
                mapNode.clearPlaceholderNodes();
            } else {
                diceButton.setImage(new Image(classLoader.getResourceAsStream(finishButtonImageName)));

                diceClipName.setFramePosition(0);
                diceClipName.start();
                clock.start();
            }
        }

        System.out.println(back.getWidth());
        System.out.println(back.getHeight());
        System.out.println(diceButton.getFitHeight());
    }

    public void setDiceImage(int n, ImageView dice){
        String diceImage = "resources/Dice" + n + ".png";
        dice.setImage(new Image(classLoader.getResourceAsStream(diceImage)));
    }

    public void setPlayerMaterialsImage (String s, ImageView playerMaterials){

        String playerMaterialsImage = "resources/Player" + s + "Materials.png";

        playerMaterials.setImage(new Image(classLoader.getResourceAsStream(playerMaterialsImage)));

    }

    public void tradeWithBankButtonClicked() throws IOException {
        buttonClipName.setFramePosition(0);
        buttonClipName.start();
        FXMLLoader loader = new FXMLLoader(new File("src/gui/TradingWithBankGUI.fxml").toURI().toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root, 1034,776);

        Stage tradingStage = new Stage();
        tradingStage.initModality(Modality.APPLICATION_MODAL);
        tradingStage.setTitle("Trade with Bank");
        tradingStage.setScene(scene);
        tradingStage.initOwner(back.getScene().getWindow());
        tradingStage.showAndWait();
        FXTradingController tradingController = loader.getController();
        inMaterial = tradingController.getTradeInMaterial();
        receiveMaterial = tradingController.getReceiveMaterial();
        System.out.println(inMaterial);
        System.out.println(receiveMaterial);
        if(tradingController.getAccepted()){

            tradeWithBank(inMaterial, receiveMaterial);

        } else {
            message.setImage(new Image("resources/tradingCancelledMessage.png"));
            SequentialTransition transition = new SequentialTransition();

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1));
            fadeIn.setNode(message);

            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.setCycleCount(1);
            message.setVisible(true);
            transition.getChildren().add(fadeIn);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), message);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            transition.getChildren().add(fadeOut);

            transition.play();

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new Controller();
        controller.setPresenter(this);

        message.setVisible(false);

        try{
            diceClipNameAIS = AudioSystem.getAudioInputStream(getClass().getResource("/resources/DiceSound.wav"));
            diceClipName = AudioSystem.getClip();
            diceClipName.open(diceClipNameAIS);

            buttonClipNameAIS = AudioSystem.getAudioInputStream(getClass().getResource("/resources/buttonPressedSound.wav"));
            buttonClipName = AudioSystem.getClip();
            buttonClipName.open(buttonClipNameAIS);
        } catch (Exception e){System.out.println("Failure to load sound");}


        int colorIndex = ThreadLocalRandom.current().nextInt(0, PlayerColor.values().length);
        var colors = PlayerColor.values();
        PlayerColor color = colors[colorIndex];

        /*DO NOT DELETE; BINDING IS BEING IMPLEMENTED HERE BUT IS A PAIN IN THE ASS*/

        setupBindings();
        setupPlayers();

        //tradeWithBankButton.setVisible(false);

        controller.newGame();
        mapNode.setMap(controller.getMap());
        setResources();


        MapFrame mapFrame = new MapFrame(mapNode);

        mapFrame.setScaleX(1.5);
        mapFrame.setScaleY(1.5);
        mapFrame.getMapNode().refreshOutput();
        mapFrame.setLayoutX(300);
        mapFrame.setLayoutY(430);

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
*/
        //Set Fonts
        woodLabel.setFont(Font.font("Bauhaus 93", 27));
        wheatLabel.setFont(Font.font("Bauhaus 93", 27));
        clayLabel.setFont(Font.font("Bauhaus 93", 27));
        oreLabel.setFont(Font.font("Bauhaus 93", 27));
        woolLabel.setFont(Font.font("Bauhaus 93", 27));
        
        woodAmount.setFont(Font.font("Bauhaus 93", 27));
        wheatAmount.setFont(Font.font("Bauhaus 93", 27));
        clayAmount.setFont(Font.font("Bauhaus 93", 27));
        oreAmount.setFont(Font.font("Bauhaus 93", 27));
        woolAmount.setFont(Font.font("Bauhaus 93", 27));
        
        currentPlayer.setFont(Font.font("Bauhaus 93", 27));


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

        setPlayerMaterialsImage(controller.getCurrentPlayerColor().name(), playerMaterials);
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

    public void tradeWithBank(String in, String out){
        MaterialType materialIn;
        MaterialType materialOut;

        switch (in) {
            case "Wood" -> materialIn = MaterialType.WOOD;
            case "Wheat" -> materialIn = MaterialType.WHEAT;
            case "Wool" -> materialIn = MaterialType.WOOL;
            case "Ore" -> materialIn = MaterialType.ORE;
            case "Clay" -> materialIn = MaterialType.CLAY;
            default -> throw new IllegalStateException("Unexpected value: " + in);
        }

        switch (out){
            case "Wood" -> materialOut = MaterialType.WOOD;
            case "Wheat" -> materialOut = MaterialType.WHEAT;
            case "Wool" -> materialOut = MaterialType.WOOL;
            case "Ore" -> materialOut = MaterialType.ORE;
            case "Clay" -> materialOut = MaterialType.CLAY;
            default -> throw new IllegalStateException("Unexpected value: " + out);
        }
        System.out.println(materialIn + "," + materialOut);

        if(controller.hasCurrentPlayerRolled()) {

            boolean trade = controller.bankTrade(materialOut, materialIn);
            if (trade) {
                System.out.println("done");
                refreshOutput();

                message.setImage(new Image("resources/tradingSuccessfulMessage.png"));
                SequentialTransition t = new SequentialTransition();

                FadeTransition yesFadeIn = new FadeTransition(Duration.seconds(1));
                yesFadeIn.setNode(message);

                yesFadeIn.setFromValue(0.0);
                yesFadeIn.setToValue(1.0);
                yesFadeIn.setCycleCount(1);
                if(!message.isVisible()){
                    message.setVisible(true);
                }
                t.getChildren().add(yesFadeIn);

                    FadeTransition yesFadeOut = new FadeTransition(Duration.seconds(3), message);
                    yesFadeOut.setFromValue(1.0);
                    yesFadeOut.setToValue(0.0);
                    t.getChildren().add(yesFadeOut);

                t.play();

            } else if(!trade){
                System.out.println("not possible");
                refreshOutput();

                message.setImage(new Image("resources/tradingFailedMessage.png"));
                SequentialTransition tr = new SequentialTransition();

                FadeTransition noFadeIn = new FadeTransition(Duration.seconds(1));
                noFadeIn.setNode(message);

                noFadeIn.setFromValue(0.0);
                noFadeIn.setToValue(1.0);
                noFadeIn.setCycleCount(1);
                if(!message.isVisible()){
                    message.setVisible(true);
                }
                tr.getChildren().add(noFadeIn);

                FadeTransition noFadeOut = new FadeTransition(Duration.seconds(3), message);
                noFadeOut.setFromValue(1.0);
                noFadeOut.setToValue(0.0);
                tr.getChildren().add(noFadeOut);

                tr.play();
            }

        } else {
            refreshOutput();

            message.setImage(new Image("resources/tradingFailedMessage.png"));
            SequentialTransition tr = new SequentialTransition();

            FadeTransition noFadeIn = new FadeTransition(Duration.seconds(1));
            noFadeIn.setNode(message);

            noFadeIn.setFromValue(0.0);
            noFadeIn.setToValue(1.0);
            noFadeIn.setCycleCount(1);
            if(!message.isVisible()){
                message.setVisible(true);
            }
            tr.getChildren().add(noFadeIn);

            FadeTransition noFadeOut = new FadeTransition(Duration.seconds(3), message);
            noFadeOut.setFromValue(1.0);
            noFadeOut.setToValue(0.0);
            tr.getChildren().add(noFadeOut);

            tr.play();
        }

        refreshOutput();

    }



    public void rollAnimation(){
        clock.start();
    }
}
