package ensi;

import ensi.model.gameModel;
import ensi.model.viewUpdate;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by faye on 01/06/2017.
 */

public class ServeurMessage {

    public static void main(String[] zero)
    {
        boolean verif = true;

        while(verif) {
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
                    //System.exit(0);

                    OutputStream os = inputSocket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);

                    System.out.println("Resquest received");
                    System.out.println(request);
                    switch (request) {
                        case "New game":
                            verif = false;
                            oos.writeObject("Bien reçu : nouvelle partie");// envoie de l'objet

                            gameModel model = new gameModel();
                            viewUpdate update = new viewUpdate(model);
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

}
