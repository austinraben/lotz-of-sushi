package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class CustomerTest {

	@Test
	void testOrderItem() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1, "Billy Bob");
		newCustomer.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		Order myOrder = newCustomer.getOrder();
		
		assertEquals(myOrder.getItems().get(0).getItemName(), "Miso Soup");
	}
	
	@Test
	void testGetAssignedTable() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1, "Billy Bob");
		
		assertEquals(newCustomer.getAssignedTable(), table);
		
	}
	
	@Test
	void testTip() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1, "Billy Bob");
		newCustomer.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		newCustomer.tip(6.99);
		Order myOrder = newCustomer.getOrder();
		
		assertEquals(myOrder.getTip(), 6.99);
	}
	
	@Test
	void testGetBill() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1, "Billy Bob");
		newCustomer.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		newCustomer.tip(6.99);
		Bill myBill = newCustomer.getBill();
		
		assertEquals(myBill.getPriceBeforeTip(), 6.99);
		assertEquals(myBill.getPriceAfterTip(), 13.98);
	}
	
	@Test
	void changeBillTotal() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1, "Billy Bob");
		newCustomer.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		newCustomer.tip(6.99);
		newCustomer.changeBillTotal(5.00);
		Bill myBill = newCustomer.getBill();
		
		assertEquals(myBill.getPriceBeforeTip(), 5.00);
		assertEquals(myBill.getPriceAfterTip(), 11.99);
	}
	
	@Test
	void testGetOrderNum() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1, "Billy Bob");
		
		assertEquals(newCustomer.getOrderNum(), 1);
	}
	
	@Test
	void testEqualsSame() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer c1 = new Customer(table, 1, "Billy Bob");
		
		assertTrue(c1.equals(c1));
	}
	
	@Test
	void testEqualsTrue() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer c1 = new Customer(table, 1, "Billy Bob");
		Customer c2 = new Customer(table, 1, "Billy Bob");
		
		assertEquals(c1, c2);
	}
	
	@Test
	void testEqualsFalse() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer c1 = new Customer(table, 1, "Billy Bob");
		Customer c2 = new Customer(table, 2, "Billy Bob");
		
		assertFalse(c1.equals(c2));
	}
	
	@Test
	void testEquals() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer c1 = new Customer(table, 1, "Billy Bob");
		
		assertFalse(c1.equals(table));
	}
	
	@Test
    public void testHashCodeSameData() {
        Table table1 = new Table(1);
        Customer customer1 = new Customer(table1, 1, "Billy Bob");
        Customer customer2 = new Customer(new Table(1), 1, "Billy Bob"); 

        assertEquals(customer1.hashCode(), customer2.hashCode());
	}
}
