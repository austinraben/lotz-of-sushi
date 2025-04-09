package model;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private int tableNumber;
    private Server server;

    public Table(int tableNumber, String serverName) {
        this.tableNumber = tableNumber;
        assignServer(serverName); 
    }
    
    // assign a sever 
    public void assignServer(String serverName) {
    	//Server server = Restaurant.getServerByName(serverName);
    	this.server = server;
    }
    
    // getters & setters
    public int getTableNumber() {
    	return tableNumber;
    }
    
    // returns a copy of the server variable
    public Server getServer() {
    	return new Server(server);
    }
    
    
    
    
}
