package ensi.model;

import java.util.ArrayList;

public class GameModel {

    ArrayList<Whole> wholes = new ArrayList<Whole>();
    int passwordPlayer1;
    int passwordPlayer2;

    public void setPasswordPlayer1(int passwordPlayer1) {
        this.passwordPlayer1 = passwordPlayer1;
    }

    public void setPasswordPlayer2(int passwordPlayer2) {
        this.passwordPlayer2 = passwordPlayer2;
    }

    public GameModel() {
       for(int i=0;i<12;i++){
           wholes.add(new Whole());
           wholes.get(i).setNb_seed(4);
        }
    }

    public void moveWholes(int index) {
        int numberSeed = wholes.get(index).getNb_seed();

        for(int i = 0; i < numberSeed; i++) {
            wholes.get((index+i+1)%12).setNb_seed(wholes.get((index+1)%12).getNb_seed()+1);
        }

        System.out.println("MoveWholes SIZE : " + wholes.size());

        wholes.get(index).setNb_seed(0);
    }

    public ArrayList<Whole> getWholes() {
        return wholes;
    }

    public void setWholes(ArrayList<Whole> wholes) {
        this.wholes = wholes;
    }
}

