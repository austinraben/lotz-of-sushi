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
    	String roundedPriceBeforeTip = String.format("%.2f", priceBeforeTip);
    	return Double.parseDouble(roundedPriceBeforeTip);
    }
    
    public double getPriceAfterTip() {
    	String roundedPriceAfterTip = String.format("%.2f", priceAfterTip);
    	return Double.parseDouble(roundedPriceAfterTip);    }

}
