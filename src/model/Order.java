package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
	private int orderNumber;
	private List<OrderedItem> items;
	private Bill bill;
    private int orderNum;
    private double tip;

    public Order(int orderNum) {
    	this.orderNum = orderNum;
    	this.bill = new Bill();
        this.items = new ArrayList<>();
        this.tip = 0.0;
    }
    
    // copy constructor
    public Order(Order other) {
    	this.orderNumber = other.orderNumber;
    	this.bill = other.bill;
        this.items = other.items;
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
   
   public int getOrderNum() {
	   return orderNum;
   }
   
   
   
   @Override
   public int hashCode() {
	   return Objects.hash(items, orderNum, orderNumber, tip);
   }

   @Override
   public boolean equals(Object obj) {
	   if (this == obj)
		   return true;
	   if (obj == null)
		   return false;
	   if (getClass() != obj.getClass())
		   return false;
	   Order other = (Order) obj;
	   return Objects.equals(items, other.items) && orderNum == other.orderNum && orderNumber == other.orderNumber
			&& Double.doubleToLongBits(tip) == Double.doubleToLongBits(other.tip);
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
	   message += "TIP: $" + tip + '\n';
	  
	   message += "\nTOTAL: $" + bill.getPriceAfterTip() + "\n---------------------\n";
	   
	   return message;
			   			
   }
    
    
}
