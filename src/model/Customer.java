package model;

import java.util.Objects;

public class Customer {
    private Order order;
    private Table assignedTable;
    //private String serverName; -- maybe include
    
    public Customer(Table table, int orderNum, String serverName) {
    	assignedTable = table;
        this.order = new Order(orderNum, serverName);
    }
    
    // functionality
    public void orderItem(String itemName, String modification, Menu menu) {
    	order.orderItem(itemName, modification, menu);
    }
    
    public void tip(double tipAmt) {
    	order.makeTip(tipAmt);
    }
    
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
    
    @Override
    public int hashCode() {
    	return Objects.hash(this.assignedTable) + this.getOrderNum();
    }
    
    @Override
    public boolean equals(Object other) {
    	if (this == other) return true;
    	if (other == null || this.getClass() != other.getClass()) return false;
    	
    	return this.assignedTable.equals(((Customer) other).assignedTable) 
    			&& this.getOrderNum() == ((Customer) other).getOrderNum();
    }
    
 }
