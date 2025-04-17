package model;

public class Bill {
	private double priceBeforeTip;
	private double priceAfterTip;

	
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
    	priceBeforeTip += price;
    	priceAfterTip += price;
    }
    
    public void changeBeforeTipPrice(double newPrice) {
    	priceAfterTip += newPrice - priceBeforeTip;
    	priceBeforeTip = newPrice;
    	
    }
    
    public void updateTipPrice(double tip) {
    	priceAfterTip += tip;
    }
    
    public double getPriceBeforeTip() {
    	return priceBeforeTip;
    }
    
    public double getPriceAfterTip() {
    	return priceAfterTip;
    }

}
