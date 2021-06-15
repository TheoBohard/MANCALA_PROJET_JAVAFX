package ensi;

import ensi.model.Communication;
import ensi.utils.idGenerator;
import ensi.utils.playerUtils;
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



    public static void main(String[] zero) throws IOException {
        int index_joueur = -1;
        int ordre = -1;

        int nb_player_connected = 0;
        ArrayList<String> list_ports = new ArrayList<>();
        list_ports.add("2020");
        list_ports.add("2015");
        GameModel model = new GameModel();
        ViewUpdate update = new ViewUpdate(model, list_ports);
        idGenerator utilPass = new idGenerator();
        ArrayList<String> passwords = new ArrayList<>();

        ordre = index_joueur  = playerUtils.chooseRandomNumber(2);

        if(ordre < 0 || ordre > 1) {
            System.out.println("Backup de l'ordre [Pas très accurate]");
            ordre = index_joueur = 0;
        }

        System.out.println("Ordre = " + ordre);

        while (passwords.size() < 2) {
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
                            int position=-1;

                            if(nb_player_connected==0 && ordre==0){
                                position = 1;
                            }else if(nb_player_connected==0 && ordre==1) {
                                position = 2;
                            }else if(nb_player_connected==1 && ordre==0) {
                                position = 2;
                            }else if(nb_player_connected==1 && ordre==1) {
                                position = 1;
                            }

                            String port = list_ports.get(nb_player_connected);
                            nb_player_connected++;
                            String pass = utilPass.generateId();
                            passwords.add(pass);
                            oos.writeObject("Bien reçu : nouvelle partie,"
                                    .concat(port)
                                    .concat(",").concat(pass)
                                    .concat(",")
                                    .concat(String.valueOf(nb_player_connected))
                                    .concat(",")
                                    .concat(Integer.toString(position)));

                            update.initViewAndComm();
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

        String playerTurn = passwords.get(ordre);
        //TODO : Informer le client de si il doit jouer ou non


        //Deroulement de la partie
        while (true) {

            ServerSocket serverSocket;
            Socket inputSocket;

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
                System.out.println("Request : " + request);
                switch (request) {
                    case "give_me_view":
                        update.updateView(oos);
                    case "EXIT":
                        System.out.println("EXIT");
                        socket.close();
                        break;
                    default:
                        System.out.println("Default !!");
                        String[] requestSplitted = request.split(",");
                        // [0] => GridPane [1] => Password
                        if (requestSplitted[1].equals(playerTurn)) {
                            System.out.println("On fait l'action");
                            playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                            model.moveWholes(Integer.parseInt(requestSplitted[0]), "PLAYER_ONE");
                            update.updateView(oos);
                            System.out.println("Index du joueur : " + index_joueur);
                            index_joueur = (index_joueur+1)%2;
                            update.updateViewOtherPlayer(index_joueur);
                        } else {
                            System.out.println("On ne fait pas l'action");
                            oos.writeObject("Ce n'est pas ton tour");
                        }

                        break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            inputSocket.close();
            serverSocket.close();

        }

    }
}
