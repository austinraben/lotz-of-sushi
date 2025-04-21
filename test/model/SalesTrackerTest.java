package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SalesTrackerTest {

	@Test
	void testUpdateOrder() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		SalesTracker mySales = myRestaurant.getSalesTracker();
		Order order = new Order(1, "Billy Bob");
		order.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		mySales.updateOrder(order);
		
		assertEquals(mySales.getTotalSale(), 6.99);
		assertEquals(mySales.getTotalQuantity(), 1);
		
		assertEquals(mySales.getQuantityForItem(myRestaurant.getEntreeMenu().getMenuItem("Miso Soup")), 1);
		assertEquals(mySales.getSaleForItem(myRestaurant.getEntreeMenu().getMenuItem("Miso Soup")), 6.99);
	}
	
	@Test
	void testUpdateServerTips() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		assertEquals(mySales.getTotalTips(), 5.50);
		assertEquals(mySales.getTipForServer(new Server("Colin Gale")), 5.50);
	}
	
	@Test
	void testQuantitySoldSortedString() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Colin's Roll", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		String compare = "---Sorted Menu Items Sold---\n";
		compare += "Miso Soup" + ": 2\n";
		compare += "Colin's Roll" + ": 1\n";
		
		
        for (MenuItem menuItem : mySales.getQuantitySold().keySet()) {
        	String name = menuItem.getItemName();
        	if (name.equals("Miso Soup") || name.equals("Colin's Roll"))
        		continue;
        	else
        		compare += name + ": 0\n";
        }
        
        assertEquals(compare, mySales.quantitySoldSortedString());
		
	}
	
	@Test
	void testTotalSalesSortedString() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Colin's Roll", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		String compare = "---Sorted Menu Items Sales---\n";
		compare += "Miso Soup" + ": $13.98\n";
		compare += "Colin's Roll" + ": $8.99\n";
		
        for (MenuItem menuItem : mySales.getTotalSales().keySet()) {
        	String name = menuItem.getItemName();
        	if (name.equals("Miso Soup") || name.equals("Colin's Roll"))
        		continue;
        	else
        		compare += name + ": $0.0\n";
        }
        
        compare += "\nTotal Sales: $22.97\n";
        
        assertEquals(compare, mySales.totalSalesSortedString());
		
	}
	
	@Test
	void testQuantitySoldString() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		String compare = "---Menu Items Sold---\n";
		
		
        for (MenuItem menuItem : mySales.getQuantitySold().keySet()) {
        	String name = menuItem.getItemName();
        	if (name.equals("Miso Soup"))
        		compare += name + ": 1\n";
        	else
        		compare += name + ": 0\n";
        }
        
        assertEquals(compare, mySales.quantitySoldString());
		
	}
	
	@Test
	void testTotalSalesString() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		String compare = "---Menu Items Sales---\n";
		
		
        for (MenuItem menuItem : mySales.getTotalSales().keySet()) {
        	String name = menuItem.getItemName();
        	if (name.equals("Miso Soup"))
        		compare += name + ": $6.99\n";
        	else
        		compare += name + ": $0.0\n";
        }
        
        compare += "\nTotal Sales: $6.99\n";
        
        assertEquals(compare, mySales.totalSalesString());
		
	}
	
	
	@Test
	void testTotalTipsString() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		String compare = "---Server Tips---\n" +
						"Austin Raben: $0.0\n" +
						"Lisette Robles: $0.0\n" +
						"Billy Bob: $0.0\n" +
						"Colin Gale: $5.5\n" +
						"Ruby Gilliland: $0.0\n" +
						"\nTotal Tips Collected: $5.5\n";
		assertEquals(compare, mySales.totalTipsString());
	}
	
	
	@Test
	void testTotalTipsSortedString() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.seatCustomers(1, 2);
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.assignServerToTable("Ruby Gilliland", 2);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		myRestaurant.orderItem(2, 1, "Ruby's Roll", "None");
		myRestaurant.closeOrder(2, 1, 2.50);
		
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		String compare = "---Sorted Server Tips---\n" +
						"Colin Gale: $5.5\n" +
						"Ruby Gilliland: $2.5\n" +
						"Billy Bob: $0.0\n" +
						"Lisette Robles: $0.0\n" +
						"Austin Raben: $0.0\n" +
						"\nTotal Tips Collected: $8.0\n";
		assertEquals(compare, mySales.totalTipsSortedString());
	}
	
	@Test
	void testGetHighestTippedServer() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.seatCustomers(1, 2);		
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.assignServerToTable("Ruby Gilliland", 2);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(1, 1, 5.50);
		myRestaurant.orderItem(2, 1, "Ruby's Roll", "None");
		myRestaurant.closeOrder(2, 1, 2.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		Server myServer = mySales.getHighestTippedServer();
		assertEquals("Colin Gale", myServer.getServerName());
	}
}
