/*
 *
 * ENSICAEN
 * 6 Boulevard Maréchal Juin
 * F-14050 Caen Cedex
 *
 *  This file is owned by ENSICAEN students. No portion of this document may be reproduced,
 *  copied or revised without written permission of the authors.
 *
 *  @file GameModel.java
 *  @author Théo BOHARD (theo.bohard@ecole.ensicaen.fr)
 *  @author Thomas FILLION (thomas.fillion@ecole.ensicaen.fr)
 *  @brief Class used to manage a game
 *  @version 1.0
 *  @date 2021-6-19
 *
 *  @copyright Copyright (c) 2021.
 *
 *
 *
 */

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

    /**
     * Constructor for game model all the default Hole for the moment
     */
    public GameModel() {
       for(int i=0;i<12;i++){
           holes.add(new Hole());
           holes.get(i).setNbSeed(4);
        }
        old_holes = this.copyHoles();
    }

    /**
     * This function permit to check if surrender is possible
     * @return True if yes else otherwise
     */
    public boolean surrenderPossible(){
        return this.getNbSeed() <= 10;
    }

    /**
     * This function permit to get the number of round of a match
     * @return The number of round
     */
    public int getNbRound() {
        return nb_round;
    }

    /**
     * This function permit to reset a round
     */
    public void reInitRound(){
        holes.clear();
        for(int i=0;i<12;i++){
            holes.add(new Hole());
            holes.get(i).setNbSeed(4);
        }
    }

    /**
     * This function permit to get the number of seed of the Holes
     * @return The number of seed
     */
    public Integer getNbSeed(){
        int counter=0;
        for(Hole hole : holes){
            counter+= hole.getNbSeed();
        }
        return counter;
    }

    /**
     * This function permit to get the number of round win by player 1
     * @return The number of round win by player 1
     */
    public Integer getRoundJoueur1() {
        return roundPlayer1;
    }

    /**
     * This function permit to get the number of round win by player 2
     * @return The number of round win by player 2
     */
    public Integer getRoundPlayer2() {
        return roundPlayer2;
    }

    /**
     * This function permit to get the score of player 1
     * @return The score of player 1
     */
    public Integer getScoreJoueur1() {
        return scorePlayer1;
    }

    /**
     * This function permit to get the score of player 2
     * @return The score of player 2
     */
    public Integer getScorePlayer2() {
        return scorePlayer2;
    }

    /**
     * This function permit to move the seed from holes
     * @param index The index to retrieve the good hole
     * @param playerIndex The player index
     */
    public void moveHoles(int index, int playerIndex) {

        old_holes = copyHoles();

        index--;
        int numberSeed = holes.get(index).getNbSeed();


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


        holes.get(index).setNbSeed(0);
    }

    /**
     * This function permit to display a Holes as string
     */
    private void holesToString(){
        System.out.print("[");
        for(Hole hole :this.holes){
            System.out.print(hole.getNbSeed());
        }
        System.out.println("]");
    }

    /**
     * This function permit to check if a move is playable
     * @param index The index to retrieve the good hole
     * @param playerIndex The player index
     * @return True if the move is playable, false otherwise
     */
    public boolean isMovePlayable(int index, int playerIndex){
        this.holesToString();
        GameModel gameCopy =  copyGameModel();
        gameCopy.moveHoles(index,playerIndex);

        if(this.getHoles().get(index-1).getNbSeed()==0){
            this.holesToString();
            gameCopy.holesToString();

            return  false;
        }

        boolean verif=true;
        //Si le joueur adverse n'a plus de graines
        //Alors on regarde si le coup joué lui redonne des graines
        if (this.getEnemySeeds(this,playerIndex)==0) {
          int enemySeeds = gameCopy.getEnemySeeds(gameCopy,playerIndex);
            verif= enemySeeds > 0;
        } else{
            int enemySeeds = gameCopy.getEnemySeeds(gameCopy,playerIndex);
            this.rightToTakeSeed = enemySeeds != 0;
        }

        return verif;
    }

    /**
     * This function permit to check if a party is finished
     * @param playerIndex The player index
     */
    public void isPartyFinish(int playerIndex) {
        ArrayList<Integer> res = this.getAllPossibleIndex(playerIndex);

        boolean verif=false;
        for(int i:res){
            GameModel copy = this.copyGameModel();
            copy.moveHoles(i+1,playerIndex);
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
            if (this.getNbSeed()<6) {
                this.endGame();
            }
        }

        if(!verif){
            this.endGame();
        }
    }

    /**
     * This function permit to distrib seed to players
     */
    public void distribSeeds(){
        this.scorePlayer1 += this.getNbSeed()/2;
        this.scorePlayer2 += this.getNbSeed()/2;
    }

    /**
     * This function permit to end a game
     */
    public void endGame(){
        if(this.scorePlayer1 > this.scorePlayer2){
            this.roundPlayer1++;
        }else if(this.scorePlayer1 < this.scorePlayer2){
            this.roundPlayer2++;
        }

        this.nb_round++;
        this.scorePlayer1 = this.scorePlayer2 = 0;

        if(this.nb_round == 6){
            this.isPartyOn = false;
        }else{
            this.newRound = true;
            this.reInitRound();
        }
    }

    /**
     * This function permit to get all possible index for the current player
     * @param playerIndex The player index
     * @return The possible index
     */
    private ArrayList<Integer> getAllPossibleIndex(int playerIndex) {
        ArrayList<Integer> res = new ArrayList<Integer>();
        if (playerIndex==1) {
            for(int i=0;i<6;i++){
                res.add(i);
            }
        } else{
            for(int i=6;i<12;i++) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * This function permit to get the enemy seed
     * @param gameCopy A copy of the game
     * @param playerIndex The player index
     * @return The number of enemy seed
     */
    private int getEnemySeeds(GameModel gameCopy, Integer playerIndex) {
        int counter=0;
        int counterSeed=0;
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

    /**
     * This function permit to copy Hole
     * @return The copied holes
     */
    private ArrayList<Hole> copyHoles(){
        ArrayList<Hole> copyHoles = new ArrayList<Hole>();
        for(Hole hole : this.holes){
            copyHoles.add(hole.copyHole());
        }
        return copyHoles;
    }

    /**
     * This function permit to copy a GameModel
     * @return The copiedGameModel
     */
    private GameModel copyGameModel() {
        GameModel copy = new GameModel();
        copy.setHoles(copyHoles());
        return copy;
    }

    /**
     * This function permit to check if we have to take the seed on a Hole
     * @param holeToCheck The Hole to check
     * @param holeIndex The hole index
     * @param playerIndex The player index
     */
    private void checkHole(Hole holeToCheck, Integer holeIndex, Integer playerIndex) {
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
    }

    /**
     * This function permit to get Hole arrayList
     * @return The Hole ArrayList
     */
    public ArrayList<Hole> getHoles() {
        return holes;
    }

    /**
     * This function permit to set Hole arraylist
     * @param holes The arraylist we want to set
     */
    public void setHoles(ArrayList<Hole> holes) {
        this.holes = holes;
    }

    /**
     * This function permit to set the difficulty
     * @param difficultyChosen The difficulty
     */
    public void setDifficulty(String difficultyChosen) {
        this.difficulty =  difficultyChosen;
    }

    /**
     * This function permit to cancel a move
     * @return If we can cancel the move
     */
    public boolean cancelMove() {
        if(this.old_holes !=this.holes){
            this.holes = this.old_holes;
            return true;
        }else{
            return false;
        }
    }
}

