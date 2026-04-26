package models;

import jdk.jshell.execution.Util;
import utils.Utilities;

public abstract class Pet {
    private String name;
    private int age;
    private Owner owner;
    private int id;

    // days of the week; Monday through Sunday
    private boolean[] daysAttending = {
            false, false, false, false, false, false, false
    };

    private static int nextId = 1000;

    public Pet(String name, int age, Owner owner, int id){
        if(!Utilities.validStringlength(name, 30)){
            name = Utilities.truncateString(name, 30);
        }
        this.name = name;

        if(age < 0){
            age = 0;
        }
        this.age = age;

        // if owner is not of type Owner
        if(!(owner instanceof Owner)){
            owner = null;
        }
        this.owner = owner;

        // id of usually -1 or less means to set the id automatically
        if(id < 0){
            id = nextId;
            // increment nextId unless user specifically selects an id
            nextId += 1;
        }
        this.id = id;
    }

    @Override
    public String toString(){
        return "Name: " + getName()
                +" | Age: " + getAge()
                + " | Owner: " + getOwner().getName()
                + " | Id: " + getId();
    }

    //
    // Getters and Setters
    //

    public int getId() {
        return id;
    }
    public void setId(int id) {
        if(id >= 0){
            this.id = id;
        }
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        if(owner instanceof Owner){
            this.owner = owner;
        }
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        if(age >= 0){
            this.age = age;
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(Utilities.validStringlength(name, 30)){
            this.name = name;
        }
    }

    public boolean[] getDaysAttending() {
        return daysAttending;
    }
    public void setDaysAttending(boolean[] daysAttending) {
        this.daysAttending = daysAttending;
    }

    //
    // Main methods
    //

    public void checkIn(int dayIndex){
        if(Utilities.validRange(dayIndex, 0, 7)){
            getDaysAttending()[dayIndex] = true;
        }
    }

    public void checkOut(int dayIndex){
        if(Utilities.validRange(dayIndex, 0, 7)){
            getDaysAttending()[dayIndex] = false;
        }
    }

    public void initName(String name){

    }

    public abstract double calculateWeeklyFee();

    public int numOfDaysAttending(){
        int daysAttendingCount = 0;
        for(boolean day : this.getDaysAttending()){
            if(day == true){
                daysAttendingCount++;
            }
        }
        return daysAttendingCount;
    }
}