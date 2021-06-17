package ensi.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
     * @throws IOException
     */
    public void onePlayer() throws IOException {
        controllerMenu.setMode("Oneplayer");
        setDifficultyMode();
    }

    /**
     * This function permit to choose Two Players mode
     * @throws IOException
     */
    public void twoPlayers() throws IOException {
        controllerMenu.setMode("Twoplayers");
        setDifficultyMode();
    }

    /**
     * This function permit to set the difficulty mode
     * @throws IOException
     */
    private void setDifficultyMode() throws IOException {
        controllerMenu.chooseDifficultyMode();
   }

    /**
     * This function permit to choose easy difficulty mode
     * @throws IOException
     */
    public void easyMode() throws IOException {
        controllerMenu.setDifficulty("easyMode");
        controllerMenu.launchgame();
    }

    /**
     * This function permit to choose middle difficulty mode
     * @throws IOException
     */
    public void middleMode() throws IOException {
        controllerMenu.setDifficulty("middleMode");
        controllerMenu.launchgame();
    }
}
