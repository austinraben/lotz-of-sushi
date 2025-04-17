package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BillTest {

	@Test
	void testUpdateBeforeTip() {
		Bill myBill = new Bill();
		myBill.updateBeforeTipPrice(8.99);
		assertEquals(myBill.getPriceBeforeTip(), 8.99);
	}
	
	@Test
	void testUpdateTipPrice() {
		Bill myBill = new Bill();
		myBill.updateBeforeTipPrice(8.99);
		myBill.updateTipPrice(5.99);
		assertEquals(myBill.getPriceAfterTip(), 14.98);
	}
	
	@Test
	void testCopyConstructor() {
		Bill myBill = new Bill();
		myBill.updateBeforeTipPrice(8.99);
		myBill.updateTipPrice(5.99);
		Bill myNewBill = new Bill(myBill);
		assertEquals(myBill.getPriceAfterTip(), 14.98);
	}
	
	@Test
	void testChangeBeforeTipPrice() {
		Bill myBill = new Bill();
		myBill.updateBeforeTipPrice(8.99);
		myBill.updateTipPrice(5.99);
		myBill.changeBeforeTipPrice(7.99);
		assertEquals(myBill.getPriceBeforeTip(), 7.99);
		assertEquals(myBill.getPriceAfterTip(), 13.98);
	}

}
