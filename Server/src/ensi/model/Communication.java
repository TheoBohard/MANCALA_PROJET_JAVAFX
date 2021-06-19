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

package ensi.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Communication {

    /**
     * This function permit to send init message to the client
     *
     * @param message          The message
     * @param portSocket       The port of the socket
     * @param portSocketServer The port of the server socket
     */
    public void sendInitMessage(Object message, int portSocket, int portSocketServer, InetAddress address) {
        Socket socket;
        Socket serverSocket;
        try {
            socket = createServerSocket(address, portSocket);

            ServerSocket clientSocket = new ServerSocket(portSocketServer);
            serverSocket = clientSocket.accept();

            sendMessageSocket(serverSocket, message);

            serverSocket.close();
            clientSocket.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This funciton permit to send a Object over a ObjectOutputStream
     *
     * @param message The object
     * @param oos     The ObjectOutputStream
     * @throws IOException IOException
     */
    public void sendMessage(Object message, ObjectOutputStream oos) throws IOException {

        oos.writeObject(message);
    }

    /**
     * This function permit to create a server socket
     *
     * @param address The ip address
     * @param port    The port
     * @return The socket
     * @throws IOException IOException
     */
    private Socket createServerSocket(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        System.out.println("Connexion au client, IP = " + address.toString() + " / Port = " + port);
        return socket;
    }

    /**
     * This function permit to send a message over a socket
     *
     * @param socket  The socket
     * @param message The message
     * @throws IOException IOException
     */
    private void sendMessageSocket(Socket socket, Object message) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(message);
    }
}
