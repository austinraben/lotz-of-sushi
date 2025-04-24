package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class RestaurantTest {
	
	void fireTestServer() {
	    Path path = Paths.get("data/staff.txt");
	    if (Files.exists(path)) {
	        try {
	            String content = Files.readString(path);
	            if (content.contains("Server")) {
	                Files.write(path, content.replace("Test Server\n", "").replace("Test Server", "").getBytes());
	            }
	        } catch (IOException e) {
	        }
	    }
	}
	
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
	void testFireServer() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.fireServers("Billy Bob");
		assertFalse(myRestaurant.serverIsHired("Billy Bob"));
		myRestaurant.hireServers("Billy Bob");
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
	void testUnassignedTables() {
		Restaurant myRestaurant = new Restaurant("Resturant Test");
		String result1 = "UNASSIGNED TABLES: 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25";
		assertEquals(result1, myRestaurant.getUnassignedTables());
		
		
		myRestaurant.hireServers("Test Server");
		myRestaurant.assignServerToTable("Test Server", 1);
        myRestaurant.assignServerToTable("Test Server", 2);
        String result2 = "UNASSIGNED TABLES: 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25";
        assertEquals(result2, myRestaurant.getUnassignedTables());
        fireTestServer();
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
						"\tNO TABLES ASSIGNED\n\n" +
						"Colin Gale\n" +
						"\tTIPS: 2.0\n" +
						"\tTABLES: 1,2\n\n" +
						"Lisette Robles\n" +
						"\tTIPS: 0.0\n" +
						"\tNO TABLES ASSIGNED\n\n" +
						"Ruby Gilliland\n" +
						"\tTIPS: 0.0\n" +
						"\tNO TABLES ASSIGNED\n\n";
		
		assertEquals(myRestaurant.getAllServersInfo(), servers);
	}
	
	
	@Test
	void testGetListOfServers() {
		System.out.println("Testing getListOfServers()...");
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		String servers = "1. Austin Raben\n2. Billy Bob\n3. Colin Gale\n4. Lisette Robles\n5. Ruby Gilliland";
		
		assertEquals(myRestaurant.getListOfServers(), servers);
		
	}
	
	@Test
	void testGetAvailableTables() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 22);
		myRestaurant.assignServerToTable("Colin Gale", 11);
		
		String allAvailable = "OPEN TABLES: 11,22";
		
		assertEquals(allAvailable, myRestaurant.getAvailableTables());
	}
	
	@Test
	void testGetTablesFromServer() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 22);
		myRestaurant.assignServerToTable("Colin Gale", 11);
		
		String allAvailable = "TABLES: 22,11";
		
		assertEquals(allAvailable, myRestaurant.getTablesFromServer("Colin Gale"));
	}
	
	@Test
	void testHasModification() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		
		assertTrue(myRestaurant.hasModification("Colin's Roll"));
		assertFalse(myRestaurant.hasModification("Miso Soup"));
		assertFalse(myRestaurant.hasModification("Pepsi"));
		assertFalse(myRestaurant.hasModification("Edamame"));
	}
	
	@Test
	void testGetTableOrder() {
		Restaurant myRestaurant = new Restaurant("Restaurant Test");
		myRestaurant.assignServerToTable("Colin Gale", 1);
		myRestaurant.seatCustomers(3, 1);
		myRestaurant.orderItem(1, 1, "Miso Soup", "None");
		myRestaurant.orderItem(1, 1, "Spider Roll", "No Mayo");
		myRestaurant.orderItem(1, 2, "Pepsi", "None");
		myRestaurant.orderItem(1, 2, "Colin's Roll", "None");
		myRestaurant.orderItem(1, 3, "Ruby's Roll", "None");
		
		Order myOrder = myRestaurant.getTableOrder(1);
		Order testOrder = new Order(-1, "Colin Gale");
		
	    MenuItem misoSoup = new MenuItem(FoodCourse.ENTREES, "", "Miso Soup", 6.99, false, "tofu seaweed and green onions in a miso broth");
	    MenuItem spiderRoll = new MenuItem(FoodCourse.ENTREES, "Speciality Roll", "Spider Roll", 7.99, true, "description omitted");
	    MenuItem pepsi = new MenuItem(FoodCourse.DRINKS, "Soda", "Pepsi", 2.99, false, "");
	    MenuItem colinsRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Colin's Roll", 8.99, true, "description omitted");
	    MenuItem rubysRoll = new MenuItem(FoodCourse.ENTREES, "Specialty Roll", "Ruby's Roll", 8.99, true, "description omitted");

		testOrder.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu(), misoSoup);
		testOrder.orderItem("Spider Roll", "No Mayo", myRestaurant.getEntreeMenu(), spiderRoll);
		testOrder.orderItem("Pepsi", "None", myRestaurant.getDrinkMenu(), pepsi);
		testOrder.orderItem("Colin's Roll", "None", myRestaurant.getEntreeMenu(), colinsRoll);
		testOrder.orderItem("Ruby's Roll", "None", myRestaurant.getEntreeMenu(), rubysRoll);
		
		for (int i = 0; i < myOrder.getItems().size(); i++) {
			assertEquals(myOrder.getItems().get(i).getItemName(), testOrder.getItems().get(i).getItemName());
			assertEquals(myOrder.getItems().get(i).getModification(), testOrder.getItems().get(i).getModification());
		}
		
		assertEquals(myOrder.getBill().getPriceBeforeTip(), testOrder.getBill().getPriceBeforeTip());
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
				" | HOST                                   |\n" +
				" |                                        |\n" +
				" --ENTRANCE-------------------------------- \n";
		
		assertEquals(myRestaurant.toString(), restaurant);
	}
}
