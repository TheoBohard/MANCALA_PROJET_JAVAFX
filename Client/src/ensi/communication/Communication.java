/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file Communication.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used for communication
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

package ensi.communication;

import ensi.controller.ControllerMenu;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Communication {

    /**
     * This function permit to send a message on the server
     *
     * @param message The message
     * @return The Object provided by the server
     */
    public Object sendMessage(String message) {
        Socket socket;
        Socket serverSocket;
        Object response = null;

        try {
            socket = createServerSocket(InetAddress.getByName(ControllerMenu.ip), Integer.parseInt(String.valueOf(2009)));

            ServerSocket clientSocket = new ServerSocket(2010);
            serverSocket = clientSocket.accept();

            sendMessageSocket(serverSocket, message);

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            if(!message.equals("EXIT")){
                response = ois.readObject();
                System.out.println(response);
            }
            socket.close();
            serverSocket.close();
            clientSocket.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }


        return response;
    }

    /**
     * This function permit to create a socket
     *
     * @param address The ip address
     * @param port    The port
     * @return The socket
     * @throws IOException IOException
     */
    private Socket createServerSocket(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        System.out.println("Connexion au serveur, IP = " + address.toString() + " / Port = " + port);
        return socket;
    }

    /**
     * This function permit to send a message with a socket provided
     *
     * @param socket  The socket
     * @param message The message
     * @throws IOException IOException
     */
    private void sendMessageSocket(Socket socket, String message) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(message);
    }

}
