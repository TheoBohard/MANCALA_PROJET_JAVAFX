package ensi.model;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Communication {

    private String ip;
    private String port;
    private int nbPlayerConnected;
    public int getNbPlayerConnected() {
        return nbPlayerConnected;
    }
    public void setNbPlayerConnected(int nbPlayerConnected) {
        this.nbPlayerConnected = nbPlayerConnected;
    }

    public Communication() {
    }

    public void sendInitMessage(Object message, int portSocket, int portSocketServeur) {
        Socket socket;
        Socket serverSocket;
        System.out.println(portSocket);
        System.out.println(portSocketServeur);
        try {
            System.out.println(portSocket);
            socket = createServerSocket(InetAddress.getLocalHost(), portSocket);

            ServerSocket clientSocket = new ServerSocket(portSocketServeur);
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
