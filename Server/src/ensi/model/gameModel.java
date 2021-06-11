package ensi.model;

import java.util.ArrayList;

public class gameModel {

    ArrayList<Whole> wholes = new ArrayList<Whole>();

    public gameModel() {
       for(int i=0;i<16;i++){
           wholes.add(new Whole());
           wholes.get(i).setNb_seed(4);
        }
    }

    public ArrayList<Whole> getWholes() {
        return wholes;
    }

    public void setWholes(ArrayList<Whole> wholes) {
        this.wholes = wholes;
    }
}

