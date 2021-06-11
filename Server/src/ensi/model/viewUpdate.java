package ensi.model;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class viewUpdate {

    private final gameModel model;

    public viewUpdate(gameModel model) {
        this.model=model;
    }

    public void update_view() throws UnknownHostException {

        ArrayList<Integer> seed_info = new ArrayList<Integer>();

        ArrayList<Whole> wholes = this.model.getWholes();
        for(Whole whole:wholes){
            int nb_seed = whole.getNb_seed();
            seed_info.add(nb_seed);
        }

        Communication com = new Communication();
        System.out.println("TENTATIVE ENVOIE");
        com.sendMessage(seed_info);

    }



}
