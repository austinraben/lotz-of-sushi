 package view;

import model.Restaurant;
import model.Menu;
import model.MenuItem;
import model.Order;
import model.FoodCourse;
import model.ManagerPassword;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private Restaurant restaurant;
    private java.util.Scanner scanner;
    private ManagerPassword managerPassword;
    
    private final String MAIN_MENU = "=================================\n    Welcome to Lotz of Sushi!\n=================================\n\nWhat would you like to do today?\n\n"+
    									"1. Manage\n2. Host\n3. Serve\n4. View Menu";
    									
    									

    public UserInterface(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.scanner = new java.util.Scanner(System.in);
        this.managerPassword = new ManagerPassword();
        
    }

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Lotz of Sushi");
        
        UserInterface ui = new UserInterface(restaurant);
        while (true) {
        	ui.mainMenu();
        }
    }
    
    public void mainMenu() {
    	System.out.println(MAIN_MENU);
    	System.out.print("\nEnter the number of the command you would like (1-4): ");
    	
    	Scanner userInput = new Scanner(System.in);
		String inputString = userInput.nextLine().strip().toLowerCase();

		switch(inputString) {
			case "1":
				this.manage();
				break;
			case "manage":
				this.manage();
				break;
			case "2":
				this.host();
				break;
			case "host":
				this.host();
				break;
			case "3":
				this.serve();
				break;
			case "4":
				this.displayMenu();
				break;
			default:
				System.out.println("Command not found. Please try again!");
				break;
		}
		
		Scanner wait = new Scanner(System.in);
		System.out.print("\nHit enter to return to list of commands: \n");
		String waitResponse = wait.nextLine();
    }
   
    // view sales, view tips
    public void manage() {
    	managerPassword.getManagerPassword("/data/staff.txt");
    	Scanner userInputPass = new Scanner(System.in);
    	System.out.print("\n=================================\n          Manger Login\n=================================\n");
    	System.out.print("\nEnter in password: ");
    	String password = userInputPass.nextLine().strip();
    	
    	try {
			if (ManagerPassword.hashPassword(password, managerPassword.getSalt()).equals(managerPassword.getHashedPassword())) {
				while (true) {
					System.out.print("\n=================================\n         Manager Menu\n=================================\n\n");
					System.out.println("1. View servers\n2. View tables\n3. View sales\n4. View tips\n5. View closed orders\n6. Hire servers\n7. Fire servers\n8. Exit Manage");
					System.out.print("\nEnter in a command (1-8): ");
					
					Scanner userInput = new Scanner(System.in);
					String inputString = userInput.nextLine().strip().toLowerCase();
					
					switch (inputString) {
						case "1":
							System.out.print("\n=================================\n           Server View\n=================================\n\n");
							System.out.print(restaurant.getAllServersInfo());
							System.out.print("\nPress Enter to return to Manager Menu");
							
							break;
						case "2": 
							System.out.print("\n===========================================\n                 Table View\n===========================================\n\n");
							System.out.print(restaurant.toString());
							System.out.print("\nPress Enter to return to Manager Menu");
							break;
						case "3":
							System.out.print("\n=================================\n           Sales View\n=================================\n");
							this.viewSales();
							break;
						case "4":
							System.out.println("TOTAL TIPS: " + restaurant.getSalesTracker().getTotalTips());
							break;
						case "5": 
							this.viewClosedOrders();
							break;
						case "6":
							this.hireServers();
							break;
						case "7":
							this.fireServers();
							break;
						case "8":
							break;
						default:
							System.out.println("Command not found. Please enter the NUMBER of the command!");
							break;
					}
					
					if (inputString.equals("8")) break;
					
					Scanner wait = new Scanner(System.in);
					String waitString = wait.nextLine();
				}
				managerPassword.setPassword(password);
				managerPassword.rewriteToFile("/data/staff.txt");
			}
			else {
				System.out.println("\nIncorrect password. Please try again!");
				return;
			}
		} 
    	catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void hireServers() {
    	System.out.print("\nEnter the name of the server you would like to hire: ");
    	Scanner userInput = new Scanner(System.in);
		String inputString = userInput.nextLine().strip().toLowerCase();
		
		this.restaurant.hireServers(capitalizeFirstLetterOfEachWord(inputString));
		System.out.println("\n" + capitalizeFirstLetterOfEachWord(inputString) + " has been hired!\n");
    }
    
    public void fireServers() {
    	boolean exit = false;
    	while (true) {
	    	System.out.println("\nWhich server would you like to fire?\n");
	    	System.out.println(restaurant.getListOfServers());
	    	System.out.print("\nEnter the NAME of the server: ");
	    	
	    	Scanner userInput = new Scanner(System.in);
			String inputString = userInput.nextLine().strip().toLowerCase();
			
			if (this.restaurant.serverIsHired(capitalizeFirstLetterOfEachWord(inputString))) {
				this.restaurant.fireServers(capitalizeFirstLetterOfEachWord(inputString));
				System.out.println("\n" + capitalizeFirstLetterOfEachWord(inputString) + " has been fired!\n");
				exit = true;
			}
			else if (inputString.equals("") || inputString.equals("none")) {
				System.out.println("\nNo server will be fired. Returning back to menu.");
				exit = true;
			}
			else {
				System.out.println("\nServer can't be found. Please try again!");
			}
			
			if (exit) break;
    	}
    }
    
    public void host() {
    	while (true) {
    		System.out.print("\n=================================\n            Host Menu\n=================================\n");
	    	System.out.println("1. Seat customers\n2. View servers\n3. View tables\n4. Assign server to table\n5. Remove server from table\n6. Exit Host");
	    	System.out.print("\nEnter in a command (1-6): ");
	    	
	    	Scanner userInput = new Scanner(System.in);
			String inputString = userInput.nextLine().strip().toLowerCase();
			switch (inputString) {
			case "1":
				this.seatCustomers();
				break;
			case "2":
				System.out.print("\n=================================\n           Server View\n=================================\n\n");
				System.out.print(restaurant.getAllServersInfo());
				System.out.print("\nPress Enter to return to Host Menu");
				break;
			case "3": 
				System.out.print("\n===========================================\n                 Table View\n===========================================\n\n");
				System.out.print(restaurant.toString());
				System.out.print("\nPress Enter to return to Host Menu");
				break;
			case "4":
				System.out.print("\n=================================================\n                Server Assignment\n=================================================\n");
				this.assignServersToTables();
				break;
			case "5":
				System.out.print("\n=================================================\n                Server Assignment\n=================================================\n");
				this.removeServerFromTable();
				break;
			case "6":
				break;
			default:
				System.out.println("Command not found. Please enter the NUMBER of the command!");
				break;
			}
			
			if (inputString.equals("6")) break;
			
			Scanner wait = new Scanner(System.in);
			String waitString = wait.nextLine();
    	}
    }
    
    public void seatCustomers() {
    	Scanner userInput = new Scanner(System.in);
    	System.out.print("\n====================================\n           Seat Customers\n====================================\n");
    	System.out.print("\nHow many customers should be seated? ");
    	int customerNum = userInput.nextInt();
    	
    	if (restaurant.getAvailableTables().equals("OPEN TABLES:")) System.out.println("\nNo tables are available.");
    	
    	else {
    	System.out.println("\n" + restaurant.getAvailableTables() + "\n");
    	
    	while(true) {
    	System.out.print("\nEnter the NUMBER of the table to seat them at (1-25): ");
    	int tableNum = userInput.nextInt();
    	
    	boolean validTable = false;
        String[] tableList = restaurant.getAvailableTables().split(": ");
        if (tableList.length > 1) {
        	tableList = tableList[1].split(",");
    	for (int i = 0; i < tableList.length; i++) {
    		if(Integer.valueOf(tableList[i]).equals(tableNum)) validTable = true;
    	}
    	} 
    	
    	if (validTable) {
    	restaurant.seatCustomers(customerNum, tableNum);
    	System.out.println("\n" + customerNum + " customers have been seated at TABLE " + tableNum);
    	break;
    	} else System.out.println("\nThis table has no server or has already been seated. Try again.");
    	}
    	}
    }
    
    public void assignServersToTables() {
    	Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhich server would you like to assign to a table?\n");
            System.out.println(restaurant.getListOfServers());
            System.out.print("\nEnter the NAME of the server: ");

            String serverName = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());

            if (restaurant.serverIsHired(serverName)) {
            	
            	while(true) {
            	System.out.println("\n" + restaurant.getUnassignedTables());
                System.out.print("\nEnter table NUMBER to assign " + serverName + " to (1-25): ");
                int tableNum = userInput.nextInt();
                
                boolean validTable = false;
                String[] tableList = restaurant.getUnassignedTables().split(": ")[1].split(",");
            	for (int i = 0; i < tableList.length; i++) {
            		if(Integer.valueOf(tableList[i]).equals(tableNum)) validTable = true;
            	} 
            	
            	if (validTable) {
                restaurant.assignServerToTable(serverName, tableNum);
                break; 
            	} else System.out.println("\n"
            			+ "This table has already been assigned. Please try again.");
            	} break;
            } else {
                System.out.println("This server does not exist. Please try again.\n");
            }
        }
    }
    
    public void removeServerFromTable() {
        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhich server would you like to remove from a table?\n");
            System.out.println(restaurant.getListOfServers());
            System.out.print("\nEnter the NAME of the server: ");

            String serverName = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());

            if (restaurant.serverIsHired(serverName)) {
                String[] tableList = restaurant.getTablesFromServer(serverName).split(":");

                if (tableList.length <= 1 || tableList[1].strip().isEmpty()) {
                    System.out.println("\nThis server has no assigned tables.");
                    continue; 
                }

                while (true) {
                    System.out.println("\n" + restaurant.getTablesFromServer(serverName));
                    System.out.print("\nEnter table NUMBER you'd like to remove " + serverName + " from (1-25): ");

                    int tableNum = userInput.nextInt();
                    userInput.nextLine(); 

                    boolean validTable = false;
                    String[] assignedTables = tableList[1].split(",");

                    for (String table : assignedTables) {
                        if (Integer.valueOf(table.strip()).equals(tableNum)) {
                            validTable = true;
                            break;
                        }
                    }

                    if (validTable) {
                        restaurant.removeServerFromTable(serverName, tableNum);
                        return; 
                    } else {
                        System.out.println("\nThis table is not currently assigned to " + serverName + ". Please try again.");
                    }
                }
            } else {
                System.out.println("\nThis server does not exist. Please try again.\n");
            }
        }
    }

    
    public void viewClosedOrders() {
    	System.out.print("\n====================================\n           All Closed Orders\n====================================\n");
    	if (this.restaurant.getClosedOrders().size() == 0) System.out.println("\nNo orders to show.\n");
    	for (Order o : this.restaurant.getClosedOrders()) {
    		System.out.println("\n" + o);
    	}
    }
    
    public void serve() {
    	
    	Scanner userInputName = new Scanner(System.in);
    	System.out.print("\n=================================\n          Server Login\n=================================\n");
    	System.out.print("\nPlease enter in your name: ");
    	String serverName = capitalizeFirstLetterOfEachWord(userInputName.nextLine().strip());
    	
    	if (!this.restaurant.serverIsHired(serverName)) {
    		System.out.println("\nServer not found. Please try again!\n");
    		return;
    	}

    	while (true) {
    		String firstName = (serverName.split(" "))[0];
    		String header = firstName + "'s Server Menu";
    		int totalWidth = 33;
    		int padding = (totalWidth - header.length()) / 2;
    		String formattedHeader = " ".repeat(Math.max(0, padding)) + header;

    		System.out.print("\n=================================\n" +
    		    formattedHeader + "\n" +
    		    "=================================\n\n");
        	System.out.println("1. Take orders\n2. View tables\n3. Close Order\n4. Exit Server");
        	System.out.print("\nEnter in a command (1-4): ");
    		
	    	Scanner userInput = new Scanner(System.in);
			String inputString = userInput.nextLine().strip().toLowerCase();
			
			switch (inputString) {
			case "1":
				System.out.print("\n=================================\n          Take Orders!\n=================================\n\n");
				System.out.println("\nYOUR " + restaurant.getTablesFromServer(serverName) + "\n");
				this.takeOrders(serverName);
				break;
			case "2":
				System.out.print("\n=================================\n          Table View\n=================================\n\n");
				getIndividualTable(serverName);
				break;
			case "3":
				System.out.print("\n=================================\n          Close Order\n=================================\n\n");
				System.out.println("\nYOUR " + restaurant.getTablesFromServer(serverName) + "\n");
				this.closeOrder(serverName);
				break;
			case "4":
				break;
			default:
				System.out.println("Command not found. Please enter the NUMBER of the command!");
				break;
			}
			
			if (inputString.equals("4")) break;
			
			Scanner wait = new Scanner(System.in);
			String waitString = wait.nextLine();
    	}
    }
    
    public void takeOrders(String serverName) {
    	Scanner userInput = new Scanner(System.in);
    	System.out.print("Which table are you taking orders from? (1-25): ");
    	int tableNum = userInput.nextInt();
    	
    	userInput.nextLine();
    	boolean validTable = false;
    	String[] checkTableList = restaurant.getTablesFromServer(serverName).split(" ");
    	if (checkTableList.length <= 1) {
    		System.out.println("\nNo Tables Found. Ask host to assign you to a table.");
    		return;
    	}
    	String[] tableList = restaurant.getTablesFromServer(serverName).split(" ")[1].split(",");
    	for (int i = 0; i < tableList.length; i++) {
    		if(Integer.valueOf(tableList[i]).equals(tableNum)) validTable = true;
    	}
    	
    	if (validTable) {
	    	int customers = restaurant.getNumCustomers(tableNum);
	    	if (customers == 0) {
	    		System.out.println("\nThis table has not been seated yet. Try again when host has seated table.");
	    		return;
	    	}
	    	for (int i = 0; i < customers; i++) {
	    		while(true) {
	    		System.out.println("\nCostumer #" + (i+1));
	    		System.out.print("Ordered item: ");
	    		String orderedItem = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());
	    		System.out.print("Modification: ");
	    		String modification = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());
	    		try {
	    			restaurant.orderItem(tableNum, i + 1, orderedItem, modification);
	    		} catch (Exception e) {
	    			System.out.println("\nMenu item not found. Please Try again!\n");
	    		}
	    		
	    		
	    		System.out.print("Next customer? (Y/N) ");
    			// print out each customers order
    			if(userInput.nextLine().trim().equalsIgnoreCase("Y")) break;
	    		}
	    	System.out.println("\n" + restaurant.getTableOrder(tableNum).toString());
	    	}
    	}
    	else {
    		System.out.println("\nYou are not assigned to this table. Ask host to assign you to the table and try again!");
    		return;
    	}
    }
    	// print order to show server
    
    public void closeOrder(String serverName) {
    	Scanner userInput = new Scanner(System.in);
    	System.out.print("Which table are you taking orders from? (1-25): ");
    	int tableNum = userInput.nextInt();
    	
    	userInput.nextLine();
    	boolean validTable = false;
    	String[] checkTableList = restaurant.getTablesFromServer(serverName).split(" ");
    	if (checkTableList.length <= 1) {
    		System.out.println("\nNo Tables Found. Ask host to assign you to a table.");
    		return;
    	}
    	
    	String[] tableList = restaurant.getTablesFromServer(serverName).split(" ")[1].split(",");
    	for (int i = 0; i < tableList.length; i++) {
    		if(Integer.valueOf(tableList[i]).equals(tableNum)) validTable = true;
    	}
    	
    	if (validTable) {
	    	int customers = restaurant.getNumCustomers(tableNum);
	    	if (customers == 0) {
	    		System.out.println("\nThis table has not been seated yet. Try again when host has seated table.");
	    		return;
	    	}
	    	
	    	String inputString;
	    	while (true) {
		    	System.out.println("1. Pay seperate\n2. Split bill evenly\n");
		    	System.out.print("How are customers paying? ");
		    	inputString = userInput.nextLine().strip().toLowerCase();
		    	
		    	switch (inputString) {
		    		case "1":
		    			break;
		    		case "2":
		    			this.restaurant.splitBillEvenly(tableNum);
		    			break;
		    		default:
		    			System.out.println("\nCommand not found. Please try again!\n");
		    			break;
		    	
		    	}
		    	if (inputString.equals("1") || inputString.equals("2")) break;
	    	}
	    	
	    	for (int i = 0; i < customers; i++) {
	    		System.out.println("\nCostumer #" + (i+1));
	    		System.out.print("Tip Amount: ");
	    		double tipAmt = userInput.nextDouble();
	    		userInput.nextLine();
	    		System.out.println("\n" + this.restaurant.getOrderFromCustomer(tableNum, i + 1));
	    		this.restaurant.closeOrder(tableNum, i + 1, tipAmt);
	    	}
	    	
	    	System.out.println(restaurant.getTableOrder(tableNum).toString());
	    	this.restaurant.clearCustomersFromTable(tableNum);
	    	System.out.println("\nTable " + tableNum + " is now available to be seated!\n");
    	}
    	else {
    		System.out.println("\nYou are not assigned to this table. Ask host to assign you to the table and try again!");
    		return;
    	}
    }
    
    public void getIndividualTable(String serverName) {
    	System.out.println("YOUR " + restaurant.getTablesFromServer(serverName) + "\n");
    	System.out.print("What table would you like to view? (Enter NUMBER): ");
    	int choice = scanner.nextInt();
    	
    	restaurant.printServerTable(choice);
    }
    
    
    
    // Display the menu, grouped by FoodCourse and then by specificCategory
    public void displayMenu() {
        System.out.print("\nWhich menu would you like to view? (App/Drink/Entree/Dessert/): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        Menu selectedMenu = null;
        switch (choice) {
            case "app":
                selectedMenu = restaurant.getAppMenu();
                break;
            case "drink":
                selectedMenu = restaurant.getDrinkMenu();
                break;
            case "entree":
                selectedMenu = restaurant.getEntreeMenu();
                break;
            case "dessert":
                selectedMenu = restaurant.getDessertMenu();
                break;
            default:
                System.out.println("Invalid choice. Please select apps, drinks, entree, or dessert.");
                return;
        }

        if (selectedMenu != null) {
            selectedMenu.printMenu();
        }
    }
    
    public void viewSales() {
    	System.out.println("\nWhat would you like to view?\n");
    	System.out.println("1. Sales for all items\n2. Revenue for all items\n3. Sale for specific item\n4. Revenue for specific item\n");
    	System.out.print("Enter in command (1-4): ");
    	
    	Scanner userInput = new Scanner(System.in);
		String inputString = userInput.nextLine().strip().toLowerCase();
		
		switch (inputString) {
			case "1":
				System.out.println("\n" + this.restaurant.getSalesTracker().quantitySoldString() + "\n");
				break;
			case "2":
				System.out.println("\n" + this.restaurant.getSalesTracker().totalSalesString() + "\n");
				break;
			case "3":
				this.saleOfItem();
				break;
			case "4":
				this.revenueOfItem();
				break;
			default:
				System.out.println("Command not found. Please enter the NUMBER of the command!");
				break;
		}
    }
    
    public void saleOfItem() {
    	System.out.print("\nEnter in the name of the food item you wish to check: ");
    	
		Scanner userInput = new Scanner(System.in);
		String inputString = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip());
		
		Menu myMenu = this.restaurant.getMenuForItem(inputString);
		if (myMenu == null) {
			System.out.println("Can't find this item. Please try again!");
			return;
		}
		else {
			MenuItem myItem = myMenu.getMenuItem(inputString);
			System.out.println("\n" + inputString + ": " + this.restaurant.getSalesTracker().getQuantityForItem(myItem) + "\n");
			return;
		}
	}
    
    public void revenueOfItem() {
    	System.out.print("\nEnter in the name of the food item you wish to check: ");
    	
		Scanner userInput = new Scanner(System.in);
		String inputString = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip());
		
		Menu myMenu = this.restaurant.getMenuForItem(inputString);
		if (myMenu == null) {
			System.out.println("Can't find this item. Please try again!");
			return;
		}
		else {
			MenuItem myItem = myMenu.getMenuItem(inputString);
			System.out.println("\n" + inputString + ": $" + this.restaurant.getSalesTracker().getSaleForItem(myItem) + "\n");
			return;
		}
	}
    
    // helper method for transforming user input to input that restaurant and menu use
    private static String capitalizeFirstLetterOfEachWord(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        StringBuilder result = new StringBuilder();
        // split by any whitespace
        String[] words = text.split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1));
            }
         // re-add space after each word
            result.append(" ");
        }

        return result.toString().trim();
    }
    
}
