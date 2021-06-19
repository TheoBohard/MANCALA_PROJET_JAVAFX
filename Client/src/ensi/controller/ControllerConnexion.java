/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file ControllerConnexion.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to do manage the connection
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

public class ControllerConnexion implements Initializable {

    @FXML public TextArea ip;
    @FXML public TextArea port;
    public String ipFinal;
    public String portFinal;
    public ControllerMenu controllerMenu;

    /**
     * This function permit to set the controllerMenu on the controllerConnexion
     * @param controllerMenu The controllerMenu
     */
    public void setControllerMenu(ControllerMenu controllerMenu) {
        this.controllerMenu = controllerMenu;
    }

    /**
     * Initializaztion function of ControllerConnexion
     * @param url The URL
     * @param resourceBundle The RessourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ipFinal = "";
        this.portFinal = "";
    }

    /**
     * This function permit to connect client to the server
     *
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public void connexion() throws IOException, ClassNotFoundException {
        this.ipFinal =ip.getText();
        this.portFinal =port.getText();
        this.controllerMenu.tryConnection(this.ipFinal,this.portFinal);
    }

    /**
     * This function permit to get the ip
     *
     * @return The ip
     */
    public String getIpFinal(){
        return this.ipFinal;
    }

    /**
     * This function permit to get the port
     * @return The port
     */
    public String getPortFinal() {
        return portFinal;
    }
}
