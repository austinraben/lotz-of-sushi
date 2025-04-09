package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Restaurant {
    private String name;
    private HashMap<String, Server> servers;
    private HashMap<Server, ArrayList<Table>> assignedTables;
    private HashMap<Table, ArrayList<Customer>> tableMap;
    private List<Table> tables;
    private Menu drinkMenu;
    private Menu appMenu;
    private Menu entreeMenu;
    private Menu dessertMenu;

    public Restaurant(String name) {
        this.name = name;
        this.servers = new HashMap<>();
        this.assignedTables = new HashMap<>();
        this.tables = createTables();
        this.tableMap = createTableMap();
        this.drinkMenu = new DrinkMenu();
        this.appMenu = new AppMenu();
        this.entreeMenu = new EntreeMenu();
        this.dessertMenu = new DessertMenu();

    }
 
    // read menu.txt, create MenuItems and add them to Menu and its respective child class
    public void loadMenuItems(String filename) {
        
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            
    		String line;
            
            while ((line = reader.readLine()) != null) {
            	
            	// skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("//")) {
                    continue;
                }

                String[] parts = line.split(",", 6);
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                // extract data from menu
                FoodCourse course = FoodCourse.valueOf(parts[0]);
                String category = parts[1];
                String itemName = parts[2];
                double price = Double.parseDouble(parts[3]);
                boolean modifiable = Boolean.parseBoolean(parts[4]);
                String description = (parts.length > 5) ? parts[5] : ""; 

                MenuItem item = new MenuItem(course, category, itemName, price, modifiable, description);

                Menu subMenu = getMenuForCourse(course);
                if (subMenu != null) {
                    subMenu.addItem(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Menu getMenuForCourse(FoodCourse course) {
        switch (course) {
            case DRINKS:
                return drinkMenu;
            case APPS:
                return appMenu;
            case ENTREES:
                return entreeMenu;
            case DESSERTS:
                return dessertMenu;
            default:
                return null;
        }
    }
    
    // server and table
    
    /*
     * My idea with the mapping functionality is to reduce the possibility of escaping references
     * and having too many object assignments. This way, servers and Customers (and subsequently orders and 
     * Bills) are kept separate, only connected by their shared table, which can easily be found. Then when 
     * server and customer interaction is required, just look up their shared table and connect them that way.
     */
    
    public void hireServers(String serverName) {
    	
    	// create new Server object
    	Server newServer = new Server(serverName);
    	
    	// add Server to servers map
    	servers.put(serverName, newServer);
    	
    	// add Server to server/tables map
    	assignedTables.put(newServer, new ArrayList<Table>());
    }
    
    // map servers to tables
    public void assignServerToTable(String serverName, Table table) {
    	
    	// find Server object with given name
    	Server found = servers.get(serverName);
    	
    	// add table to Servers internal list
    	found.addTable(table);
    	
    	// add table to server/tables map
    	assignedTables.get(found).add(table);
    }
    
    
    // make a set amount of tables -- helper method (stay private)
    private ArrayList<Table> createTables() {
    	ArrayList<Table> tables = new ArrayList<Table>();
    	for (int i = 0; i < 25; i++) {
    		tables.add(new Table(i + 1));
    	}
    	return tables;
    }
    
    // map tables to customers -- helper method (stay private)
    private HashMap<Table, ArrayList<Customer>> createTableMap(){
    	HashMap<Table, ArrayList<Customer>> tableMap = new HashMap<Table, ArrayList<Customer>>();
    	for (Table t : tables) {
    		tableMap.put(t, new ArrayList<Customer>());
    	}
    	
    	return tableMap;
    }
    
    // customer interaction
    public void seatCustomers(int customerAmt, int tableNum) {
    	
    	// gets assigned table by number
    	Table table = tables.get(tableNum - 1);
    	
    	// creates a new customer object for amount of customers given
    	for (int i = 0; i < customerAmt; i++) {
    		Customer newCustomer = new Customer(table);
    		
    		// adds customer list for its table in tableMap
    		tableMap.get(table).add(newCustomer);
    	}
    }
    
    // getters
    
    public Menu getDrinkMenu() {
        return drinkMenu;
    }

    public Menu getAppMenu() {
        return appMenu;
    }

    public Menu getEntreeMenu() {
        return entreeMenu;
    }

    public Menu getDessertMenu() {
        return dessertMenu;
    }

    public Menu getMenu(FoodCourse course) {
        return getMenuForCourse(course);
    }   
    
    // sorters
}