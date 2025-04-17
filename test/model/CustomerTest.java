package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CustomerTest {

	@Test
	void testOrderItem() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1);
		newCustomer.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		Order myOrder = newCustomer.getOrder();
		
		assertEquals(myOrder.getItems().get(0).getItemName(), "Miso Soup");
	}
	
	@Test
	void testGetAssignedTable() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1);
		
		assertEquals(newCustomer.getAssignedTable(), table);
		
	}
	
	@Test
	void testTip() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1);
		newCustomer.orderItem("Miso Soup", "None", myRestaurant.getEntreeMenu());
		newCustomer.tip(6.99);
		Order myOrder = newCustomer.getOrder();
		
		assertEquals(myOrder.getTip(), 6.99);
	}
	
	@Test
	void testGetBill() {
		Restaurant myRestaurant = new Restaurant("Customer Test");
		Table table = myRestaurant.getTableByNumber(1);
		Customer newCustomer = new Customer(table, 1);
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
		Customer newCustomer = new Customer(table, 1);
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
		Customer newCustomer = new Customer(table, 1);
		
		assertEquals(newCustomer.getOrderNum(), 1);
	}
}
