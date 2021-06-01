package ensi;

import ensi.model.Personne;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_menu implements Initializable {

    @FXML public Button new_game;
    @FXML public Button load_game;
    @FXML public Button options;
    @FXML public Button leave;
    public Controller_connexion Cconnexion;
    public String ip;
    public String port;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void connexion() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tab.fxml"));
        this.Cconnexion = loader.getController();
        while(this.Cconnexion.getIp_final()==null){
            Thread.sleep(1000);
        }
        this.ip = this.Cconnexion.getIp_final();
        this.port =  this.Cconnexion.getPort_final();
    }

    public void start_new_game(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(this.ip==null){
            this.connexion();
        }
        this.communique("New game");
    }

    public void load_a_game(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(this.ip==null){
            this.connexion();
        }
        this.communique("Load game");
    }

    public void start_options(ActionEvent actionEvent) {

    }

    public void leave_program(ActionEvent actionEvent) {
        this.communique("EXIT");

        System.exit(0);
    }

    public void communique(String message) {
        Socket socket;
        Socket socketServeur;

        try {
            socket = createServerSocket(InetAddress.getLocalHost(), 2009);

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

