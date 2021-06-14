package ensi.utils;

import java.util.Random;

public class idGenerator {
    public String generateId(){
        Random rand = new Random();
        StringBuilder pass= new StringBuilder();
        for(int i = 0 ; i < 20 ; i++){
            char c = (char)(rand.nextInt(26) + 97);
            pass.append(c);
        }
        return pass.toString();
    }
}
