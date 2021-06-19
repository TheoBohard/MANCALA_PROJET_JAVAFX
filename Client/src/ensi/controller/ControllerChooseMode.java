/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file ControllerChooseMode.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to choose play mode
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
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChooseMode implements Initializable {

    @FXML
    public TextArea ip;
    @FXML
    public TextArea port;
    public String ipFinal;
    public String portFinal;
    public ControllerMenu controllerMenu;

    public ControllerMenu getControllerMenu() {
        return controllerMenu;
    }

    public void setCmenu(ControllerMenu cmenu) {
        controllerMenu = cmenu;
    }

    /**
     * This function permit to initialize the mode choose fxml
     * @param url The URL
     * @param resourceBundle The RessourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ipFinal = "";
        this.portFinal = "";
    }

    /**
     * This function permit to choose One Player mode
     *
     * @throws IOException IOException
     */
    public void onePlayer() throws IOException {
        controllerMenu.setMode("Oneplayer");
        setDifficultyMode();
    }

    /**
     * This function permit to choose Two Players mode
     *
     * @throws IOException IOException
     */
    public void twoPlayers() throws IOException {
        controllerMenu.setMode("Twoplayers");
        setDifficultyMode();
    }

    /**
     * This function permit to set the difficulty mode
     *
     * @throws IOException IOException
     */
    private void setDifficultyMode() throws IOException {
        controllerMenu.chooseDifficultyMode();
    }

    /**
     * This function permit to choose easy difficulty mode
     * @throws IOException IOException
     */
    public void easyMode() throws IOException {
        controllerMenu.setDifficulty("easyMode");
        controllerMenu.launchgame();
    }

    /**
     * This function permit to choose middle difficulty mode
     * @throws IOException IOException
     */
    public void middleMode() throws IOException {
        controllerMenu.setDifficulty("middleMode");
        controllerMenu.launchgame();
    }
}
