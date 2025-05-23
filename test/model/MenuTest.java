package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;

public class MenuTest {
    private Menu appMenu;
    private Menu drinkMenu;
    private Menu entreeMenu;
    private Menu dessertMenu;
    private MenuItem tunaRoll;
    private MenuItem californiaRoll;
    private MenuItem misoSoup;
    private MenuItem greenTea;
    private MenuItem vanillaMochi;

    @Before
    public void setUp() {
        appMenu = new Menu(FoodCourse.APPS);
        drinkMenu = new Menu(FoodCourse.DRINKS);
        entreeMenu = new Menu(FoodCourse.ENTREES);
        dessertMenu = new Menu(FoodCourse.DESSERTS);
        tunaRoll = new MenuItem(FoodCourse.ENTREES, "Regular Roll", "Tuna Roll", 6.99, true, "tuna and avocado");
        californiaRoll = new MenuItem(FoodCourse.ENTREES, "Regular Roll", "California Roll", 6.99, true, "cucumber avocado and imitation crab");
        misoSoup = new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false, "tofu seaweed and green onions in a miso broth");
        greenTea = new MenuItem(FoodCourse.DRINKS, "Tea", "Green Tea", 3.99, false, "");
        vanillaMochi = new MenuItem(FoodCourse.DESSERTS, "Mochi", "Vanilla Mochi", 4.99, false, "sweet mochi dough filled with vanilla ice cream");
    }

    @Test
    public void testgetCourse() {
        assertEquals(FoodCourse.APPS, appMenu.getCourse());
        assertEquals(FoodCourse.DRINKS, drinkMenu.getCourse());
        assertEquals(FoodCourse.ENTREES, entreeMenu.getCourse());
        assertEquals(FoodCourse.DESSERTS, dessertMenu.getCourse());
    }
    
    @Test
    public void testAddAndGetItems() {
    	entreeMenu.addItem(tunaRoll);
        entreeMenu.addItem(californiaRoll);
        entreeMenu.addItem(misoSoup);
        assertEquals(3, entreeMenu.getItems().size());
        assertEquals(tunaRoll, entreeMenu.getMenuItem("Tuna Roll"));
        assertEquals(californiaRoll, entreeMenu.getMenuItem("California Roll"));
        assertEquals(misoSoup, entreeMenu.getMenuItem("Miso Soup"));
        assertNull(entreeMenu.getMenuItem("Nonexistent Item"));
    }

    @Test
    public void testContainsMenuItem() {
        entreeMenu.addItem(californiaRoll);
        assertTrue(entreeMenu.containsMenuItem("California Roll"));
        assertFalse(entreeMenu.containsMenuItem("Miso Soup"));
    }

    @Test
    public void testIterator() {
        entreeMenu.addItem(tunaRoll);
        entreeMenu.addItem(californiaRoll);
        entreeMenu.addItem(misoSoup);
        Iterator<String> iterator = entreeMenu.iterator();
        assertTrue(iterator.hasNext());
        String item1 = iterator.next();
        assertTrue(iterator.hasNext());
        String item2 = iterator.next();
        assertNotEquals(item1, item2);
        String item3 = iterator.next();
        assertNotEquals(item2, item3);
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testCopyConstructor() {
    	entreeMenu.addItem(californiaRoll);
        entreeMenu.addItem(misoSoup);
        
        Menu myMenuCopy = new Menu(entreeMenu);
        assertEquals(myMenuCopy.getCourse(), entreeMenu.getCourse());
        assertTrue(myMenuCopy.containsMenuItem("California Roll"));
        assertTrue(myMenuCopy.containsMenuItem("Miso Soup"));
        
    }
    
    @Test
    public void testPrintMenu() {
        entreeMenu.addItem(californiaRoll);
        entreeMenu.addItem(misoSoup);
        drinkMenu.addItem(greenTea);
        dessertMenu.addItem(vanillaMochi);
        entreeMenu.printMenu();
        drinkMenu.printMenu();
        dessertMenu.printMenu();
    }
}