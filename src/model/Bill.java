/*
 * This class models a Bill, storing a price before and after the tip is added. Each Bill is associated with an Order
 * which is associated with each customer as well. There is also a Bill associated with the table, representing the combination
 * of all orders at the table.
 */

package model;

public class Bill {
	private double priceBeforeTip;
	private double priceAfterTip;

	// default constructor
    public Bill() {
        this.priceBeforeTip = 0;
        this.priceAfterTip = 0;
    }
    
    // copy constructor
    public Bill(Bill bill) {
    	this.priceAfterTip = bill.priceAfterTip;
    	this.priceBeforeTip = bill.priceBeforeTip;
    }
    
    public void updateBeforeTipPrice(double price) {
    	/*
    	 * This method updates the price before tip and after tip so the two variables are consistent with each other
    	 * until the tip is added.
    	 */
    	priceBeforeTip += price;
    	priceAfterTip += price;
    }
    
    public void changeBeforeTipPrice(double newPrice) {
    	/*
    	 * This method sets the price before tip and the respective price after tip so the two variables are consistent
    	 * with each other.
    	 */
    	priceAfterTip += newPrice - priceBeforeTip;
    	priceBeforeTip = newPrice;
    	
    }
    
    public void updateTipPrice(double tip) {
    	priceAfterTip += tip;
    }
    
    public double getPriceBeforeTip() {
    	/*
    	 * Gets the price before tip rounded to two decimal places (representing dollars and cents)
    	 */
    	String roundedPriceBeforeTip = String.format("%.2f", priceBeforeTip);
    	return Double.parseDouble(roundedPriceBeforeTip);
    }
    
    public double getPriceAfterTip() {
    	/*
    	 * Gets the price after tip rounded to two decimal places (representing dollars and cents)
    	 */
    	String roundedPriceAfterTip = String.format("%.2f", priceAfterTip);
    	return Double.parseDouble(roundedPriceAfterTip);    }

}
