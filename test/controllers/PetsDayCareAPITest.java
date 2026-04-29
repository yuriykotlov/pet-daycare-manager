package controllers;

import models.*;
import models.Dog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import utils.*;

class PetsDayCareAPITest {

    PetsDayCareAPI validAPI;
    PetsDayCareAPI longNameAPI;
    PetsDayCareAPI boundaryMaxAPI;

    File file;

    @BeforeEach
    void setUp() {
        file = new File("test.xml");

        validAPI = new PetsDayCareAPI("HappyPets", 20, file);
        longNameAPI = new PetsDayCareAPI("VeryVeryLongDayCareName", 20, file); // >10 chars
        boundaryMaxAPI = new PetsDayCareAPI("Test", 100, file); // upper boundary
    }

    // ----------------------------
    // Constructor + Getters
    // ----------------------------
    @Test
    void testConstructorAndGetters() {
        assertEquals("HappyPets", validAPI.getName());
        assertEquals(20, validAPI.getMaxNumberOfPets());
        assertNotNull(validAPI.getPets());
        assertEquals(0, validAPI.getPets().size());
        assertEquals(file, validAPI.getFile());
        assertEquals("test.xml", validAPI.fileName());
    }

    // ----------------------------
    // Name Boundary (max 10 chars)
    // ----------------------------
    @Test
    void testNameBoundary() {
        assertEquals(10, longNameAPI.getName().length());
    }

    // ----------------------------
    // setName()
    // ----------------------------
    @Test
    void testSetName() {
        validAPI.setName("NewName");
        assertEquals("NewName", validAPI.getName());

        validAPI.setName("ThisNameIsTooLong"); // invalid
        assertEquals("NewName", validAPI.getName()); // unchanged
    }

    // ----------------------------
    // Max Pets Boundary
    // ----------------------------
    @Test
    void testMaxNumberBoundary() {
        assertEquals(100, boundaryMaxAPI.getMaxNumberOfPets());

        validAPI.setMaxNumberOfPets(10); // lower boundary
        assertEquals(10, validAPI.getMaxNumberOfPets());

        validAPI.setMaxNumberOfPets(101); // invalid
        assertEquals(10, validAPI.getMaxNumberOfPets());
    }

    // ----------------------------
    // setPetsArray / getPetsArray
    // ----------------------------
    @Test
    void testPetsArraySetterGetter() throws Exception {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(100, "John", "0871234567");
        Dog dog = new Dog("Rex", 5, owner, 1000, 'M', true, 10, false, false, "Labrador Retriever");

        pets.add(dog);

        validAPI.setPets(pets);

        validAPI.save();

        assertEquals(1, validAPI.getPets().size());
        assertEquals(dog, validAPI.getPets().get(0));
    }

