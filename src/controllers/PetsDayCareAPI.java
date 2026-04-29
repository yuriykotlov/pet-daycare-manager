package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.Utilities;
import utils.BirdUtility;
import utils.ISerializer;

import java.io.*;
import java.util.ArrayList;

public class PetsDayCareAPI implements ISerializer {
    private ArrayList<Pet> pets;
    private String name;
    private int maxNumberOfPets;
    private File file;

    //-------------------------------------
    //  Constructor
    //-------------------------------------

    /**
     * The Constructor of the PetsDayCareAPI
     *
     * @param name The name of the Pets Day Care
     * @param maxNumberOfPets Maximum number of pets allowed in the Pets Day Care
     * @param file File which the pets registered save to
     */
    public PetsDayCareAPI(String name, int maxNumberOfPets, File file) {
        pets = new ArrayList<Pet>();

        initName(name);

        if(!Utilities.validRange(maxNumberOfPets, 10,  100)){
            maxNumberOfPets = 10;
        }
        this.maxNumberOfPets = maxNumberOfPets;

        this.file = file;
    }

    //-------------------------------------
    //  Setters/Getters
    //-------------------------------------

    /**
     * @return Returns ArrayList<Pet> pets
     */
    public ArrayList<Pet> getPets() {
        return pets;
    }
    /**
     * @param pets Updates the ArrayList<Pet> pets variable with a new list of pets
     */
    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    /**
     * @return Returns the name of the Pets Day Care
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The new name for the Pets Day Care
     */
    public void setName(String name) {
        if(Utilities.validStringlength(name, 10)){
            this.name = name;
        }
    }

    /**
     * @return Returns the maximum number of pets allowed in the Pets Day Care
     */
    public int getMaxNumberOfPets() {
        return maxNumberOfPets;
    }
    /**
     * @param maxNumberOfPets The new maximum amount of pets allowed in the Pets Day Care
     */
    public void setMaxNumberOfPets(int maxNumberOfPets) {
        if(Utilities.validRange(maxNumberOfPets, 10,  100)){
            this.maxNumberOfPets = maxNumberOfPets;
        }
    }

    /**
     * @return Returns the save file of pets
     */
    public File getFile() {
        return file;
    }
    /**
     * @param file The new file which overwrites the existing pets save file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return Returns the name of the pets save file
     */
    public String fileName(){
        return String.valueOf(file);
    }

    //-------------------------------------
    //  Pet ARRAYLIST CRUD
    //-------------------------------------

    /**
     * @param name The name that is first used to initialise the PetsDayCareAPI class
     */
    private void initName(String name){
        this.name = name.length() <= 20 ? name : Utilities.truncateString(name ,10);
    }

    /**
     * Removes a pet at a chosen index
     *
     * @param targetIndex The index to remove a pet from
     * @return Returns the pet removed, or null if unsuccessful
     */
    public Pet removePetAtPosition(int targetIndex){
        if(isValidPetIndex(targetIndex)){
            return pets.remove(targetIndex);
        }
        return null;
    }

    /**
     * Updates a pet at a selected index
     *
     * @param targetIndex The index to update a pet at
     * @param updatedPet The new pet with its new variables, to overwrite the original pet
     * @return Returns the updated pet, or null if unsuccessful
     */
    public Pet updatePet(int targetIndex, Pet updatedPet){
        if(isValidPetIndex(targetIndex) && pets.get(targetIndex) != null){
            Pet thisPet = pets.get(targetIndex);

            // update values of this pet at target index
            thisPet.setName(updatedPet.getName());
            thisPet.setAge(updatedPet.getAge());
            thisPet.setDaysAttending(updatedPet.getDaysAttending());
            thisPet.setId(updatedPet.getId());
            thisPet.setOwner(updatedPet.getOwner());

            return thisPet;
        }
        return null;
    }

    /**
     * Adds a pet to the pets arraylist
     *
     * @param newPet The new pet of class Pet to add
     * @return Returns a boolean whether adding was successful or not
     */
    public boolean addPet(Pet newPet){
        boolean successful = pets.add(newPet);

        // make sure the arraylist is correctly sorted
        sortPetsById();

        return successful;
    }

    //-------------------------------------
    //  Pet ARRAYLIST - Utility methods
    //-------------------------------------

