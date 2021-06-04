package ensi.controller;

import ensi.Main;
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
    public String mode;
    public ControllerConnexion controllerConnexion;
    public String ip;
    public String port;
    private ControllerChooseMode controllerModeChoose;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void tryConnection(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void connexion() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/connexion.fxml"));

        anchorConnexion.getChildren().add(loader.load());
        this.controllerConnexion = loader.getController();
        this.controllerConnexion.setCmenu(this);

        this.ip = this.controllerConnexion.getIpFinal();
        this.port =  this.controllerConnexion.getPortFinal();
    }

    public void startNewGame() throws IOException {
        if(this.ip==null){
            this.connexion();
        } else {
            String response = this.sendMessage("New game");
            if(response.equals("Bien reçu : nouvelle partie")) {

                anchorConnexion.getChildren().clear();
                //Selection of game_mode
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/mode_choose.fxml"));
                anchorConnexion.getChildren().add(loader.load());
                this.controllerModeChoose = loader.getController();
                this.controllerModeChoose.setCmenu(this);


            }
        }
    }

    public void launchgame() throws IOException {
        Stage gameStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/game.fxml"));
        gameStage.setTitle("Jeu MANCALA");
        gameStage.setScene(new Scene(root, 1100, 800));
        gameStage.show();
        Main.primaryStage.close();
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

    public String sendMessage(String message) throws UnknownHostException {
        Socket socket;
        Socket serverSocket;
        String response = null;

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
                response = (String) ois.readObject();
                System.out.println(response);
            }
            socket.close();

        } catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }

        if(response!=null){
            return response;
        }else{
            return null;
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

