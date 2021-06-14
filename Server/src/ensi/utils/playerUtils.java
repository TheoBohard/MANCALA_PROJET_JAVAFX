package ensi.utils;

import java.lang.reflect.Array;
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

    static public String chooseRandomPlayer(ArrayList<String> players) {
        Random rand = new Random();
        return players.get(rand.nextInt(2));
    }
}
