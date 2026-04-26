package models;

import utils.BirdUtility;

public class Parrot extends Bird {
    private String vocabularySize = "Amazing";

    private final float BASE_RATE = 10;

    public Parrot(String name, int age, Owner owner, int id, double wingSpan, boolean canFly, int vocabularySize){
        super(name, age, owner, id, wingSpan, canFly);
        this.vocabularySize = BirdUtility.getVocabularyLevel2(vocabularySize);
    }

    @Override
    public String toString(){
        return "";
    }

    //
    // Getters and Setters
    //

    public String getVocabularySize() {
        return vocabularySize;
    }
    public void setVocabularySize(String vocabularySize) {
        this.vocabularySize = vocabularySize;
    }

    //
    // Main methods
    //

    @Override
    public double calculateWeeklyFee() {
        return numOfDaysAttending() * BASE_RATE;
    }
}