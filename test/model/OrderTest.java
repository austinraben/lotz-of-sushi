package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class OrderTest {

	@Test
	void testOrderItem() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder = new Order(1, "Billy Bob");
	    MenuItem colinsRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Colin's Roll", 8.99, true, "description omitted");
		myOrder.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu(), colinsRoll);
		
		assertEquals(myOrder.getItems().get(0).getItemName(), "Colin's Roll");
		assertEquals(myOrder.getBill().getPriceBeforeTip(), 8.99);
	}
	
	@Test
	void testCopyOrder() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(23, "Billy Bob");
	    MenuItem colinsRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Colin's Roll", 8.99, true, "description omitted");
	    MenuItem rubysRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Ruby's Roll", 8.99, true, "description omitted");
		myOrder1.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu(), colinsRoll);
		myOrder1.orderItem("Ruby's Roll", "None", myRestaurant.getEntreeMenu(), rubysRoll);
		Order myOrder2 = new Order(myOrder1);
		
		assertEquals(myOrder2.getItems().get(0).getItemName(), "Colin's Roll");
		assertEquals(myOrder2.getItems().get(1).getItemName(), "Ruby's Roll");
		assertEquals(myOrder2.getBill().getPriceBeforeTip(), 17.98);
	}
	
	@Test
	void testMakeTip() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(23, "Billy Bob");
		MenuItem colinsRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Colin's Roll", 8.99, true, "description omitted");
	    MenuItem rubysRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Ruby's Roll", 8.99, true, "description omitted");
		myOrder1.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu(), colinsRoll);
		myOrder1.orderItem("Ruby's Roll", "None", myRestaurant.getEntreeMenu(), rubysRoll);
		
		myOrder1.makeTip(2.50);
		
		assertEquals(myOrder1.getTip(), 2.50);
		assertEquals(myOrder1.getBill().getPriceAfterTip(), 20.48);
	}
	
	
	@Test
	void testToString() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(23, "Billy Bob");
		MenuItem colinsRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Colin's Roll", 8.99, true, "description omitted");
	    MenuItem rubysRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Ruby's Roll", 8.99, true, "description omitted");
		myOrder1.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu(), colinsRoll);
		myOrder1.orderItem("Ruby's Roll", "No Shrimp", myRestaurant.getEntreeMenu(), rubysRoll);
		myOrder1.makeTip(2.50);
		
		String compare = "---------------------\n" +
						"Order Number: " + 23 + "\n" +
						"Server: Billy Bob\n\n" + "ITEMS:\n";
		compare += "\t$8.99 - Colin's Roll\n";
		compare += "\t$8.99 - Ruby's Roll\n\t\tModification: No Shrimp\n";
		compare += "AMOUNT: $17.98\nTIP: $2.5\n\nTOTAL: $20.48\n---------------------\n";
		
		assertEquals(compare, myOrder1.toString());
		
		
	}
	
	
	@Test
	void testSetItems() {
		Restaurant myRestaurant = new Restaurant("Test");
		Order myOrder1 = new Order(123, "Billy Bob");
	    MenuItem austinsRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Austin's Roll", 8.99, true, "description omitted");
	    MenuItem lisettesRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Lisette's Roll", 8.99, true, "description omitted");
	    myOrder1.orderItem("Austin's Roll", "None", myRestaurant.getEntreeMenu(), austinsRoll);
		myOrder1.orderItem("Lisette's Roll", "None", myRestaurant.getEntreeMenu(), lisettesRoll);
		
		List<OrderedItem> myOrderItems = new ArrayList<>();
		MenuItem greenTea = myRestaurant.getDrinkMenu().getMenuItem("Green Tea");
		myOrderItems.add(new OrderedItem(greenTea, "None", greenTea));
		myOrder1.setItems(myOrderItems);
		
		assertEquals(myOrder1.getItems().get(0).getItemName(), "Green Tea");
	}
	
	@Test
	void testGetOrderNum() {
		Order myOrder1 = new Order(2123213, "Billy Bob");
		assertEquals(myOrder1.getOrderNum(), 2123213);
	}
	
	@Test
	void testChangeBillTotal() {
		Order myOrder1 = new Order(3, "Billy Bob");
		myOrder1.changeBillTotal(33.45);
		assertEquals(myOrder1.getBill().getPriceBeforeTip(), 33.45);
	}
	
	@Test
    public void testEqualsSameValues() {
		Restaurant myRestaurant = new Restaurant("Test");
        Order o1 = new Order(1, "Billy Bob");
        Order o2 = new Order(1, "Billy Bob");

        List<OrderedItem> items = new ArrayList<>();
		MenuItem greenTea = myRestaurant.getDrinkMenu().getMenuItem("Green Tea");
        items.add(new OrderedItem(greenTea, "None", greenTea));
        o1.setItems(items);
        o2.setItems(new ArrayList<>(items));

        o1.makeTip(5.0);
        o2.makeTip(5.0);

        assertEquals(o1, o2);
    }
	
	 @Test
	 public void testEqualsDifferentItems() {
		 Restaurant myRestaurant = new Restaurant("Test");
	        Order o1 = new Order(1, "Billy Bob");
	        Order o2 = new Order(1, "Billy Bob");

	        List<OrderedItem> items1 = new ArrayList<>();
			MenuItem greenTea = myRestaurant.getDrinkMenu().getMenuItem("Green Tea");
	        items1.add(new OrderedItem(greenTea, "None", greenTea));
	        o1.setItems(items1);

	        List<OrderedItem> items2 = new ArrayList<>();
			MenuItem jasmineTea = myRestaurant.getDrinkMenu().getMenuItem("Jasmine Tea");
	        items2.add(new OrderedItem(jasmineTea, "None", jasmineTea));
	        o2.setItems(items2);

	        o1.makeTip(5.0);
	        o2.makeTip(5.0);

	        assertNotEquals(o1, o2);
	    }
	 
	 @Test
	 public void testEqualsDifferentTips() {
	        Order o1 = new Order(1, "Billy Bob");
	        Order o2 = new Order(1, "Billy Bob");

	        o1.makeTip(3.0);
	        o2.makeTip(5.0);

	        assertNotEquals(o1, o2);
	    }
	 
	 @Test
	 public void testEqualsSameObject() {
	        Order o1 = new Order(1, "Billy Bob");
	        assertEquals(o1, o1);
	    }

	 @Test
	 public void testEqualsNull() {
	        Order o1 = new Order(1, "Billy Bob");
	        assertNotEquals(o1, null);
	    }

	 @Test
	 public void testEqualsDifferentClass() {
	        Order o1 = new Order(1, "Billy Bob");
	        assertNotEquals(o1, "NotAnOrder");
	    }
	    
	 @Test
	 public void testHashCodeConsistency() {
	    	Restaurant myRestaurant = new Restaurant("Test");
	        Order o1 = new Order(1, "Billy Bob");
	        o1.makeTip(4.0);

	        List<OrderedItem> items = new ArrayList<>();
			MenuItem jasmineTea = myRestaurant.getDrinkMenu().getMenuItem("Jasmine Tea");
	        items.add(new OrderedItem(jasmineTea, "None", jasmineTea));
	        o1.setItems(items);

	        int hash1 = o1.hashCode();
	        int hash2 = o1.hashCode();

	        assertEquals(hash1, hash2, "hashCode should be consistent on same object");
	    }
}