    /**
     * Checks if the index provided is valid in the context of the pets arraylist
     *
     * @param petIndex The index to check
     * @return Returns whether the index is valid
     */
    public boolean isValidPetIndex(int petIndex){
        return petIndex >= 0 && petIndex <= pets.size();
    }

    /**
     * Gets a pet at the selected index
     *
     * @param petIndex The index to get the pet from
     * @return Returns the pet if found or null if not
     */
    public Pet getPetByIndex(int petIndex){
        if(!pets.isEmpty() && isValidPetIndex(petIndex)){
            return pets.get(petIndex);
        }
        return null;
    }

    /**
     * Loops through the pets arraylist to get a pet by matching names
     *
     * @param targetPetName The name of interest
     * @return Returns the pet if found or null if not
     */
    public Pet getPetByName(String targetPetName){
        for(Pet pet : pets){
            if(pet.getName().toLowerCase().equals(targetPetName.toLowerCase())){
                return pet;
            }
        }
        return null;
    }

    /**
     * Loops through the pets arraylist to get a pet by matching Ids
     *
     * @param id The Id of interest
     * @return Returns the pet if found or null if not
     */
    public Pet getPetById(int id){
        for(Pet pet : pets){
            if(pet.getId() == id){
                return pet;
            }
        }
        return null;
    }

    //------------------------------------
    // LISTING METHODS - Basic and Advanced
    //------------------------------------

    // Utility methods of listing Methods

    /**
     * Finds out if the Pet is an instance of class Cat, Dog or Parrot
     *
     * @param pet The pet to which determine its specific class
     * @return Returns a string of its specific class
     */
    private String getPetType(Pet pet){
        if(pet instanceof Cat){
            return "Cat";
        } else if(pet instanceof Dog){
            return "Dog";
        } else if(pet instanceof Parrot){
            return "Parrot";
        }
        return "?";
    }

    /**
     * Creates a formatted string using a Pet which is an instance of class Mammal
     *
     * @param pet The pet of interest
     * @return Returns a formatted string with information about variables from the Mammal class
     */
    private String getMammalGeneralInfo(Pet pet){
        if(!(pet instanceof Mammal mammal)) return "";

        return "\n  - Sex: " + mammal.getSex()
                + " | Weight: " + mammal.getWeight()
                + " | Is Neutered: " + Utilities.booleanToYN(mammal.isNeutered())
                + " | Is Vaccinated: " + Utilities.booleanToYN(mammal.isVaccinated());
    }

    // Listing specific pet provided

    /**
     * Creates a formatted string using a Pet
     *
     * @param pet The pet of interest
     * @return Returns a formatted string with information about variables from the Pet class
     */
    public String listPet(Pet pet){
        return pet.getName()
                + "\n  - Owner:\n\t-> " + pet.getOwner()
                + "\n  - Type of Pet: " + getPetType(pet)
                + "\n  - Id: " + pet.getId() + " | Age: " + pet.getAge()
                + "\n  - Days Attending: " + pet.displayStringOfDaysAttending()
                + "\n\n";
    }

    /**
     * Creates a formatted string using a Pet which is an instance of class Dog
     *
     * @param pet The pet of interest
     * @return Returns a formatted string with information about variables from the Dog class
     */
    public String listDog(Pet pet){
        if(!(pet instanceof Dog dog)) return "";

        return dog.getName()
                + "\n  - Owner:\n\t-> " + dog.getOwner()
                + "\n  - Id: " + dog.getId() + " | Age: " + dog.getAge()
                + getMammalGeneralInfo(dog)
                + "\n  - Breed: " + dog.getBreed()
                + "\n  - Is a dangerous breed: " + Utilities.booleanToYN(dog.isDangerousBreed())
                + "\n  - Current daily rate: " + dog.getCurrentRate()
                + "\n  - Days Attending: " + dog.displayStringOfDaysAttending()
                + "\n\n";
    }

