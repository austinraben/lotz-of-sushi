package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String name;
    private HashMap<String, Server> servers;
    private HashMap<Server, ArrayList<Table>> serverTables;
    private HashMap<Table, ArrayList<Customer>> tableMap;
    private List<Table> tables;
    private List<Order> closedOrders;
    private Menu drinkMenu;
    private Menu appMenu;
    private Menu entreeMenu;
    private Menu dessertMenu;
    private SalesTracker sales;

    public Restaurant(String name) {
        this.name = name;
        this.servers = new HashMap<>();
        this.serverTables = new HashMap<>();
         
        try {
        	loadServers("/data/staff.txt");
        }
        catch (Exception e){
        	System.err.println("Error loading servers: " + e.getMessage());
        	e.printStackTrace();
        }
        
        this.tables = createTables();
        this.tableMap = createTableMap();
        this.closedOrders = new ArrayList<Order>();
        this.drinkMenu = new DrinkMenu();
        this.appMenu = new AppMenu();
        this.entreeMenu = new EntreeMenu();
        this.dessertMenu = new DessertMenu();
        
        try {
            loadMenuItems("/data/menu.txt");
        } catch (Exception e) {
            System.err.println("Error loading menu: " + e.getMessage());
            e.printStackTrace();
        }
        
        initializeSalesTracker();
    }
    
    private void initializeSalesTracker() {
    	ArrayList<Menu> allMenus = new ArrayList<>();
    	allMenus.add(drinkMenu);
        allMenus.add(appMenu);
        allMenus.add(entreeMenu);
        allMenus.add(dessertMenu);
        
        ArrayList<String> allMenuItems = new ArrayList<>();
        for (Menu menu : allMenus) {
        	for (String s : menu) {
        		allMenuItems.add(s.strip());
        	}
        }
        this.sales = new SalesTracker(allMenus, allMenuItems, servers);
    }
    
    private void loadServers(String filename) {
    	filename = System.getProperty("user.dir") + filename;
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            
    		String line;
            
            while ((line = reader.readLine()) != null) {
            	// skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("//")) {
                    continue;
                }
                
                String serverName = line.strip();
                Server newServer = new Server(serverName);
                servers.put(serverName, newServer);
                serverTables.put(newServer, new ArrayList<Table>());
            }
          }catch (IOException e) {
             e.printStackTrace();
         }
    	
    }
    
    // read menu.txt, create MenuItems and add them to Menu and its respective child class
    private void loadMenuItems(String filename) {
    	filename = System.getProperty("user.dir") + filename;

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
                	//System.out.println(item.getItemName() + " added to " + subMenu.getCourse());
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
    
    public void hireServers(String serverName) {
    	
    	// create new Server object
    	Server newServer = new Server(serverName);
    	
    	// add Server to servers map
    	servers.put(serverName, newServer);
    	
    	// add Server to server/tables map
    	serverTables.put(newServer, new ArrayList<Table>());
    }
    
    // map servers to tables
    public void assignServerToTable(String serverName, int tableNum) {
    	
    	// find Server object with given name
    	Server found = servers.get(serverName);
    	
    	Table table = getTableByNumber(tableNum);
    	
    	// add table to Servers internal list
    	found.addTable(table);
    	
    	// add table to server/tables map
    	serverTables.get(found).add(table);
    }
    
    // helper method - returns a list of copies of all current server objects
    private ArrayList<Server> getAlphabeticalServerList(){
    	ArrayList<Server> serverList = new ArrayList<Server>();
    	for (Server s : serverTables.keySet()) {
    		serverList.add(new Server(s));
    	}
    	
    	// gets alphabetical list of servers
    	serverList.sort(Server.sortByNameComparator());
    	return serverList;
    }
    
    // returns a string of all servers in the restaurant
    public String getAllServersInfo() {
    	ArrayList<Server> serverList = getAlphabeticalServerList();
    	
    	String allServers = ""; 
    	for (Server s : serverList) {
    		allServers += s.toString() + "\n";
    	}
    	return allServers;
    }
    
    public String getListOfServers() {
    	ArrayList<Server> serverList = getAlphabeticalServerList();
    	String serverStrList = "";
    	for (int i = 0; i < serverList.size(); i++) {
    		serverStrList += (i+1) + ". " +  serverList.get(i).getServerName() + "\n";
    	}
    	
    	return serverStrList.strip();
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
    
    // helper method
    private String getServerByTable(Table table) {
    	String serverName = "";
    	
    	// searches for a given table
    	for (Map.Entry<Server, ArrayList<Table>> entry : serverTables.entrySet()) {
    		if (entry.getValue().contains(table)) {
    			
    			// finds server for that table -- returns the name
    			Server found = entry.getKey();
    			serverName = found.getServerName();
    		}
    	}
    	return serverName;
    }
    
    public Table getTableByNumber(int tableNumber) {
    	if (tableNumber < tables.size())
    		return tables.get(tableNumber - 1);
    	else
    		return null;
    }
    
    // customer interaction
    public void seatCustomers(int customerAmt, int tableNum) {
        Table table = tables.get(tableNum - 1);
        ArrayList<Customer> customers = new ArrayList<Customer>();

        for (int i = 0; i < customerAmt; i++) {
            Customer newCustomer = new Customer(table, i + 1);
            customers.add(newCustomer);
        }
        tableMap.put(table, customers);
    }

    
    public void orderItem(Table table, int orderNum, String item, String modification) {
    	
    	// get customer associated with given orderNum
    	Customer customer = tableMap.get(table).get(orderNum - 1);
    	
    	Menu menu = getMenuForItem(item);
    	
    	// order item 
    	customer.orderItem(item, modification, menu);
    }
    
    // helper method -- closes an individual order 
    public void closeOrder(Table table, int orderNum, double tipAmt) {
    	
    	// add tip to customers bill
    	Customer customer = tableMap.get(table).get(orderNum - 1);
    	customer.tip(tipAmt);
    	
        // update the sales tracker with the customers order
    	sales.updateOrder(customer.getOrder());
    	
    	// add closed order to maintained list of closed orders -- uses a COPY of the Order
    	addToClosedOrders(customer.getOrder());
    	
    	// add tip to servers tip
    	Server server = servers.get(getServerByTable(table));
    	server.addTip(tipAmt);
    	sales.updateServerTips(servers);
    	
    	// TODO print order toString() ?
    	
    	// remove customer from table
    	tableMap.get(table).remove(customer);    	
    }
    
    // helper method -- adds closed orders to a list
    private void addToClosedOrders(Order closedOrder) {
    	closedOrders.add(closedOrder);
    }
    
    public Bill getBillFromCustomer(Table table, int orderNumber) {
    	Customer customer = tableMap.get(table).get(orderNumber - 1);
    	return customer.getBill();
    }
    
    
    // server functionality
    public Bill getBillByTable(Table table) {
    	

    	// create new table bill
    	Bill tableBill = new Bill();

    	// iterate through customers at given table
    	ArrayList<Customer> customers = tableMap.get(table);
    	for (Customer c : customers) {
    		
    		// sum the bill of all customers at table
    		tableBill.updateBeforeTipPrice(c.getBill().getPriceBeforeTip());
    	}
    	return tableBill;
    }
    
    // changes the bill amount before tip for all customers
    public void splitBillEvenly(Table table){
    	
    	// creates a tableBill that is the sum of all current bills at the table
    	Bill tableBill = getBillByTable(table);
    	
    	// get customers at table
    	ArrayList<Customer> customers = tableMap.get(table);
    	
    	// calculate amount per customer for evenly split bill
    	double amtPerCustomer = tableBill.getPriceBeforeTip()/customers.size();
    	
    	// change bill amount before tip for all customers
    	for (Customer c : customers) {
    		c.changeBillTotal(amtPerCustomer);
    		} 
    }
      
     public Menu getMenuForItem(String itemName) {
    	ArrayList<Menu> allMenus = new ArrayList<>();
     	allMenus.add(drinkMenu);
        allMenus.add(appMenu);
        allMenus.add(entreeMenu);
        allMenus.add(dessertMenu);
        
        for (Menu m : allMenus) {
        	if (m.containsMenuItem(itemName)) {
        		return m;
        	}
        }
        
        System.out.println("Can't find menu for item.");
        return null;
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
    
    public SalesTracker getSalesTracker() {
    	return new SalesTracker(sales);
    }
    
    // sorters

	public void setTableMap(HashMap<Table, ArrayList<Customer>> tableMap) {
		this.tableMap = tableMap;
	}
}