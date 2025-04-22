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
    private String serverName;

    public Order(int orderNum, String serverName) {
    	this.orderNum = orderNum;
    	this.bill = new Bill();
        this.items = new ArrayList<>();
        this.tip = 0.0;
        this.serverName = serverName;
    }
    
    // copy constructor
    public Order(Order other) {
    	this.orderNumber = other.orderNumber;
    	this.bill = other.bill;
        this.items = other.items;
        this.tip = other.tip;
        this.serverName = other.serverName;
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
	   this.tip += tip;
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
   
   public void setOrderNum(int orderNum) {
	   this.orderNum = orderNum;
   }
   
   public int getOrderNum() {
	   return orderNum;
   }
   
   public String getServerName() {
       return serverName;
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
		String message = "";
		if (orderNum >= 0) {
			message += "---------------------\n" +
			   			"Order Number: " + orderNum + "\n" +
			   			"Server: " + serverName + "\n\n" +
			   			"ITEMS:\n";
		}
		else {
			message += "---------------------\n" +
		   			"Order Number: TABLE'S ORDER\n" +
		   			"Server: " + serverName + "\n\n" +
		   			"ITEMS:\n";
		}
	   for (OrderedItem oi : items) {
		   message += "\t$" + oi.getPrice() + " - " + oi.getItemName() + "\n";
		   if (oi.getModification() != null && 
			  !oi.getModification().equals("None") && 
			  !oi.getModification().isEmpty() && 
			  !oi.getModification().equals("This item can not be modified")) {
			   message += "\t\tModification: " + oi.getModification() + "\n";
		   }
	   }
	   
	   message += "AMOUNT: $" + bill.getPriceBeforeTip() + '\n';
	   message += "TIP: $" + tip + '\n';
	  
	   message += "\nTOTAL: $" + bill.getPriceAfterTip() + "\n---------------------\n";
	   
	   return message;
			   			
   }
    
    
}