    // ----------------------------
    // Utility tests
    // ----------------------------
    @Test
    void testPetsArrayUtility() {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(100, "John", "0871234567");

        Dog dog = new Dog("Rex", 5, owner, 1000, 'M', true, 10, false, true, "Labrador Retriever");

        // staying for 3 days
        boolean[] daysStaying = {
                true, true, true, false, false, false, false
        };

        dog.setDaysAttending(daysStaying);

        Cat cat = new Cat("Rex2", 6, owner, 1001, 'F', true, 10, false, "something", true);
        int parrotVocabularySize = 25;
        Parrot parrot = new Parrot("Rex3", 7, owner, 1002, 'M', true, parrotVocabularySize);

        // make sure all lists work when there are no pets in the register
        assertEquals("There are no pets in the register.", validAPI.listAllPets());
        assertEquals("There are no dogs in the register.", validAPI.listAllDogs());
        assertEquals("There are no (dangerous) dogs in the register.", validAPI.listAllDangerousDogs());
        assertEquals("There are no cats in the register.", validAPI.listAllCats());
        assertEquals("There are no (indoor) cats in the register.", validAPI.listAllIndoorCats());
        assertEquals("There are no parrots in the register.", validAPI.listAllParrots());
        assertEquals("There are no parrots in the register with the vocabulary size provided.", validAPI.listParrotsByVocabularySize(0));

        // now add pets
        validAPI.addPet(dog);
        validAPI.addPet(cat);
        validAPI.addPet(parrot);

        // test if no errors when sorting by name
        validAPI.sortPetsByName();

        // getting number of all types of pets
        assertEquals(3, validAPI.numberOfPets());
        assertEquals(1, validAPI.numberOfDogs());
        assertEquals(1, validAPI.numberOfCats());
        assertEquals(1, validAPI.numberOfParrots());

        // getting number of specific type of pets
        assertEquals(1, validAPI.numberOfDangerousDogs());
        assertEquals(1, validAPI.numberOfIndoorCats());
        assertEquals(1, validAPI.numberOfParrotsByVocabularySize(parrotVocabularySize));

        // finding pet with id
        assertEquals(0, validAPI.findPetPositionInArrayWithId(dog.getId()));
        assertEquals(1, validAPI.findPetPositionInArrayWithId(cat.getId()));
        assertEquals(2, validAPI.findPetPositionInArrayWithId(parrot.getId()));

        // finding pet position in array but testing if it can return -1
        assertEquals(-1, validAPI.findPetPositionInArrayWithId(-1));

        // get pet
        assertEquals(dog, validAPI.getPetById(1000));
        assertEquals(dog, validAPI.getPetByIndex(0));
        assertEquals(cat, validAPI.getPetByName("Rex2"));

        // test if getting can return null
        assertNull(validAPI.getPetByIndex(-1));
        assertNull(validAPI.getPetByName(""));
        assertNull(validAPI.getPetById(-1));

        // testing if lists return the correct values

        // pets
        ArrayList<String> wordsToCheckPet = new ArrayList<String>();
        wordsToCheckPet.add("Rex");
        wordsToCheckPet.add("5");
        wordsToCheckPet.add("1000");
        wordsToCheckPet.add("Dog");
        wordsToCheckPet.add(dog.displayStringOfDaysAttending());

        for(String word : wordsToCheckPet){
            assertTrue(validAPI.listPet(dog).contains(word));
            assertTrue(validAPI.listAllPets().contains(word));
        }

        // dogs
        ArrayList<String> wordsToCheckDog = new ArrayList<String>();
        wordsToCheckDog.add("Rex");
        wordsToCheckDog.add("5");
        wordsToCheckDog.add("1000");
        wordsToCheckDog.add("M");
        wordsToCheckDog.add("Y");
        wordsToCheckDog.add("N");
        wordsToCheckDog.add("Labrador Retriever");

        for(String word : wordsToCheckDog){
            assertTrue(validAPI.listDog(dog).contains(word));
            assertTrue(validAPI.listAllDogs().contains(word));
        }

        // cats
        ArrayList<String> wordsToCheckCat = new ArrayList<String>();
        wordsToCheckCat.add("Rex2");
        wordsToCheckCat.add("6");
        wordsToCheckCat.add("1001");
        wordsToCheckCat.add("F");
        wordsToCheckCat.add("Y");
        wordsToCheckCat.add("N");
        wordsToCheckCat.add("something");

        for(String word : wordsToCheckCat){
            assertTrue(validAPI.listCat(cat).contains(word));
            assertTrue(validAPI.listAllCats().contains(word));
        }

        // parrots
        ArrayList<String> wordsToCheckParrot = new ArrayList<String>();
        wordsToCheckParrot.add("Rex3");
        wordsToCheckParrot.add("7");
        wordsToCheckParrot.add("1002");
        wordsToCheckParrot.add("F");
        wordsToCheckParrot.add("Y");
        wordsToCheckParrot.add(parrot.getVocabularySize());

        for(String word : wordsToCheckParrot){
            assertTrue(validAPI.listParrot(parrot).contains(word));
            assertTrue(validAPI.listAllParrots().contains(word));
        }

        // testing specific lists
        assertTrue(validAPI.listAllDangerousDogs().contains("Labrador Retriever"));
        assertTrue(validAPI.listAllIndoorCats().contains("Rex2"));
        assertTrue(validAPI.listParrotsByVocabularySize(parrotVocabularySize).contains("Rex3"));

        // list by owner
        for(String word : wordsToCheckPet){
            assertTrue(validAPI.listAllPetsByOwner(owner.getName()).contains(word));
        }

        // list staying more than x days
        for(String word : wordsToCheckPet){
            assertTrue(validAPI.listAllPetsThatStayMoreThanXDays(2).contains(word));
        }

        // list staying more than x days but it will force empty
        assertTrue(validAPI.listAllPetsThatStayMoreThanXDays(7).contains("There are no pets staying more than 7 days."));

        // get weekly income, should not be 0, should be able to count all pets
        assertNotEquals(0, validAPI.getWeeklyIncome());

        // get average days per week stayed, should not be 0 as there dog is staying for 3 days
        assertNotEquals(0, validAPI.getAverageNumDaysPerWeekStayed());
    }

    @Test
    void testPetsUpdating() {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(100, "John", "0871234567");

        Dog dog = new Dog("Rex", 5, owner, 1000, 'M', true, 10, false, true, "Labrador Retriever");
        Dog dogUpdated = new Dog("RexUpdated", 6, owner, 1001, 'M', false, 11, false, true, "Labrador Retriever");

        pets.add(dog);

        validAPI.setPets(pets);

        // test if updating pet can return null
        assertNull(validAPI.updatePet(-1, null));

        // get updated pet
        assertEquals("RexUpdated", validAPI.updatePet(0, dogUpdated).getName());
    }

    @Test
    void testPetsSwapping() {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(100, "John", "0871234567");

        Dog dog = new Dog("Rex1", 5, owner, 1000, 'M', true, 10, false, true, "Labrador Retriever");
        Dog dog2 = new Dog("Rex2", 6, owner, 1001, 'M', false, 11, false, true, "Labrador Retriever");

        pets.add(dog);
        pets.add(dog2);

        validAPI.setPets(pets);

        // swap with pets passed
        validAPI.swapPets(dog, dog2);
        assertEquals(validAPI.getPetByIndex(0), dog2);

        // swap with indexes passed
        validAPI.swapPets(0, 1);
        assertEquals(validAPI.getPetByIndex(1), dog2);
    }

    @Test
    void testPetsLoading() throws Exception {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(200, "JohnOther", "0871234567");
        Dog dog = new Dog("RexOther", 5, owner, 1000, 'M', true, 10, false, false, "Labrador Retriever");

        // overwrite
        pets.add(dog);
        validAPI.setPets(pets);

        assertNotNull(validAPI.removePetAtPosition(0));
        assertNull(validAPI.getPetByIndex(0));

        assertNotNull(validAPI.getFile());

        // load in save file
        validAPI.load();

        // check if pets arraylist was updated
        assertNotNull(validAPI.findDogByOwnerAndBreedAndAge("John", "Labrador Retriever", 5));
        assertNull(validAPI.findDogByOwnerAndBreedAndAge("JohnOther", "Labrador Retriever", 5));
    }

    }