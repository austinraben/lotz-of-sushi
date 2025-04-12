package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private int orderNumber;
	private List<OrderedItem> items;
	private Bill bill;
<<<<<<< HEAD
    private boolean isClosed;
    private double tip;
=======
    private Table table;
    private int orderNum;
>>>>>>> 7c55964323d51bbf7e04c7cd6ade71ea582d304c


    public Order(Table table, int orderNum) {
    	this.table = table;
    	this.orderNum = orderNum;
    	this.bill = new Bill();
        this.items = new ArrayList<>();
<<<<<<< HEAD
        this.isClosed = false;
        this.tip = 0.0;
=======
>>>>>>> 7c55964323d51bbf7e04c7cd6ade71ea582d304c
    }
    
    // copy constructor
    public Order(Order other) {
    	this.orderNumber = other.orderNumber;
    	this.bill = other.bill;
        this.items = other.items;
<<<<<<< HEAD
        this.isClosed = other.isClosed;
        this.tip = other.tip;
=======
>>>>>>> 7c55964323d51bbf7e04c7cd6ade71ea582d304c
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
	   this.tip = tip;
	   bill.updateTipPrice(tip);
   }
   
<<<<<<< HEAD
   public double getTip() {
	   return tip;
=======
   public void changeBillTotal(double price) {
	   bill.changeBeforeTipPrice(price);
>>>>>>> 7c55964323d51bbf7e04c7cd6ade71ea582d304c
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
<<<<<<< HEAD

   
   public boolean isClosed() {
	   return isClosed;
=======
   
   public int getOrderNum() {
	   return orderNum;
>>>>>>> 7c55964323d51bbf7e04c7cd6ade71ea582d304c
   }
   
   // TODO create toString method that prints out order like a receipt
  
    
    
}
