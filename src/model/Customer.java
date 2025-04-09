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
    
    // getters and setters
        
    public Table getAssignedTable() {
    	return assignedTable;
    }
    
    public Server getAssignedServer() {
    	return assignedTable.getServer();
    }
    
}
