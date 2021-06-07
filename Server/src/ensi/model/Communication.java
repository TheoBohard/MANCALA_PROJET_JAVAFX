package ensi.model;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communication {

    private String ip;
    private String port;

    public Communication() {
    }

    public String sendMessage(Object message) throws UnknownHostException {
        Socket socket;
        Socket serverSocket;
        String response = null;

        try {
            socket = createServerSocket(InetAddress.getByName("127.0.0.1"), 2020);

            ServerSocket clientSocket = new ServerSocket(2019);
            serverSocket = clientSocket.accept();

            sendMessageSocket(serverSocket, message);

            serverSocket.close();
            clientSocket.close();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null) {
            return response;
        } else {
            return null;
        }
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