    /**
     * Creates a formatted string using a Pet which is an instance of class Cat
     *
     * @param pet The pet of interest
     * @return Returns a formatted string with information about variables from the Cat class
     */
    public String listCat(Pet pet){
        if(!(pet instanceof Cat cat)) return "";

        return cat.getName()
                + "\n  - Owner:\n\t-> " + cat.getOwner()
                + "\n  - Id: " + cat.getId() + " | Age: " + cat.getAge()
                + getMammalGeneralInfo(cat)
                + "\n  - Favourite Toy: " + cat.getFavouriteToy()
                + "\n  - Is an indoor cat: " + Utilities.booleanToYN(cat.isIndoorCat())
                + "\n  - Weekly Fee: " + cat.calculateWeeklyFee()
                + "\n  - Days Attending: " + cat.displayStringOfDaysAttending()
                + "\n\n";
    }

    /**
     * Creates a formatted string using a Pet which is an instance of class Parrot
     *
     * @param pet The pet of interest
     * @return Returns a formatted string with information about variables from the Parrot class
     */
    public String listParrot(Pet pet){
        if(!(pet instanceof Parrot parrot)) return "";

        return parrot.getName()
                + "\n  - Owner:\n\t-> " + parrot.getOwner()
                + "\n  - Id: " + parrot.getId() + " | Age: " + parrot.getAge()
                + "\n  - Wing Span: " + parrot.getWingSpan()
                + "\n  - Can Fly: " + Utilities.booleanToYN(parrot.canFly())
                + "\n  - Level of Vocabulary Size: " + parrot.getVocabularySize()
                + "\n  - Weekly Fee: " + parrot.calculateWeeklyFee()
                + "\n  - Days Attending: " + parrot.displayStringOfDaysAttending()
                + "\n\n";
    }

    // Creating a list of all Pets, Dogs, Cats, or Parrots

    /**
     * Creates a formatted string of all pets in the pets arraylist
     *
     * @return Returns a formatted string of all pets found
     */
    public String listAllPets(){
        String petsString = "";

        if(pets.isEmpty()){
            return "There are no pets in the register.";
        }

        for(int i = 0; i < pets.size(); i++){
            petsString += i + " | " + listPet(pets.get(i));
        }

        return petsString;
    }

