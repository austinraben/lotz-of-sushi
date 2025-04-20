 package view;

import model.Restaurant;
import model.Menu;
import model.MenuItem;
import model.FoodCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {
    private Restaurant restaurant;
    private java.util.Scanner scanner;
    private java.util.Random random;
    
    private final String MAIN_MENU = "=================================\n    Welcome to Lotz of Sushi!\n=================================\n\nWhat would you like to do today?\n\n"+
    									"1. Manage\n2. Host\n3. Serve\n4. View Menu";
    									
    									

    public UserInterface(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.scanner = new java.util.Scanner(System.in);
        this.random = new java.util.Random();
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
			case "5":
				break;
			case "6":
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
    	while (true) {
    		System.out.print("\n=================================\n         Manager Menu\n=================================\n\n");
	    	System.out.println("1. View servers\n2. View tables\n3. View sales\n4. View tips\n5. Hire servers\n6. Fire servers\n7. Exit Manage");
	    	System.out.print("\nEnter in a command (1-7): ");
	    	
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
					restaurant.getSalesTracker().getTotalTips();
					break;
				case "5": 
					break;
				case "6":
					break;
				case "7":
					break;
				default:
					System.out.println("Command not found. Please enter the NUMBER of the command!");
					break;
			}
			
			if (inputString.equals("7")) break;
			
			Scanner wait = new Scanner(System.in);
			String waitString = wait.nextLine();
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
    	System.out.println("\n" + restaurant.getAvailableTables() + "\n");
    	System.out.print("\nEnter the NUMBER of the table to seat them at (1-25): ");
    	int tableNum = userInput.nextInt();
    	restaurant.seatCustomers(customerNum, tableNum);
    }
    
    public void assignServersToTables() {
    	Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhich server would you like to assign to a table?\n");
            System.out.println(restaurant.getListOfServers());
            System.out.print("\nEnter the NAME of the server: ");

            String serverName = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());

            if (restaurant.serverIsHired(serverName)) {
            	System.out.println("\n" + restaurant.getAvailableTables());
                System.out.print("\nEnter table NUMBER to assign " + serverName + " to (1-25): ");
                int tableNum = userInput.nextInt();
                restaurant.assignServerToTable(serverName, tableNum);
                break; 
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
            	System.out.println("\n" + restaurant.getTablesFromServer(serverName));
                System.out.print("\nEnter table NUMBER you'd like to remove" + serverName + " from (1-25): ");
                int tableNum = userInput.nextInt();
                restaurant.removeServerFromTable(serverName, tableNum);
                break; 
            } else {
                System.out.println("This server does not exist. Please try again.\n");
            }
        }
    }
    
    public void serve() {
    	
    	Scanner userInputName = new Scanner(System.in);
    	System.out.print("\n=================================\n          Server Login\n=================================\n");
    	System.out.print("\nPlease enter in your name: ");
    	String serverName = userInputName.nextLine().strip();
    	
    	if (!this.restaurant.serverIsHired(serverName)) {
    		System.out.println("\nServer not found. Please try again!\n");
    		return;
    	}
    	
    	
    	while (true) {
    		System.out.print("\n=================================\n          Server Menu\n=================================\n\n");
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
				System.out.println("\nYOUR " + restaurant.getTablesFromServer(serverName) + "\n");
				System.out.println(restaurant.getAvailableTables());
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
	    		String orderedItem = userInput.nextLine().strip();
	    		System.out.print("Modification: ");
	    		String modification = userInput.nextLine().strip();
	    		restaurant.orderItem(tableNum, i + 1, orderedItem, modification);
	    		
	    		if (i != customers - 1) {
	    			System.out.println("Next customer? (Y/N) ");
	    			if(userInput.nextLine().trim().equalsIgnoreCase("Y")) break;
	    			}
	    		else {
	    			break;
	    		}
	    		}
	    	}
	    	System.out.println("\n" + restaurant.getTableOrder(tableNum).toString());
    	}
    	else {
    		System.out.println("\nYou are not assigned to this table. Ask host to assign you to the table and try again!");
    		return;
    	}
    	// print order to show server
    }
    
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
	    	for (int i = 0; i < customers; i++) {
	    		while(true) {
	    		System.out.println("\nCostumer #" + (i+1));
	    		System.out.print("Tip Amount: ");
	    		int tipAmt = userInput.nextInt();
	    		this.restaurant.closeOrder(tableNum, i + 1, tipAmt);
	    		
	    		if (i != customers - 1) {
	    			System.out.println("Next customer? (Y/N) ");
	    			if(userInput.nextLine().trim().equalsIgnoreCase("Y")) break;
	    		}
	    		}
	    	}
	    	System.out.println(restaurant.getTableOrder(tableNum).toString());
	    	this.restaurant.clearCustomersFromTable(tableNum);
    	}
    	else {
    		System.out.println("\nYou are not assigned to this table. Ask host to assign you to the table and try again!");
    		return;
    	}
    	// print order to show server
    }
    
    
    
    // Display the menu, grouped by FoodCourse and then by specificCategory
    public void displayMenu() {
        System.out.print("\nWhich menu would you like to view? (App/Drink/Entree/Dessert): ");
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
    
    // Main Menu
    // Option 1: Assign Tables to Servers
    // Option 2: Act as a Server
    // Option 3: Search Menu
    // Option 4: View Menu
    // Option 5: View Sorted Menu by sales or revenue
    // Option 6: View Sorted Server Tips
    
    // Option 2: Act as a Server (Customer decisions are random)
    // - create Order 
    //   - add OrderedItems per Customer (for each of the 4 Menus)
    // - close the order
    // - calculate the bill (either evenly split or individually)
    // - close the table
