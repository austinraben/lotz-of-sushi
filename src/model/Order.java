package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
	private List<OrderedItem> items;
	private Bill bill;
    private boolean isClosed;
    private Table table;
    private int orderNum;

    public Order(Table table, int orderNum) {
    	this.table = table;
    	this.orderNum = orderNum;
    	this.bill = new Bill();
        this.items = new ArrayList<>();
        this.isClosed = false;  
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
   
   public List<OrderedItem> getItems() {
	return items;
   }

   public void setItems(List<OrderedItem> items) {
	   this.items = items;
   }

   public Bill getBill() {
	   return bill;
   }


   public boolean isClosed() {
	   return isClosed;
   }

   public void setClosed(boolean isClosed) {
	   this.isClosed = isClosed;
   }

   public void closeOrder() {
	   isClosed = true;
   	}
   
   public int getOrderNum() {
	   return orderNum;
   }
    
    
    
}
