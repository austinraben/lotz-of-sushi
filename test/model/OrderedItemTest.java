package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderedItemTest {

	@Test
	void testGetModification() {
		MenuItem tunaRoll = new MenuItem(FoodCourse.ENTREES, "Regular Roll", "Tuna Roll", 6.99, true, "tuna and avocado");
		OrderedItem tuna = new OrderedItem(tunaRoll, "No avocado", tunaRoll);
		assertEquals("No avocado", tuna.getModification());
	}
	
	@Test
	void testGetModificationNotAllowed() {
		MenuItem misoSoup = new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false, "tofu seaweed and green onions in a miso broth");
		OrderedItem soup = new OrderedItem(misoSoup, "modification", misoSoup);
		assertEquals("This item can not be modified", soup.getModification());
	}
	
	@Test
	void testGetDecoratedItem() {
		MenuItem misoSoup = new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false, "tofu seaweed and green onions in a miso broth");
        HappyHourManager.toggleHappyHour();
		MenuItem discountedMisoSoup = new MenuItem(misoSoup);
		OrderedItem orderedDiscountedMisoSoup = new OrderedItem(misoSoup, "modification", discountedMisoSoup);
		assertEquals(discountedMisoSoup, orderedDiscountedMisoSoup.getDecoratedItem());
	}
	
	@Test
	void testGetPrice() {
		MenuItem misoSoup = new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false, "tofu seaweed and green onions in a miso broth");
		OrderedItem soup = new OrderedItem(misoSoup, "modification", misoSoup);
		assertEquals(soup.getPrice(), 6.99);
		
	}
}
