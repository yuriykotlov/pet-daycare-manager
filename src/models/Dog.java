package models;

public class Dog extends Mammal {
    private final float NONDANGEROUS_DAILY_RATE = 1;
    private boolean dangerousBreed;
    private final float DANGEROUS_DAILY_RATE = 1;

    private String breed;

    public Dog(String name, int age, Owner owner, int id, char sex, boolean neutered, double weight, boolean vaccinated, boolean dangerousBreed, String breed){
        super(name, age, owner, id, sex, neutered, weight, vaccinated);
        this.dangerousBreed = dangerousBreed;
        this.breed = breed;
    }

    @Override
    public String toString(){
        return "";
    }

    //
    // Getters and Setters
    //

    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isDangerousBreed() {
        return dangerousBreed;
    }
    public void setDangerousBreed(boolean dangerousBreed) {
        this.dangerousBreed = dangerousBreed;
    }

    //
    // Main methods
    //

    @Override
    public double calculateWeeklyFee() {
        return super.calculateWeeklyFee();
    }
}
