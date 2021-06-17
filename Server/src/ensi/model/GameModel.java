package ensi.model;

import java.util.ArrayList;

public class GameModel {

    private ArrayList<Hole> holes = new ArrayList<Hole>();
    private Integer scorePlayer1 = 0;
    private Integer scorePlayer2 = 0;
    private Integer roundPlayer1 = 0;
    private Integer roundPlayer2 = 0;
    private boolean rightToTakeSeed = true;
    public boolean isPartyOn = true;
    public boolean newRound = false;
    private String difficulty = "";
    private  int nb_round = 0;
    private ArrayList<Hole> old_holes;

    public GameModel() {
       for(int i=0;i<12;i++){
           holes.add(new Hole());
           holes.get(i).setNbSeed(4);
        }
        old_holes = this.copyHoles();
    }

    public boolean abandonPossible(){
        if(this.getNbSedd()<=10){
            return true;
        }else{
            return false;
        }
    }

    public int getNb_round() {
        return nb_round;
    }

    public void reInitRound(){
        holes.clear();
        for(int i=0;i<12;i++){
            holes.add(new Hole());
            holes.get(i).setNbSeed(4);
        }
    }

    public Integer getNbSedd(){
        int counter=0;
        for(Hole hole : holes){
            counter+= hole.getNbSeed();
        }
        return counter;
    }

    public Integer getRoundJoueur1() {
        return roundPlayer1;
    }

    public Integer getRoundPlayer2() {
        return roundPlayer2;
    }

    public Integer getScoreJoueur1() {
        return scorePlayer1;
    }

    public Integer getScorePlayer2() {
        return scorePlayer2;
    }

    public void moveHoles(int index, int playerIndex) {

        old_holes = copyHoles();

        index--;
        int numberSeed = holes.get(index).getNbSeed();
        System.out.println("Number seed : => " + numberSeed);

        for(int i = 0; i < numberSeed; i++) {
            if((index - i - 1 + 12)%12 != index) {
                Hole hole = holes.get((index - i - 1 + 12*10) % 12);
                hole.setNbSeed(hole.getNbSeed() + 1);
            }else{
                numberSeed++;
            }
        }

        if(this.rightToTakeSeed) {
            int indexHoleToCheck = (index - numberSeed + 12*10) % 12;
            Hole holeToCheck = holes.get(indexHoleToCheck);
            checkHole(holeToCheck, indexHoleToCheck, playerIndex);
        }

        System.out.println("MoveHoles SIZE : " + holes.size());

        holes.get(index).setNbSeed(0);
    }

    private void holesToString(){
        System.out.print("[");
        for(Hole hole :this.holes){
            System.out.print(hole.getNbSeed());
        }
        System.out.println("]");
    }

    public boolean isMovePlayable(int index, int playerIndex){
        this.holesToString();
        GameModel gameCopy =  copyGameModel();
        gameCopy.moveHoles(index,playerIndex);

        if(this.getHoles().get(index-1).getNbSeed()==0){
            this.holesToString();
            gameCopy.holesToString();

            System.out.println("JE PASSE");
            return  false;
        }

        boolean verif=true;
        //Si le joueur adverse n'a plus de graines
        //Alors on regarde si le coup joué lui redonne des graines
        if(this.getEnemySeeds(this,playerIndex)==0) {
          int enemySeeds = gameCopy.getEnemySeeds(gameCopy,playerIndex);
          if(enemySeeds>0){
              verif=true;
          }else{
              verif=false;
          }
        }else{
            int enemySeeds = gameCopy.getEnemySeeds(gameCopy,playerIndex);
            if(enemySeeds==0){
                this.rightToTakeSeed = false;
            }else{
                this.rightToTakeSeed = true;
            }
        }

        if(verif==false){
            System.out.println("JE PASSE 2");
        }


        return verif;
    }

