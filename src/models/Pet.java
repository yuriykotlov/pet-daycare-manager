package models;

public class Pet {
    private String name;
    private int age;
    private Owner owner;
    private int id;
    private boolean[] daysAttending;

    public Pet(String name, int age, Owner owner, int id){
        this.name = name;
        this.age = age;
        this.owner = owner;
        this.id = id;
    }

    @Override
    public String toString(){
        return "";
    }

    //
    // Getters and Setters
    //

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public void checkOut(int id){

    }

    public void checkIn(int id){

    }

    public void initName(String name){

    }

    public double calculateWeeklyFee(){
        return 0.0;
    }

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