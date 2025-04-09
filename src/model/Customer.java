package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Order order;
    private Table assignedTable;
    
    public Customer(Table table) {
    	assignedTable = table;
        this.order = new Order(assignedTable);
    }
    
    // functionality
    public void orderItem(String itemName, String modification, Menu menu) {
    	order.orderItem(itemName, modification, menu);
    }
    
    public Bill getBill() {
    	return order.getBill();
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
    
}
