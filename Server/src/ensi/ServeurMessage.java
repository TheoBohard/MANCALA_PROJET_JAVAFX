/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file ServeurMessage.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to use the others class to manage a game
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

package ensi;

import ensi.model.GameModel;
import ensi.model.ViewUpdate;
import ensi.utils.idGenerator;
import ensi.utils.playerUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;


public class ServeurMessage {


    /**
     * This function permit to execute all class and function to play games...
     *
     * @param zero Program arguments
     * @throws IOException IOException
     */
    public static void main(String[] zero) throws IOException {
        int indexJoueur;
        int ordre;

        int nbPlayerConnected = 0;
        ArrayList<String> listPorts = new ArrayList<>();
        listPorts.add("2020");
        listPorts.add("2015");
        GameModel model = new GameModel();
        ViewUpdate update = new ViewUpdate(model, listPorts);
        idGenerator utilPass = new idGenerator();
        ArrayList<String> passwords = new ArrayList<>();

        String modeChoosed;
        String difficultyChosed;

        ordre = indexJoueur = playerUtils.chooseRandomNumber(2);

        if (ordre < 0 || ordre > 1) {
            ordre = indexJoueur = 0;
        }


        while (passwords.size() < 2) {
            ServerSocket serverSocket;
            Socket inputSocket;

            try {
                serverSocket = new ServerSocket(2009);
                System.out.println("Le ensi est à l'écoute du port " + serverSocket.getLocalPort());
                inputSocket = serverSocket.accept();

                try (Socket socket = new Socket(inputSocket.getInetAddress(), 2010)) {

                    InputStream is = socket.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(is);

                    String request = (String) ois.readObject();

                    OutputStream os = inputSocket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);



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

                            update.initViewAndComm(inputSocket.getInetAddress());

                            break;
                        case "Load game":
                            oos.writeObject("Bien reçu : chargement de partie");// envoie de l'objet
                            break;
                        case "EXIT":

                            socket.close();
                            break;
                        case "GAME_INFO":
                            if(nbPlayerConnected == 1) {
                                String[] requestSplitted = request.split(",");
                                oos.writeObject("OK");
                                modeChoosed = requestSplitted[2];
                                difficultyChosed = requestSplitted[1];
                                model.setDifficulty(difficultyChosed);
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


            try (Socket socket = new Socket(inputSocket.getInetAddress(), 2010)) {

                InputStream is = socket.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);

                String request = (String) ois.readObject();

                OutputStream os = inputSocket.getOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(os);

                if ("EXIT".equals(request)) {

                    socket.close();
                } else {
                    String[] requestSplitted = request.split(",");
                    if (requestSplitted[1].equals("CANCEL_MOVE") && requestSplitted[2].equals(playerTurn)) {

                        boolean change_turn = model.cancelMove();

                        if (change_turn) {
                            playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                            update.updateView(oos);
                            indexJoueur = (indexJoueur + 1) % 2;
                            update.updateViewOtherPlayer(indexJoueur, inputSocket.getInetAddress());
                        } else {
                            oos.writeObject("Deplacement impossible");
                        }
                    } else if (requestSplitted[1].equals("SURRENDER_GAME") && requestSplitted[2].equals(playerTurn)) {

                        if (model.surrenderPossible()) {
                            playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                            update.updateView(oos);
                            indexJoueur = (indexJoueur + 1) % 2;
                            update.askOpponent("ABANDON?", inputSocket.getInetAddress());
                        } else {
                            oos.writeObject("Deplacement impossible");
                        }
                    } else if (requestSplitted[1].equals("ABANDONYES") && requestSplitted[2].equals(playerTurn)) {
                        model.distribSeeds();
                        model.endGame();

                        playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                        update.updateView(oos);
                        indexJoueur = (indexJoueur + 1) % 2;
                        update.updateViewOtherPlayer(indexJoueur, inputSocket.getInetAddress());

                        model.newRound = false;
                    } else if (requestSplitted[1].equals(playerTurn)) {
                        model.isPartyFinish(indexJoueur);
                        boolean moveIsPlayable = model.isMovePlayable(Integer.parseInt(requestSplitted[0]), indexJoueur);

                        if (!model.isPartyOn) {
                            oos.writeObject("PARTIE TERMINE");
                        } else if (moveIsPlayable && !model.newRound) {
                            playerTurn = playerUtils.changePlayer(playerTurn, passwords);

                            model.moveHoles(Integer.parseInt(requestSplitted[0]), indexJoueur);
                            update.updateView(oos);
                            indexJoueur = (indexJoueur + 1) % 2;
                            update.updateViewOtherPlayer(indexJoueur, inputSocket.getInetAddress());
                        } else if (model.newRound) {
                            playerTurn = playerUtils.changePlayer(playerTurn, passwords);
                            update.updateView(oos);
                            indexJoueur = (indexJoueur + 1) % 2;
                            update.updateViewOtherPlayer(indexJoueur, inputSocket.getInetAddress());

                            model.newRound = false;
                        } else {
                            oos.writeObject("Deplacement impossible");
                        }
                    } else {
                        oos.writeObject("Ce n'est pas ton tour");
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            inputSocket.close();
            serverSocket.close();

        }

    }
}
