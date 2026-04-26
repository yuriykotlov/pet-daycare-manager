package models;

public class Cat extends Mammal {
    private String favouriteToy;
    private boolean indoorCat;

    public Cat(String name, int age, Owner owner, int id, char sex, boolean neutered, double weight, boolean vaccinated, String favouriteToy, boolean indoorCat){
        super(name, age, owner, id, sex, neutered, weight, vaccinated);
        this.favouriteToy = favouriteToy;
        this.indoorCat = indoorCat;
    }

    @Override
    public String toString(){
        return "";
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
        return super.calculateWeeklyFee();
    }
}
