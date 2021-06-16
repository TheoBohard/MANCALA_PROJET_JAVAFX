package ensi.model;

public class Whole {

    int nbSeed = 0;

    public Whole() {
    }

    public int getNbSeed() {
        return nbSeed;
    }

    public void setNbSeed(int nb_seed) {
        this.nbSeed = nb_seed;
    }

    public Whole copyWhole(){
        Whole copy = new Whole();
        copy.setNbSeed(this.getNbSeed());
        return copy;
    }
}
