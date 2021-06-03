package ensi.controller;

import ensi.Main;
import ensi.controller.ControllerConnexion;
import javafx.event.ActionEvent;
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
import java.net.*;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {

    @FXML public Button newGame;
    @FXML public Button loadGame;
    @FXML public Button options;
    @FXML public Button leave;
    @FXML public AnchorPane anchorConnexion;
    @FXML public Text textAnchor;
    public ControllerConnexion controllerConnexion;
    public String ip;
    public String port;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void tryConnection(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public void connexion() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/connexion.fxml"));

        anchorConnexion.getChildren().add(loader.load());
        this.controllerConnexion = loader.getController();
        controllerConnexion.setCmenu(this);

        this.ip = this.controllerConnexion.getIpFinal();
        this.port =  this.controllerConnexion.getPortFinal();
    }

    public void startNewGame() throws IOException {
        if(this.ip==null){
            this.connexion();
        } else {
            this.sendMessage("New game");

            Stage gameStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../view/game.fxml"));

            gameStage.setTitle("Jeu MANCALA");
            gameStage.setScene(new Scene(root, 1100, 700));
            gameStage.show();
            Main.primaryStage.close();
        }
    }

    public void loadAGame() throws IOException {
        if(this.ip==null){
            this.connexion();
        }
        this.sendMessage("Load game");
    }

    public void start_options(ActionEvent actionEvent) {

    }

    public void leaveProgram() throws UnknownHostException {
        this.sendMessage("EXIT");

        System.exit(0);
    }

    public void sendMessage(String message) throws UnknownHostException {
        Socket socket;
        Socket serverSocket;

        try {
            socket = createServerSocket(InetAddress.getByName(this.ip), Integer.parseInt(this.port));

            ServerSocket clientSocket = new ServerSocket(2010);
            serverSocket = clientSocket.accept();

            sendMessageSocket(serverSocket, message);

            serverSocket.close();
            clientSocket.close();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            if(!message.equals("EXIT")){
                String response = (String) ois.readObject();
                System.out.println(response);
            }


            socket.close();

        } catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
    }

    private Socket createServerSocket(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        System.out.println("Connexion au serveur, IP = " + address.toString() + " / Port = " + port);
        return socket;
    }

    private void sendMessageSocket(Socket socket, String message) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(message);
    }

}

