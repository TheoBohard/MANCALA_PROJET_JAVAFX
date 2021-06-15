package ensi.model;

import java.util.ArrayList;

public class GameModel {

    ArrayList<Whole> wholes = new ArrayList<Whole>();

    public GameModel() {
       for(int i=0;i<12;i++){
           wholes.add(new Whole());
           wholes.get(i).setNb_seed(4);
        }

    }

    public void moveWholes(int index, String playerTurn) {
        index--;
        int numberSeed = wholes.get(index).getNb_seed();
        System.out.println("Number seed : => " + numberSeed);

        for(int i = 0; i < numberSeed; i++) {
            Whole whole = wholes.get((index - i - 1 + 12 )%12);
            System.out.println("Le whole concerné au niveau : " + (i+1) + " = " + whole.getNb_seed());
            whole.setNb_seed(whole.getNb_seed()+1);
        }

        System.out.println("MoveWholes SIZE : " + wholes.size());

        wholes.get(index).setNb_seed(0);
    }

    public void checkIfPointEarned(String playerTurn, Whole whole) {
        if(playerTurn.equals("PLAYER_1")) {

        } else if(playerTurn.equals("PLAYER_2")) {

        }
    }

    public ArrayList<Whole> getWholes() {
        return wholes;
    }

    public void setWholes(ArrayList<Whole> wholes) {
        this.wholes = wholes;
    }
}

