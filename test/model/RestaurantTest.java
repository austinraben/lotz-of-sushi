package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RestaurantTest {

	@Test
	void testAssignServerToTable() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Austin Raben", 13);
		myRestaurant.assignServerToTable("Colin Gale", 12);
		myRestaurant.assignServerToTable("Ruby Gilliland", 12);
		assertEquals(myRestaurant.getServerByTable(13), "Austin Raben");
		assertEquals(myRestaurant.getServerByTable(12), "Colin Gale");
		
	}
	
	@Test
	void testHireServer() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.hireServers("Billy Bob");
		assertTrue(myRestaurant.serverIsHired("Billy Bob"));
	}
	
	@Test
	void testRemoveServerFromTable() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 22);
		myRestaurant.removeServerFromTable("Colin Gale", 22);
		myRestaurant.removeServerFromTable("Colin Gale", 13);
		
		assertEquals(myRestaurant.getServerByTable(22), "");
	}
	
	@Test
	void testOrderItem() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Spider Roll", "No Mayo");
		
		Order myOrder = myRestaurant.getOrderFromCustomer(1, 1);
		
		assertEquals(myOrder.getItems().get(0).getItemName(), "Miso Soup");
		assertEquals(myOrder.getItems().get(0).getModification(), "This item can not be modified");
		assertEquals(myOrder.getItems().get(1).getItemName(), "Spider Roll");
		assertEquals(myOrder.getItems().get(1).getModification(), "No Mayo");
		
	}
	
	@Test
	void testCloseOrder() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Spider Roll", "No Mayo");
		
		myRestaurant.closeOrder(1, 1, 2.50);
		Order myOrder = myRestaurant.getOrderFromCustomer(1, 1);
		Order myClosedOrder = myRestaurant.getClosedOrders().get(0);
		assertEquals(myOrder, myClosedOrder);
		assertEquals(myClosedOrder.getTip(), 2.50);
	}
	
	@Test
	void GetBillByTable(){
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.seatCustomers(3, 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Spider Roll", "No Mayo");
		myRestaurant.orderItem(1, 2, "Pepsi", "None");
		myRestaurant.orderItem(1, 2, "Colin's Roll", "None");
		myRestaurant.orderItem(1, 3, "Ruby's Roll", "None");
		myRestaurant.closeOrder(1, 1, 2.5);
		myRestaurant.closeOrder(1, 2, 2.5);
		myRestaurant.closeOrder(1, 3, 2.5);
		
		Bill myBill = myRestaurant.getBillByTable(1);
		
		assertEquals(myBill.getPriceBeforeTip(), 35.95);
		assertEquals(myBill.getPriceAfterTip(), 43.45);
	}
	
	@Test
	void splitBillEvenly() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.seatCustomers(3, 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Spider Roll", "No Mayo");
		myRestaurant.orderItem(1, 2, "Pepsi", "None");
		myRestaurant.orderItem(1, 2, "Colin's Roll", "None");
		myRestaurant.orderItem(1, 3, "Ruby's Roll", "None");
		
		myRestaurant.splitBillEvenly(1);
		
		assertTrue((myRestaurant.getBillFromCustomer(1, 1).getPriceBeforeTip() - 11.983333) < 0.01);
		assertTrue((myRestaurant.getBillFromCustomer(1, 1).getPriceBeforeTip() - 11.983333) < 0.01);
		assertTrue((myRestaurant.getBillFromCustomer(1, 1).getPriceBeforeTip() - 11.983333) < 0.01);
	}
	
	@Test
	void testClearCustomersFromTable() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.seatCustomers(3, 1);
		
		assertTrue(myRestaurant.tableSeated(1));
		myRestaurant.clearCustomersFromTable(1);
		assertFalse(myRestaurant.tableSeated(1));
	}
	
	@Test
	void testGetAllServerInfo() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.assignServerToTable("Colin Gale", 2);
		myRestaurant.assignServerToTable("Austin Raben", 5);
		myRestaurant.seatCustomers(1, 1);
		myRestaurant.seatCustomers(1, 5);
		myRestaurant.closeOrder(1, 1, 2.00);
		myRestaurant.closeOrder(5, 1, 3.00);
		
		String servers = "Austin Raben\n" +
						"\tTIPS: 3.0\n" +
						"\tTABLES: 5\n\n" +
						"Billy Bob\n" +
						"\tTIPS: 0.0\n" +
						"\tTABLES:\n\n" +
						"Colin Gale\n" +
						"\tTIPS: 2.0\n" +
						"\tTABLES: 1,2\n\n" +
						"Lisette Robles\n" +
						"\tTIPS: 0.0\n" +
						"\tTABLES:\n\n" +
						"Ruby Gilliland\n" +
						"\tTIPS: 0.0\n" +
						"\tTABLES:\n\n";
		
		assertEquals(myRestaurant.getAllServersInfo(), servers);
	}
	
	@Test
	void testGetListOfServers() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		String servers = "1. Austin Raben\n2. Billy Bob\n3. Colin Gale\n4. Lisette Robles\n5. Ruby Gilliland";
		
		assertEquals(myRestaurant.getListOfServers(), servers);
	}
	
	
	@Test
	void testToString() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.seatCustomers(1, 3);
		myRestaurant.assignServerToTable("Colin Gale", 4);
		String restaurant = " ------------------------------------------ \n" +
				" |                   |                    | \n" +
				" |   RESTROOMS       |     KITCHEN        | \n" +
				" |                   |                    | \n" +
				" --DOOR----------------------------DOOR---- \n" +
				" |                                        | \n" +
				" | \u001B[31m Table 1:\u001B[0m    \u001B[31m Table 2:\u001B[0m    \u001B[32m Table 3:\u001B[0m    |\n" +
				" |  \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        | \n" +
				" | \u001B[31m Table 4:\u001B[0m    \u001B[31m Table 5:\u001B[0m    \u001B[31m Table 6:\u001B[0m    |\n" +
				" |  \u001B[32m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        | \n" +
				" | \u001B[31m Table 7:\u001B[0m    \u001B[31m Table 8:\u001B[0m    \u001B[31m Table 9:\u001B[0m    |\n" +
				" |  \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        | \n" +
				" | \u001B[31m Table 10:\u001B[0m   \u001B[31m Table 11:\u001B[0m   \u001B[31m Table 12:\u001B[0m   |\n" +
				" |  \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        | \n" +
				" | \u001B[31m Table 13:\u001B[0m   \u001B[31m Table 14:\u001B[0m   \u001B[31m Table 15:\u001B[0m   |\n" +
				" |  \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        | \n" +
				" | \u001B[31m Table 16:\u001B[0m   \u001B[31m Table 17:\u001B[0m   \u001B[31m Table 18:\u001B[0m   |\n" +
				" |  \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        | \n" +
				" | \u001B[31m Table 19:\u001B[0m   \u001B[31m Table 20:\u001B[0m   \u001B[31m Table 21:\u001B[0m   |\n" +
				" |  \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        | \n" +
				" | \u001B[31m Table 22:\u001B[0m   \u001B[31m Table 23:\u001B[0m   \u001B[31m Table 24:\u001B[0m   |\n" +
				" |  \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m     \u001B[31m SERVER \u001B[0m    |\n" +
				" |                                        |\n" +
				" |               \u001B[31mTable 25:\u001B[0m                |\n" +
				" |               \u001B[31m SERVER \u001B[0m                 |\n" +
				" |                                        |\n" +
				" | HOSTESS                                |\n" +
				" |                                        |\n" +
				" --ENTRANCE-------------------------------- \n";
		
		assertEquals(myRestaurant.toString(), restaurant);
	}
}
