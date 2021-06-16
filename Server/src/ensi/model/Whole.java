package ensi.model;

import java.util.ArrayList;

public class Whole {

    int nb_seed = 0;

    public Whole() {
    }

    public int getNb_seed() {
        return nb_seed;
    }

    public void setNb_seed(int nb_seed) {
        this.nb_seed = nb_seed;
    }

    public Whole copy_whole(){
        Whole copy = new Whole();
        copy.setNb_seed(this.getNb_seed());
        return copy;
    }
}
