package model;

import java.util.ArrayList;
import java.util.List;

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
    
    // table is passed in as a copy
    public void addTable(Table table) {
    	assignedTables.add(table);
    }
    
    // table is passed in as a copy    
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
    
}
