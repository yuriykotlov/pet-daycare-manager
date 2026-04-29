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
    void testPetsArraySetterGetter() {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(100, "John", "0871234567");
        Dog dog = new Dog("Rex", 5, owner, 1000, 'M', true, 10, false, false, "Labrador Retriever");

        pets.add(dog);

        validAPI.setPets(pets);

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

        pets.add(dog);
        pets.add(cat);
        pets.add(parrot);

        validAPI.setPets(pets);

        // get pet
        assertEquals(dog, validAPI.getPetById(1000));
        assertEquals(dog, validAPI.getPetByIndex(0));
        assertEquals(cat, validAPI.getPetByName("Rex2"));

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
    }
    @Test
    void testPetsUpdating() {
        ArrayList<Pet> pets = new ArrayList<>();

        Owner owner = new Owner(100, "John", "0871234567");

        Dog dog = new Dog("Rex", 5, owner, 1000, 'M', true, 10, false, true, "Labrador Retriever");
        Dog dogUpdated = new Dog("RexUpdated", 6, owner, 1001, 'M', false, 11, false, true, "Labrador Retriever");

        pets.add(dog);

        validAPI.setPets(pets);

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

    }