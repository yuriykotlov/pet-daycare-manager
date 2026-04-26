package models;

public abstract class Mammal extends Pet {
    private char sex;
    private boolean neutered;
    private double weight;
    private boolean vaccinated;

    public Mammal(String name, int age, Owner owner, int id, char sex, boolean neutered, double weight, boolean vaccinated){
        super(name, age, owner, id);
        this.sex = sex;
        this.neutered = neutered;
        this.weight = weight;
        this.vaccinated = vaccinated;
    }

    @Override
    public String toString(){
        return "";
    }

    //
    // Getters and Setters
    //

    public char getSex() {
        return sex;
    }
    public void setSex(char sex) {
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
        this.weight = weight;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }
    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
}
