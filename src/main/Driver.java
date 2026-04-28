package main;

import controllers.PetsDayCareAPI;
import controllers.OwnerAPI;
import models.*;
import utils.DogBreedUtility;
import utils.ScannerInput;
import utils.Utilities;

import java.io.File;

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

        System.out.println("\nYour Pets Day Care '" + name + "' was created.");
        ScannerInput.readNextLine("\n[... Press any key to continue ...]");

        runMenu();
    }

    public int mainMenu(){
        return ScannerInput.readNextInt("""
                
                                Main Menu | Pets Day Care
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   ### Pets Day Care ###                             |
                |   [1] Admin Panel                                   |
                |                                                     |
                |   ### Pets ###                                      |
                |   [2] Edit Pet Information                          |
                |   [3] View Pet Information                          |
                |                                                     |
                |   ### Owners ###                                    |
                |   [4] Edit Owner Information                        |
                |   [5] View Owner Information                        |
                |                                                     |
                |   [0] Exit Application                              |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public int editPetsMenu(){
        return ScannerInput.readNextInt("""
                          Edit Pets Registry | Pets Day Care
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   ### GENERAL ###                                   |
                |   [1] Add a new Pet                                 |
                |   [2] Remove a Pet                                  |
                |   [3] Update a Pet                                  |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public int viewPetsMenu(){
        return ScannerInput.readNextInt("""
                        View Pets in Registry | Pets Day Care
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   ### GENERAL ###                                   |
                |   [1] List all Pets                                 |
                |   [2] List all Pets staying more than X days        |
                |                                                     |
                |   ### Owners ###                                    |
                |   [3] List Pets owned by a specific owner           |
                |                                                     |
                |   ### Dogs ###                                      |
                |   [4] List all Dogs                                 |
                |   [5] List all 'Dangerous' Dogs                     |
                |   [6] Find a specific dog                           |
                |                                                     |
                |   ### Cats ###                                      |
                |   [7] List all Cats                                 |
                |   [8] List all 'Indoor' Cats                        |
                |                                                     |
                |   ### Parrots ###                                   |
                |   [9] List all Parrots                              |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public int editOwnersMenu(){
        return ScannerInput.readNextInt("""
                          Edit Owners Registry | Pets Day Care
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   === GENERAL ===                                   |
                |   [1] Add a new Owner                               |
                |   [2] Remove an Owner                               |
                |   [3] Update an Owner                               |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public int viewOwnersMenu(){
        return ScannerInput.readNextInt("""
                        View Owners in Registry | Pets Day Care
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   === GENERAL ===                                   |
                |   [1] List all Owner                                |
                |   [2] List all Owners (names) that start with "..." |
                |                                                     |
                |   ### Search By ###                                 |
                |   [3] Find an Owner by name                         |
                |   [4] Find an Owner by index                        |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public int viewPetsDayCareMenu(){
        return ScannerInput.readNextInt("""
                               Configure | Pets Day Care
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   === GENERAL ===                                   |
                |   [1] Edit 'Pets Day Care' name                     |
                |   [2] Show Weekly Income                            |
                |   [3] Show Pets' average days stayed per week       |
                |                                                     |
                |   ### Saving/Loading ###                            |
                |   [4] Load Pets from File                           |
                |   [5] Save Pets to File                             |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public void runMenu(){
        int option = mainMenu();
        while(option != 0){
            System.out.println();

            switch(option){
                case 1 -> viewPetsDayCare(viewPetsDayCareMenu());
                case 2 -> editPets(editPetsMenu());
                case 3 -> viewPets(viewPetsMenu());
                case 4 -> editOwners(editOwnersMenu());
                case 5 -> viewOwners(viewOwnersMenu());
                default -> System.out.println(" - Invalid option, try again.");
            }

            option = mainMenu();
        }

        System.out.println("\nClosing application... goodbye!");
        System.exit(0);
    }

    public void editPets(int option){
        System.out.println();

        while(option != 0){
            switch(option){
                case 1 -> addPet(-1);
                case 2 -> removePet();
                case 3 -> updatePet();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = editPetsMenu();
        }
    }

    public void viewPets(int option){
        while(option != 0){
            switch(option){
                case 1 -> listAllPets();
                case 2 -> listAllPetsStayingMoreThanXDays();
                case 3 -> listAllPetsBySpecificOwner();
                case 4 -> listAllDogs();
                case 5 -> listAllDangerousDogs();
                case 6 -> findSpecificDog();
                case 7 -> listAllCats();
                case 8 -> listAllIndoorCats();
                case 9 -> listAllParrots();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = viewPetsMenu();
        }
    }

    public void editOwners(int option){
        while(option != 0){
            System.out.println();

            switch(option){
                case 1 -> addOwner();
                case 2 -> removeOwner();
                case 3 -> updateOwner();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = editOwnersMenu();
        }
    }

    public void viewOwners(int option){
        while(option != 0){
            System.out.println();

            switch(option){
                case 1 -> listAllOwners();
                case 2 -> listAllOwnersWithPrefix();
                case 3 -> findOwnerWithName();
                case 4 -> findOwnerAtIndex();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = viewOwnersMenu();
        }
    }

    private void viewPetsDayCare(int option){
        while(option != 0){
            System.out.println();

            switch(option){
                case 1 -> editPetsDayCareName();
                case 2 -> showWeeklyIncome();
                case 3 -> showPetsAverageDaysStayedPerWeek();
                case 4 -> loadFile();
                case 5 -> saveFile();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = editOwnersMenu();
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

    private Dog registerDog(String name, int age, Owner owner, int id){
        char sex = getSex();
        boolean isNeutered = getBooleanFromYesOrNo("Is this dog neutered? (Yes/No): ");
        double weight = getWeight();
        boolean isVaccinated = getBooleanFromYesOrNo("Is this dog vaccinated? (Yes/No): ");

        // dog specific variables
        boolean dangerousBreed = getBooleanFromYesOrNo("Is this dog a dangerous breed? (Yes/No): ");

        String breed = "";
        while(true){
            breed = ScannerInput.readNextLine("What breed of dog is this?: ");
            if(DogBreedUtility.checkBreed(breed)) {
                break;
            } else{
                System.out.println("The breed that was entered is not a valid breed.");
            }
        }

        return new Dog(name, age, owner, id, sex, isNeutered, weight, isVaccinated, dangerousBreed, breed);
    }

    private Cat registerCat(String name, int age, Owner owner, int id){
        char sex = getSex();
        boolean isNeutered = getBooleanFromYesOrNo("Is this cat neutered? (Yes/No): ");
        double weight = getWeight();
        boolean isVaccinated = getBooleanFromYesOrNo("Is this cat vaccinated? (Yes/No): ");

        // cat specific variables
        String favouriteToy = ScannerInput.readNextLine("What is this cat's favourite toy?: ");
        boolean indoorCat = getBooleanFromYesOrNo("Is this cat an indoor cat? (Yes/No): ");

        return new Cat(name, age, owner, id, sex, isNeutered, weight, isVaccinated, favouriteToy, indoorCat);
    }

    private Parrot registerParrot(String name, int age, Owner owner, int id){
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

        return new Parrot(name, age, owner, id, wingSpan, canFly, vocabularySize);
    }

    private Pet addPet(int id){
        // first add pet, then ask if there is an owner or not,
        // and if there is an owner, call the addOwner method

        String name = ScannerInput.readNextLine("Enter a name for this pet: ");
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
            case 1 -> newPet = registerDog(name, age, owner, id);
            case 2 -> newPet = registerCat(name, age, owner, id);
            case 3 -> newPet = registerParrot(name, age, owner, id);
        }

        // if id provided isn't -1, it means this pet is an updated version (and a duplicate for now)
        if(id != -1){
            System.out.println(petsDayCareAPI.addPet(newPet) ? "Pet " + name + " was successfully added." : "Pet " + name + " was not added due to an error.");
        }

        return newPet;
    }

    private void listInstructions(){
        ScannerInput.readNextLine("""
                
                ---------------------------------------------
                
                   A list of all pets currently registered
                     will be displayed with their index.
                
                   You will be prompted to choose an index
                      of the pet that you want to modify.
                
                ---------------------------------------------
                
                       [ Press any key to continue ]
                
                """);
    }

    private void removePet(){
        System.out.println("         Remove a Pet | Instructions");
        listInstructions();
        listAllPets();

        int targetIndex = -1;
        while(!petsDayCareAPI.isValidPetIndex(targetIndex)){
            targetIndex = ScannerInput.readNextInt("Enter the index of the pet you want to remove: ");
        }

        Pet removedPet = petsDayCareAPI.removePetAtPosition(targetIndex);

        System.out.println();

        if(removedPet == null){
            System.out.println("This pet could not be removed due to an error.");
        } else{
            System.out.println("The pet with the information below was successfully removed:\n");
            System.out.println(petsDayCareAPI.listPet(removedPet));
        }
    }

    private void updatePet(){
        System.out.println("         Update a Pet | Instructions");
        listInstructions();
        listAllPets();

        int targetIndex = -1;
        while(!petsDayCareAPI.isValidPetIndex(targetIndex)){
            targetIndex = ScannerInput.readNextInt("Enter the index of the pet you want to remove: ");
        }

        Pet petToUpdate = petsDayCareAPI.getPetByIndex(targetIndex);
        System.out.println();

        if(petToUpdate == null){
            System.out.println("The pet at index " + targetIndex + " could not be found due to an error.");
        } else{
            System.out.println("The pet at index " + targetIndex + " was found. These are its current properties:\n");
            System.out.println(petsDayCareAPI.listPet(petToUpdate));
            System.out.println();

            Pet updatedPet = addPet(petToUpdate.getId());

            // remove original pet as copy was already made
            Pet removedPet = petsDayCareAPI.removePetAtPosition(targetIndex);

            if(removedPet == null){
                System.out.println("This pet could not be updated, as the original pet wasn't able to be removed due to an error.");
            } else{
                petsDayCareAPI.setPetAtSpecificIndex(updatedPet, targetIndex);

                System.out.println("The pet at index " + targetIndex + " was updated, as shown below:\n");
                if(updatedPet instanceof Dog){
                    System.out.println(petsDayCareAPI.listDog(updatedPet));
                } else if(updatedPet instanceof Cat){
                    System.out.println(petsDayCareAPI.listCat(updatedPet));
                } else if(updatedPet instanceof Parrot){
                    System.out.println(petsDayCareAPI.listParrot(updatedPet));
                }
            }
        }
    }

    private void listAllPets(){
        System.out.println();
        System.out.println(petsDayCareAPI.listAllPets());
    }

    private void listAllPetsStayingMoreThanXDays(){
        int xDays = ScannerInput.readNextInt("To show all pets staying more than X days at the Pets Day Care,\nenter the number of days for X: ");
        System.out.println();
        System.out.println(petsDayCareAPI.listAllPetsThatStayMoreThanXDays(xDays));
    }

    private void listAllPetsBySpecificOwner(){
        String ownerName = "";

        while(!ownerAPI.isValidOwner(ownerName)){
            ownerName = ScannerInput.readNextLine("Enter the name of an owner to see their registered pets: ");
        }

        System.out.println();
        System.out.println(petsDayCareAPI.listAllPetsByOwner(ownerName));
    }

    private void listAllDogs(){
        System.out.println();
        System.out.println(petsDayCareAPI.listAllDogs());
    }

    private void listAllDangerousDogs(){
        System.out.println();
        System.out.println(petsDayCareAPI.listAllDangerousDogs());
    }

    private void findSpecificDog(){
        String ownerName = "";
        while(true){
            ownerName = ScannerInput.readNextLine("Enter the name of an owner to see their registered pets: ");
            if(ownerAPI.isValidOwner(ownerName)){
               break;
            } else{
                System.out.println("The owner '" + ownerName + "' is not registered.");
                // this is to prevent soft-locks, as if there are no owners at all the user cannot exit this loop
                if(getBooleanFromYesOrNo("Do you want to cancel this search?: ")) break;
            }
        }

        String breed = "";
        while(true){
            breed = ScannerInput.readNextLine("Enter the breed of this dog: ");
            if(DogBreedUtility.checkBreed(breed)) {
                break;
            } else{
                System.out.println("The breed that was entered is not a valid breed.");
            }
        }

        int age = -1;
        while(age < 0){
            age = ScannerInput.readNextInt("Enter the age of this dog: ");
        }

        Pet thisDog = petsDayCareAPI.findDogByOwnerAndBreedAndAge(ownerName, breed, age);

        System.out.println();

        if(thisDog == null){
            System.out.println("A dog with these parameters was not found.");
        } else{
            thisDog = (Dog) thisDog;

            System.out.println("A dog with these parameters was found.");
            System.out.println(thisDog);
        }
    }

    private void listAllCats(){
        System.out.println();
        System.out.println(petsDayCareAPI.listAllCats());
    }

    private void listAllIndoorCats(){
        System.out.println();
        System.out.println(petsDayCareAPI.listAllIndoorCats());
    }

    private void listAllParrots(){
        System.out.println();
        System.out.println(petsDayCareAPI.listAllParrots());
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
