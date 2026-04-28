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
        return String.valueOf(file);
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

    public Pet setPetAtSpecificIndex(Pet pet, int targetIndex){
        return pets.set(targetIndex, pet);
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

    public String listPet(Pet pet){
        return pet.getName()
                + "\n  - Owner: " + pet.getOwner().getName()
                + "\n  - Type of Pet: " + getPetType(pet)
                + "\n  - Id: " + pet.getId()
                + "\n  - Age: " + pet.getAge()
                + "\n  - Days Attending: " + pet.displayStringOfDaysAttending()
                + "\n";
    }

    public String listDog(Pet pet){
        if(!(pet instanceof Dog dog)) return "";

        return dog.getName()
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

    public String listCat(Pet pet){
        if(!(pet instanceof Cat cat)) return "";

        return cat.getName()
                + "\n  - Owner: " + cat.getOwner().getName()
                + "\n  - Id: " + cat.getId()
                + "\n  - Age: " + cat.getAge()
                + getMammalGeneralInfo(cat)
                + "\n  - Favourite Toy: " + cat.getFavouriteToy()
                + "\n  - Is an indoor cat: " + Utilities.booleanToYN(cat.isIndoorCat())
                + "\n";
    }

    public String listParrot(Pet pet){
        if(!(pet instanceof Parrot parrot)) return "";

        return parrot.getName()
                + "\n  - Owner: " + parrot.getOwner().getName()
                + "\n  - Id: " + parrot.getId()
                + "\n  - Age: " + parrot.getAge()
                + "\n  - Wing Span: " + parrot.getWingSpan()
                + "\n  - Can Fly: " + Utilities.booleanToYN(parrot.canFly())
                + "\n  - Level of Vocabulary Size: " + parrot.getVocabularySize()
                + "\n  - Days Attending: " + parrot.displayStringOfDaysAttending()
                + "\n";
    }

    public String listAllPets(){
        String petsString = "";

        for(int i = 0; i < pets.size(); i++){
            petsString += i + " | " + listPet(pets.get(i));
        }

        if(petsString.isEmpty()){
            return "There are no pets in the register.";
        }

        return petsString;
    }

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

    public String listAllPetsByOwner(String ownerName){
        String petsOfOwnerString = "";

        for(int i = 0; i < pets.size(); i++){
            if(!pets.get(i).getOwner().getName().toLowerCase().equals(ownerName)) continue;
            petsOfOwnerString += i + " | " + listPet(pets.get(i));
        }

        if(petsOfOwnerString.isEmpty()){
            return "There are no pets in the register that are owned by " + ownerName + ".";
        }

        return petsOfOwnerString;
    }

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

    public int numberOfPets(){
        int petCount = 0;

        for(Pet pet : pets){
            petCount++;
        }

        return petCount;
    }

    public int numberOfCats(){
        int catCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Cat)) continue;
            catCount++;
        }

        return catCount;
    }

    public int numberOfIndoorCats(){
        int indoorCatCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Cat)) continue;
            if(!((Cat) pet).isIndoorCat()) continue;
            indoorCatCount++;
        }

        return indoorCatCount;
    }

    public int numberOfDogs(){
        int dogCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Dog)) continue;
            dogCount++;
        }

        return dogCount;
    }

    public int numberOfDangerousDogs(){
        int dangerousDogsCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Dog)) continue;
            if(!((Dog) pet).isDangerousBreed()) continue;
            dangerousDogsCount++;
        }

        return dangerousDogsCount;
    }

    public int numberOfParrots(){
        int parrotCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Parrot)) continue;
            parrotCount++;
        }

        return parrotCount;
    }

    // returns number of parrots in same category of vocabulary size
    public int numberOfParrotsByVocabularySize(int vocabularySize){
        int parrotWithVocabSizeCount = 0;

        for(Pet pet : pets){
            if(!(pet instanceof Parrot)) continue;
            if(!((Parrot) pet).getVocabularySize().equals(BirdUtility.getVocabularyLevel2(vocabularySize))) continue;
            parrotWithVocabSizeCount++;
        }

        return parrotWithVocabSizeCount;
    }

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

    public Pet findDogByOwnerAndBreedAndAge(String ownerName, String breed, int age){
        for(Pet pet : pets){
            if(!(pet instanceof Dog dog)) continue;
            if(pet.getOwner().getName().toLowerCase().equals(ownerName) && dog.getBreed().equals(breed) && pet.getAge() == age){
                return pet;
            }
        }
        return null;
    }

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
    //  Sorting methods as per spec .
    // NOTE : THESE MUST BE WRITTEN WITHOUT THE USE OF THE SORT LIBRARY. If in doubt, just ask.

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

    private void swapPets(int position1, int position2){
        if(!isValidPetIndex(position1) || !isValidPetIndex(position2)) return;

        Pet pet1 = pets.get(position1);

        pets.set(position1, pets.get(position2));
        pets.set(position2, pet1);
    }

    private void swapPets(Pet pet1, Pet pet2){
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

    @Override
    public void save() throws Exception{
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(file));
        out.writeObject(pets);
        out.close();
    }
}



