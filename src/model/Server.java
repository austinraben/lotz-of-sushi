package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Server {
	private String serverName;
    private double tipsEarned;
    private List<Table> assignedTables;

    public Server(String serverName) {
        this.serverName = serverName;
        this.tipsEarned = 0;
        this.assignedTables = new ArrayList<>();
    }
    
    // copy constructor
    public Server(Server s) {
    	this.serverName = s.serverName;
    	this.tipsEarned = s.tipsEarned;
    	this.assignedTables = s.getTables();
    }
    
    // getters & setters
    
    public void addTable(Table table) {
    	assignedTables.add(table);
    }
    
    public void removeTable(Table table) {
    	assignedTables.remove(table);
    }
    
    public void addTip(double tip) {
    	tipsEarned += tip;
    }
    
    public String getServerName() {
    	return serverName;
    }
        
    public double getTipsEarned() {
    	return tipsEarned;
    }
    
    public List<Table> getTables() {
    	ArrayList<Table> tables = new ArrayList<Table>(assignedTables);
    	List<Table> copyTables = tables;
    	return copyTables;
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(assignedTables, serverName, tipsEarned);
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
		return Objects.equals(assignedTables, other.assignedTables) && Objects.equals(serverName, other.serverName)
				&& Double.doubleToLongBits(tipsEarned) == Double.doubleToLongBits(other.tipsEarned);
	}
    
}
