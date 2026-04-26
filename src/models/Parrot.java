package models;

public class Parrot extends Bird {
    private String vocabularySize;

    public Parrot(String name, int age, Owner owner, int id, double wingSpan, boolean canFly, String vocabularySize){
        super(name, age, owner, id, wingSpan, canFly);
        this.vocabularySize = vocabularySize;
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
        return super.calculateWeeklyFee();
    }
}