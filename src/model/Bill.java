package model;

public class Bill {
	private double priceBeforeTip;
	private double priceAfterTip;

	
    public Bill() {
        this.priceBeforeTip = 0;
        this.priceAfterTip = 0;
    }
    
    public void updateBeforeTipPrice(double price) {
    	priceBeforeTip += price;
    }
    
    public void changeBeforeTipPrice(double newPrice) {
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
