package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MenuItemTest {
	private MenuItem water;
    private MenuItem tunaRoll;
    private MenuItem californiaRoll;
    private MenuItem misoSoup;
	private MenuItem copy;
	
    @Before
    public void setUp() {
    	water = new MenuItem(FoodCourse.DRINKS, "Water", "Bottled Water", 1.99, false, "");
		tunaRoll = new MenuItem(FoodCourse.ENTREES, "Regular Roll", "Tuna Roll", 6.99, true, "tuna and avocado");
	    californiaRoll = new MenuItem(FoodCourse.ENTREES, "Regular Roll", "California Roll", 6.99, true, "cucumber avocado and imitation crab");
	    misoSoup = new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false, "tofu seaweed and green onions in a miso broth");
	    copy = new MenuItem(tunaRoll);
    }
    
    @Test
    public void testCopyConstructor() {
        assertEquals(tunaRoll.getFoodCourse(), copy.getFoodCourse());
        assertEquals(tunaRoll.getSpecificCategory(), copy.getSpecificCategory());
        assertEquals(tunaRoll.getItemName(), copy.getItemName());
        assertEquals(tunaRoll.getPrice(), copy.getPrice(), 0.001);
        assertEquals(tunaRoll.isModifiable(), copy.isModifiable());
        assertEquals(tunaRoll.getDescription(), copy.getDescription());
    }

    @Test
    public void testEqualsAndHashCode() {
        MenuItem nullMenuItem = null;
        assertEquals(tunaRoll, tunaRoll);
        assertEquals(tunaRoll, copy);
        assertNotEquals(tunaRoll, nullMenuItem);
        assertNotEquals(tunaRoll, new Object());
        assertNotEquals(tunaRoll, californiaRoll);
        assertEquals(tunaRoll.hashCode(), copy.hashCode());
    }

    @Test
    public void testEmptyDescriptionAndCategory() {
        assertEquals("", misoSoup.getSpecificCategory());
        assertEquals("", water.getDescription());
    }
    
    // this also tests happy hour now
    @Test
    public void testOrderedItemConstructor() {
        // Test with modifiable item
        OrderedItem orderedTunaRoll = new OrderedItem(tunaRoll, "No avocado", copy);
        assertEquals("No avocado", orderedTunaRoll.getModification());
        assertEquals("Tuna Roll", orderedTunaRoll.getItemName());
        assertEquals(6.99, orderedTunaRoll.getPrice(), 0.001);

        // Test with non-modifiable item
        OrderedItem orderedMisoSoup = new OrderedItem(misoSoup, "No miso with my miso soup", misoSoup);
        assertEquals("This item can not be modified", orderedMisoSoup.getModification());
        assertEquals("Miso Soup", orderedMisoSoup.getItemName());
        assertEquals(6.99, misoSoup.getPrice(), 0.001);
    }
}