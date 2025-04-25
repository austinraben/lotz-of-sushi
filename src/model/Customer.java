/*
 * This class models a Customer, storing an order taken by a server and which table the customer is assigned to. This class has
 * methods to order an item, tip, and change their Bill total (associated with the order).
 */

package model;

import java.util.Objects;

public class Customer {
    private Order order;
    private Table assignedTable;
    
    // default constructor
    public Customer(Table table, int orderNum, String serverName) {
    	assignedTable = table;
        this.order = new Order(orderNum, serverName);
    }
    
    public void orderItem(MenuItem item, String modification, Menu menu) {
    	/*
    	 * This method orders a MenuItem from a specific Menu, which adds a OrderedItem
    	 * object to the order's internal list.
    	 */
        order.orderItem(item.getItemName(), modification, menu, item);
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
    
    // hash code and equals
    
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
