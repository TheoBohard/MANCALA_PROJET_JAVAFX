package ensi.utils;

import java.util.ArrayList;
import java.util.Random;

public class playerUtils {
    static public String changePlayer(String player, ArrayList<String> passwordList) {
        if(player.equals(passwordList.get(0))) {
            player = passwordList.get(1);
        }
        else
        {
            player = passwordList.get(0);
        }

        return player;
    }

    static public int chooseRandomNumber(int max) {
        Random rand = new Random();
        return rand.nextInt(2);
    }
}
