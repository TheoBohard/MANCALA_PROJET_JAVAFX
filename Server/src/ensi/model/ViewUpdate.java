package ensi.model;

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

    public void update_view() throws UnknownHostException {

        ArrayList<Integer> seed_info = new ArrayList<Integer>();

        ArrayList<Whole> wholes = this.model.getWholes();
        for(Whole whole:wholes){
            int nb_seed = whole.getNb_seed();
            seed_info.add(nb_seed);
        }

        int port = Integer.parseInt(list_ports.get(index_port));
        this.index_port=(this.index_port+1)%2;
        System.out.println(list_ports + "-" + index_port);

        System.out.println("TENTATIVE ENVOIE");
        com.sendMessage(seed_info,port,port-1);
    }



}
