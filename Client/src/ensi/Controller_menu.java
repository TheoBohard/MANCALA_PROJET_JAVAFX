package ensi;

import ensi.model.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_menu implements Initializable {

    @FXML public Button new_game;
    @FXML public Button load_game;
    @FXML public Button options;
    @FXML public Button leave;
    @FXML public AnchorPane AnchorConnexion;
    @FXML public Text textAnchor;
    public Controller_connexion Cconnexion;
    public String ip;
    public String port;

    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void tryConnection(String ip, String port){
        this.ip = ip;
        this.port = port;
    }

    public void connexion() throws IOException, InterruptedException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("connexion.fxml"));
        AnchorPane anchor = new AnchorPane();
        anchor = loader.load();
        AnchorConnexion.getChildren().add(anchor);
        this.Cconnexion = loader.getController();
        Cconnexion.setCmenu(this);

        this.ip = this.Cconnexion.getIp_final();
        this.port =  this.Cconnexion.getPort_final();
    }

    public void start_new_game(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(this.ip==null){
            this.connexion();
        }else {
            this.communique("New game");
        }
    }

    public void load_a_game(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(this.ip==null){
            this.connexion();
        }
        this.communique("Load game");
    }

    public void start_options(ActionEvent actionEvent) {

    }

    public void leave_program(ActionEvent actionEvent) throws UnknownHostException {
        this.communique("EXIT");

        System.exit(0);
    }

    public void communique(String message) throws UnknownHostException {
        Socket socket;
        Socket socketServeur;

        try {
            socket = createServerSocket(InetAddress.getByName(this.ip), Integer.parseInt(this.port));

            ServerSocket socketenvoieclient = new ServerSocket(2010);
            socketServeur = socketenvoieclient.accept();

            sendMessageSocket(socketServeur, message);

            socketServeur.close();
            socketenvoieclient.close();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            if(!message.equals("EXIT")){
                String response = (String) ois.readObject();// envoie de l'objet
                System.out.println(response);
            }


            socket.close();

        }catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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

