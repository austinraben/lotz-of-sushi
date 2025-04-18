package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class ServerTest {

	@Test
	void testAddTable() {
		Restaurant myRestaurant = new Restaurant("Server Test");
		Server myServer = new Server("Colin Gale");
		myServer.addTable(myRestaurant.getTableByNumber(1));
		myServer.addTable(myRestaurant.getTableByNumber(4));
		
		for (Table t : myServer.getTables()) {
			assertTrue(t.getTableNumber() == 1 || t.getTableNumber()  == 4);
		}
		
	}
	
	@Test
	void testRemoveTables() {
		Restaurant myRestaurant = new Restaurant("Server Test");
		Server myServer = new Server("Colin Gale");
		myServer.addTable(myRestaurant.getTableByNumber(1));
		myServer.addTable(myRestaurant.getTableByNumber(4));
		myServer.addTable(myRestaurant.getTableByNumber(5));
		
		myServer.removeTable(myRestaurant.getTableByNumber(4));
		
		for (Table t : myServer.getTables()) {
			assertFalse(t.getTableNumber() == 4);
		}
	}
	
	@Test
	void testCopyConstructor() {
		Restaurant myRestaurant = new Restaurant("Server Test");
		Server myServer = new Server("Colin Gale");
		
		myServer.addTip(5.50);
		myServer.addTable(myRestaurant.getTableByNumber(1));
		myServer.addTable(myRestaurant.getTableByNumber(4));
		
		Server myNewServer = new Server(myServer);
		assertEquals(myNewServer.getTipsEarned(), 5.50);
		for (Table t : myNewServer.getTables()) {
			assertTrue(t.getTableNumber() == 1 || t.getTableNumber()  == 4);
		}
	}
		
	@Test
	void testEqualsSameAndSameName() {
		Restaurant myRestaurant = new Restaurant("Server Test");
		Server myServer = new Server("Colin Gale");
		
		myServer.addTip(5.50);
		myServer.addTable(myRestaurant.getTableByNumber(1));
		myServer.addTable(myRestaurant.getTableByNumber(4));
		Server myServerAlso = myServer;
		
		Server myNewServer = new Server("Colin Gale");

		assertEquals(myServer, myServerAlso);
		assertEquals(myServer, myNewServer);
	}
	
	@Test
	void testEqualsNotSameObjectAndNotSameNameAndNull() {
		Restaurant myRestaurant = new Restaurant("Server Test");
		Server myServer = new Server("Colin Gale");
		
		myServer.addTip(5.50);
		myServer.addTable(myRestaurant.getTableByNumber(1));
		myServer.addTable(myRestaurant.getTableByNumber(4));
		Order notAServer = new Order(1);
		
		Server myNewServer = new Server("Ruby Gilliland");

		assertFalse(myServer.equals(notAServer));
		assertFalse(myServer.equals(myNewServer));
		assertFalse(myServer.equals(null));
	}
	
	@Test
	void testToString() {
		Restaurant myRestaurant = new Restaurant("Server Test");
		Server myServer = new Server("Colin Gale");
		myServer.addTip(5.50);
		myServer.addTable(myRestaurant.getTableByNumber(1));
		myServer.addTable(myRestaurant.getTableByNumber(4));
		
		String server = "Colin Gale\n" +
						"\tTIPS: 5.5\n" +
						"\tTABLES: 1,4\n";
		
		assertEquals(myServer.toString(), server);
	}
	
	@Test
	void testGetServerName() {
		Server myServer = new Server("Colin Gale");
		assertEquals(myServer.getServerName(), "Colin Gale");
	}
		
	@Test
	void testSortByNameComparator() {
		ArrayList<Server> myServers = new ArrayList<>();
		myServers.add(new Server("Colin Gale"));
		myServers.add(new Server("Austin Raben"));
		myServers.add(new Server("Ruby Gilliland"));
		myServers.add(new Server("Lisette Robles"));
		
		Collections.sort(myServers,  Server.sortByNameComparator());
		
		assertEquals(myServers.get(0).getServerName(), "Austin Raben");
		assertEquals(myServers.get(1).getServerName(), "Colin Gale");
		assertEquals(myServers.get(2).getServerName(), "Lisette Robles");
		assertEquals(myServers.get(3).getServerName(), "Ruby Gilliland");
	}
	
	@Test
	void testSortByTipsComparator() {
		ArrayList<Server> myServers = new ArrayList<>();
		Server server1 = new Server("Colin Gale");
		Server server2 = new Server("Austin Raben");
		Server server3 = new Server("Ruby Gilliland");
		Server server4 = new Server("Lisette Robles");
		server1.addTip(100.0);
		server2.addTip(10.0);
		server3.addTip(40.0);
		myServers.add(server1);
		myServers.add(server2);
		myServers.add(server3);
		myServers.add(server4);
		
		Collections.sort(myServers,  Server.sortByTipsComparator());
		
		assertEquals(myServers.get(3).getServerName(), "Colin Gale");
		assertEquals(myServers.get(2).getServerName(), "Ruby Gilliland");
		assertEquals(myServers.get(1).getServerName(), "Austin Raben");
		assertEquals(myServers.get(0).getServerName(), "Lisette Robles");
	}

}
