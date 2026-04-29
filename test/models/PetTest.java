package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PetTest {
    /*As Pet is an abstract class, we cannot instantiate a Pet object
       so instead we instantiate a Dog object and just test the Pet methods from it.
       */
    Owner owner;

    Dog dogNormal, onEdgeDog, belowEdgeDog, aboveEdgeDog;
    Cat cat;
    boolean[] days;
    @BeforeEach
    void setUp() {
        owner = new Owner(100, "John Doe", "0871234567");
        dogNormal = new Dog("Buddy", 5, owner, 1000, 'M', true, 10, false, false, "Labrador Retriever");
        belowEdgeDog = new Dog("1234567890123456789",10, owner,1002,  'F', true, 199, false, false, "Labrador Retriever");
        onEdgeDog = new Dog("12345678901234567890", 3, owner, 1001, 'M', true, 20, false, true, "Bulldog");
        aboveEdgeDog = new Dog("123456789012345678901", 4, owner, 1004, 'F', false, 99, true, true, "Bulldog");
        days = new boolean[7];
        days[0]  = false; //Monday
        days[1]  = true;
        days[2]  = true;
        days[3]  = true;
        days[4]  = false;
        days[5]  = false;
        days[6]  = true; //Sunday

        cat = new Cat("cat", 5, owner, 1001, 'F', true, 10, false, "Tunnel", false);
     }
    @Test
    void testInitName() {
        assertEquals("Buddy", dogNormal.getName());
        assertEquals("1234567890123456789", belowEdgeDog.getName() );
        assertEquals("12345678901234567890", onEdgeDog.getName() );
        assertEquals("123456789012345678901", aboveEdgeDog.getName() );
    }
    @Test
    void testSetId() {
        assertEquals(1000, dogNormal.getId());
        dogNormal.setId(1001);
        assertEquals(1001, dogNormal.getId());
        dogNormal.setId(2000);
        assertEquals(2000, dogNormal.getId());
        dogNormal.setId(999);
        assertEquals(999, dogNormal.getId());
    }
    @Test
    void testSetName() {
        assertEquals("Buddy", dogNormal.getName());
        dogNormal. setName("Boda");
        assertEquals("Boda", dogNormal.getName());
        dogNormal. setName("1234567890123456789");
        assertEquals("1234567890123456789", dogNormal.getName());
        dogNormal. setName("12345678901234567890");
        assertEquals("12345678901234567890", dogNormal.getName());
        dogNormal. setName("boda");
        assertEquals("boda", dogNormal.getName());
        dogNormal.setName("1234567890123456789012");
        assertEquals("1234567890123456789012", dogNormal.getName());
    }
    @Test
    void testConstructorAndGetters() {
        assertEquals("Buddy", dogNormal.getName());
        assertEquals(5, dogNormal.getAge());
        assertEquals(owner, dogNormal.getOwner());
        assertEquals(1000, dogNormal.getId());
    }

    @Test
    void testSetAge() {
        assertEquals(5, dogNormal.getAge());
        dogNormal.setAge(10);
        assertEquals(10, dogNormal.getAge());
        dogNormal.setAge(0);
        assertEquals(0, dogNormal.getAge());
        dogNormal.setAge(19);
        assertEquals(19, dogNormal.getAge());
        dogNormal.setAge(20);
        assertEquals(20, dogNormal.getAge());
        // below and above
        dogNormal.setAge(-1);
        assertEquals(20, dogNormal.getAge()); //no change
        //reset
        dogNormal.setAge(10);
        assertEquals(10, dogNormal.getAge());
        dogNormal.setAge(21);
        assertEquals(21, dogNormal.getAge()); // no change
    }
    @Test
    void testSetOwner() {
        Owner newOwner = new Owner(101, "Jane Smith", "0871234567");
        dogNormal.setOwner(newOwner);
        assertEquals(newOwner, dogNormal.getOwner());
    }

    @Test
    void testDaysAttendingSetterGetter() {
        dogNormal.setDaysAttending(days);
        assertArrayEquals(days, dogNormal.getDaysAttending());
    }
    @Test
    void testcheckIn() {
        boolean[] days = {true, false, false, true, false, false, false};
        dogNormal.setDaysAttending(days);
        assertFalse(dogNormal.getDaysAttending()[1]);
        dogNormal.checkIn(1);
        assertTrue(dogNormal.getDaysAttending()[1]);

        assertTrue(dogNormal.getDaysAttending()[3]);
        dogNormal.checkIn(3 );
        assertTrue(dogNormal.getDaysAttending()[3]);
        dogNormal.setDaysAttending(days);
        dogNormal.checkIn(-1);
        assertEquals(days, dogNormal.getDaysAttending()); // no change
        dogNormal.checkIn(dogNormal.getDaysAttending().length);
        assertEquals(days, dogNormal.getDaysAttending()); // no change

    }

    @Test
    void testcheckOut() {
        dogNormal.setDaysAttending(days);
        assertTrue(dogNormal.getDaysAttending()[1]);
        dogNormal.checkOut(1);
        assertFalse(dogNormal.getDaysAttending()[1]);

        assertFalse(dogNormal.getDaysAttending()[4]);
        dogNormal.checkOut(4);
        assertFalse(dogNormal.getDaysAttending()[4]);

        dogNormal.setDaysAttending(days);  // reset
        dogNormal.checkOut(-1);
        assertEquals(days, dogNormal.getDaysAttending());
        dogNormal.checkOut(dogNormal.getDaysAttending().length);
        assertEquals(days, dogNormal.getDaysAttending());
    }

    @Test
    void testNumOfDaysAttending(){
        dogNormal.setDaysAttending(days);
        assertEquals(4, dogNormal.numOfDaysAttending());
        dogNormal.checkIn(0);
        assertEquals(5, dogNormal.numOfDaysAttending());

        boolean[] noDaysAttending = {false, false, false, false, false,false ,  false};
        dogNormal.setDaysAttending(noDaysAttending);
        assertEquals(0, dogNormal.numOfDaysAttending());

        boolean[] allDaysAttending = {true, true, true, true, true, true , true};
        dogNormal.setDaysAttending(allDaysAttending);
        assertEquals(7, dogNormal.numOfDaysAttending());

        // test if specific day updating works
        dogNormal.updateSpecificDayAttending(0, false);
        assertFalse(dogNormal.getDaysAttending()[0]);

        // making sure days attending string isn't empty
        assertNotEquals("", dogNormal.displayStringOfDaysAttending());
    }
    @Test
    void testToString() {
        String result = dogNormal.toString();
        assertTrue(result.contains("Buddy"));
        assertTrue(result.contains("Age: 5"));
        assertTrue(result.contains("Id: 1000"));
        assertTrue(result.contains("Breed: Labrador Retriever"));
        assertTrue(result.contains("Dangerous Breed: N"));
        assertTrue(result.contains("Current Rate: "));
        assertTrue(result.contains("Weekly Fee: "));

        String result2 = cat.toString();
        assertTrue(result2.contains("Cat"));
        assertTrue(result2.contains("Age: 5"));
        assertTrue(result2.contains("Id: 1001"));
        assertTrue(result2.contains("Favourite Toy: Tunnel"));
        assertTrue(result2.contains("Indoor Cat: N"));
        assertTrue(result2.contains("Weekly Fee: "));
    }

    @Test
    public void testCat(){
        assertEquals("Tunnel", cat.getFavouriteToy());
        cat.setFavouriteToy("Other");
        assertEquals("Other", cat.getFavouriteToy());

        assertFalse(cat.isIndoorCat());
        cat.setIndoorCat(true);
        assertTrue(cat.isIndoorCat());
    }
}