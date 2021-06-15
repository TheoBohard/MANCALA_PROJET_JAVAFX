package ensi.model;

import java.util.ArrayList;

public class GameModel {

    ArrayList<Whole> wholes = new ArrayList<Whole>();
    Integer scoreJoueur1 = 0;
    Integer scoreJoueur2 = 0;

    public GameModel() {
       for(int i=0;i<12;i++){
           wholes.add(new Whole());
           wholes.get(i).setNb_seed(4);
        }

    }

    public Integer getScoreJoueur1() {
        return scoreJoueur1;
    }

    public Integer getScoreJoueur2() {
        return scoreJoueur2;
    }

    public void moveWholes(int index, int index_joueur) {
        index--;
        int numberSeed = wholes.get(index).getNb_seed();
        System.out.println("Number seed : => " + numberSeed);

        for(int i = 0; i < numberSeed; i++) {
            Whole whole = wholes.get((index - i - 1 + 12 )%12);
            System.out.println("Le whole concerné au niveau : " + (i+1) + " = " + whole.getNb_seed());
            whole.setNb_seed(whole.getNb_seed()+1);
        }

        //On regarde la dernière graines placé et on vérifie si elle remplit les deux conditions
        int index_whole_to_check = (index - numberSeed + 12 )%12;
        Whole whole_to_check = wholes.get(index_whole_to_check);
        check_whole(whole_to_check,index_whole_to_check,index_joueur);

        System.out.println("MoveWholes SIZE : " + wholes.size());

        wholes.get(index).setNb_seed(0);
    }

    private void check_whole(Whole whole_to_check, Integer index_whole, Integer index_joueur) {
        if(index_joueur==0){
            if(index_whole<6 && whole_to_check.getNb_seed()==2 || whole_to_check.getNb_seed()==3){
                int score_to_give = whole_to_check.getNb_seed();
                whole_to_check.setNb_seed(0);
                scoreJoueur1 += score_to_give;
                int new_index_to_check = (index_whole+1)%12;
                check_whole(this.wholes.get(new_index_to_check),new_index_to_check,index_joueur);
            }
        }else if(index_joueur==1){
            if(index_whole>=6 && whole_to_check.getNb_seed()==2 || whole_to_check.getNb_seed()==3){
                int score_to_give = whole_to_check.getNb_seed();
                whole_to_check.setNb_seed(0);
                scoreJoueur2+= score_to_give;
                int new_index_to_check = (index_whole+1)%12;
                check_whole(this.wholes.get(new_index_to_check),new_index_to_check,index_joueur);
            }
        }
        System.out.println(this.scoreJoueur1);
        System.out.println(this.scoreJoueur2);
    }


    public ArrayList<Whole> getWholes() {
        return wholes;
    }

    public void setWholes(ArrayList<Whole> wholes) {
        this.wholes = wholes;
    }
}

