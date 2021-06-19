/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file Hole.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to manage different hole of a game
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

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
