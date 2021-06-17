package ensi.model;

public class Hole {

    int nbSeed = 0;

    public Hole() {
    }

    /**
     * This function permit to get the number of seed of the hole
     * @return The number of seed
     */
    public int getNbSeed() {
        return nbSeed;
    }

    /**
     * This functio npermit to set the number of see of a hole
     * @param nbSeed The number of seed we want
     */
    public void setNbSeed(int nbSeed) {
        this.nbSeed = nbSeed;
    }

    /**
     * This function permit to copy a Hole
     * @return The copied Hole
     */
    public Hole copyHole(){
        Hole copy = new Hole();
        copy.setNbSeed(this.getNbSeed());
        return copy;
    }
}
