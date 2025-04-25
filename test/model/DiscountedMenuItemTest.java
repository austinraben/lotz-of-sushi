package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DiscountedMenuItemTest {

	@Test
	void testGetPriceDiscounted() {
		MenuItem pepsi = new DiscountedMenuItem(new MenuItem(FoodCourse.DRINKS, "", "Pepsi", 2.99, false,"Pepsi Cola"));
		
		assertEquals(pepsi.getPrice(), 2.99);
		HappyHourManager.toggleHappyHour();
		assertEquals((pepsi.getPrice()), 2.39);
	}
	
	@Test
	void testGetPriceNonDiscounted() {
		MenuItem misoSoup = new DiscountedMenuItem(new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false,"Yummy"));
		
		assertEquals(misoSoup.getPrice(), 6.99);
		HappyHourManager.toggleHappyHour();
		assertEquals((misoSoup.getPrice()), 6.99);
	}
	
	@Test
	void testCopyConstructor() {
		DiscountedMenuItem misoSoup = new DiscountedMenuItem(new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false,"Yummy"));
		DiscountedMenuItem copyMiso = new DiscountedMenuItem(misoSoup);
		assertEquals(misoSoup, copyMiso);
	}

}
