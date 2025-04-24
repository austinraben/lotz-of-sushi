package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HappyHourDecoratorTest {
	private Restaurant myRestaurant;
	private Menu appMenu, entreeMenu, drinkMenu;
	private MenuItem crabPuffs, austinsRoll, pepsi;
	
	@BeforeEach
	void setUpRestaurant() {
		// Instantiate Restaurant
		myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.loadMenuItems("/data/menu.txt");	
		
		// Instantiate Menus and MenuItems
		appMenu = myRestaurant.getMenuForCourse(FoodCourse.APPS);
		entreeMenu = myRestaurant.getMenuForCourse(FoodCourse.ENTREES);
		drinkMenu = myRestaurant.getMenuForCourse(FoodCourse.DRINKS);
		crabPuffs = appMenu.getMenuItem("Crab Puffs"); // 6.99 before discount, $5.59 after
		austinsRoll = entreeMenu.getMenuItem("Austin's Roll"); // 8.99
		pepsi = drinkMenu.getMenuItem("Pepsi"); // $2.99 before discount, $2.39 after
	}
	
	@AfterEach
	void turnOffHappyHour() {
		if (HappyHourManager.isHappyHour()) {
			HappyHourManager.toggleHappyHour();
		}
	}

	@Test
	void testHappyHourDecorator() {
		// Turn ON happy hour decorator (20% discount now applies to MenuItems on APPS or DRINKS Menu)
		HappyHourManager.toggleHappyHour();
		
		// Assign Server and Customer to Table
		myRestaurant.assignServerToTable("Austin Raben", 7);
		myRestaurant.seatCustomers(1, 7);
				
		// Instantiate OrderedItem's list of items
		List<OrderedItem> myOrderItems = new ArrayList<>();
		myOrderItems.add(new OrderedItem(crabPuffs, "None", crabPuffs));
		myOrderItems.add(new OrderedItem(austinsRoll, "Extra Eel Sauce", austinsRoll));
		myOrderItems.add(new OrderedItem(pepsi, "None", pepsi));

		// Test 1: Order Crab Puffs (APPS) and see Bill applies 20% discount
		myRestaurant.orderItem(7, 1, myOrderItems.get(0).getItemName(), "None");
		Bill myBill = myRestaurant.getBillByTable(7);
		assertEquals(5.59, myBill.getPriceBeforeTip(), 0.001);

		// Test 2: Order Austin's Roll (ENTRES) and see Bill does NOT apply 20% discount
		myRestaurant.orderItem(7, 1, myOrderItems.get(1).getItemName(), "Extra Eel Sauce");
		myBill = myRestaurant.getBillByTable(7);
		assertEquals(14.58, myBill.getPriceBeforeTip(), 0.001);

		// Test 3: Turn OFF Happy Hour decorator
		// Order Pepsi (DRINKS) and see Bill does NOT apply 20% discount anymore
		HappyHourManager.toggleHappyHour();
		myRestaurant.orderItem(7, 1, myOrderItems.get(2).getItemName(), "");
		myBill = myRestaurant.getBillByTable(7);
		assertEquals(17.57, myBill.getPriceBeforeTip(), 0.001);
	}
	
	@Test
	void testGetPrice() {
		HappyHourManager.toggleHappyHour();
		MenuItem discountedPepsi = new DiscountedMenuItem(pepsi);
    	Double discountedPrice = discountedPepsi.getPrice();
		assertEquals(2.39, discountedPrice, 0.001);
	}
}
