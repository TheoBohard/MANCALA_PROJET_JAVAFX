package ensi.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ViewUpdate {

    private final GameModel model;
    private final ArrayList<String> portList;
    private int indexPort;
    private final Communication com = new Communication();

    /**
     * Constructor for ViewUpdate
     * @param model A game model
     * @param portList The port to used to communicate
     */
    public ViewUpdate(GameModel model, ArrayList<String> portList) {
        this.model = model;
        this.portList = portList;
        this.indexPort =0;
    }

    /**
     * This function permit to handle when a player want to surrender
     *
     * @param message The message to display
     */
    public void askOpponent(String message, InetAddress address) {
        int port = Integer.parseInt(portList.get(indexPort));
        this.indexPort = (this.indexPort + 1) % 2;
        com.sendInitMessage(message, port, port - 1, address);
    }

    /**
     * This function permit to init the connection and the view of clients
     *
     * @throws UnknownHostException
     */
    public void initViewAndComm(InetAddress address) throws UnknownHostException {

        ArrayList<Integer> seedInfo = new ArrayList<>();

        processArrayList(seedInfo);

        int port = Integer.parseInt(portList.get(indexPort));
        this.indexPort = (this.indexPort + 1) % 2;
        System.out.println(portList + "-" + indexPort);

        System.out.println("TENTATIVE ENVOIE");
        com.sendInitMessage(seedInfo, port, port - 1, address);
    }

    /**
     * This function permit to update the view of the clients
     * @param oos The ObjectOutputStream to communicate
     * @throws IOException
     */
    public void updateView(ObjectOutputStream oos) throws IOException {
        ArrayList<Integer> seedInfo = new ArrayList<>();
        System.out.println("TEST");
        updateViewCommon(seedInfo);
        System.out.println(seedInfo);

        com.sendMessage(seedInfo, oos);
    }

    /**
     * This function permit to update the view of the second player who is not playing
     *
     * @param indexPlayer The player index
     */
    public void updateViewOtherPlayer(int indexPlayer, InetAddress address) {
        ArrayList<Integer> seedInfo = new ArrayList<>();
        System.out.println("TEST other player");
        updateViewCommon(seedInfo);
        System.out.println(seedInfo);

        com.sendInitMessage(seedInfo, Integer.parseInt(this.portList.get((indexPlayer) % 2)) + 1,
                Integer.parseInt(this.portList.get((indexPlayer) % 2)) + 2, address);
    }

    /**
     * This function permit to make the arraylist to send to the user to update view
     *
     * @param seedInfo The arraylist
     */
    private void updateViewCommon(ArrayList<Integer> seedInfo) {
        processArrayList(seedInfo);
    }

    /**
     * This function permit to populate a game array list
     *
     * @param seedInfo The arraylist
     */
    private void processArrayList(ArrayList<Integer> seedInfo) {
        ArrayList<Hole> holes = this.model.getHoles();
        for (Hole hole : holes) {
            int nbSeed = hole.getNbSeed();
            seedInfo.add(nbSeed);
        }

        seedInfo.add(model.getRoundJoueur1());
        seedInfo.add(model.getRoundPlayer2());
        seedInfo.add(model.getScoreJoueur1());
        seedInfo.add(model.getScorePlayer2());
        seedInfo.add(model.getNbRound());
    }
}
