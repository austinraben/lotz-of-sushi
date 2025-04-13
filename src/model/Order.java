package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private int orderNumber;
	private List<OrderedItem> items;
	private Bill bill;
    private boolean isClosed;
    private double tip;
    private int orderNum;


    public Order(int orderNum) {
    	this.orderNum = orderNum;
    	this.bill = new Bill();
        this.items = new ArrayList<>();
        this.isClosed = false;
        this.tip = 0.0;
    }
    
    // copy constructor
    public Order(Order other) {
    	this.orderNumber = other.orderNumber;
    	this.bill = other.bill;
        this.items = other.items;
        this.isClosed = other.isClosed;
        this.tip = other.tip;
    }
    
   public void orderItem(String itemName, String modification, Menu menu) {
	   if (menu.containsMenuItem(itemName)) {
		   MenuItem menuItem = menu.getMenuItem(itemName);
		   OrderedItem orderedItem = new OrderedItem(menuItem, modification);
		   items.add(orderedItem);
		   bill.updateBeforeTipPrice(orderedItem.getPrice());
	   }
	   else {
		   System.out.println("This item is not on the menu");
		   return;
	   }
   }
   
   public void makeTip(double tip) {
	   this.tip = tip;
	   bill.updateTipPrice(tip);
   }
   
   public double getTip() {
	   return tip;
   }
   
   public void changeBillTotal(double price) {
	   bill.changeBeforeTipPrice(price);
   }
   
   public List<OrderedItem> getItems() {
	return items;
   }

   public void setItems(List<OrderedItem> items) {
	   this.items = items;
   }

   public Bill getBill() {
	   return new Bill(bill);
   }

   public void closeOrder() {
	   isClosed = true;
   }
   
   public boolean isClosed() {
	   return isClosed;
   }
   
   public int getOrderNum() {
	   return orderNum;
   }
   
   @Override
   public String toString() {
	   String message = "---------------------\n" +
			   			"Order Number: " + orderNum + "\n" +
			   			"Server: NONE\n\n" +
			   			"ITEMS:\n";
	   for (OrderedItem oi : items) {
		   message += "\t$" + oi.getPrice() + " - " + oi.getItemName() + "\n";
		   if (oi.getModification() != null && !oi.getModification().equals("None")) {
			   message += "\t\tModification: " + oi.getModification() + "\n";
		   }
	   }
	   
	   message += "AMOUNT: $" + bill.getPriceBeforeTip() + '\n';
	   if (isClosed) {
		   message += "TIP: $" + tip + '\n';
	   }
	   else {
		   message += "TIP: NO TIP YET\n";
	   }
	   message += "\nTOTAL: $" + bill.getPriceAfterTip() + "\n---------------------\n";
	   
	   return message;
			   			
   }
    
    
}
