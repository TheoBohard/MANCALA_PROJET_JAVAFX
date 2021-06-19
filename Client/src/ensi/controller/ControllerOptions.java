/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file ControllerOptions.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to manage options
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

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

    /**
     * This function permit to initialize a ControllerOptions
     */
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

    /**
     * Handle when the option CLIENT_NUMBER_SEED is changed
     */
    private void sliderNumberSeedChanged() {
        ControllerJeu.setCLIENT_NUMBER_SEED(sliderNumberSeed.getValue() > 0);
    }

    /**
     * Handle when the option CLIENT_BOARD_STATE is changed
     */
    private void sliderBoardStateChanged() {
        ControllerJeu.setCLIENT_BOARD_STATE(sliderBoardState.getValue() > 0);
    }

    /**
     * Handle when the option CLIENT_SOUND_EFFECT is changed
     */
    private void sliderSoundEffectChanged() {
        ControllerJeu.setCLIENT_SOUND_EFFECT(sliderSoundEffect.getValue() > 0);
    }

    /**
     * Handle when the option CLIENT_MUSIC is changed
     */
    private void sliderMusicChanged() {
        ControllerJeu.setCLIENT_MUSIC(sliderMusic.getValue() > 0);
    }
}
