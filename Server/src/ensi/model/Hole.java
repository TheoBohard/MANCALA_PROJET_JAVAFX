package ensi.model;

public class Hole {

    int nbSeed = 0;

    public Hole() {
    }

    public int getNbSeed() {
        return nbSeed;
    }

    public void setNbSeed(int nb_seed) {
        this.nbSeed = nb_seed;
    }

    public Hole copyHole(){
        Hole copy = new Hole();
        copy.setNbSeed(this.getNbSeed());
        return copy;
    }
}
