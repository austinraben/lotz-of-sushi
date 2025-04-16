package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BillTest {

	@Test
	void testUpdateBeforeTip() {
		Restaurant myRestaurant = new Restaurant("Bill Test");
		myRestaurant.seatCustomers(1, 1);
		Table myTable = myRestaurant.getTableByNumber(1);
		myRestaurant.assignServerToTable("Colin Gale", myTable);
		myRestaurant.orderItem(myTable, 1, "Spicy Chicken Ramen", "None");

		
		Bill myBill = myRestaurant.getBillByTable(myTable);
		myRestaurant.closeOrder(myTable, 1, 2.50);
		assertEquals(myBill.getPriceBeforeTip(), 8.99);
	}

}
