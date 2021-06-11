package ensi.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by faye on 01/06/2017.
 */
public class ClientMessage {
    public static void main(String[] zero)
    {
        Socket socket;

        try
        {
            socket = new Socket(InetAddress.getLocalHost(),2009);
            System.out.println("Demande de connexion");
            InputStream is=socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);


            socket.close();

        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
