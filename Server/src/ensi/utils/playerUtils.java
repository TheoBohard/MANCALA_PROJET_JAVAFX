/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file playerUtils.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to manage some player features
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

package ensi.utils;

import java.util.ArrayList;
import java.util.Random;

public class playerUtils {
    /**
     * This function permit to change the current player
     *
     * @param player       The old player
     * @param passwordList The id list
     * @return The new player
     */
    static public String changePlayer(String player, ArrayList<String> passwordList) {
        if (player.equals(passwordList.get(0))) {
            player = passwordList.get(1);
        } else {
            player = passwordList.get(0);
        }

        return player;
    }

    /**
     * This function permit to generate a number between 0 and max
     *
     * @param max Max number
     * @return The random number
     */
    static public int chooseRandomNumber(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }
}