    /**
     * Creates a formatted string of all dogs in the pets arraylist
     *
     * @return Returns a formatted string of all dogs found
     */
    public String listAllDogs(){
        String dogsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Dog dog)) continue;

            dogsString += i + " | " + listDog(dog);
        }

        if(dogsString.isEmpty()){
            return "There are no dogs in the register.";
        }

        return dogsString;
    }

    /**
     * Creates a formatted string of all cats in the pets arraylist
     *
     * @return Returns a formatted string of all cats found
     */
    public String listAllCats(){
        String catsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Cat cat)) continue;

            catsString += i + " | " + listCat(cat);
        }

        if(catsString.isEmpty()){
            return "There are no cats in the register.";
        }

        return catsString;
    }

    /**
     * Creates a formatted string of all parrots in the pets arraylist
     *
     * @return Returns a formatted string with all parrots found
     */
    public String listAllParrots(){
        String parrotsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Parrot parrot)) continue;

            parrotsString += i + " | " + listParrot(parrot);
        }

        if(parrotsString.isEmpty()){
            return "There are no parrots in the register.";
        }

        return parrotsString;
    }

    // Creating a list but filtered with a certain variable

    /**
     * Creates a formatted string of all indoor cats in the pets arraylist
     *
     * @return Returns a formatted string with all indoor cats found
     */
    public String listAllIndoorCats(){
        String indoorCatsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Cat cat)) continue;
            if(!cat.isIndoorCat()) continue;

            indoorCatsString += i + " | " + listCat(cat);
        }

        if(indoorCatsString.isEmpty()){
            return "There are no (indoor) cats in the register.";
        }

        return indoorCatsString;
    }

    /**
     * Creates a formatted string of all dangerous dogs in the pets arraylist
     *
     * @return Returns a formatted string with all dangerous dogs found
     */
    public String listAllDangerousDogs(){
        String dangerousDogsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Dog dog)) continue;
            if(!dog.isDangerousBreed()) continue;

            dangerousDogsString += i + " | " + listDog(dog);
        }

        if(dangerousDogsString.isEmpty()){
            return "There are no (dangerous) dogs in the register.";
        }

        return dangerousDogsString;
    }

    /**
     * Creates a formatted string of all parrots in the pets arraylist with the same vocabulary level
     *
     * @return Returns a formatted string with all parrots in same category of vocabulary size
     */
    public String listParrotsByVocabularySize(int vocabularySize){
        String parrotsWithXVocabLevelString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Parrot parrot)) continue;
            if(!parrot.getVocabularySize().equals(BirdUtility.getVocabularyLevel2(vocabularySize))) continue;

            parrotsWithXVocabLevelString += i + " | " + listParrot(parrot);
        }

        if(parrotsWithXVocabLevelString.isEmpty()){
            return "There are no parrots in the register with the vocabulary size provided.";
        }

        return parrotsWithXVocabLevelString;
    }

    /**
     * Creates a formatted string of all pets which are owned by a specific owner in the pets arraylist
     *
     * @return Returns a formatted string with of all pets which are owned by a specific owner
     */
    public String listAllPetsByOwner(String ownerName){
        String petsOfOwnerString = "";

        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).getOwner() == null) continue;
            if(!pets.get(i).getOwner().getName().toLowerCase().equals(ownerName.toLowerCase())) continue;
            petsOfOwnerString += i + " | " + listPet(pets.get(i));
        }

        if(petsOfOwnerString.isEmpty()){
            return "There are no pets in the register that are owned by " + ownerName + ".";
        }

        return petsOfOwnerString;
    }

    /**
     * Creates a formatted string of all pets in the pets arraylist which are staying more than X days
     *
     * @return Returns a formatted string with of all pets which are staying more than X days
     */
    public String listAllPetsThatStayMoreThanXDays(int daysStayed){
        String petsThatAreStayingString = "";

        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).numOfDaysAttending() <= daysStayed) continue;
            petsThatAreStayingString += i + " | " + listPet(pets.get(i));
        }

        if(petsThatAreStayingString.isEmpty()){
            return "There are no pets staying more than " + daysStayed + " days.";
        }

        return petsThatAreStayingString;
    }

    //-------------------------------------
    //  Counting Methods
    //-------------------------------------

    // Getting number of overall Pets, Dogs, Cats, and Parrots

    /**
     * @return Returns the size of the pets arraylist
     */
    public int numberOfPets(){
        return pets.size();
    }

    /**
     * @return Returns the number of dogs in the pets arraylist
     */
    public int numberOfDogs(){
        int dogCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Dog)) continue;
            dogCount++;
        }

        return dogCount;
    }

    /**
     * @return Returns the number of cats in the pets arraylist
     */
    public int numberOfCats(){
        int catCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Cat)) continue;
            catCount++;
        }

        return catCount;
    }

    /**
     * @return Returns the number of parrots in the pets arraylist
     */
    public int numberOfParrots(){
        int parrotCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Parrot)) continue;
            parrotCount++;
        }

        return parrotCount;
    }

    // Getting number of filtered result

    /**
     * @return Returns the number of indoor cats in the pets arraylist
     */
    public int numberOfIndoorCats(){
        int indoorCatCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Cat cat)) continue;
            if(!cat.isIndoorCat()) continue;
            indoorCatCount++;
        }

        return indoorCatCount;
    }

    /**
     * @return Returns the number of dangerous dogs in the pets arraylist
     */
    public int numberOfDangerousDogs(){
        int dangerousDogsCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Dog dog)) continue;
            if(!dog.isDangerousBreed()) continue;
            dangerousDogsCount++;
        }

        return dangerousDogsCount;
    }

    /**
     * @return Returns the number of parrots in same category of vocabulary size
     */
    public int numberOfParrotsByVocabularySize(int vocabularySize){
        int parrotWithVocabSizeCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Parrot parrot)) continue;
            if(!parrot.getVocabularySize().equals(BirdUtility.getVocabularyLevel2(vocabularySize))) continue;
            parrotWithVocabSizeCount++;
        }

        return parrotWithVocabSizeCount;
    }

    /**
     * @return Returns a double of the weekly income earned
     */
    public double getWeeklyIncome(){
        double totalIncome = 0.0;

        for(Pet pet : pets){
            for(boolean dayAttending : pet.getDaysAttending()){
                if(!dayAttending) continue;

                if(pet instanceof Cat cat){
                    totalIncome += cat.calculateWeeklyFee();
                } else if(pet instanceof Dog dog){
                    totalIncome += dog.calculateWeeklyFee();
                } else if(pet instanceof Parrot parrot){
                    totalIncome += parrot.calculateWeeklyFee();
                }
            }
        }

        return totalIncome;
    }

    /**
     * @return Returns a double of the average days an animal stays per week
     */
    public double getAverageNumDaysPerWeekStayed(){
        // 7 is the number of days in a week
        int totalDays = pets.size() * 7;
        int totalDaysStayed = 0;

        for(Pet pet : pets) {
            for (boolean dayAttending : pet.getDaysAttending()) {
                if(!dayAttending) continue;
                totalDaysStayed++;
            }
        }

        return (double) totalDays / totalDaysStayed;
    }

    //------------------------------
    //  FINDING METHODS
    //-------------------------------

    /**
     * Finds a dog which has specifically the three variables as provided
     *
     * @param ownerName The name of the owner that owns this dog
     * @param breed The breed of this dog
     * @param age The age this dog
     * @return Returns a Pet instance, if found, if it has the specific ownerName, breed, and age as provided
     */
    public Pet findDogByOwnerAndBreedAndAge(String ownerName, String breed, int age){
        for(Pet pet : pets){
            if(!(pet instanceof Dog dog)) continue;
            if(pet.getOwner() == null) continue;
            if(pet.getOwner().getName().toLowerCase().equals(ownerName.toLowerCase()) && dog.getBreed().toLowerCase().equals(breed.toLowerCase()) && pet.getAge() == age){
                return pet;
            }
        }
        return null;
    }

    /**
     * Finds a pet's index in the pets arraylist using its Id
     *
     * @param id The id of interest
     * @return Returns the index of the pet if found, or -1 if not found
     */
    public int findPetPositionInArrayWithId(int id){
        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    //--------------------------------
    // Sorting methods
    //--------------------------------

    /**
     * Sorts all pets in the pets arraylist by their Id in a descending manner
     */
    public void sortPetsById(){
        for(int i = pets.size() - 1; i >= 0; i--){
            int largestIndex = i;
            for(int j = 0; j < i; j++){
                if(pets.get(j).getId() > pets.get(largestIndex).getId()){
                    largestIndex = j;
                }
            }
            swapPets(i, largestIndex);
        }
    }

    /**
     * Sorts all pets in the pets arraylist by their Name in a descending manner
     */
    public void sortPetsByName(){
        for(int i = pets.size() - 1; i >= 0; i--){
            int largestIndex = i;
            for(int j = 0; j < i; j++){
                if(pets.get(j).getName().compareTo(pets.get(largestIndex).getName()) > 0){
                    largestIndex = j;
                }
            }
            swapPets(i, largestIndex);
        }
    }

    /**
     * Swaps two pets at the positions (indexes) provided
     *
     * @param position1 The first position to swap
     * @param position2 The second position to swap
     */
    public void swapPets(int position1, int position2){
        if(!isValidPetIndex(position1) || !isValidPetIndex(position2)) return;

        Pet pet1 = pets.get(position1);

        pets.set(position1, pets.get(position2));
        pets.set(position2, pet1);
    }

    /**
     * Swaps the places of the two pets provided
     *
     * @param pet1 The first pet to swap
     * @param pet2 The second pet to swap
     */
    public void swapPets(Pet pet1, Pet pet2){
        int pet1Position = -1;
        int pet2Position = -1;

        for(int i = 0; i < pets.size(); i++){
            if(pets.get(i).getId() == pet1.getId() && pet1Position == -1){
                pet1Position = i;
            } else if(pets.get(i).getId() == pet2.getId() && pet2Position == -1){
                pet2Position = i;
            }
        }

        if(pet1Position != -1 && pet2Position != -1){
            pets.set(pet1Position, pet2);
            pets.set(pet2Position, pet1);
        }
    }

    //---------------------------------
    //  Methods for Persistence
    // --------------------------------

    /**
     * Loads the pets saved
     */
    @Override
    public void load() throws Exception{
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{
                Owner.class,
                Pet.class,
                Mammal.class,
                Cat.class,
                Dog.class,
                Bird.class,
                Parrot.class
        };

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        pets = (ArrayList<Pet>) in.readObject();
        in.close();
    }

    /**
     * Saves the pets currently in the pets arraylist
     */
    @Override
    public void save() throws Exception{
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(file));
        out.writeObject(pets);
        out.close();
    }
}
