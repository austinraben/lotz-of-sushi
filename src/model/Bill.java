package model;

public class Bill {
	private Table table;
	private double priceBeforeTip;
	private double priceAfterTip;

    public Bill(Table table) {
        this.table = table;
        this.priceBeforeTip = 0;
        this.priceAfterTip = 0;
    }
}
