package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderedItemTest {

	@Test
	void testGetModification() {
		MenuItem tunaRoll = new MenuItem(FoodCourse.ENTREES, "Regular Roll", "Tuna Roll", 6.99, true, "tuna and avocado");
		OrderedItem tuna = new OrderedItem(tunaRoll, "No avocado");
		assertEquals("No avocado", tuna.getModification());
	}
	
	@Test
	void testGetModificationNotAllowed() {
		MenuItem misoSoup = new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false, "tofu seaweed and green onions in a miso broth");
		OrderedItem soup = new OrderedItem(misoSoup, "modification");
		assertEquals("This item can not be modified", soup.getModification());
	}

}
