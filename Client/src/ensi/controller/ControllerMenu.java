/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file ControllerMenu.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to manage the menu interface
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

package ensi.controller;

import ensi.Main;
import ensi.communication.Communication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControllerMenu implements Initializable {

    public static String ip;
    public static String port;
    @FXML
    public Button newGame;
    @FXML
    public Button loadGame;
    @FXML
    public Button options;
    @FXML
    public Button leave;
    public String mode;
    public String difficulty;
    public ControllerConnexion controllerConnexion;
    @FXML
    public AnchorPane anchorConnexion;
    @FXML
    public Text textAnchor;
    public String comPort;
    private ControllerChooseMode controllerModeChoose;
    private ArrayList<Integer> tab_seed = new ArrayList<>();
    private Communication com;
    private String passWord;
    private int numberPlayer = 0;
    private String position;

    /**
     * This function is what we do when a instance of ControllerMenu is implemented
     * @param url The URL
     * @param resourceBundle The RessourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        com = new Communication();
    }

    /**
     * This function permit to try the connexion on the server
     *
     * @param ip   The IP of the server
     * @param port The port of the server
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public void tryConnection(String ip, String port) throws IOException, ClassNotFoundException {
        ControllerMenu.ip = ip;
        ControllerMenu.port = port;
        this.startNewGame();
    }

    /**
     * Setter for mode
     * @param mode The mode used
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * This function permit to set the difficulty of the game
     * @param difficulty The difficulty
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * This function permit to do load connexion view and setup the game
     *
     * @throws IOException IOException
     */
    public void connexion() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/connexion.fxml"));

        anchorConnexion.getChildren().add(loader.load());
        this.controllerConnexion = loader.getController();
        this.controllerConnexion.setControllerMenu(this);

        ip = this.controllerConnexion.getIpFinal();
        port = this.controllerConnexion.getPortFinal();
    }

    /**
     * This function permit to choose the difficulty mode
     *
     * @throws IOException IOException
     */
    public void chooseDifficultyMode() throws IOException {
        anchorConnexion.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/difficultyChoose.fxml"));
        anchorConnexion.getChildren().add(loader.load());
        this.controllerModeChoose = loader.getController();
        this.controllerModeChoose.setCmenu(this);
    }

    /**
     * This function permit to start a new game
     *
     * @throws IOException            IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */
    public void startNewGame() throws IOException, ClassNotFoundException {
        if (ip == null) {
            this.connexion();
        } else {
            String response = (String) com.sendMessage("New game");
            String[] tab = response.split(",");
            this.comPort = tab[1];
            this.passWord = tab[2];
            this.numberPlayer = Integer.parseInt(tab[3]);
            this.position = tab[4];

            if (tab[0].equals("Bien reçu : nouvelle partie")) {

                anchorConnexion.getChildren().clear();
                //Selection of game_mode
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/modeChoose.fxml"));
                anchorConnexion.getChildren().add(loader.load());
                this.controllerModeChoose = loader.getController();
                this.controllerModeChoose.setCmenu(this);

                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(this.comPort));
                System.out.println("Le client est à l'écoute du port " + serverSocket.getLocalPort());
                Socket inputSocket = serverSocket.accept();

                Socket socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(this.comPort) - 1);

                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);

                tab_seed = (ArrayList<Integer>) ois.readObject();

                OutputStream os = inputSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);
            }
        }
    }

    /**
     * This function permit to launch the game
     *
     * @throws IOException IOException
     */
    public void launchgame() throws IOException {
        Stage gameStage = new Stage();
        //TODO : Server will say what view we want to load
        String view = "../view/gamePlayer".concat(String.valueOf(numberPlayer)).concat(".fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        gameStage.setTitle("Jeu MANCALA");
        gameStage.setScene(scene);

        ControllerJeu gameController = loader.getController();
        gameController.setFxmlFileLoaded(view);
        gameController.setPassWord(this.passWord);
        gameController.setPort(this.comPort);

        gameController.init(tab_seed);
        Main.primaryStage.close();

        String sendGameInfo = ((String) com.sendMessage("GAME_INFO," + difficulty + "," + mode));


        gameStage.show();

        if (this.position.equals("2")){
            ExecutorService threadpool;
            threadpool = Executors.newCachedThreadPool();
            threadpool.submit(() -> {
                ArrayList<?> holesList = null;
                try {
                    holesList = (ArrayList<?>) gameController.listenToServer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    gameController.updateView(holesList);
                }
            });
            gameController.setTurnInfo("Ce n'est pas votre tour !");
        }
        else {
            gameController.setTurnInfo("C'est votre tour !");
        }
    }

    /**
     * This function permit to load a game
     * @throws IOException IOException
     */
    public void loadAGame() throws IOException {
        if (ip == null) {
            this.connexion();
        }
        com.sendMessage("Load game");
    }

    /**
     * This function permit to launch options
     */
    public void startOptions() {
    }

    /**
     * This function permit to exit the game
     */
    public void leaveProgram() {
        com.sendMessage("EXIT");
        System.exit(0);
    }

}

