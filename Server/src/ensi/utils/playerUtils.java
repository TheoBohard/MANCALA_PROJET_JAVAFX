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
