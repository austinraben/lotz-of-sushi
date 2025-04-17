package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Order order;
    private Table assignedTable;
    //private String serverName; -- maybe include
    
    public Customer(Table table, int orderNum) {
    	assignedTable = table;
        this.order = new Order(orderNum);
    }
    
    // functionality
    public void orderItem(String itemName, String modification, Menu menu) {
    	order.orderItem(itemName, modification, menu);
    }
    
    public void tip(double tipAmt) {
    	order.makeTip(tipAmt);
    }
    
    // TODO
    /*
     * public void payBill()
     */
    
    // getters and setters
        
    public Table getAssignedTable() {
    	return assignedTable;
    }
    
    public int getOrderNum() {
    	return order.getOrderNum();
    }
    
    public void changeBillTotal(double price) {
    	order.changeBillTotal(price);
    }
    
    public Bill getBill() {
    	return new Bill(order.getBill());
    }
    
    public Order getOrder() {
    	return new Order(order);
    }
    
}
