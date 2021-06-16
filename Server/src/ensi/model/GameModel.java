package ensi.model;

import java.util.ArrayList;

public class GameModel {

    private ArrayList<Whole> wholes = new ArrayList<Whole>();
    private Integer scoreJoueur1 = 0;
    private Integer scoreJoueur2 = 0;
    private Integer roundJoueur1 = 0;
    private Integer roundJoueur2 = 0;
    private boolean right_to_take_seed = true;
    public boolean party_On=true;
    public boolean new_round=false;

    public GameModel() {
       for(int i=0;i<12;i++){
           wholes.add(new Whole());
           wholes.get(i).setNb_seed(4);
        }

    }

    public void reinit_round(){
        wholes.clear();
        for(int i=0;i<12;i++){
            wholes.add(new Whole());
            wholes.get(i).setNb_seed(4);
        }
    }

    public Integer getRoundJoueur1() {
        return roundJoueur1;
    }

    public Integer getRoundJoueur2() {
        return roundJoueur2;
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
            if((index - i - 1 + 12)%12 != index) {
                Whole whole = wholes.get((index - i - 1 + 12*10) % 12);
                whole.setNb_seed(whole.getNb_seed() + 1);
            }else{
                numberSeed++;
            }
        }

        //On regarde la dernière graines placé et on vérifie si elle remplit les deux conditions
        if(this.right_to_take_seed==true) {
            int index_whole_to_check = (index - numberSeed + 12*10) % 12;
            Whole whole_to_check = wholes.get(index_whole_to_check);
            check_whole(whole_to_check, index_whole_to_check, index_joueur);
        }

        System.out.println("MoveWholes SIZE : " + wholes.size());

        wholes.get(index).setNb_seed(0);
    }

    private void wholesToString(){
        System.out.print("[");
        for(Whole whole:this.wholes){
            System.out.print(whole.getNb_seed());
        }
        System.out.println("]");
    }

    public boolean is_move_playable(int index, int index_joueur){
        this.wholesToString();
        GameModel gameCopy =  copyGameModel();
        gameCopy.moveWholes(index,index_joueur);

        if(this.getWholes().get(index-1).getNb_seed()==0){
            this.wholesToString();
            gameCopy.wholesToString();

            System.out.println("JE PASSE");
            return  false;
        }

        boolean verif=true;
        //Si le joueur adverse n'a plus de graines
        //Alors on regarde si le coup joué lui redonne des graines
        if(this.getEnemySeeds(this,index_joueur)==0) {
          int enemySeeds = gameCopy.getEnemySeeds(gameCopy,index_joueur);
          if(enemySeeds>0){
              verif=true;
          }else{
              verif=false;
          }
        }else{
            int enemySeeds = gameCopy.getEnemySeeds(gameCopy,index_joueur);
            if(enemySeeds==0){
                this.right_to_take_seed = false;
            }else{
                this.right_to_take_seed = true;
            }
        }

        if(verif==false){
            System.out.println("JE PASSE 2");
        }


        return verif;
    }

    public void isThereAnyPossiblemove(int index_joueur) {
        ArrayList<Integer> res = this.getAllPossibleIndex(index_joueur);

        boolean verif=false;
        for(int i:res){
            GameModel copy = this.copyGameModel();
            copy.moveWholes(i+1,index_joueur);
            System.out.println("ICI------------------------------------------------------------");
            System.out.println(i);
            copy.wholesToString();
            if(copy.getEnemySeeds(copy,index_joueur)!=0){
                verif=true;
                break;
            }
        }
        if(verif==false){
            this.end_game();
        }
    }

    private void end_game(){
        if(this.scoreJoueur1>this.scoreJoueur2){
            this.roundJoueur1++;
        }else{
            this.roundJoueur2++;
        }
        this.scoreJoueur1=this.scoreJoueur2=0;

        if(this.roundJoueur1+this.roundJoueur2==6){
            System.out.println("FIN DE LA PARTIE");
            this.party_On = false;
        }else{
            this.new_round=true;
            this.reinit_round();
        }
    }

    private ArrayList<Integer> getAllPossibleIndex(int index_joueur) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(index_joueur==1){
            for(int i=0;i<6;i++){
                res.add(i);
            }
        }else{
            for(int i=6;i<12;i++){
                res.add(i);
            }
        }
        return res;
    }


    private int getEnemySeeds(GameModel gameCopy, Integer index_joueur) {
        int counter=0;
        int counterSeed=0;
        boolean verif = false;
        for (Whole whole : gameCopy.wholes) {
            if (counter < 6 && index_joueur == 0) {
                int nbSeed = whole.getNb_seed();
                counterSeed+=nbSeed;

            }
            if (counter >= 6 && index_joueur == 1) {
                int nbSeed = whole.getNb_seed();
                counterSeed+=nbSeed;

            }
            counter++;
        }
        return counterSeed;
    }

    private ArrayList<Whole> copyWholes(){
        ArrayList<Whole> copyWholes = new ArrayList<Whole>();
        for(Whole whole : this.wholes){
            copyWholes.add(whole.copy_whole());
        }
        return copyWholes;
    }

    private GameModel copyGameModel() {
        GameModel copy = new GameModel();
        copy.setWholes(copyWholes());
        return copy;
    }

    private void check_whole(Whole whole_to_check, Integer index_whole, Integer index_joueur) {
        if(index_joueur==0){
            if(index_whole<6 && (whole_to_check.getNb_seed()==2 || whole_to_check.getNb_seed()==3)){
                int score_to_give = whole_to_check.getNb_seed();
                whole_to_check.setNb_seed(0);
                scoreJoueur1 += score_to_give;
                int new_index_to_check = (index_whole+1)%12;
                check_whole(this.wholes.get(new_index_to_check),new_index_to_check,index_joueur);
            }
        }else if(index_joueur==1){
            if(index_whole>=6 && (whole_to_check.getNb_seed()==2 || whole_to_check.getNb_seed()==3)){
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

