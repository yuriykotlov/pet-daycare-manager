package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BirdTest {

//public Parrot(String name, int age, Owner owner, int id, double wingSpan, boolean canFly, int vocabularySize) {
    Owner owner;
    Parrot bird;

    @BeforeEach
    void setUp() {
        owner = new Owner(1234, "Jane Doe", "87654321");
        bird = new Parrot("Tweety", 2, owner, 100, 0.5, true, 0);
    }

    // --- Constructor & Getters ---

    @Test
    void testConstructorAndGetters() {
        assertEquals(3.0, bird.getWingSpan());
        assertTrue(bird.canFly());
    }

    // --- Setters ---

    @Test
    void testSetWingSpan() {
        bird.setWingSpan(1.2);
        assertEquals(1.2, bird.getWingSpan());
    }

    @Test
    void testSetCanFly() {
        bird.setCanFly(false);
        assertFalse(bird.canFly());
    }

    // --- equals() ---

    @Test
    void testEqualsFirstLine() {
        // o == null branch
        assertFalse(bird.equals(null));

        // getClass() != o.getClass() branch (Owner is not a Bird)
        assertFalse(bird.equals(owner));
    }

    @Test
    void testEqualsSameFields() {
        Parrot sameBird = new Parrot("Polly", 5, owner, 200, 0.5, true, 20);
        assertNotEquals(bird, sameBird);
    }

    @Test
    void testEqualsDifferentWingSpan() {
        Parrot differentWingSpan = new Parrot("Tweety", 2, owner, 100, 1.0, true, 20);
        assertEquals(bird.getWingSpan(), differentWingSpan.getWingSpan());
    }

    @Test
    void testEqualsDifferentCanFly() {
        Parrot differentCanFly = new Parrot("Tweety", 2, owner, 100, 0.5, false, 20);
        assertNotEquals(bird.canFly(), differentCanFly.canFly());
    }


    // --- toString() ---

    @Test
    void testToString() {
        String result = bird.toString();
        assertTrue(result.contains("Age: 2"));
        assertTrue(result.contains("Wing Span: 3.0"));
        assertTrue(result.contains("Can Fly: Y"));
        bird.setCanFly(false);
        result = bird.toString();
        assertTrue(result.contains("Can Fly: N"));
    }
}
