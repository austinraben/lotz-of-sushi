/*
 * This class models the Restaurant itself. Restaurant has a name, an HashMap of servers, a HashMap of Servers and their respective lists of 
 * tables they are serving, a HashMap of Tables and the Table's respective list of customers at the table, a list of all tables in restaurant, a list
 * of previous orders/closed orders that have been payed, four menus of different food courses, and finally a SalesTracker to keep track of restaurant's
 * sales. This is the main class that most commands start from.
 */

package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
	public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
	
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
    
    // default constructor
    public Restaurant(String name) { 
        this.name = name;
        this.servers = new HashMap<>();
        this.serverTables = new HashMap<>();
        
        // fill servers and serverTables with the servers listed in staff.txt
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
        
        // create four menus of different food courses
        this.drinkMenu = new Menu(FoodCourse.DRINKS);
        this.appMenu = new Menu(FoodCourse.APPS);
        this.entreeMenu = new Menu(FoodCourse.ENTREES);
        this.dessertMenu = new Menu(FoodCourse.DESSERTS);
        
        // fill all menus with MenuItem objects created from information in menu.txt
        try {
            loadMenuItems("/data/menu.txt");
        } catch (Exception e) {
            System.err.println("Error loading menu: " + e.getMessage());
            e.printStackTrace();
        }
        
        initializeSalesTracker();
    }
    
    private void initializeSalesTracker() {
    	/*
    	 * This method initializes the SalesTracker for the restaurant by adding all MenuItems to
    	 * the SalesTracker's internal HashMaps and all Server objects to internal HashMap.
    	 */
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
    	/*
    	 * This is a helper method for the constructor to read from staff.txt and "hire" all servers
    	 * that were currently employed. This makes it so servers stay in the system even after program stops running.
    	 */
    	filename = System.getProperty("user.dir") + filename;
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            
    		String line;
            
            while ((line = reader.readLine()) != null) {
            	// skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("//")) {
                	if (line.trim().equals("//MANAGER")){
                		break;
                	}
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
    
    public void loadMenuItems(String filename) {
    	/*
    	 * This method constructs the menus of the restaurant by parsing and creating MenuItems from
    	 * menu.txt and adding those objects to its respective menu.
    	 */
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

                MenuItem item = new DiscountedMenuItem(new MenuItem(course, category, itemName, price, modifiable, description));
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
    
    public Menu getMenuForCourse(FoodCourse course) {
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
    	/*
    	 * This method hires a new server by creating a Server object, adding Server
    	 * object to internal lists/HashMaps, and writing to staff.txt file to save
    	 * the server for next program run.
    	 */
    	
        // new server object
        Server newServer = new Server(serverName);
        
        // add server to server map and server tables map
        servers.put(serverName, newServer);
        serverTables.put(newServer, new ArrayList<>());

        // prepare new server list
        ArrayList<Server> allServers = getAlphabeticalServerList();
        
        // update text file path
        String filename = System.getProperty("user.dir") + "/data/staff.txt";

        try {
        	
        	// list of everything in manager section including header
            List<String> managerSection = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            
            String line;
            boolean managerReached = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("//MANAGER")) {
                    managerReached = true;
                    managerSection.add(line);
                } 
                else if (managerReached) {
                	// everything after //MANAGER
                    managerSection.add(line);
                }
            }
            reader.close();

            // rewrite file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("//SERVERS\n");
            for (Server s : allServers) {
                writer.write(s.getServerName() + "\n");
            }
            for (String managerLine : managerSection) {
                writer.write(managerLine + "\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    
    public void fireServers(String serverName) {
    	/*
    	 * This method fires a server by creating a Server object and using that object
    	 * to remove server from internal lists/HashMaps. This method also rewrites staff.txt
    	 * as to not re-hire the server upon rerunning program.
    	 */
    	
        // create new server object
        Server newServer = new Server(serverName);
        
        // remove server from server map and server table map
        servers.remove(serverName);
        serverTables.remove(newServer);

        // prepare new server list
        ArrayList<Server> allServers = getAlphabeticalServerList();
        
        // update text file path
        String filename = System.getProperty("user.dir") + "/data/staff.txt";

        try {
        	// list of everything in manager section including header
            List<String> managerSection = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            boolean managerReached = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("//MANAGER")) {
                    managerReached = true;
                    // preserves header and two lines after //MANAGER
                    managerSection.add(line);
                } 
                else if (managerReached) {
                    managerSection.add(line);
                }
            }
            reader.close();

            // rewrite file
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write("//SERVERS\n");
            
            // rewrites servers to file (new server already removed from this list)
            for (Server s : allServers) {
                writer.write(s.getServerName() + "\n");
            }
            for (String managerLine : managerSection) {
                writer.write(managerLine + "\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    
    
    

    public void assignServerToTable(String serverName, int tableNum) {
    	/*
    	 * This method gets a Server object by serverName, Table by tableNum, and
    	 * adds that Table to the Server's tableMap (mapping servers to list of their tables).
    	 */
    	
    	// only assigns server to table if table does not have a server
    	if (!tableHasServer(tableNum)) {
    		
    	// find Server object with given name
    	Server found = servers.get(serverName);
    	
    	Table table = getTableByNumber(tableNum);
    	
    	// add table to Servers internal list
    	found.addTable(table);
    	
    	// add table to server/tables map
    	serverTables.get(found).add(table);
    	
    	System.out.println("\n" + serverName + " has been assigned to Table " + tableNum);
    	
    	} else System.out.println("\nThis table has already been assigned.");
    	}
    
    public void removeServerFromTable(String serverName, int tableNum) {
    	/*
    	 * This method gets a Server object by serverName, Table by tableNum, and
    	 * removes that Table to the Server's tableMap (mapping servers to list of their tables).
    	 */
    	if (tableHasServer(tableNum)) {
    		// find Server object with given name
        	Server found = servers.get(serverName);
        	
        	Table table = getTableByNumber(tableNum);
        	// add table to Servers internal list
        	found.removeTable(table);
        	
        	// add table to server/tables map
        	serverTables.get(found).remove(table);
        	System.out.println(serverName + " has been removed from " + table.toString());

    	} else System.out.println("\nThis table has not yet been assigned.");
    }
   
    
    private ArrayList<Server> getAlphabeticalServerList(){
    	/*
    	 * This is a helper method that returns a list of all servers sorted in alphabetical order.
    	 */
    	ArrayList<Server> serverList = new ArrayList<Server>();
    	for (Server s : serverTables.keySet()) {
    		serverList.add(new Server(s));
    	}
    	
    	// gets alphabetical list of servers
    	serverList.sort(Server.sortByNameComparator());
    	return serverList;
    }
    
    public String getAllServersInfo() {
    	/*
    	 * This method returns a string of all servers in the restaurant, sorted by name
    	 */
    	
    	ArrayList<Server> serverList = getAlphabeticalServerList();
    	
    	String allServers = ""; 
    	for (Server s : serverList) {
    		allServers += s.toString() + "\n";
    	}
    	return allServers;
    }
    
    
    // returns a numbered list of all current servers' names
    public String getListOfServers() {
    	ArrayList<Server> serverList = getAlphabeticalServerList();
    	String serverStrList = "";
    	for (int i = 0; i < serverList.size(); i++) {
    		serverStrList += (i+1) + ". " +  serverList.get(i).getServerName() + "\n";
    	}
    	
    	return serverStrList.strip();
    }
    
    private ArrayList<Table> createTables() {
    	/*
    	 * This is a helper method to initialize the Table objects
    	 * in the constructor.
    	 */
    	
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
   
    // verifies whether a server exists in the restaurant
    public boolean serverIsHired(String serverName) {
    	return servers.keySet().contains(serverName);
    }
    
    public String getServerByTable(int tableNum) {
    	/*
    	 * This method retrieves the name of a server at a certain table.
    	 */
    	Table table = tables.get(tableNum - 1);
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
    
    public void seatCustomers(int customerAmt, int tableNum) {
    	/*
    	 * This method creates a certain amount of Customer objects, and seats them all
    	 * at a specified table.
    	 */
        Table table = tables.get(tableNum - 1);
        
        // check if Table is free or not
        if (!(tableSeated(table.getTableNumber()))) {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        String serverName = getServerByTable(tableNum);
        
        for (int i = 0; i < customerAmt; i++) {
            Customer newCustomer = new Customer(table, i + 1, serverName);
            customers.add(newCustomer);
        }
        tableMap.put(table, customers);
        } 
        else System.out.println("This table has already been seated.");
        }
    
    public void orderItem(int tableNum, int orderNum, String item, String modification) {
    	/*
    	 * This method calls upon separate method in Customer to add an OrderedItem to
    	 * a Customer's internal Order.
    	 */
        Table table = getTableByNumber(tableNum);
        Customer customer = tableMap.get(table).get(orderNum - 1);
        Menu menu = getMenuForItem(item);
        MenuItem originalItem = menu.getMenuItem(item);

        
        customer.orderItem(originalItem, modification, menu);
    }
    
    public void closeOrder(int tableNum, int orderNum, double tipAmt) {
    	/*
    	 * This method closes a Customer's order by entering in the tip that the customer gave and 
    	 * adding the order to the list of closed orders. This method should be called when the customer
    	 * has paid and left the tip.
    	 */
    	
    	Table table = tables.get(tableNum - 1);
    	// add tip to customers bill
    	Customer customer = tableMap.get(table).get(orderNum - 1);
    	customer.tip(tipAmt);
    	
        // update the sales tracker with the customers order
    	sales.updateOrder(customer.getOrder());
    	
    	// add closed order to maintained list of closed orders -- uses a COPY of the Order
    	addToClosedOrders(customer.getOrder());
    	
    	// add tip to servers tip
    	Server server = servers.get(getServerByTable(tableNum));
    	server.addTip(tipAmt);
    	sales.updateServerTips(servers);
    	
    }
    
    private void addToClosedOrders(Order closedOrder) {
    	/*
    	 * This is a helper method that adds to the closed order list and re-adjusts
    	 * the closedOrder's order number to ensure no duplicate order numbers.
    	 */
    	Order newClosedOrder = new Order(closedOrder);
    	newClosedOrder.setOrderNum(closedOrders.size() + 1);
    	closedOrders.add(newClosedOrder);
    }
    
    public Bill getBillFromCustomer(int tableNum, int orderNumber) {
    	Table table = tables.get(tableNum - 1);
    	Customer customer = tableMap.get(table).get(orderNumber - 1);
    	
    	return customer.getBill();
    } 
    
    // server functionality
    
    public void printServerTable(int tableNum) {
    	/*
    	 * This method will print a string representation of a Table's customers and
    	 * the customers' orders comprised as a Table's Bill.
    	 */
    	Table table = getTableByNumber(tableNum);
    	
    	String tableStr = table.toString() + "\n";
    	tableStr += "# of Customers: " + tableMap.get(table).size() + "\n";
    	tableStr += "Current Table Bill:\n";
    	
    	for (Customer c : tableMap.get(table)) {
    		tableStr += c.getOrder().toString();
    	}
    	
    	System.out.print(tableStr);
    
    }
    public Bill getBillByTable(int tableNum) {
    	/*
    	 * This method creates a Bill object for a collective Table, representing
    	 * the Bill of each customer at that Table combined.
    	 */
    	
    	Table table = tables.get(tableNum - 1);
    	// create new table bill
    	Bill tableBill = new Bill();

    	// iterate through customers at given table
    	ArrayList<Customer> customers = tableMap.get(table);
    	int size = customers.size();
    	for (int i = 0; i < size; i++) {
    		Bill customerBill = getBillFromCustomer(tableNum, i + 1);
    		
    		// sum the bill of all customers at table
    		tableBill.updateBeforeTipPrice(customerBill.getPriceBeforeTip());
    		tableBill.updateTipPrice(customerBill.getPriceAfterTip() - customerBill.getPriceBeforeTip());
    	}

    	return new Bill(tableBill);
    }
    
    public void splitBillEvenly(int tableNum){
    	/*
    	 * This method allows for Customer's to split their Table Bill evenly
    	 * among each other. It changes the bill amount before tip for all customers.
    	 */
    	
    	Table table = tables.get(tableNum - 1);
    	// creates a tableBill that is the sum of all current bills at the table
    	Bill tableBill = getBillByTable(tableNum);
    	
    	// get customers at table
    	ArrayList<Customer> customers = tableMap.get(table);
    	
    	// calculate amount per customer for evenly split bill
    	double amtPerCustomer = tableBill.getPriceBeforeTip()/customers.size();
    	amtPerCustomer = Math.round(amtPerCustomer * 100.0) / 100.0;
    	
    	// change bill amount before tip for all customers
    	for (Customer c : customers) {
    		c.changeBillTotal(amtPerCustomer);
    		} 
    }
      
    // returns menu for which that item is held in
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

    
    public Order getOrderFromCustomer(int tableNum, int orderNum) {
    	/*
    	 * This method gets a specific order from a Customer by which Table they are at and
    	 * what order number they are at that table.
    	 */
    	Table table = tables.get(tableNum - 1);
    	if (orderNum - 1 >= tableMap.get(table).size())
    		return null;
    	Customer customer = tableMap.get(table).get(orderNum - 1);
    	Order returnOrder = customer.getOrder();
    	returnOrder.setOrderNum(orderNum);
    	return returnOrder;
    }
    
    // returns a String list of Tables a server is serving
    public String getTablesFromServer(String serverName) {
    	Server myServer = servers.get(serverName);
    	String tableStr = "TABLES: ";
		for (Table t : myServer.getTables()) {
			tableStr += t.getTableNumber() + ",";
		}
		
		return tableStr.substring(0, tableStr.length() - 1);
    }
    
    
    public String getAvailableTables() {
    	/*
    	 * This method returns a String of all Tables that have not been seated and have a server waiting for
    	 * customers to be seated.
    	 */
    	 String allAvailable = "OPEN TABLES: ";
    	 for (Table t : tables) {
    		 // Table must not be occupied and must have a server in order to be sat at
    		 if ((!(tableSeated(t.getTableNumber()))) && tableHasServer(t.getTableNumber())) allAvailable += t.getTableNumber() + ",";
    	 }
    	 return allAvailable.substring(0, allAvailable.length() - 1);
     }
    
    
     public String getUnassignedTables() {
    	 /*
    	  * This method returns a String of all Tables with no server yet assigned to them
    	  */
    	 String allUnassigned = "UNASSIGNED TABLES: ";
    	 for (Table t : tables) {
    		 boolean isAssigned = false;
    	     for (ArrayList<Table> serverTablesList : serverTables.values()) {
    	    	 if (serverTablesList.contains(t)) {
    	    		 isAssigned = true;
    	             break;
    	         }
    	     }
    	     if (!isAssigned) {
    	    	 allUnassigned += t.getTableNumber() + ",";
    	     }
    	 }
		return allUnassigned.substring(0, allUnassigned.length() - 1);
    }
     
     public int getNumCustomers(int tableNum) {
    	 Table t = getTableByNumber(tableNum);
    	 return tableMap.get(t).size();
     }
    
    // tells if a Table has customers seated at it
    public boolean tableSeated(int tableNumber) {
    	Table table = tables.get(tableNumber - 1);
    	int customersAtTable = tableMap.get(table).size();
    	return (customersAtTable > 0);
    }
    // tells if a Tables has been assigned a server
    public boolean tableHasServer(int tableNumber) {
    	Table table = tables.get(tableNumber - 1);
    	for (ArrayList<Table> st : serverTables.values()) {
    		if (st.contains(table)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public void clearCustomersFromTable(int tableNumber) {
    	/*
    	 * This method simulates customers leaving a Table, clearing the Table allows the new customers
    	 * to be sat at this Table.
    	 */
    	Table table = tables.get(tableNumber - 1);
    	tableMap.put(table, new ArrayList<Customer>());
    }
    
    public Order getTableOrder(int tableNumber) {
    	/*
    	 * This method returns a combined Order from all customers at a Table.
    	 */
    	Table table = tables.get(tableNumber - 1);
    	int customers = getNumCustomers(tableNumber);
    	String serverName = getServerByTable(tableNumber);
    	
    	// negative order number tells Order toString that it is the Table's Order
    	Order combinedOrder = new Order(-1, serverName);
    	
    	for (Customer c : tableMap.get(table)) {
    		for (OrderedItem oi : c.getOrder().getItems()) {
    			// adds to combined order
    			combinedOrder.orderItem(oi.getItemName(), oi.getModification(), getMenuForCourse(oi.getFoodCourse()), oi);
    		}
    		combinedOrder.makeTip(c.getOrder().getTip());
    	}
    
    	
    	return combinedOrder;
    }
    
    public Table getTableByNumber(int tableNumber) {
    	if (tableNumber <= tables.size())
    		return tables.get(tableNumber - 1);
    	else
    		return null;
    }
    
    // returns if MenuItem can be modified or not
    public boolean hasModification(String itemName) {
    	Menu menu = getMenuForItem(itemName);
    	MenuItem item = menu.getMenuItem(itemName);
    	return item.isModifiable();
    }
    

    // getters
    
    public Menu getDrinkMenu() {
    	return new Menu(drinkMenu);
    }

    public Menu getAppMenu() {
        return new Menu(appMenu);
    }

    public Menu getEntreeMenu() {
        return new Menu(entreeMenu);
    }

    public Menu getDessertMenu() {
        return new Menu(dessertMenu);
    }  
 
    public SalesTracker getSalesTracker() {
    	return new SalesTracker(sales);
    }
    
    public ArrayList<Order> getClosedOrders(){
    	return new ArrayList<>(closedOrders);
    }
    
    // sorters

	public void setTableMap(HashMap<Table, ArrayList<Customer>> tableMap) {
		this.tableMap = tableMap;
	}
	
	
	// string methods
	public void printMenu(String foodCourse) {
		switch(foodCourse) {
		case "drink":
			drinkMenu.printMenu();
			break;
		case "app":
			appMenu.printMenu();
			break;
		case "entree":
			entreeMenu.printMenu();
			break;
		case "dessert":
			dessertMenu.printMenu();
			break;
		case "full":
			appMenu.printMenu();
			entreeMenu.printMenu();
			dessertMenu.printMenu();
			drinkMenu.printMenu();
			break;
		default:
	        System.out.println("Invalid choice. Please select apps, drinks, entree, or dessert.");
		}
	}
	
	
	@Override
	public String toString() {
		// Start building the restaurant layout
        StringBuilder restaurant = new StringBuilder();

        restaurant.append(" ------------------------------------------ \n")
                  .append(" |                   |                    | \n")
                  .append(" |   RESTROOMS       |     KITCHEN        | \n")
                  .append(" |                   |                    | \n")
                  .append(" --DOOR----------------------------DOOR---- \n");

        for (int i = 0; i < 24; i += 3) {
            restaurant.append(" |                                        | \n");
            restaurant.append(" |");

            // Add 3 table labels per row
            for (int j = 0; j < 3; j++) {
                if (i + j >= 24) break;

                String tableLabel = String.format(" Table %d:", i + j + 1);
                
                // if Table is seated string will be green, else will be red
                tableLabel = (tableSeated(i + j + 1) ? GREEN : RED) + tableLabel + RESET;
                restaurant.append(String.format(" %-13s", tableLabel));
                if ((i + j + 1) < 10) {
                	restaurant.append("   ");
                }
                else {
                	restaurant.append("  ");
                }
            }
            restaurant.append(" |\n");

            restaurant.append(" |");

            // Add up to 3 server labels
            for (int j = 0; j < 3; j++) {
                if (i + j >= 24) break;
                
                // if tableHasServer string will be green, else will be red
                String serverLabel = tableHasServer(i + j + 1) ? GREEN + " SERVER " + RESET : RED + " SERVER " + RESET;
                restaurant.append(" ");
                restaurant.append(String.format(" %-13s", serverLabel));
                restaurant.append("   ");
            }
            restaurant.append(" |\n");
        }
        // 25th table in middle
        restaurant.append(" |                                        |\n");
        restaurant.append(" |              ");
        String tableLabel = "Table 25:";
        
        // if Table is seated string will be green, else will be red
        tableLabel = (tableSeated(25) ? GREEN : RED) + tableLabel + RESET;
        restaurant.append(String.format(" %-13s", tableLabel));
        restaurant.append("                |\n");
        restaurant.append(" |              ");
        
        // if tableHasServer string will be green, else will be red
        String serverLabel = tableHasServer(25) ? GREEN + " SERVER " + RESET : RED + " SERVER " + RESET;
        restaurant.append(String.format(" %-13s", serverLabel));
        restaurant.append("                 |\n");
        
        
        restaurant.append(" |                                        |\n")
                  .append(" | HOST                                   |\n")
                  .append(" |                                        |\n")
                  .append(" --ENTRANCE-------------------------------- \n");

        return restaurant.toString();
    }
}
	
	/*
	@Override
	public String toString() {
		String restaurant = " ------------------------------------------ \n" +
							" |                   |                    | \n" +
							" |   RESTROOMS       |     KITCHEN        | \n" +
							" |                   |                    | \n" +
							" --DOOR----------------------------DOOR---- \n" +
							" |                                        | \n" +
							" | Table 1:     Table 2:     Table 3:     | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" | Table 4:     Table 5:     Table 6:     | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" | Table 7:     Table 8:     Table 9:     | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" | Table 10:    Table 11:    Table 12:    | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" | Table 13:    Table 14:    Table 15:    | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" | Table 16:    Table 17:    Table 18:    | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" | Table 19:    Table 20:    Table 21:    | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" | Table 22:    Table 23:    Table 24:    | \n" +
							" |  SERVER       SERVER       SERVER      | \n" +
							" |                                        | \n" +
							" |              Table 25:                 | \n" +
							" |               SERVER                   | \n" +
							" |                                        | \n" +
							" | HOSTESS                                | \n" +
							" |                                        | \n" +
							" --ENTRANCE-------------------------------- \n";

	}
*/