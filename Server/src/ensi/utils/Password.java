package ensi.utils;

import java.util.Random;

public class Password {
    public String generatePassword(){
        Random rand = new Random();
        String pass="";
        for(int i = 0 ; i < 20 ; i++){
            char c = (char)(rand.nextInt(26) + 97);
            pass+=c;
        }
        return pass;
    }
}
