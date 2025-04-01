package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
	private String serverName;
    private double tipsEarned;
    private HashMap<String, Table> assignedTables;

    public Server(String serverName) {
        this.serverName = serverName;
        this.tipsEarned = 0;
        this.assignedTables = new HashMap<>();
    }
    
    // getters & setters
}
