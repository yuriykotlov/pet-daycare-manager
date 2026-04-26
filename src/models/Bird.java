package models;

import java.util.Objects;

public abstract class Bird extends Pet {
    private double wingSpan;
    private boolean canFly;

    public Bird(String name, int age, Owner owner, int id, double wingSpan, boolean canFly){
        super(name, age, owner, id);
        this.wingSpan = wingSpan;
        this.canFly = canFly;
    }

    public String toString(){
        return "";
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

    public boolean isCanFly() {
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
