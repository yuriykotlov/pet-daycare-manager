 //TODO - Uncomment when you are ready to go

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

    public ArrayList<Pet> getPets() {
        return pets;
    }
    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if(!Utilities.validStringlength(name, 10)){
            this.name = name;
        }
    }

    public int getMaxNumberOfPets() {
        return maxNumberOfPets;
    }
    public void setMaxNumberOfPets(int maxNumberOfPets) {
        if(Utilities.validRange(maxNumberOfPets, 10,  100)){
            this.maxNumberOfPets = maxNumberOfPets;
        }
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

    public String fileName(){
        return file.getName();
    }

    //-------------------------------------
    //  Pet ARRAYLIST CRUD
    //-------------------------------------

    private void initName(String name){
        this.name = name.length() <= 20 ? name : Utilities.truncateString(name ,20);
    }

    public Pet removePetAtPosition(int targetIndex){
        if(isValidPetIndex(targetIndex)){
            return pets.remove(targetIndex);
        }
        return null;
    }

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

    public boolean addPet(Pet newPet){
        return pets.add(newPet);
    }

    //-------------------------------------
    //  Pet ARRAYLIST - Utility methods
    //-------------------------------------

    public boolean isValidPetIndex(int petIndex){
        return petIndex >= 0 && petIndex <= pets.size();
    }

    public Pet getPetByIndex(int petIndex){
        if(isValidPetIndex(petIndex)){
            return pets.get(petIndex);
        }
        return null;
    }

    public Pet getPetByName(String targetPetName){
        for(Pet pet : pets){
            if(pet.getName().toLowerCase().equals(targetPetName)){
                return pet;
            }
        }
        return null;
    }

    public Pet getPetByiD(int id){
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
//TODO e.g. Add a method, listAllPets().  The return type is String.
//     This method returns a list of the pets stored in the array list.
//     Each pet should be on a new line and should be preceded by the index number e.g.
//        0: pet 1 Details
//        1: pet 2 Details
//    If there are no pets stored in the array list, return a string that contains "There are no pets registered at the moment".

//TODO +  and the remaining listing methods as per spec

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

    private String getMammalGeneralInfo(Pet pet){
        if(!(pet instanceof Mammal mammal)) return "";

        return "\n  - Sex: " + mammal.getSex()
                + "\n  - Weight: " + mammal.getWeight()
                + "\n  - Is Neutered: " + mammal.isNeutered()
                + "\n  - Is Vaccinated: " + mammal.isVaccinated();
    }

    public String listAllPets(){
        String petsString = "";

        for(int i = 0; i < pets.size(); i++){
            Pet thisPet = pets.get(i);

            petsString += i + " | " + thisPet.getName()
                            + "\n  - Owner: " + thisPet.getOwner().getName()
                            + "\n  - Type of Pet: " + getPetType(thisPet)
                            + "\n  - Id: " + thisPet.getId()
                            + "\n  - Age: " + thisPet.getAge()
                            + "\n  - Days Attending: " + thisPet.displayStringOfDaysAttending()
                            + "\n";
        }

        return petsString;
    }

    public String listAllCats(){
        String catsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Cat cat)) continue;

            catsString += i + " | " + cat.getName()
                    + "\n  - Owner: " + cat.getOwner().getName()
                    + "\n  - Id: " + cat.getId()
                    + "\n  - Age: " + cat.getAge()
                    + getMammalGeneralInfo(cat)
                    + "\n  - Favourite Toy: " + cat.getFavouriteToy()
                    + "\n  - Is an Indoor Cat: " + Utilities.booleanToYN(cat.isIndoorCat())
                    + "\n  - Days Attending: " + cat.displayStringOfDaysAttending()
                    + "\n";
        }

        return catsString;
    }

    public String listAllDogs(){
        String dogsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Dog dog)) continue;

            dogsString += i + " | " + dog.getName()
                    + "\n  - Owner: " + dog.getOwner().getName()
                    + "\n  - Id: " + dog.getId()
                    + "\n  - Age: " + dog.getAge()
                    + getMammalGeneralInfo(dog)
                    + "\n  - Breed: " + dog.getBreed()
                    + "\n  - Is a dangerous breed: " + Utilities.booleanToYN(dog.isDangerousBreed())
                    + "\n  - Current daily rate: " + dog.getCurrentRate()
                    + "\n  - Days Attending: " + dog.displayStringOfDaysAttending()
                    + "\n";
        }

        return dogsString;
    }

    public String listAllParrots(){
        String parrotsString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!(pets.get(i) instanceof Parrot parrot)) continue;

            parrotsString += i + " | " + parrot.getName()
                    + "\n  - Owner: " + parrot.getOwner().getName()
                    + "\n  - Id: " + parrot.getId()
                    + "\n  - Age: " + parrot.getAge()
                    + "\n  - Wing Span: " + parrot.getWingSpan()
                    + "\n  - Can Fly: " + Utilities.booleanToYN(parrot.canFly())
                    + "\n  - Level of Vocabulary Size: " + parrot.getVocabularySize()
                    + "\n  - Days Attending: " + parrot.displayStringOfDaysAttending()
                    + "\n";
        }

        return parrotsString;
    }

    public String listAllDangerousDogs(){
        return "";
    }

    public String listAllPetsByOwner(String ownerName){
        return "";
    }

    public String listAllPetsThatStayMoreThanXDays(int daysStayed){
        return "";
    }

    //-------------------------------------
    //  Counting Methods
    //-------------------------------------

    public int numberOfPets(){
        return 0;
    }

    public int numberOfCats(){
        return 0;
    }

    public int numberOfIndoorCats(){
        return 0;
    }

    public int numberOfDogs(){
        return 0;
    }

    public int numberOfDangerousDogs(){
        return 0;
    }

    public int numberOfParrots(){
        return 0;
    }

    // returns number of parrots in same category of vocabulary size
    public int numberOfParrotsByVocabularySize(int vocabularySize){
        return 0;
    }

    public double getWeeklyIncome(){
        return 0.0;
    }

    public double getAverageNumDaysPerWeekStayed(){
        return 0.0;
    }


    //------------------------------
    //  FINDING METHODS
    //-------------------------------
    // TOD Add all the find methods

    public Pet findDogByOwnerAndBreedAndAge(String ownerName, String breed, int age){
        return null;
    }

    public String getPetsByOwnerName(String ownerName){
        return "";
    }

    //--------------------------------
    // Sorting methods
    //--------------------------------
    //  Sorting methods as per spec .
    // NOTE : THESE MUST BE WRITTEN WITHOUT THE USE OF THE SORT LIBRARY. If in doubt, just ask.

    public void sortPetsById(){

    }

    public void sortPetsByName(){

    }

    private void swapPetsAtPositions(int position1, int position2){

    }

    private void swapPets(Pet pet1, Pet pet2){

    }

    //---------------------------------
    //  Methods for Persistence
    // --------------------------------

    //TODO Add a method, load().  The return type is void.
    //    This method uses the XStream component to deserialise the Pets array list  from
    //    an XML file into the Pets array list.

    @Override
    public void load(){

    }


    //TODO Add a method, save().  The return type is void.
    //    This method uses the XStream component to serialise the Pets array list object  to
    //    an XML file.

    @Override
    public void save(){

    }

}



