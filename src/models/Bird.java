package models;

import utils.Utilities;

import java.util.Objects;

public abstract class Bird extends Pet {
    // wingspan is in centimetres
    private double wingSpan = 3;
    private boolean canFly = false;

    public Bird(String name, int age, Owner owner, int id, double wingSpan, boolean canFly){
        super(name, age, owner, id);
        if(Utilities.validRange(wingSpan, 3, 155.5)){
            this.wingSpan = wingSpan;
        }
        this.canFly = canFly;
    }

    @Override
    public String toString(){
        return super.toString() + " | Wing Span: " + wingSpan
                + " | Can Fly: " + Utilities.booleanToYN(canFly);
    }

    //
    // Getters and Setters
    //

    public double getWingSpan() {
        return wingSpan;
    }
    public void setWingSpan(double wingSpan) {
        this.wingSpan = wingSpan;
    }

    public boolean canFly() {
        return canFly;
    }
    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    //
    // Main methods
    //

    @Override
    public int hashCode() {
        return Objects.hash(wingSpan, canFly);
    }

    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Bird bird = (Bird) object;
        return getId() == bird.getId() && Objects.equals(getName(), bird.getName());
    }
}
