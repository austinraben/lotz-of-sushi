package model;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private int tableNumber;
    private Server server;
    private List<Customer> customers;

    public Table(int tableNumber, int customerAmount, String serverName) {
        this.tableNumber = tableNumber;
        assignServer(serverName); 
        assignCustomers(customerAmount);
    }
    
    // assign a sever 
    public void assignServer(String serverName) {
    	//Server server = Restaurant.getServerByName(serverName);
    	this.server = server;
    }
    
    public void assignCustomers(int customerAmount) {
    	for (int i = 0; i < customerAmount; i++) {
    		customers.add(new Customer());
    	}
    }
    
    // getters & setters
    public int getTableNumber() {
    	return tableNumber;
    }
    
    // returns a copy of the server variable
    public Server getServer() {
    	return new Server(server);
    }
    
    // returns a deep copy of the customers list
    /*
    public List<Customer> getCustomers(){
    	ArrayList<Customer> customersCopy = new ArrayList<Customer>();
    	for (Customer c : customers) {
    		customersCopy.add(new Customer(c));
    	}
    	List<Customer> copyCustomers = customersCopy;
    	return copyCustomers;
    	
    }
    */
    
    
    
}
