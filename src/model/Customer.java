package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Order order;
    private Table assignedTable;
    //private String serverName; -- maybe include
    
    public Customer(Table table, int orderNum) {
    	assignedTable = table;
        this.order = new Order(assignedTable, orderNum);
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
    
    // TODO fix escaping reference
    public Bill getBill() {
    	return order.getBill();
    }
    
}
