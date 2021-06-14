package ensi.controller;

import javafx.event.ActionEvent;
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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ipFinal = "";
        this.portFinal = "";
    }

    public void one_player(ActionEvent actionEvent) throws IOException, InterruptedException {
        controllerMenu.setMode("Oneplayer");
        controllerMenu.launchgame();
    }

    public void two_players(ActionEvent actionEvent) throws IOException, InterruptedException {
        controllerMenu.setMode("Twoplayers");
        controllerMenu.launchgame();
    }
}
