package models;

import utils.Utilities;

public abstract class Mammal extends Pet {
    private char sex = 'M';
    private boolean neutered = false;
    // weight is in kilograms
    private double weight = 2;
    private boolean vaccinated = false;

    public Mammal(String name, int age, Owner owner, int id, char sex, boolean neutered, double weight, boolean vaccinated){
        super(name, age, owner, id);

        if(sex != 'M' && sex != 'F'){
            // Unidentified sex
            sex = 'U';
        }
        this.sex = sex;
        this.neutered = neutered;

        // range of 2 to 200 kg
        if(Utilities.validRange(weight, 2, 200)){
            this.weight = weight;
        }

        this.vaccinated = vaccinated;
    }

    @Override
    public String toString(){
        return super.toString() + " | Sex: " + getSex()
                + " | Neutered: " + isNeutered()
                + " | Weight: " + getWeight()
                + "kg | Vaccinated: " + isVaccinated();
    }

    //
    // Getters and Setters
    //

    public char getSex() {
        return sex;
    }
    public void setSex(char sex) {
        if(sex != 'M' && sex != 'F'){
            // Unidentified sex
            sex = 'U';
        }
        this.sex = sex;
    }

    public boolean isNeutered() {
        return neutered;
    }
    public void setNeutered(boolean neutered) {
        this.neutered = neutered;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        if(Utilities.validRange(weight, 2, 200)){
            this.weight = weight;
        }
    }

    public boolean isVaccinated() {
        return vaccinated;
    }
    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}
