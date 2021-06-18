package ensi.communication;

import ensi.controller.ControllerMenu;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Communication {

    /**
     * This funciton permit to send a message on the server
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
     * @param address The ip address
     * @param port The port
     * @return The socket
     * @throws IOException
     */
    private Socket createServerSocket(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        System.out.println("Connexion au serveur, IP = " + address.toString() + " / Port = " + port);
        return socket;
    }

    /**
     * This function permit to send a message with a socket provided
     * @param socket The socket
     * @param message The message
     * @throws IOException
     */
    private void sendMessageSocket(Socket socket, String message) throws IOException {
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(message);
    }

}
