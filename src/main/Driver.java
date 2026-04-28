package main;

import controllers.PetsDayCareAPI;
import controllers.OwnerAPI;
import models.*;
import utils.DogBreedUtility;
import utils.ScannerInput;
import utils.Utilities;

import java.io.File;
import java.util.Scanner;

public class Driver {
    //TODO Define  objects of the PetDayCareAPI and OwnerAPI here.  They  should be declared private.
    // And other fields as per spec
    private static OwnerAPI ownerAPI;
    private static PetsDayCareAPI petsDayCareAPI;

    public static void main(String[] args) {
        new Driver();
    }

    public Driver() {
        File file = new File("Pets.xml");
        ownerAPI = new OwnerAPI(file);

        String name = ScannerInput.readNextLine("Enter a name for your Pets Day Care: ");
        int maxNumberOfPets = ScannerInput.readNextInt("Enter a maximum number of pets allowed in your Pets Day Care: ");

        petsDayCareAPI = new PetsDayCareAPI(name, maxNumberOfPets, file);

        runMenu();
    }

    public int mainMenu(){
        return ScannerInput.readNextInt("""
                
                Main Menu | Pets Day Care
                
                ### Pets ###
                [1] Edit Pet Information
                [2] View Pet Information
                
                ### Owners ###
                [3] Edit Owner Information
                [4] View Owner Information
                
                ### Pets Day Care ###
                [5] Edit Name
                [6] Show Weekly Income
                [7] Get Pets' average days stayed per week
                
                ### Saving/Loading ###
                [8] Load Pets from File
                [9] Save Pets to File
                
                [0] Exit Application
                
                \n
                """);
    }

    public int editPetsMenu(){
        return ScannerInput.readNextInt("""
                
                Edit Pets Registry | Pets Day Care
                
                [1] Add a new Pet
                [2] Remove a Pet
                [3] Update a Pet
                
                [0] Back
                
                \n
                """);
    }

    public int viewPetsMenu(){
        return ScannerInput.readNextInt("""
                
                View Pets in Registry | Pets Day Care
                
                [0] Back
                
                === GENERAL ===
                [1] List all Pets
                [2] List all Pets staying more than X days
                
                ### Owners ###
                [3] List Pets owned by a specific owner
                
                ### Dogs ###
                [4] List all Dogs
                [5] List all 'Dangerous' Dogs
                [6] Find a specific dog
                
                ### Cats ###
                [7] List all Cats
                [8] List all 'Indoor' Cats
                
                ### Parrots ###
                [9] List all Parrots
                
                \n
                """);
    }

    public int editOwnersMenu(){
        return ScannerInput.readNextInt("""
                
                Edit Owners Registry | Pets Day Care
                
                [1] Add a new Owner
                [2] Remove an Owner
                [3] Update an Owner
                
                [0] Back
                
                \n
                """);
    }

    public int viewOwnersMenu(){
        return ScannerInput.readNextInt("""
                
                View Owners in Registry | Pets Day Care
                
                [0] Back
                
                === GENERAL ===
                [1] List all Owner
                [2] List all Owners (names) that start with "..."
                
                ### Filter ###
                [3] Find an Owner by name
                [4] Find an Owner by index
                
                \n
                """);
    }

    public void runMenu(){
        int option = mainMenu();
        while(option != 0){
            switch(option){
                case 1: editPets(editPetsMenu());
                case 2: viewPets(viewPetsMenu());
                case 3: editOwners(editOwnersMenu());
                case 4: viewOwners(viewOwnersMenu());
                case 5: editPetsDayCareName();
                case 6: showWeeklyIncome();
                case 7: showPetsAverageDaysStayedPerWeek();
                case 8: loadFile();
                case 9: saveFile();
                default: System.out.println(" - Invalid option, try again.");
            }

            option = mainMenu();
        }
    }

    public void editPets(int option){
        while(option != 0){
            switch(option){
                case 1: addPet();
                case 2: removePet();
                case 3: updatePet();
                default: System.out.println(" - Invalid option, try again.");
            }
            option = editPetsMenu();
        }
    }

    public void viewPets(int option){
        while(option != 0){
            switch(option){
                case 1: listAllPets();
                case 2: listAllPetsStayingMoreThanXDays();
                case 3: listAllPetsBySpecificOwner();
                case 4: listAllDogs();
                case 5: listAllDangerousDogs();
                case 6: findSpecificDog();
                case 7: listAllCats();
                case 8: listAllIndoorCats();
                case 9: listAllParrots();
                default: System.out.println(" - Invalid option, try again.");
            }
            option = viewPetsMenu();
        }
    }

    public void editOwners(int option){
        while(option != 0){
            switch(option){
                case 1: addOwner();
                case 2: removeOwner();
                case 3: updateOwner();
                default: System.out.println(" - Invalid option, try again.");
            }
            option = editOwnersMenu();
        }
    }

    public void viewOwners(int option){
        while(option != 0){
            switch(option){
                case 1: listAllOwners();
                case 2: listAllOwnersWithPrefix();
                case 3: findOwnerWithName();
                case 4: findOwnerAtIndex();
                default: System.out.println(" - Invalid option, try again.");
            }
            option = viewOwnersMenu();
        }
    }

    //
    //  Pets
    //

    private boolean getBooleanFromYesOrNo(String prompt){
        String answer = "?";
        while(true){
            if(!prompt.isEmpty()){
                if(answer.substring(0, 1).toLowerCase().equals("y")){
                    return true;
                } else if(answer.substring(0, 1).toLowerCase().equals("n")){
                    return false;
                }
            }
            answer = ScannerInput.readNextLine(prompt);
        }
    }

