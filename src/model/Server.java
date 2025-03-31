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
    
    // getters & setters
}
