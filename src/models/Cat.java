package models;

import utils.CatToyUtility;
import utils.Utilities;

public class Cat extends Mammal {
    private String favouriteToy = "None";
    private boolean indoorCat = true;

    private final float BASE_RATE = 20;

    public Cat(String name, int age, Owner owner, int id, char sex, boolean neutered, double weight, boolean vaccinated, String favouriteToy, boolean indoorCat){
        super(name, age, owner, id, sex, neutered, weight, vaccinated);
        this.favouriteToy = favouriteToy;
        this.indoorCat = indoorCat;
    }

    @Override
    public String toString(){
        return super.toString() + " | Favourite Toy: " + getFavouriteToy()
                + " | Indoor Cat: " + Utilities.booleanToYN(isIndoorCat())
                + " | Weekly Fee: " + calculateWeeklyFee();
    }

    //
    // Getters and Setters
    //

    public boolean isIndoorCat() {
        return indoorCat;
    }
    public void setIndoorCat(boolean indoorCat) {
        this.indoorCat = indoorCat;
    }

    public String getFavouriteToy() {
        return favouriteToy;
    }
    public void setFavouriteToy(String favouriteToy) {
        this.favouriteToy = favouriteToy;
    }

    //
    // Main methods
    //

    @Override
    public double calculateWeeklyFee() {
        // if indoor cat then rate is 25 else 20
        float thisRate = isIndoorCat() ? BASE_RATE + 5 : BASE_RATE;
        return numOfDaysAttending() * thisRate;
    }
}
