package ensi.model;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

public class Communication {

    private String ip;
    private String port;
    private int nb_player_connected;
    public int getNb_player_connected() {
        return nb_player_connected;
    }
    public void setNb_player_connected(int nb_player_connected) {
        this.nb_player_connected = nb_player_connected;
    }

    public Communication() {
    }

    public void sendInitMessage(Object message, int port_socket, int port_socket_serveur) {
        Socket socket;
        Socket serverSocket;
        System.out.println(port_socket);
        System.out.println(port_socket_serveur);
        try {
            System.out.println(port_socket);
            socket = createServerSocket(InetAddress.getLocalHost(), port_socket);

            ServerSocket clientSocket = new ServerSocket(port_socket_serveur);
            serverSocket = clientSocket.accept();

            sendMessageSocket(serverSocket, message);

            serverSocket.close();
            clientSocket.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Object message, ObjectOutputStream oos) throws IOException {

        oos.writeObject(message);
    }

    private Socket createServerSocket(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        System.out.println("Connexion au client, IP = " + address.toString() + " / Port = " + port);
        return socket;
    }

    private void sendMessageSocket(Socket socket, Object message) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(message);
    }
}
