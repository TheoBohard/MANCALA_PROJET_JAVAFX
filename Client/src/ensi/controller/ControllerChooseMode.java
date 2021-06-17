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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ipFinal = "";
        this.portFinal = "";
    }

    public void one_player() throws IOException {
        controllerMenu.setMode("Oneplayer");
        setDifficultyMode();
    }

    public void two_players() throws IOException {
        controllerMenu.setMode("Twoplayers");
        setDifficultyMode();
    }

    private void setDifficultyMode() throws IOException {
        controllerMenu.chooseDifficultyMode();
   }

    public void easyMode() throws IOException {
        controllerMenu.setDifficulty("easyMode");
        controllerMenu.launchgame();
    }

    public void middleMode() throws IOException {
        controllerMenu.setDifficulty("middleMode");
        controllerMenu.launchgame();
    }
}
