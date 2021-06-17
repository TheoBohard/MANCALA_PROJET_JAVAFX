package ensi.model;

import java.util.ArrayList;

public class GameModel {

    private ArrayList<Whole> wholes = new ArrayList<Whole>();
    private Integer scorePlayer1 = 0;
    private Integer scorePlayer2 = 0;
    private Integer roundPlayer1 = 0;
    private Integer roundPlayer2 = 0;
    private boolean rightToTakeSeed = true;
    public boolean isPartyOn = true;
    public boolean newRound = false;
    private String difficulty = "";

    public GameModel() {
       for(int i=0;i<12;i++){
           wholes.add(new Whole());
           wholes.get(i).setNbSeed(4);
        }

    }

    public void reInitRound(){
        wholes.clear();
        for(int i=0;i<12;i++){
            wholes.add(new Whole());
            wholes.get(i).setNbSeed(4);
        }
    }

    public Integer getNbSedd(){
        int counter=0;
        for(Whole whole:wholes){
            counter+=whole.getNbSeed();
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

    public void moveWholes(int index, int playerIndex) {
        index--;
        int numberSeed = wholes.get(index).getNbSeed();
        System.out.println("Number seed : => " + numberSeed);

        for(int i = 0; i < numberSeed; i++) {
            if((index - i - 1 + 12)%12 != index) {
                Whole whole = wholes.get((index - i - 1 + 12*10) % 12);
                whole.setNbSeed(whole.getNbSeed() + 1);
            }else{
                numberSeed++;
            }
        }

        //On regarde la dernière graines placé et on vérifie si elle remplit les deux conditions
        if(this.rightToTakeSeed) {
            int indexWholeToCheck = (index - numberSeed + 12*10) % 12;
            Whole wholeToCheck = wholes.get(indexWholeToCheck);
            checkWhole(wholeToCheck, indexWholeToCheck, playerIndex);
        }

        System.out.println("MoveWholes SIZE : " + wholes.size());

        wholes.get(index).setNbSeed(0);
    }

    private void wholesToString(){
        System.out.print("[");
        for(Whole whole:this.wholes){
            System.out.print(whole.getNbSeed());
        }
        System.out.println("]");
    }

    public boolean isMovePlayable(int index, int playerIndex){
        this.wholesToString();
        GameModel gameCopy =  copyGameModel();
        gameCopy.moveWholes(index,playerIndex);

        if(this.getWholes().get(index-1).getNbSeed()==0){
            this.wholesToString();
            gameCopy.wholesToString();

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
            copy.moveWholes(i+1,playerIndex);
            System.out.println("ICI------------------------------------------------------------");
            System.out.println(i);
            copy.wholesToString();
            if(copy.getEnemySeeds(copy,playerIndex)!=0){
                verif=true;
                break;
            }
        }

        if(difficulty.equals("middleMode")){
            if(scorePlayer1>=25 || scorePlayer2>=25){
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

    private void endGame(){
        if(this.scorePlayer1 > this.scorePlayer2){
            this.roundPlayer1++;
        }else{
            this.roundPlayer2++;
        }
        this.scorePlayer1 = this.scorePlayer2 = 0;

        if(this.roundPlayer1 + this.roundPlayer2 == 6){
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
        for (Whole whole : gameCopy.wholes) {
            if (counter < 6 && playerIndex == 0) {
                int nbSeed = whole.getNbSeed();
                counterSeed += nbSeed;

            }
            if (counter >= 6 && playerIndex == 1) {
                int nbSeed = whole.getNbSeed();
                counterSeed += nbSeed;

            }
            counter++;
        }
        return counterSeed;
    }

    private ArrayList<Whole> copyWholes(){
        ArrayList<Whole> copyWholes = new ArrayList<Whole>();
        for(Whole whole : this.wholes){
            copyWholes.add(whole.copyWhole());
        }
        return copyWholes;
    }

    private GameModel copyGameModel() {
        GameModel copy = new GameModel();
        copy.setWholes(copyWholes());
        return copy;
    }

    private void checkWhole(Whole wholeToCheck, Integer wholeIndex, Integer playerIndex) {
        if(playerIndex==0){
            if(wholeIndex<6 && (wholeToCheck.getNbSeed()==2 || wholeToCheck.getNbSeed()==3)){
                int scoreToGive = wholeToCheck.getNbSeed();
                wholeToCheck.setNbSeed(0);
                scorePlayer1 += scoreToGive;
                int newIndexToCheck = (wholeIndex+1)%12;
                checkWhole(this.wholes.get(newIndexToCheck),newIndexToCheck,playerIndex);
            }
        }else if(playerIndex==1){
            if(wholeIndex>=6 && (wholeToCheck.getNbSeed()==2 || wholeToCheck.getNbSeed()==3)){
                int scoreToGive = wholeToCheck.getNbSeed();
                wholeToCheck.setNbSeed(0);
                scorePlayer2 += scoreToGive;
                int newIndexToCheck = (wholeIndex+1)%12;
                checkWhole(this.wholes.get(newIndexToCheck),newIndexToCheck,playerIndex);
            }
        }
        System.out.println(this.scorePlayer1);
        System.out.println(this.scorePlayer2);
    }


    public ArrayList<Whole> getWholes() {
        return wholes;
    }

    public void setWholes(ArrayList<Whole> wholes) {
        this.wholes = wholes;
    }

    public void setDifficulty(String difficultyChosen) {
        this.difficulty =  difficultyChosen;
    }
}

