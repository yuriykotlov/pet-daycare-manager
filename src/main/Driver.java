package main;

import controllers.PetsDayCareAPI;
import controllers.OwnerAPI;
import models.*;
import utils.DogBreedUtility;
import utils.ScannerInput;
import utils.Utilities;
import utils.CatToyUtility;

import java.io.File;
import java.net.URISyntaxException;

public class Driver {
    //TODO Define  objects of the PetDayCareAPI and OwnerAPI here.  They  should be declared private.
    // And other fields as per spec
    private static OwnerAPI ownerAPI;
    private static PetsDayCareAPI petsDayCareAPI;

    public static void main(String[] args) {
        new Driver();
    }

    public static File resolveDataFile(String filename) {
        try {
            File appDir = new File(Driver.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParentFile();
            return new File(appDir, filename);
        } catch (URISyntaxException | NullPointerException e) {
            // Fallback: couldn't determine app location, use current working directory
            return new File(filename);
        }
    }

    public Driver() {
        File file = resolveDataFile("Pets.xml");
        ownerAPI = new OwnerAPI(file);

        String name = ScannerInput.readNextLine("Enter a name for your Pets Day Care: ");
        int maxNumberOfPets = ScannerInput.readNextInt("Enter a maximum number of pets allowed in your Pets Day Care: ");

        petsDayCareAPI = new PetsDayCareAPI(name, maxNumberOfPets, file);

        System.out.println("\nYour Pets Day Care '" + name + "' was created.");
        ScannerInput.readNextLine("\n[... Press any key to continue ...]");

        runMenu();
    }

    // -----------------------------------------
    //  Displayed Menu Methods
    // -----------------------------------------

    public int mainMenu(){
        return ScannerInput.readNextInt("""
                
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |               Main Menu | Pets Day Care             |
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
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |         Edit Pets Registry | Pets Day Care          |
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
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |       View Pets in Registry | Pets Day Care         |
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
                |   [10] List all Parrots with X vocabulary level     |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public int editOwnersMenu(){
        return ScannerInput.readNextInt("""
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |         Edit Owners Registry | Pets Day Care        |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   ### GENERAL ###                                   |
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
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |       View Owners in Registry | Pets Day Care       |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   ### GENERAL ###                                   |
                |   [1] List all Owner                                |
                |   [2] List all Owners (names) that start with "..." |
                |                                                     |
                |   ### Search By ###                                 |
                |   [3] Find an Owner by Name                         |
                |   [4] Find an Owner by Id                           |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    public int viewPetsDayCareMenu(){
        return ScannerInput.readNextInt("""
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |              Configure | Pets Day Care              |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                |                                                     |
                |   ### GENERAL ###                                   |
                |   [1] Edit 'Pets Day Care' name                     |
                |   [2] Edit maximum amount of pets allowed           |
                |                                                     |
                |   ### Statistics ###                                |
                |   [3] Show Weekly Income                            |
                |   [4] Show Pets' average days stayed per week       |
                |                                                     |
                |   ### Saving/Loading ###                            |
                |   [5] Load Pets from File                           |
                |   [6] Save Pets to File                             |
                |                                                     |
                |   [0] Back                                          |
                |                                                     |
                | + + + + + + + + + + + + + + + + + + + + + + + + + + |
                """);
    }

    // Display Method for CRUD actions (specifically updating or removing)
    private void listInstructions(){
        ScannerInput.readNextLine("""
                
                ----------------------------------------------
                
                 A list of all instances currently registered
                     will be displayed with their index.
                
                   You will be prompted to choose an index
                      of the pet that you want to modify.
                
                ----------------------------------------------
                
                       [ Press any key to continue ]
                
                """);
    }

    // -----------------------------------------
    //  Input into Displayed Menu Methods
    // -----------------------------------------

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

    // Pets

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
                case 10 -> listAllParrotsWithXVocabularyLevel();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = viewPetsMenu();
        }
    }

    // Owners

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
                case 4 -> findOwnerWithId();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = viewOwnersMenu();
        }
    }

    // Pets Day Care

    private void viewPetsDayCare(int option){
        while(option != 0){
            System.out.println();

            switch(option){
                case 1 -> editPetsDayCareName();
                case 2 -> editMaximumPetsAllowed();
                case 3 -> showWeeklyIncome();
                case 4 -> showPetsAverageDaysStayedPerWeek();
                case 5 -> loadFile();
                case 6 -> saveFile();
                default -> System.out.println(" - Invalid option, try again.");
            }

            ScannerInput.readNextLine("\n[... Press any key to continue ...]\n");

            option = viewPetsDayCareMenu();
        }
    }

    // -----------------------------------------
    //  Pet Methods
    // -----------------------------------------

    // I changed to addPet method from void to Pet to make it easier when updating a pet
    private Pet addPet(int id){
        if(id == -1 && petsDayCareAPI.getPets().size() == petsDayCareAPI.getMaxNumberOfPets()){
            System.out.println("The maximum pet limit was reached (" + petsDayCareAPI.getMaxNumberOfPets()
                    + ").\nTry updating the maximum amount of pets allowed.");
            return null;
        }

        // first add pet, then ask if there is an owner or not,
        // and if there is an owner, call the addOwner method

        String name = ScannerInput.readNextLine("Enter a name for this pet: ");
        int age = ScannerInput.readNextInt("Enter an age for this pet: ");

        // make sure a valid option is being chosen
        int animalOption = -1;
        while(animalOption < 1 || animalOption > 3){
            animalOption = ScannerInput.readNextInt("Is this pet a [1] dog, [2] cat, or [3] parrot?: ");
        }

        // give option whether to add an owner or not (this is why I made addOwner return type of Owner)
        boolean addOwner = getBooleanFromYesOrNo("Does this pet have an owner? (Yes/No): ");
        Owner owner = null;

        if(addOwner){
            owner = addOwner();
        }

        // register new pet corresponding to pet type
        Pet newPet = null;
        switch (animalOption){
            case 1 -> newPet = registerDog(name, age, owner, id);
            case 2 -> newPet = registerCat(name, age, owner, id);
            case 3 -> newPet = registerParrot(name, age, owner, id);
        }

        // set days attending
        System.out.println("\n\t### Days Attending ###");
        for(int i = 0; i < newPet.getDaysAttending().length; i++){
            newPet.updateSpecificDayAttending(
                    i,
                    getBooleanFromYesOrNo("Is '" + name + "' staying on " + newPet.DAYS[i] + "? (Yes/No): ")
            );
        }

        // if id is -1 then let the new pet be added to the arraylist automatically
        if(id == -1){
            System.out.println(petsDayCareAPI.addPet(newPet) ? "Pet " + name + " was successfully added." : "Pet '" + name + "' was not added due to an error.");
        }

        return newPet;
    }

    private void removePet(){
        // display menu with instructions
        System.out.println("         Remove a Pet | Instructions");
        listInstructions();
        System.out.println(petsDayCareAPI.listAllPets());

        // init targetIndex as out of bounds so that while loop is accessed
        // the reason why I do this is to not have two scannerInput reads in
        // init variable and while loop, I just find that it looks better this way
        int targetIndex = petsDayCareAPI.getPets().size() + 1;
        while(targetIndex != -1 && !petsDayCareAPI.isValidPetIndex(targetIndex)){
            targetIndex = ScannerInput.readNextInt("Enter the index of the pet you want to remove, or -1 to cancel: ");
        }

        // option to cancel removing pet
        if(targetIndex == -1){
            System.out.println("Removing pet cancelled.");
            return;
        }

        Pet removedPet = petsDayCareAPI.removePetAtPosition(targetIndex);
        System.out.println();

        // removing pet result
        if(removedPet == null){
            System.out.println("This pet could not be removed due to an error.");
        } else{
            System.out.println("The pet with the information below was successfully removed:\n");
            System.out.println(petsDayCareAPI.listPet(removedPet));
        }
    }

    private void updatePet(){
        // display menu with instructions
        System.out.println("         Update a Pet | Instructions");
        listInstructions();
        System.out.println(petsDayCareAPI.listAllPets());

        // refer to comment at the start of removePet method
        int targetIndex = petsDayCareAPI.getPets().size() + 1;
        while(targetIndex != -1 && !petsDayCareAPI.isValidPetIndex(targetIndex)){
            targetIndex = ScannerInput.readNextInt("Enter the index of the pet you want to update, or -1 to cancel: ");
        }

        // option to cancel removing pet
        if(targetIndex == -1){
            System.out.println("Updating pet cancelled.");
            return;
        }

        Pet petToUpdate = petsDayCareAPI.getPetByIndex(targetIndex);
        System.out.println();

        // update pet result
        if(petToUpdate == null){
            System.out.println("The pet at index " + targetIndex + " could not be found due to an error.");
        } else{
            System.out.println("The pet at index " + targetIndex + " was found. These are its current properties:\n");
            System.out.println(petsDayCareAPI.listPet(petToUpdate));
            System.out.println();

            Pet updatedPet = addPet(petToUpdate.getId());

            // update the target pet with the new pet variables
            updatedPet = petsDayCareAPI.updatePet(targetIndex, updatedPet);

            // update pet result
            if(updatedPet == null){
                System.out.println("This pet could not be updated due to an error.");
            } else{
                // display new pet properties according to class
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

    // General listing (All pets, dogs, cats, parrots)

    // always providing an option to show the whole list of pets or not,
    // with showing the number of objects in the list first

    private void listAllPets(){
        System.out.println();
        System.out.println("There are " + petsDayCareAPI.numberOfPets() + " pet(s) in the registry");

        if(getBooleanFromYesOrNo("Do you want to see the full list of pets? (Yes/No): ")){
            System.out.println(petsDayCareAPI.listAllPets());
        }
    }

    private void listAllDogs(){
        System.out.println();
        System.out.println("There are " + petsDayCareAPI.numberOfDogs() + " dog(s) in the registry");

        if(getBooleanFromYesOrNo("Do you want to see the full list of dogs? (Yes/No): ")){
            System.out.println(petsDayCareAPI.listAllDogs());
        }
    }

    private void listAllCats(){
        System.out.println();
        System.out.println("There are " + petsDayCareAPI.numberOfCats() + " cat(s) in the registry");

        if(getBooleanFromYesOrNo("Do you want to see the full list of cats? (Yes/No): ")){
            System.out.println(petsDayCareAPI.listAllCats());
        }
    }

    private void listAllParrots(){
        System.out.println();
        System.out.println("There are " + petsDayCareAPI.numberOfParrots() + " parrot(s) in the registry");

        if(getBooleanFromYesOrNo("Do you want to see the full list of parrots? (Yes/No): ")){
            System.out.println(petsDayCareAPI.listAllParrots());
        }
    }

    // Specific listing (depending on certain variables)

    private void listAllDangerousDogs(){
        System.out.println();
        System.out.println("There are " + petsDayCareAPI.numberOfDangerousDogs() + " (dangerous) dog(s) in the registry");

        if(getBooleanFromYesOrNo("Do you want to see the full list of (dangerous) dogs? (Yes/No): ")){
            System.out.println(petsDayCareAPI.listAllDangerousDogs());
        }
    }

    private void listAllParrotsWithXVocabularyLevel(){
        System.out.println();
        int vocabularySize = ScannerInput.readNextInt("Enter the vocabulary size, which will determine a level,\nthat you want to sort by: ");

        System.out.println();
        System.out.println("There are " + petsDayCareAPI.numberOfParrotsByVocabularySize(vocabularySize) + " parrot(s) in the registry");

        if(getBooleanFromYesOrNo("Do you want to see the full list of parrots? (Yes/No): ")){
            System.out.println(petsDayCareAPI.listParrotsByVocabularySize(vocabularySize));
        }
    }

    private void listAllIndoorCats(){
        System.out.println();
        System.out.println("There are " + petsDayCareAPI.numberOfIndoorCats() + " (indoor) cat(s) in the registry");

        if(getBooleanFromYesOrNo("Do you want to see the full list of (indoor) cats? (Yes/No): ")){
            System.out.println(petsDayCareAPI.listAllIndoorCats());
        }
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

    private void findSpecificDog(){
        // find owner
        String ownerName = "";
        while(true){
            ownerName = ScannerInput.readNextLine("Enter the name of an owner to see their registered pets: ");
            // see if owner is actually in the array
            if(ownerAPI.isValidOwner(ownerName)){
               break;
            } else{
                System.out.println("The owner '" + ownerName + "' is not registered.");
                // this is to prevent soft-locks, as if there are no owners at all the user cannot exit this loop
                if(getBooleanFromYesOrNo("Do you want to cancel this search?: ")) break;
            }
        }

        // find breed
        String breed = "";
        while(true){
            breed = ScannerInput.readNextLine("Enter the breed of this dog: ");
            // check if breed is valid
            if(DogBreedUtility.checkBreed(breed)) {
                break;
            } else{
                System.out.println("The breed that was entered is not a valid breed.");
            }
        }

        // just make sure age is not less than 0
        int age = -1;
        while(age < 0){
            age = ScannerInput.readNextInt("Enter the age of this dog: ");
        }

        // try to find dog
        Pet thisDog = petsDayCareAPI.findDogByOwnerAndBreedAndAge(ownerName, breed, age);
        System.out.println();

        // find dog result
        if(thisDog == null){
            System.out.println("A dog with these parameters was not found.");
        } else{
            thisDog = (Dog) thisDog;

            System.out.println("A dog with these parameters was found.");
            System.out.println(thisDog);
        }
    }

    // -----------------------------------------
    //  Owner Methods
    // -----------------------------------------

    // Originally this method returns void, however I found that making it
    // return type of Owner allowed for more flexibility in choosing to add/not to add
    // an owner to a specified pet instance.
    private Owner addOwner(){
        System.out.println("\n\t### Owner ###");
        String name = ScannerInput.readNextLine("What is this owner's name?: ");
        String phoneNumber = ScannerInput.readNextLine("What is this owner's phone number? (Numbers only): ");

        // make sure there are no letters
        while(!Utilities.onlyContainsNumbers(phoneNumber)){
            System.out.println("The phone number provided has letters which are not allowed.");
            phoneNumber = ScannerInput.readNextLine("What is this owner's phone number? (Numbers only): ");
        }

        System.out.println();

        // add owner to arraylist and return it
        Owner owner = new Owner(-1, name, phoneNumber);
        ownerAPI.addOwner(owner);

        return owner;
    }

    private void removeOwner(){
        System.out.println("      Remove an Owner | Instructions");
        listInstructions();
        System.out.println(ownerAPI.listOwners());
    }

    private void updateOwner(){
        System.out.println("      Update an Owner | Instructions");
        listInstructions();
        System.out.println(ownerAPI.listOwners());
    }

    // Listing owners

    private void listAllOwners(){
        System.out.println();
        System.out.println(ownerAPI.listOwners());
    }

    private void listAllOwnersWithPrefix(){
        String prefix = ScannerInput.readNextLine("Enter a prefix to filter all owners (via their name) with: ");
        System.out.println();
        System.out.println(ownerAPI.listOwnersStartsWith(prefix));
    }

    private void findOwnerWithName(){
        String name = ScannerInput.readNextLine("Enter a name of an owner to retrieve them: ");
        Owner owner = ownerAPI.getOwnerByName(name);
        if(owner == null){
            System.out.println("The owner with the name '" + name + "' was not found.");
        } else {
            System.out.println("The owner '" + name + "' was found, as shown below:");
            System.out.println(owner);
        }
    }

    private void findOwnerWithId(){
        int targetId = ScannerInput.readNextInt("Enter the Id of the owner you want to retrieve: ");
        Owner owner = ownerAPI.getOwnerById(targetId);
        if(owner == null){
            System.out.println("The owner with the Id '" + targetId + "' was not found.");
        } else {
            System.out.println("Owner '" + owner.getName() + "' with the Id '" + targetId + "' was found, as shown below:");
            System.out.println(owner);
        }
    }

    // -----------------------------------------
    //  Pets Day Care Overview Methods
    // -----------------------------------------

    private void editPetsDayCareName(){
        petsDayCareAPI.setName(ScannerInput.readNextLine("Enter a new name for your Pets Day Care: "));
        System.out.println();
        System.out.println("Edit name completed.");
    }

    private void editMaximumPetsAllowed(){
        petsDayCareAPI.setMaxNumberOfPets(ScannerInput.readNextInt("Enter the new maximum amount of pets allowed at the Pets Day Care: "));
        System.out.println();
        System.out.println("Edit maximum amount completed.");
    }

    private void showWeeklyIncome(){
        System.out.println();
        System.out.println("The weekly income of the Pets Day Care '" + petsDayCareAPI.getName() + "' is " + petsDayCareAPI.getWeeklyIncome());
    }

    private void showPetsAverageDaysStayedPerWeek(){
        System.out.println();
        System.out.println("The average days a pet stays at Pets Day Care '" + petsDayCareAPI.getName() + "' is " + petsDayCareAPI.getAverageNumDaysPerWeekStayed());
    }

    // -----------------------------------------
    // Persistence Methods
    // -----------------------------------------

    private void loadFile(){
        try{
            petsDayCareAPI.load();

            System.out.println();
            System.out.println("Pets loaded.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFile(){
        try{
            petsDayCareAPI.save();

            System.out.println();
            System.out.println("Saved Pets.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // -----------------------------------------
    //  Pet Utility Methods
    // -----------------------------------------

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
        String sexString = ScannerInput.readNextLine("What is this pet's sex? (Male/Female): ");
        if(!sexString.isEmpty()){
            if(sexString.substring(0, 1).toLowerCase().equals("m")){
                return 'M';
            } else if(sexString.substring(0, 1).toLowerCase().equals("f")){
                return 'F';
            }
        }
        return 'U';
    }

    // create new dog class
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

    // create new cat class
    private Cat registerCat(String name, int age, Owner owner, int id){
        char sex = getSex();
        boolean isNeutered = getBooleanFromYesOrNo("Is this cat neutered? (Yes/No): ");
        double weight = getWeight();
        boolean isVaccinated = getBooleanFromYesOrNo("Is this cat vaccinated? (Yes/No): ");

        System.out.println("\n" + CatToyUtility.getCatToys() + "\n");

        // cat specific variables
        String favouriteToy = ScannerInput.readNextLine("What is this cat's favourite toy?: ");

        while(!CatToyUtility.isCatToy(favouriteToy)){
            favouriteToy = ScannerInput.readNextLine("What is this cat's favourite toy?: ");
        }

        boolean indoorCat = getBooleanFromYesOrNo("Is this cat an indoor cat? (Yes/No): ");

        return new Cat(name, age, owner, id, sex, isNeutered, weight, isVaccinated, favouriteToy, indoorCat);
    }

    // create new parrot class
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
}
