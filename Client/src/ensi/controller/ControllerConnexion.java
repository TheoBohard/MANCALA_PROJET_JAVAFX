package ensi.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerConnexion implements Initializable {

    @FXML public TextArea ip;
    @FXML public TextArea port;
    public String ipFinal;
    public String portFinal;
    public ControllerMenu controllerMenu;

    public ControllerMenu getCmenu() {
        return controllerMenu;
    }

    public void setCmenu(ControllerMenu cmenu) {
        controllerMenu = cmenu;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ipFinal = "";
        this.portFinal = "";
    }

    public void connexion() throws IOException, ClassNotFoundException {
        this.ipFinal =ip.getText();
        this.portFinal =port.getText();
        this.controllerMenu.tryConnection(this.ipFinal,this.portFinal);
    }

    public String getIpFinal(){
        return this.ipFinal;
    }

    public String getPortFinal() {
        return portFinal;
    }
}
