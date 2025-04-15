package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private int orderNumber;
	private List<OrderedItem> items;
	private Bill bill;
    private int orderNum;


    public Order( int orderNum) {
    	this.orderNum = orderNum;
    	this.bill = new Bill();
        this.items = new ArrayList<>();
    }
    
    // copy constructor
    public Order(Order other) {
    	this.orderNumber = other.orderNumber;
    	this.bill = other.bill;
        this.items = other.items;
    }
    
   public void orderItem(String itemName, String modification, Menu menu) {
	   if (menu.containsMenuItem(itemName.strip().toLowerCase())) {
		   MenuItem menuItem = menu.getMenuItem(itemName.strip().toLowerCase());
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
	   bill.updateTipPrice(tip);
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

   // TODO fix escaping reference
   public Bill getBill() {
	   return bill;
   }
   
   public int getOrderNum() {
	   return orderNum;
   }
   
   // TODO create toString method that prints out order like a receipt
  
    
    
}