    public void isPartyFinish(int playerIndex) {
        ArrayList<Integer> res = this.getAllPossibleIndex(playerIndex);

        boolean verif=false;
        for(int i:res){
            GameModel copy = this.copyGameModel();
            copy.moveHoles(i+1,playerIndex);
            System.out.println("ICI------------------------------------------------------------");
            System.out.println(i);
            copy.holesToString();
            if(copy.getEnemySeeds(copy,playerIndex)!=0){
                verif=true;
                break;
            }
        }

        if(difficulty.equals("middleMode")){
            if(scorePlayer1>=2 || scorePlayer2>=2){
                this.endGame();
            }
        }else if(difficulty.equals("easyMode")) {
            if (this.getNbSedd()<6) {
                this.endGame();
            }
        }

        if(!verif){
            this.endGame();
        }
    }

    public void distrib_seeds(){
        this.scorePlayer1+=this.getNbSedd()/2;
        this.scorePlayer2+=this.getNbSedd()/2;
    }

    public void endGame(){
        System.out.println("JE PASSSSSEEEEE");
        if(this.scorePlayer1 > this.scorePlayer2){
            this.roundPlayer1++;
        }else if(this.scorePlayer1 < this.scorePlayer2){
            this.roundPlayer2++;
        }

        this.nb_round++;
        this.scorePlayer1 = this.scorePlayer2 = 0;

        if(this.nb_round == 6){
            System.out.println("FIN DE LA PARTIE");
            this.isPartyOn = false;
        }else{
            this.newRound = true;
            this.reInitRound();
        }
    }

    private ArrayList<Integer> getAllPossibleIndex(int playerIndex) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if(playerIndex==1){
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


    private int getEnemySeeds(GameModel gameCopy, Integer playerIndex) {
        int counter=0;
        int counterSeed=0;
        boolean verif = false;
        for (Hole hole : gameCopy.holes) {
            if (counter < 6 && playerIndex == 0) {
                int nbSeed = hole.getNbSeed();
                counterSeed += nbSeed;

            }
            if (counter >= 6 && playerIndex == 1) {
                int nbSeed = hole.getNbSeed();
                counterSeed += nbSeed;

            }
            counter++;
        }
        return counterSeed;
    }

    private ArrayList<Hole> copyHoles(){
        ArrayList<Hole> copyHoles = new ArrayList<Hole>();
        for(Hole hole : this.holes){
            copyHoles.add(hole.copyHole());
        }
        return copyHoles;
    }

    private GameModel copyGameModel() {
        GameModel copy = new GameModel();
        copy.setHoles(copyHoles());
        return copy;
    }

    private void checkHole(Hole holeToCheck, Integer holeIndex, Integer playerIndex) {
        System.out.println(playerIndex);
        if(playerIndex==0){
            if(holeIndex<6 && (holeToCheck.getNbSeed()==2 || holeToCheck.getNbSeed()==3)){
                int scoreToGive = holeToCheck.getNbSeed();
                holeToCheck.setNbSeed(0);
                scorePlayer1 += scoreToGive;
                int newIndexToCheck = (holeIndex+1)%12;
                checkHole(this.holes.get(newIndexToCheck),newIndexToCheck,playerIndex);
            }
        }else if(playerIndex==1){
            if(holeIndex>=6 && (holeToCheck.getNbSeed()==2 || holeToCheck.getNbSeed()==3)){
                int scoreToGive = holeToCheck.getNbSeed();
                holeToCheck.setNbSeed(0);
                scorePlayer2 += scoreToGive;
                int newIndexToCheck = (holeIndex+1)%12;
                checkHole(this.holes.get(newIndexToCheck),newIndexToCheck,playerIndex);
            }
        }
        System.out.println(this.scorePlayer1);
        System.out.println(this.scorePlayer2);
    }


    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }

    public void setDifficulty(String difficultyChosen) {
        this.difficulty =  difficultyChosen;
    }

    public boolean cancel_move() {
        if(this.old_holes !=this.holes){
            this.holes = this.old_holes;
            return true;
        }else{
            return false;
        }
    }
}

