package ensi;
import ensi.utils.Password;
import ensi.model.GameModel;
import ensi.model.ViewUpdate;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by faye on 01/06/2017.
 */

public class ServeurMessage {

    public static void main(String[] zero)
    {
        int nb_player_connected=0;
        ArrayList<String> list_ports = new ArrayList<>();
        list_ports.add("2020");
        list_ports.add("2015");
        GameModel model = new GameModel();
        ViewUpdate update = new ViewUpdate(model,list_ports);
        Password utilPass = new Password();
        ArrayList<String> passwords = new ArrayList<String>();

        while(passwords.size()>2) {
            ServerSocket serverSocket;
            Socket inputSocket;

            try {
                serverSocket = new ServerSocket(2009);
                System.out.println("Le ensi est à l'écoute du port " + serverSocket.getLocalPort());
                inputSocket = serverSocket.accept();

                try (Socket socket = new Socket(InetAddress.getLocalHost(), 2010)) {

                    InputStream is = socket.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(is);

                    String request = (String) ois.readObject();

                    OutputStream os = inputSocket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);

                    System.out.println("Resquest received");
                    System.out.println(request);
                    switch (request) {

                        case "New game":

                            String port = list_ports.get(nb_player_connected);
                            nb_player_connected++;
                            String pass = utilPass.generatePassword();
                            passwords.add(pass);
                            oos.writeObject("Bien reçu : nouvelle partie,".concat(port).concat(",").concat(pass));// envoie de l'objet

                            update.update_view();
                            System.out.println("JE SUIS LA");

                            break;
                        case "Load game":
                            oos.writeObject("Bien reçu : chargement de partie");// envoie de l'objet

                            break;
                        case "EXIT":
                            System.out.println("EXIT");
                            socket.close();
                            break;
                        case "init":

                            break;
                        default:
                            oos.writeObject("Je ne connais pas cette instruction");// envoie de l'objet

                            break;
                    }
                }

                inputSocket.close();
                serverSocket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        while (true){

            serverSocket = new ServerSocket(2009);
            System.out.println("Le ensi est à l'écoute du port " + serverSocket.getLocalPort());
            inputSocket = serverSocket.accept();

            try (Socket socket = new Socket(InetAddress.getLocalHost(), 2010)) {

                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);

                String request = (String) ois.readObject();

                OutputStream os = inputSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);

                System.out.println("Resquest received");
                System.out.println(request);
                switch (request) {

                    case "New game":

                        String port = list_ports.get(nb_player_connected);
                        nb_player_connected++;
                        String pass = utilPass.generatePassword();
                        passwords.add(pass);
                        oos.writeObject("Bien reçu : nouvelle partie,".concat(port).concat(",").concat(pass));// envoie de l'objet

                        update.update_view();
                        System.out.println("JE SUIS LA");

                        break;
                    case "Load game":
                        oos.writeObject("Bien reçu : chargement de partie");// envoie de l'objet

                        break;
                    case "EXIT":
                        System.out.println("EXIT");
                        socket.close();
                        break;
                    case "init":

                        break;
                    default:
                        oos.writeObject("Je ne connais pas cette instruction");// envoie de l'objet

                        break;
                }
            }

            inputSocket.close();
            serverSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
