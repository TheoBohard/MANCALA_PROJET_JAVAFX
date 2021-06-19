/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file idGenerator.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to generate random numbers
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

package ensi.utils;

import java.util.Random;

public class idGenerator {
    /**
     * This function permit to generate a random id
     *
     * @return The random id
     */
    public String generateId() {
        Random rand = new Random();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            char c = (char) (rand.nextInt(26) + 97);
            pass.append(c);
        }
        return pass.toString();
    }
}
