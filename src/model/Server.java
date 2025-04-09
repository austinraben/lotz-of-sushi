package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server{
	private String serverName;
    private double tipsEarned;
    private List<Table> assignedTables;
    private List<Order> orders;

    public Server(String serverName) {
        this.serverName = serverName;
        this.tipsEarned = 0;
        this.assignedTables = new ArrayList<>();
    }

	public List<Order> getOrders() {
		List<Order> copyOrders = new ArrayList<>();
		for (Order o : orders) {
			copyOrders.add(new Order(o));
		}
		return copyOrders;
	}
    
    // getters & setters
}
