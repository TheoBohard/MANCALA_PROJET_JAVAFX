package ensi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOptions {

    @FXML
    private Slider sliderNumberSeed;

    @FXML
    private Slider sliderBoardState;

    @FXML
    private Slider sliderSoundEffect;

    @FXML
    private Slider sliderMusic;

    @FXML
    public void initialize() {
        sliderNumberSeed.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderNumberSeedChanged());
        sliderBoardState.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderBoardStateChanged());
        sliderSoundEffect.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderSoundEffectChanged());
        sliderMusic.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderMusicChanged());
    }

    private void sliderNumberSeedChanged() {
        System.out.println("Yoooooooo slider changed");
    }

    private void sliderBoardStateChanged() {
    }

    private void sliderSoundEffectChanged() {
    }

    private void sliderMusicChanged() {
    }
}
