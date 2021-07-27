package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.ResourceBundle;

public class FXTradingController implements Initializable {

    private String tradeInMaterial;
    private String receiveMaterial;
    private boolean cancel = false;
    private boolean accept = false;

    private AudioInputStream tradingCancelClipNameAIS;
    private Clip tradingCancelClipName;

    private AudioInputStream tradingAcceptClipNameAIS;
    private Clip tradingAcceptClipName;


    @FXML
    private AnchorPane background;
    @FXML
    private ImageView tradeCancel;
    @FXML
    private ToggleGroup tradeIn;
    @FXML
    private ToggleGroup receive;
    @FXML
    private RadioButton tradeInWood;
    @FXML
    private RadioButton tradeInWheat;
    @FXML
    private RadioButton tradeInOre;
    @FXML
    private RadioButton tradeInClay;
    @FXML
    private RadioButton receiveWood;
    @FXML
    private RadioButton receiveWheat;
    @FXML
    private RadioButton receiveWool;
    @FXML
    private RadioButton receiveOre;
    @FXML
    private RadioButton receiveClay;


    public void acceptTradeClicked(MouseEvent mouseEvent){
        tradingAcceptClipName.setFramePosition(0);
        tradingAcceptClipName.start();
        accept = true;
        RadioButton selectedTradeInRadioButton = (RadioButton) tradeIn.getSelectedToggle();
        tradeInMaterial = selectedTradeInRadioButton.getText();
        RadioButton selectedReceiveRadioButton = (RadioButton) receive.getSelectedToggle();
        receiveMaterial = selectedReceiveRadioButton.getText();
        Stage stage = (Stage) background.getScene().getWindow();
        stage.close();
        //FXController.tradeWithBank(tradeInMaterial, receiveMaterial);

    }

    public void cancelTradeClicked(MouseEvent mouseEvent){
        tradingCancelClipName.setFramePosition(0);
        tradingCancelClipName.start();
        cancel = true;
        Stage stage = (Stage) background.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            tradingCancelClipNameAIS = AudioSystem.getAudioInputStream(getClass().getResource("/resources/tradingCancelSound.wav"));
            tradingCancelClipName = AudioSystem.getClip();
            tradingCancelClipName.open(tradingCancelClipNameAIS);

            tradingAcceptClipNameAIS = AudioSystem.getAudioInputStream(getClass().getResource("/resources/tradingAcceptSound.wav"));
            tradingAcceptClipName = AudioSystem.getClip();
            tradingAcceptClipName.open(tradingAcceptClipNameAIS);

        } catch (Exception e){System.out.println("Failure to load sound");}
    }

    public String getTradeInMaterial(){
        return this.tradeInMaterial;
    }

    public String getReceiveMaterial(){
        return this.receiveMaterial;
    }

    public boolean getAccepted(){return this.accept;}

    public boolean getCancelled(){return this.cancel;}
}
