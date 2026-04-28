package models;


import utils.Utilities;

import java.util.Objects;
// this class is fully functional
public class Owner {
    private int id;// 3 digits - default 100
    private String name; // <= 30
    private String phoneNumber; // should start with 0, have no spaces and have 10 digits

    private static int nextId = 100;

    public Owner(int id, String name, String phoneNumber) {
        setId(id);
        initName(name);
        setPhoneNumber(phoneNumber);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(int id) {
        if(id != -1){
            if (id >=100 && id <= 999){
                this.id = id;
            }
        } else {
            this.id = nextId;
            nextId++;
        }
    }
    public void initName(String name) {
        this.name = (name.length()<= 30?
                name :
                name.substring(0,30));
    }


    public void setName(String name) {
        if (name.length() <=30)  this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (Utilities.onlyContainsNumbers(phoneNumber))
            this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return getId() == owner.getId() && Objects.equals(getName(), owner.getName());
    }

    @Override
    public String toString() {
        return "Id: " + id + "| Name: " + name + "| Phone: " + phoneNumber;

    }}

