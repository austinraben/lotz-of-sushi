package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class OrderTest {

	@Test
	void testOrderItem() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder = new Order(1);
		myOrder.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu());
		
		assertEquals(myOrder.getItems().get(0).getItemName(), "Colin's Roll");
		assertEquals(myOrder.getBill().getPriceBeforeTip(), 8.99);
	}
	
	@Test
	void testCopyOrder() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(23);
		myOrder1.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu());
		myOrder1.orderItem("Ruby's Roll", "None", myRestaurant.getEntreeMenu());
		Order myOrder2 = new Order(myOrder1);
		
		assertEquals(myOrder2.getItems().get(0).getItemName(), "Colin's Roll");
		assertEquals(myOrder2.getItems().get(1).getItemName(), "Ruby's Roll");
		assertEquals(myOrder2.getBill().getPriceBeforeTip(), 17.98);
	}
	
	@Test
	void testMakeTip() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(23);
		myOrder1.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu());
		myOrder1.orderItem("Ruby's Roll", "None", myRestaurant.getEntreeMenu());
		
		myOrder1.makeTip(2.50);
		
		assertEquals(myOrder1.getTip(), 2.50);
		assertEquals(myOrder1.getBill().getPriceAfterTip(), 20.48);
	}
	
	
	@Test
	void testToString() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(23);
		myOrder1.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu());
		myOrder1.orderItem("Ruby's Roll", "No Shrimp", myRestaurant.getEntreeMenu());
		myOrder1.makeTip(2.50);
		
		String compare = "---------------------\n" +
						"Order Number: " + 23 + "\n" +
						"Server: NONE\n\n" + "ITEMS:\n";
		compare += "\t$8.99 - Colin's Roll\n";
		compare += "\t$8.99 - Ruby's Roll\n\t\tModification: No Shrimp\n";
		compare += "AMOUNT: $17.98\nTIP: $2.5\n\nTOTAL: $20.48\n---------------------\n";
		
		assertEquals(compare, myOrder1.toString());
		
		
	}
	
	
	@Test
	void testSetItems() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(123);
		myOrder1.orderItem("Austin's Roll", "None", myRestaurant.getEntreeMenu());
		myOrder1.orderItem("Lisette's Roll", "None", myRestaurant.getEntreeMenu());
		
		List<OrderedItem> myOrderItems = new ArrayList<>();
		myOrderItems.add(new OrderedItem(myRestaurant.getDrinkMenu().getMenuItem("Green Tea"), "None"));
		myOrder1.setItems(myOrderItems);
		
		assertEquals(myOrder1.getItems().get(0).getItemName(), "Green Tea");
	}
	
	@Test
	void testGetOrderNum() {
		Order myOrder1 = new Order(2123213);
		assertEquals(myOrder1.getOrderNum(), 2123213);
	}

}
