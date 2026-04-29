package models;

import utils.Utilities;
import utils.DogBreedUtility;

public class Dog extends Mammal {
    private final float NONDANGEROUS_DAILY_RATE = 30;
    private final float DANGEROUS_DAILY_RATE = 40;

    private boolean dangerousBreed = false;
    private float currentRate = 0;
    private String breed = "";

    public Dog(String name, int age, Owner owner, int id, char sex, boolean neutered, double weight, boolean vaccinated, boolean dangerousBreed, String breed){
        super(name, age, owner, id, sex, neutered, weight, vaccinated);
        this.dangerousBreed = dangerousBreed;

        // this variable is here to not have to use if statements all the time
        // to find out the currentRate for this dog
        currentRate = this.dangerousBreed ? DANGEROUS_DAILY_RATE : NONDANGEROUS_DAILY_RATE;

        // if dog breed is valid
        if(DogBreedUtility.checkBreed(breed)){
            this.breed = breed;
        }
    }

    @Override
    public String toString(){
        return super.toString() + " | Dangerous Breed: " + Utilities.booleanToYN(isDangerousBreed())
                + " | Current Rate: " + getCurrentRate()
                + " | Breed: " + getBreed()
                + " | Weekly Fee: " + calculateWeeklyFee();
    }

    //
    // Getters and Setters
    //

    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        if(DogBreedUtility.checkBreed(breed)){
            this.breed = breed;
        }
    }

    public boolean isDangerousBreed() {
        return dangerousBreed;
    }
    public void setDangerousBreed(boolean dangerousBreed) {
        this.dangerousBreed = dangerousBreed;
        currentRate = dangerousBreed ? DANGEROUS_DAILY_RATE : NONDANGEROUS_DAILY_RATE;
    }

    public float getCurrentRate() {
        return currentRate;
    }

    //
    // Main methods
    //

    @Override
    public double calculateWeeklyFee() {
        return numOfDaysAttending() * getCurrentRate();
    }
}
