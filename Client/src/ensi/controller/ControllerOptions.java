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

        sliderNumberSeed.setValue(ControllerJeu.isCLIENT_NUMBER_SEED() ? 1.0 : 0.0);
        sliderBoardState.setValue(ControllerJeu.isCLIENT_BOARD_STATE() ? 1.0 : 0.0);
        sliderSoundEffect.setValue(ControllerJeu.isCLIENT_SOUND_EFFECT() ? 1.0 : 0.0);
        sliderMusic.setValue(ControllerJeu.isCLIENT_MUSIC() ? 1.0 : 0.0);


        sliderNumberSeed.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderNumberSeedChanged());
        sliderBoardState.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderBoardStateChanged());
        sliderSoundEffect.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderSoundEffectChanged());
        sliderMusic.valueChangingProperty().addListener((source, oldValue, newValue) -> sliderMusicChanged());
    }

    private void sliderNumberSeedChanged() {
        ControllerJeu.setCLIENT_NUMBER_SEED(sliderNumberSeed.getValue() > 0);
    }

    private void sliderBoardStateChanged() {
        ControllerJeu.setCLIENT_BOARD_STATE(sliderBoardState.getValue() > 0);
    }

    private void sliderSoundEffectChanged() {
        ControllerJeu.setCLIENT_SOUND_EFFECT(sliderSoundEffect.getValue() > 0);
    }

    private void sliderMusicChanged() {
        ControllerJeu.setCLIENT_MUSIC(sliderMusic.getValue() > 0);
    }
}
