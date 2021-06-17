package ensi.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ViewUpdate {

    private final GameModel model;
    private final ArrayList<String> portList;
    private int indexPort;
    private Communication com = new Communication();

    public ViewUpdate(GameModel model, ArrayList<String> portList) {
        this.model = model;
        this.portList = portList;
        this.indexPort =0;
    }

    public void askOpponent(String message){
        int port = Integer.parseInt(portList.get(indexPort));
        this.indexPort =(this.indexPort +1)%2;
        com.sendInitMessage(message,port,port-1);
    }

    public void initViewAndComm() throws UnknownHostException {

        ArrayList<Integer> seedInfo = new ArrayList<>();

        ArrayList<Whole> wholes = this.model.getWholes();
        for(Whole whole:wholes){
            int numberSeed = whole.getNbSeed();
            seedInfo.add(numberSeed);
        }

        seedInfo.add(model.getRoundJoueur1());
        seedInfo.add(model.getRoundPlayer2());
        seedInfo.add(model.getScoreJoueur1());
        seedInfo.add(model.getScorePlayer2());
        seedInfo.add(model.getNb_round());

        int port = Integer.parseInt(portList.get(indexPort));
        this.indexPort =(this.indexPort +1)%2;
        System.out.println(portList + "-" + indexPort);

        System.out.println("TENTATIVE ENVOIE");
        com.sendInitMessage(seedInfo,port,port-1);
    }

    public void updateView(ObjectOutputStream oos, int indexJoueur) throws IOException {
        ArrayList<Integer> seedInfo = new ArrayList<>();
        System.out.println("TEST");
        updateViewCommon(seedInfo);
        System.out.println(seedInfo);

        com.sendMessage(seedInfo, oos);
    }


    public void updateViewOtherPlayer(int indexJoueur) {
        ArrayList<Integer> seedInfo = new ArrayList<>();
        System.out.println("TEST other player");
        updateViewCommon(seedInfo);
        System.out.println(seedInfo);

        com.sendInitMessage(seedInfo, Integer.parseInt(this.portList.get((indexJoueur)%2))+1, Integer.parseInt(this.portList.get((indexJoueur)%2))+2);
    }

    private void updateViewCommon(ArrayList<Integer> seedInfo) {
        ArrayList<Whole> wholes = this.model.getWholes();
        for(Whole whole : wholes){
            int nbSeed = whole.getNbSeed();
            seedInfo.add(nbSeed);
        }

        seedInfo.add(model.getRoundJoueur1());
        seedInfo.add(model.getRoundPlayer2());
        seedInfo.add(model.getScoreJoueur1());
        seedInfo.add(model.getScorePlayer2());
        seedInfo.add(model.getNb_round());
    }
}
