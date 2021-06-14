package ensi.communication;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Communication {

    public Object sendMessage(String message) {
        Socket socket;
        Socket serverSocket;
        Object response = null;

        try {
            socket = createServerSocket(InetAddress.getLocalHost(), Integer.parseInt(String.valueOf(2009)));

            ServerSocket clientSocket = new ServerSocket(2010);
            serverSocket = clientSocket.accept();

            sendMessageSocket(serverSocket, message);

            serverSocket.close();
            clientSocket.close();

            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            if(!message.equals("EXIT")){
                response = ois.readObject();
                System.out.println(response);
            }
            socket.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Socket createServerSocket(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        System.out.println("Connexion au serveur, IP = " + address.toString() + " / Port = " + port);
        return socket;
    }

    private void sendMessageSocket(Socket socket, String message) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(message);
    }

}
