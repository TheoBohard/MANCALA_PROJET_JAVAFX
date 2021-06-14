package ensi.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ViewUpdate {

    private final GameModel model;
    private final ArrayList<String> list_ports;
    private int index_port;
    private Communication com = new Communication();

    public ViewUpdate(GameModel model, ArrayList<String> list_ports) {
        this.model=model;
        this.list_ports=list_ports;
        this.index_port=0;
    }

    public void initViewAndComm() throws UnknownHostException {

        ArrayList<Integer> seed_info = new ArrayList<>();

        ArrayList<Whole> wholes = this.model.getWholes();
        for(Whole whole:wholes){
            int nb_seed = whole.getNb_seed();
            seed_info.add(nb_seed);
        }

        int port = Integer.parseInt(list_ports.get(index_port));
        this.index_port=(this.index_port+1)%2;
        System.out.println(list_ports + "-" + index_port);

        System.out.println("TENTATIVE ENVOIE");
        com.sendInitMessage(seed_info,port,port-1);
    }

    public void updateView(ObjectOutputStream oos) throws IOException {
        ArrayList<Integer> seedInfo = new ArrayList<>();
        System.out.println("TEST");
        ArrayList<Whole> wholes = this.model.getWholes();
        for(Whole whole:wholes){
            int nb_seed = whole.getNb_seed();
            seedInfo.add(nb_seed);
        }
        com.sendMessage(seedInfo, oos);
    }


    public void updateViewOtherPlayer(int index_joueur) {
        ArrayList<Integer> seedInfo = new ArrayList<>();
        ArrayList<Whole> wholes = this.model.getWholes();
        for(Whole whole:wholes){
            int nb_seed = whole.getNb_seed();
            seedInfo.add(nb_seed);
        }
        com.sendInitMessage(seedInfo, Integer.parseInt(this.list_ports.get((index_joueur)%2))+1, Integer.parseInt(this.list_ports.get((index_joueur)%2))+2);
    }
}
