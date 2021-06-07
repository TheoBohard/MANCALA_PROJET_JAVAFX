package ensi;

import ensi.model.Game_model;
import ensi.model.Personne;
import ensi.model.View_update;

import javax.swing.text.View;
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
        Boolean verif =true;
        while(verif==true) {
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
                System.out.println(request);
                if(request.equals("New game")){
                    verif=false;
                    oos.writeObject("Bien reçu : nouvelle partie");// envoie de l'objet
                    Game_model model = new Game_model();
                    View_update update = new View_update(model);
                    update.update_view();
                    System.out.println("JE SUIS LA");
                }
                else if(request.equals("Load game")){
                    oos.writeObject("Bien reçu : chargement de partie");// envoie de l'objet
                }
                else if(request.equals("EXIT")){
                    System.out.println("EXIT");
                    socket.close();
                }else if(request.equals("init")){

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
