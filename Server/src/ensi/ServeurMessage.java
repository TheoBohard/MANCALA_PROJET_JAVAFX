package ensi;

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
        int indexJoueur = -1;
        int ordre = -1;

        int nbPlayerConnected = 0;
        ArrayList<String> listPorts = new ArrayList<>();
        listPorts.add("2020");
        listPorts.add("2015");
        GameModel model = new GameModel();
        ViewUpdate update = new ViewUpdate(model, listPorts);
        idGenerator utilPass = new idGenerator();
        ArrayList<String> passwords = new ArrayList<>();

        String modeChoosed = "";
        String difficultyChosen = "";

        ordre = indexJoueur  = playerUtils.chooseRandomNumber(2);

        if(ordre < 0 || ordre > 1) {
            System.out.println("Backup de l'ordre [Pas très accurate]");
            ordre = indexJoueur = 0;
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

                    switch (request.split(",")[0]) {

                        case "New game":
                            int position=-1;

                            if(nbPlayerConnected==0 && ordre==0){
                                position = 1;
                            }else if(nbPlayerConnected==0 && ordre==1) {
                                position = 2;
                            }else if(nbPlayerConnected==1 && ordre==0) {
                                position = 2;
                            }else if(nbPlayerConnected==1 && ordre==1) {
                                position = 1;
                            }

                            String port = listPorts.get(nbPlayerConnected);
                            nbPlayerConnected++;
                            String pass = utilPass.generateId();
                            passwords.add(pass);
                            oos.writeObject("Bien reçu : nouvelle partie,"
                                    .concat(port)
                                    .concat(",").concat(pass)
                                    .concat(",")
                                    .concat(String.valueOf(nbPlayerConnected))
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
                        case "GAME_INFO":
                            if(nbPlayerConnected == 1) {
                                String[] requestSplitted = request.split(",");
                                oos.writeObject("OK");
                                modeChoosed = requestSplitted[2];
                                difficultyChosen = requestSplitted[1];
                                System.out.println("Mode : " + modeChoosed + " | Difficulty : " + difficultyChosen);
                                model.setDifficulty(difficultyChosen);
                                break;
                            }
                        default:
                            oos.writeObject("Je ne connais pas cette instruction");
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
                    case "EXIT":
                        System.out.println("EXIT");
                        socket.close();
                        break;
                    default:
                        String[] requestSplitted = request.split(",");
                        System.out.println(requestSplitted);
                        if(requestSplitted[1].equals("CANCEL_MOVE") && requestSplitted[2].equals(playerTurn)){

                            boolean change_turn = model.cancel_move();

                            if(change_turn) {
                                playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                                update.updateView(oos, indexJoueur);
                                indexJoueur = (indexJoueur + 1) % 2;
                                update.updateViewOtherPlayer(indexJoueur);
                            }else{
                                oos.writeObject("Deplacement impossible");
                            }
                        }
                        else if(requestSplitted[1].equals("SURRENDER_GAME") && requestSplitted[2].equals(playerTurn)){

                            System.out.println("--------------------------------------------------------------------");

                            if(model.abandonPossible()) {
                                playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                                update.updateView(oos, indexJoueur);
                                indexJoueur = (indexJoueur + 1) % 2;
                                update.askOpponent("ABANDON?");
                            }else{
                                oos.writeObject("Deplacement impossible");
                            }
                        }
                        else if(requestSplitted[1].equals("ABANDONYES") && requestSplitted[2].equals(playerTurn)){
                            model.distrib_seeds();
                            model.endGame();

                            playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                            update.updateView(oos, indexJoueur);
                            indexJoueur = (indexJoueur + 1) % 2;
                            update.updateViewOtherPlayer(indexJoueur);

                            model.newRound =false;
                        }
                        else if (requestSplitted[1].equals(playerTurn)) {
                            model.isPartyFinish(indexJoueur);
                            boolean moveIsPlayable = model.isMovePlayable(Integer.parseInt(requestSplitted[0]), indexJoueur);

                            if(!model.isPartyOn){
                                oos.writeObject("PARTIE TERMINE");
                            }
                            else if(moveIsPlayable && !model.newRound) {
                                playerTurn = playerUtils.changePlayer(playerTurn, passwords);

                                model.moveWholes(Integer.parseInt(requestSplitted[0]), indexJoueur);
                                update.updateView(oos, indexJoueur);
                                System.out.println("Index du joueur : " + indexJoueur);
                                indexJoueur = (indexJoueur + 1) % 2;
                                update.updateViewOtherPlayer(indexJoueur);
                            } else if(model.newRound){
                                playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                                update.updateView(oos, indexJoueur);
                                indexJoueur = (indexJoueur + 1) % 2;
                                update.updateViewOtherPlayer(indexJoueur);

                                model.newRound =false;
                            } else{
                                oos.writeObject("Deplacement impossible");
                            }
                        } else {
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
