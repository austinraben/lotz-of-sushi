package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SalesTrackerTest {

	@Test
	void testUpdateOrder() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		SalesTracker mySales = myRestaurant.getSalesTracker();
		Order order = new Order(1);
		order.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		order.closeOrder();
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
		Table myTable = myRestaurant.getTableByNumber(1);
		myRestaurant.assignServerToTable("Colin Gale", myTable);
		myRestaurant.orderItem(myTable, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(myTable, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		assertEquals(mySales.getTotalTips(), 5.50);
		assertEquals(mySales.getTipForServer(new Server("Colin Gale")), 5.50);
	}
	
	
	@Test
	void testQuantitySoldString() {
		Restaurant myRestaurant = new Restaurant("Test Sales");
		myRestaurant.seatCustomers(1, 1);
		Table myTable = myRestaurant.getTableByNumber(1);
		myRestaurant.assignServerToTable("Colin Gale", myTable);
		myRestaurant.orderItem(myTable, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(myTable, 1, 5.50);
		
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
		Table myTable = myRestaurant.getTableByNumber(1);
		myRestaurant.assignServerToTable("Colin Gale", myTable);
		myRestaurant.orderItem(myTable, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(myTable, 1, 5.50);
		
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
		Table myTable = myRestaurant.getTableByNumber(1);
		myRestaurant.assignServerToTable("Colin Gale", myTable);
		myRestaurant.orderItem(myTable, 1, "Miso Soup", "None");
		myRestaurant.closeOrder(myTable, 1, 5.50);
		
		SalesTracker mySales = myRestaurant.getSalesTracker();
		
		String compare = "---Server Tips---\n" +
						"Austin Raben: $0.0\n" +
						"Lisette Robles: $0.0\n" +
						"Colin Gale: $5.5\n" +
						"Ruby Gilliland: $0.0\n" +
						"\nTotal Tips Collected: $5.5\n";
		assertEquals(compare, mySales.totalTipsString());
	}

}
