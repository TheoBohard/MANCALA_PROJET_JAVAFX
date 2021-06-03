package ensi;
import ensi.model.Personne;
import jdk.net.Sockets;

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
        while(true) {
            ServerSocket serverSocket;
            Socket inputSocket;
            Personne pers = new Personne();

            try {
                serverSocket = new ServerSocket(2009);
                System.out.println("Le ensi est à l'écoute du port " + serverSocket.getLocalPort());
                inputSocket = serverSocket.accept();

                Socket socket = new Socket(InetAddress.getLocalHost(), 2010);

                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);

                String request= (String) ois.readObject();
                //System.exit(0);

                OutputStream os = inputSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);

                System.out.println("Resquest received");
                if(request.equals("New game")){
                    oos.writeObject("Bien reçu : nouvelle partie");// envoie de l'objet
                }
                if(request.equals("Load game")){
                    oos.writeObject("Bien reçu : chargement de partie");// envoie de l'objet
                }
                if(request.equals("EXIT")){
                    System.out.println("EXIT");
                    socket.close();
                }
                else{
                    oos.writeObject("Je ne connais pas cette instruction");// envoie de l'objet
                }

                inputSocket.close();
                serverSocket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