    private double getWeight(){
        double weight = 0;
        while(!Utilities.validRange(weight, 2, 200)){
            weight = ScannerInput.readNextDouble("What is this pet's weight?: ");
        }
        return weight;
    }

    private char getSex(){
        char sex = ScannerInput.readNextChar("What is this pet's sex?: ");
        if(sex != 'M' && sex != 'F'){
            // Unidentified sex
            sex = 'U';
        }
        return sex;
    }

    private void addPet(){
        // first add pet, then ask if there is an owner or not,
        // and if there is an owner, call the addOwner method

        String name = ScannerInput.readNextLine("\nEnter a name for this pet: ");
        int age = ScannerInput.readNextInt("Enter an age for this pet: ");

        int animalOption = -1;
        // make sure a valid option is being chosen
        while(animalOption < 1 || animalOption > 3){
            animalOption = ScannerInput.readNextInt("Is this pet a [1] dog, [2] cat, or [3] parrot?: ");
        }

        boolean addOwner = getBooleanFromYesOrNo("Does this pet have an owner? (Yes/No): ");
        Owner owner = null;

        if(addOwner){
            owner = addOwner();
        }

        Pet newPet = null;

        switch (animalOption){
            case 1: {
                // Dog
                char sex = getSex();
                boolean isNeutered = getBooleanFromYesOrNo("Is this dog neutered? (Yes/No): ");
                double weight = getWeight();
                boolean isVaccinated = getBooleanFromYesOrNo("Is this dog vaccinated? (Yes/No): ");

                // dog specific variables
                boolean dangerousBreed = getBooleanFromYesOrNo("Is this dog a dangerous breed? (Yes/No): ");

                String breed = "";
                while(!DogBreedUtility.checkBreed(breed)){
                    breed = ScannerInput.readNextLine("What breed is this dog?: ");
                }

                newPet = new Dog(name, age, owner, -1, sex, isNeutered, weight, isVaccinated, dangerousBreed, breed);
            }
            case 2: {
                // Cat
                char sex = getSex();
                boolean isNeutered = getBooleanFromYesOrNo("Is this cat neutered? (Yes/No): ");
                double weight = getWeight();
                boolean isVaccinated = getBooleanFromYesOrNo("Is this cat vaccinated? (Yes/No): ");

                // cat specific variables
                String favouriteToy = ScannerInput.readNextLine("What is this cat's favourite toy?: ");
                boolean indoorCat = getBooleanFromYesOrNo("Is this cat an indoor cat? (Yes/No): ");

                newPet = new Cat(name, age, owner, -1, sex, isNeutered, weight, isVaccinated, favouriteToy, indoorCat);
            }
            case 3: {
                // Parrot
                double wingSpan = 0;
                while(!Utilities.validRange(wingSpan, 3, 155.5)){
                    wingSpan = ScannerInput.readNextDouble("What is this parrot's wingspan?: ");
                }

                boolean canFly = getBooleanFromYesOrNo("Can this parrot fly? (Yes/No): ");

                // parrot specific variable
                int vocabularySize = -1;
                while(vocabularySize < 0){
                    vocabularySize = ScannerInput.readNextInt("What is this parrot's vocabulary size?: ");
                }

                newPet = new Parrot(name, age, owner, -1, wingSpan, canFly, vocabularySize);
            }
        }

        System.out.println(petsDayCareAPI.addPet(newPet) ? "Pet " + name + " was successfully added." : "Pet " + name + " was not added due to an error.");
        ScannerInput.readNextLine("\n[... Press any key to continue ...]");
    }

    private void removePet(){

    }

    private void updatePet(){

    }

    private void listAllPets(){

    }

    private void listAllPetsStayingMoreThanXDays(){

    }

    private void listAllPetsBySpecificOwner(){

    }

    private void listAllDogs(){

    }

    private void listAllDangerousDogs(){

    }

    private void findSpecificDog(){

    }

    private void listAllCats(){

    }

    private void listAllIndoorCats(){

    }

    private void listAllParrots(){

    }

    //
    //  Owners
    //

    private Owner addOwner(){
        return null;
    }

    private void removeOwner(){

    }

    private void updateOwner(){

    }

    private void listAllOwners(){

    }

    private void listAllOwnersWithPrefix(){

    }

    private void findOwnerWithName(){

    }

    private void findOwnerAtIndex(){

    }

    //
    //  Pets Day Care
    //

    private void editPetsDayCareName(){

    }

    private void showWeeklyIncome(){

    }

    private void showPetsAverageDaysStayedPerWeek(){

    }

    private void loadFile(){

    }

    private void saveFile(){

    }

    //TODO Refer to the tutors instructions for building this class and for the menu.  You are free to deviate in any way
    //     from the Driver menu that is in the tutors instructions, once you have these included:
    //     (with tests still compiling)
    //       - CRUD on PetDayCare
    //       - Search facility (for Pets and Owners)
    //       - Reports
    //       - Persistence
    // Note:  This is the ONLY class that can talk to the user i.e. have System.out.print and Scanner reads in it.

    //----------------------------------------------------------------------------
    // Private methods for displaying the menu and processing the selected options
    //----------------------------------------------------------------------------


    //------------------------------------
    // Private methods for CRUD on Dog
    //------------------------------------


    //-----------------------------------------------------------------
    //  Private methods for Search facility
    //-----------------------------------------------------------------


    //-----------------------------
    //  Private methods for Reports
    // ----------------------------


    //---------------------------------
    //  Private methods for Persistence
    // --------------------------------


}
