 //TODO - Uncomment when you are ready to go

package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.Pet;
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
    //TODO array list of pets, should be empty at the start.
    public PetsDayCareAPI(String name, int maxNumberOfPets, File file) {

    //TODO array list of pets, should be empty at the start.
    //     When creating the PetsDayCare, truncate the name to 20 characters.
    //     When updating an existing DayCare, only update the name if it is 10 characters or less.
    //     number of pets must be must be >= 10 <= 100 default to 10
    //     file  is the name of the file that you will load from / save to
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
        this.name = name;
    }

    public int getMaxNumberOfPets() {
        return maxNumberOfPets;
    }
    public void setMaxNumberOfPets(int maxNumberOfPets) {
        this.maxNumberOfPets = maxNumberOfPets;
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

    public String fileName(){
        return getFile().getName();
    }

    //

    //-------------------------------------
    //  Pet ARRAYLIST CRUD
    //-------------------------------------
    //TODO add the methods as per spec.

    private void initName(String name){

    }

    public Pet removePetAtPosition(int targetIndex){
        return null;
    }

    public Pet updatePet(int petId, Pet updatedPet){
        return null;
    }

    public boolean addPet(Pet newPet){
        return true;
    }

    //-------------------------------------
    //  Pet ARRAYLIST - Utility methods
    //-------------------------------------
    // e.g validation methods
    //TODO Add a method isValidIndex(int) which returns an boolean -
    //      - returns true if the index is valid for the pets arrayList (in range)
    //      - returns false otherwise
    //      As this method is used inside this class, it should be private

    public boolean isValidPetIndex(int petIndex){
        return true;
    }

   // TODO  Add a method  getPet(int) which returns a Pet object:
   //       - if the supplied index is valid, the Pet object at that location is returned
   //       - if the supplied index is invalid, null is returned

    public Pet getPetByIndex(int petIndex){
        return null;
    }

   //TODO  Add a method  getPet(String) which returns a Pet object:
   //       - if the supplied name is found, the first Dog object with that name is returned
   //       - if the supplied name is not found, null is returned

    public Pet getPetByName(String petName){
        return null;
    }


   //TODO  Add a method  getPetById(int) which returns a Pet object:
   //       - if the supplied id is found, the Pet object with that id is returned
   //       - if the supplied id is not found, null is returned

    public Pet getPetByiD(int id){
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

    public String listOwners(){
        return "";
    }

    public String listAllPets(){
        return "";
    }

    public String listAllCats(){
        return "";
    }

    public String listAllDogs(){
        return "";
    }

    public String listAllParrots(){
        return "";
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


    //-------------------------------------
    //  Counting Methods
    //-------------------------------------
    // TODO all the counting methods e.g.
    //TODO Add a method, numberOfPets().  The return type is int.
    //     This method returns the number of pets objects currently stored in the array list.

    //TODO Add a method, numberOfDogs().  The return type is int.
    //     This method returns the number of  dogs objects currently stored in the array list.


    //TODO Add a method, getWeeklyIncome().  The return type is double.
    //     This method returns the amount received from all the pets per week.

    //TODO Add a method, getAverageNumDaysPerWeek().  The return type is int.
    //     This method returns the average number of days pets stay in the Day Care.


    //------------------------------
    //  FINDING METHODS
    //-------------------------------
    // TOD Add all the find methods

    //------------------------------
    //  SEARCHING METHODS
    //-------------------------------

    // TODO Searching methods as per spec.

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

    //------------------------------
    //  OTHER METHODS
    //-------------------------------

    public double getWeeklyIncome(){
        return 0.0;
    }

    public double getAverageNumDaysPerWeekStayed(){
        return 0.0;
    }

    public Pet findDogByOwnerAndBreedAndAge(String ownerName, String breed, int age){
        return null;
    }

    public String getPetsByOwnerName(String ownerName){
        return "";
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



