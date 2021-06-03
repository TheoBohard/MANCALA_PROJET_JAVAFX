package ensi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller_connexion implements Initializable {

    @FXML public TextArea ip;
    @FXML public TextArea port;
    public String ip_final;
    public String port_final;
    public Controller_menu Cmenu;

    public Controller_menu getCmenu() {
        return Cmenu;
    }

    public void setCmenu(Controller_menu cmenu) {
        Cmenu = cmenu;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ip_final = "";
        this.port_final = "";
    }

    public void connexion(ActionEvent actionEvent) {
        this.ip_final=ip.getText();
        this.port_final=port.getText();
        this.Cmenu.tryConnection(this.ip_final,this.port_final);
    }

    public String getIp_final(){
        return this.ip_final;
    }

    public String getPort_final() {
        return port_final;
    }
}
