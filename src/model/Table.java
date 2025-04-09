package model;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private int tableNumber;
    private boolean isOccupied;
    private Server server;
    private List<Customer> customers;
    

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
        this.isOccupied = false;
        this.server = null; 
        this.customers = new ArrayList<>();
    }
    
    // getters & setters
    
    
}
