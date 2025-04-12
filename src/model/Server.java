package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(serverName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Server other = (Server) obj;
		return Objects.equals(serverName, other.serverName);
	}
    
    // getters & setters
}
