 package view;

import model.Restaurant;
import model.Menu;
import model.MenuItem;
import model.Order;
import model.HappyHourManager;
import model.ManagerPassword;

import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private Restaurant restaurant;
    private java.util.Scanner scanner;
    private ManagerPassword managerPassword;
    
    private final String MAIN_MENU = COLOR_WELCOME + BOLD + "=================================\n    Welcome to Lotz of Sushi!\n=================================\n" 
    		+ COLOR_RESET + "1. Manage\n2. Host\n3. Serve\n4. View Menu";
    
    private static final String COLOR_RESET = "\u001B[0m";  // default text color
    private static final String COLOR_INPUT = "\u001B[36m";  // cyan
    private static final String COLOR_LIST = "\u001B[94m";  // blue
    private static final String COLOR_WELCOME = "\u001B[95m";  // bright magenta
    private static final String COLOR_HEADER = "\u001B[32m";  // green
    private static final String COLOR_SUCCESS = "\u001B[92m"; // lighter green
    private static final String COLOR_ERROR = "\u001B[31m";  // red
    private static final String BOLD = "\u001B[1m";    // bold text for main menu options
    									

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
    	System.out.print("\nChoose an option (1-4): ");
    	
    	System.out.print(COLOR_INPUT);
    	Scanner userInput = new Scanner(System.in);    	
		String inputString = userInput.nextLine().strip().toLowerCase();
    	System.out.print(COLOR_RESET);

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
				System.out.println();
				break;
			default:
				System.out.println(COLOR_ERROR + "Bad option. Try again!\n" + COLOR_RESET);
				break;
		}
    }
   
    // view sales, view tips
    public void manage() {
    	managerPassword.getManagerPassword("/data/staff.txt");
    	System.out.print(COLOR_HEADER);
    	System.out.print("\n=================================\n          Manger Login\n=================================");
    	System.out.print(COLOR_RESET);
    	System.out.print("\nEnter password: ");
    	System.out.print(COLOR_INPUT);
    	Scanner userInputPass = new Scanner(System.in);
    	String password = userInputPass.nextLine().strip();
    	System.out.print(COLOR_RESET);
    	
    	try {
			if (ManagerPassword.hashPassword(password, managerPassword.getSalt()).equals(managerPassword.getHashedPassword())) {
				while (true) {
					System.out.print(COLOR_HEADER);
					System.out.print("\n=================================\n         Manager Menu\n=================================\n");
					System.out.print(COLOR_RESET);
					String happy = HappyHourManager.isHappyHour() ? "Happy Hour " + COLOR_SUCCESS + "On" + COLOR_RESET : "Happy Hour " + COLOR_ERROR + "Off" + COLOR_RESET;
					System.out.println("1. " + happy + "\n2. View servers\n3. View tables\n4. View sales\n5. View tips\n6. View closed orders\n7. Hire servers\n8. Fire servers\n9. Change Password\n0. Exit Manage");
					System.out.print("\nChoose an option (1-10): ");
					
					System.out.print(COLOR_INPUT);
					Scanner userInput = new Scanner(System.in);
					String inputString = userInput.nextLine().strip().toLowerCase();
					System.out.print(COLOR_RESET);
					
					switch (inputString) {
					case "1":
					    if (!HappyHourManager.isHappyHour()) {
					        HappyHourManager.toggleHappyHour();
					        System.out.println("Happy Hour is now " + COLOR_SUCCESS + "ON" + COLOR_RESET);
					    }
					    else {
					    	HappyHourManager.toggleHappyHour();
					        System.out.println("Happy Hour is now " + COLOR_ERROR + "OFF" + COLOR_RESET);
					    }
					    break;
						case "2":
							System.out.print(COLOR_LIST);
							System.out.print("\n=================================\n           Server View\n=================================\n");
							System.out.print(COLOR_RESET);
							System.out.print(restaurant.getAllServersInfo());
							break;
						case "3": 
							System.out.print(COLOR_LIST);
							System.out.print("\n===========================================\n                 Table View\n===========================================\n");
							System.out.print(COLOR_RESET);
							System.out.print(restaurant.toString());
							break;
						case "4":
							System.out.print(COLOR_LIST);
							System.out.print("\n=================================\n           Sales View\n=================================\n");
							System.out.print(COLOR_RESET);
							this.viewSales();
							break;
						case "5":
							System.out.println(COLOR_LIST + "TOTAL TIPS: " + COLOR_RESET + + restaurant.getSalesTracker().getTotalTips());
							break;
						case "6": 
							this.viewClosedOrders();
							break;
						case "7":
							this.hireServers();
							break;
						case "8":
							this.fireServers();
							break;
						case "9":
							password = this.changePassword(password);
							managerPassword.setPassword(password);
							managerPassword.rewriteToFile("/data/staff.txt");
							break;
						case "0":
							System.out.println();
							break;
						default:
							System.out.print(COLOR_ERROR);
							System.out.println("Bad option. Try again!\n");
							System.out.print(COLOR_RESET);
							break;
					}
					
					if (inputString.equals("0")) break;
					
				}
				managerPassword.setPassword(password);
				managerPassword.rewriteToFile("/data/staff.txt");
			}
			else {
				System.out.println(COLOR_ERROR + "Incorrect password. Access denied!\n" + COLOR_RESET);
				return;
			}
		} 
    	catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    }
    
    public void hireServers() {
    	System.out.print("\nEnter the NAME of the server to hire: ");
    	System.out.print(COLOR_INPUT);
    	Scanner userInput = new Scanner(System.in);
		String inputString = userInput.nextLine().strip().toLowerCase();
		System.out.print(COLOR_RESET);

		System.out.print(COLOR_SUCCESS);
		this.restaurant.hireServers(capitalizeFirstLetterOfEachWord(inputString));
		System.out.println("\n" + capitalizeFirstLetterOfEachWord(inputString) + " has been hired!\n");
		System.out.print(COLOR_RESET);
    }
    
    public void fireServers() {
    	boolean exit = false;
    	while (true) {
	    	System.out.println(restaurant.getListOfServers());
	    	System.out.print("\nEnter the NAME of the server to fire: ");
	    	
	    	System.out.print(COLOR_INPUT);
	    	Scanner userInput = new Scanner(System.in);
			String inputString = userInput.nextLine().strip().toLowerCase();
			System.out.print(COLOR_RESET);
			
			if (this.restaurant.serverIsHired(capitalizeFirstLetterOfEachWord(inputString))) {
				System.out.print(COLOR_SUCCESS);
				this.restaurant.fireServers(capitalizeFirstLetterOfEachWord(inputString));
				System.out.println("\n" + capitalizeFirstLetterOfEachWord(inputString) + " has been fired!\n");
				System.out.print(COLOR_RESET);
				exit = true;
			}
			
			else if (inputString.equals("") || inputString.equals("none")) {
				System.out.print(COLOR_SUCCESS);
				System.out.println("Nobody got fired. You are too kind :)");
				System.out.print(COLOR_RESET);
				exit = true;
			}
			else {
				System.out.print(COLOR_ERROR);
				System.out.println("Silly goose, you can't fire an unemployed server! xD");
				System.out.print(COLOR_RESET);
			}
			
			if (exit) break;
    	}
    }
    
    public String changePassword(String oldPassword) {
    	System.out.print("\nEnter in NEW password: ");
    	System.out.print(COLOR_INPUT);
    	Scanner userInputPass = new Scanner(System.in);
    	String password = userInputPass.nextLine().strip();
    	System.out.print(COLOR_RESET);
    	
    	System.out.print("Confirm NEW passoword: ");
    	System.out.print(COLOR_INPUT);
    	String checkPassword = userInputPass.nextLine().strip();
    	System.out.print(COLOR_RESET);
    	
    	if (password.equals(checkPassword)) {
    		System.out.print(COLOR_SUCCESS);
    		System.out.println("Password successfully updated!");
        	System.out.print(COLOR_RESET);
    		return password;
    	}
    	else {
    		System.out.print(COLOR_ERROR);
    		System.out.println("Failed to update password.");
        	System.out.print(COLOR_RESET);
    		return oldPassword;
    	}
    }
    
    
    public void host() {
    	while (true) {
    		System.out.print(COLOR_HEADER);
    		System.out.print("\n=================================\n            Host Menu\n=================================\n");
    		System.out.print(COLOR_RESET);
	    	System.out.println("1. Seat customers\n2. View servers\n3. View tables\n4. Assign server to table\n5. Remove server from table\n0. Exit Host\n");
	    	System.out.print("Choose an option (1-6): ");
	    	
	    	System.out.print(COLOR_INPUT);
	    	Scanner userInput = new Scanner(System.in);
			String inputString = userInput.nextLine().strip().toLowerCase();
	    	System.out.print(COLOR_RESET);
	    	
			switch (inputString) {
			case "1":
				this.seatCustomers();
				break;
			case "2":
				System.out.print(COLOR_LIST);
				System.out.print("\n=================================\n           Server View\n=================================\n");
				System.out.print(COLOR_RESET);
				System.out.print(restaurant.getAllServersInfo());
				break;
			case "3": 
				System.out.print(COLOR_LIST);
				System.out.print("\n===========================================\n                 Table View\n===========================================\n");
				System.out.print(COLOR_RESET);
				System.out.print(restaurant.toString());
				break;
			case "4":
				System.out.print(COLOR_LIST);
				System.out.print("\n=================================================\n                Server Assignment\n=================================================\n");
				System.out.print(COLOR_RESET);
				this.assignServersToTables();
				break;
			case "5":
				System.out.print(COLOR_LIST);
				System.out.print("\n=================================================\n                Server Assignment\n=================================================\n");
				System.out.print(COLOR_RESET);
				this.removeServerFromTable();
				break;
			case "0":
				System.out.println();
				break;
			default:
				System.out.print(COLOR_ERROR);
				System.out.println("Bad option. Try again!");
				System.out.print(COLOR_RESET);
				break;
			}
			
			if (inputString.equals("0")) break;
    	}
    }
    
    public void seatCustomers() {
    	System.out.print(COLOR_INPUT);
    	Scanner userInput = new Scanner(System.in);
    	int customerNum = getValidIntegerInput(userInput, "\nHow many customers should be seated? ");
    	
    	if (restaurant.getAvailableTables().equals("OPEN TABLES:")) System.out.println(COLOR_ERROR + "No tables are available." + COLOR_RESET);
    	
    	else {
	    	System.out.println(COLOR_LIST + restaurant.getAvailableTables() + COLOR_RESET);
	    	
	    	while(true) {
		        int tableNum = getValidIntegerInput(userInput, "\nEnter the NUMBER of the table to seat them at (1-25): ");
		
		    	
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
			    	System.out.print(COLOR_SUCCESS);
			    	System.out.println("\n" + customerNum + " customers have been seated at TABLE " + tableNum);
			    	System.out.print(COLOR_RESET);
			    	break;
		    	} else {
		    		System.out.print(COLOR_ERROR);
		    		System.out.println("This table has no server or has already been seated. Try again!");
			    	System.out.print(COLOR_RESET);
		    	}
	    	}
    	}
    }
    
    public void assignServersToTables() {
    	Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println(restaurant.getListOfServers());
            System.out.print("\nEnter the NAME of the server: ");

            System.out.print(COLOR_INPUT);
            String serverName = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());
			System.out.print(COLOR_RESET);

            if (restaurant.serverIsHired(serverName)) {
            	
            	while(true) {
            	System.out.println(COLOR_LIST + restaurant.getUnassignedTables() + COLOR_RESET);
                int tableNum = getValidIntegerInput(userInput, "\nEnter table NUMBER to assign " + serverName + " to (1-25): ");
                
                boolean validTable = false;
                String[] tableList = restaurant.getUnassignedTables().split(": ")[1].split(",");
            	for (int i = 0; i < tableList.length; i++) {
            		if(Integer.valueOf(tableList[i]).equals(tableNum)) validTable = true;
            	} 
            	
            	if (validTable) {
            		System.out.print(COLOR_SUCCESS);
	                restaurant.assignServerToTable(serverName, tableNum);
					System.out.print(COLOR_RESET);
	                break; 
            	} else {
            		System.out.println(COLOR_ERROR + "\n" + "This table is already assigned or doesn't exist. Try again!" + COLOR_RESET);
            		break;
            	}
            	} break;
            } else {
                System.out.println(COLOR_ERROR + "This server does not exist. Try again!" + COLOR_RESET);
            }
        }
    }
    
    public void removeServerFromTable() {
        Scanner userInput = new Scanner(System.in);

        while (true) {
            System.out.println("\nWhich server would you like to remove from a table?\n");
            System.out.print(COLOR_LIST);
            System.out.println(restaurant.getListOfServers());
			System.out.print(COLOR_RESET);
			
            System.out.print("\nEnter the NAME of the server: ");
            System.out.print(COLOR_INPUT);
            String serverName = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());
			System.out.print(COLOR_RESET);

            if (restaurant.serverIsHired(serverName)) {
                String[] tableList = restaurant.getTablesFromServer(serverName).split(":");

                if (tableList.length <= 1 || tableList[1].strip().isEmpty()) {
                	System.out.print(COLOR_ERROR);
                    System.out.println("\nThis server has no assigned tables.");
    				System.out.print(COLOR_RESET);
                    break; 
                }

                while (true) {
                	System.out.print(COLOR_HEADER);
                    System.out.println("\n" + restaurant.getTablesFromServer(serverName));
    				System.out.print(COLOR_RESET);

                    int tableNum = getValidIntegerInput(userInput, "\nChoose a table to remove " + serverName + " from (1-25): ");

                    boolean validTable = false;
                    String[] assignedTables = tableList[1].split(",");

                    for (String table : assignedTables) {
                        if (Integer.valueOf(table.strip()).equals(tableNum)) {
                            validTable = true;
                            break;
                        }
                    }

                    if (validTable) {
                    	System.out.print(COLOR_SUCCESS);
                        restaurant.removeServerFromTable(serverName, tableNum);
        				System.out.print(COLOR_RESET);
                        return; 
                    } else {
                    	System.out.print(COLOR_ERROR);
                        System.out.println("\nThis table is not currently assigned to " + serverName + ". Please try again!");
        				System.out.print(COLOR_RESET);
                    }
                }
            } else {
            	System.out.print(COLOR_ERROR);
                System.out.println("\nThis server does not exist. Please try again.\n");
				System.out.print(COLOR_RESET);
            }
        }
    }

    
    public void viewClosedOrders() {
    	System.out.print(COLOR_LIST);
    	System.out.print("\n====================================\n           All Closed Orders\n====================================\n");
    	System.out.print(COLOR_RESET);
    	if (this.restaurant.getClosedOrders().size() == 0) System.out.println(COLOR_ERROR + "\nNo orders to show.\n" + COLOR_RESET);
    	for (Order o : this.restaurant.getClosedOrders()) {
    		System.out.println("\n" + o);
    	}
    }
    
    public void serve() {
    	
    	Scanner userInputName = new Scanner(System.in);
    	System.out.print(COLOR_HEADER);
    	System.out.print("\n=================================\n          Server Login\n=================================\n");
		System.out.print(COLOR_RESET);
    	System.out.print("\nEnter your name: ");
    	System.out.print(COLOR_INPUT);
    	String serverName = capitalizeFirstLetterOfEachWord(userInputName.nextLine().strip());
		System.out.print(COLOR_RESET);

    	if (!this.restaurant.serverIsHired(serverName)) {
    		System.out.print(COLOR_ERROR);
    		System.out.println("\nSilly goose, " + serverName + " isn't hired.\n");
			System.out.print(COLOR_RESET);
    		return;
    	}

    	while (true) {
    		String firstName = (serverName.split(" "))[0];
    		String header = firstName + "'s Server Menu";
    		int totalWidth = 33;
    		int padding = (totalWidth - header.length()) / 2;
    		String formattedHeader = " ".repeat(Math.max(0, padding)) + header;

    		System.out.print(COLOR_HEADER);
    		System.out.print("\n=================================\n" +
    		    formattedHeader + "\n" +
    		    "=================================\n\n");
			System.out.print(COLOR_RESET);
        	System.out.println("1. Take orders\n2. View tables\n3. Close Order\n0. Exit Server");
        	System.out.print("\nChoose an option (1-4): ");
    		
        	System.out.print(COLOR_INPUT);
	    	Scanner userInput = new Scanner(System.in);
			String inputString = userInput.nextLine().strip().toLowerCase();
			System.out.print(COLOR_RESET);
			
			switch (inputString) {
			case "1":
				System.out.print(COLOR_LIST);
				System.out.println("YOUR " + restaurant.getTablesFromServer(serverName) + "\n");
				System.out.print(COLOR_RESET);
				this.takeOrders(serverName);
				break;
			case "2":
				getIndividualTable(serverName);
				break;
			case "3":
				System.out.println("\nYOUR " + restaurant.getTablesFromServer(serverName) + "\n");
				this.closeOrder(serverName);
				break;
			case "0":
				System.out.println();
				break;
			default:
				System.out.print(COLOR_ERROR);
				System.out.println("Bad option. Try again!");
				System.out.print(COLOR_RESET);
				break;
			}
			
			if (inputString.equals("0")) break;
    	}
    } 
    
    public void takeOrders(String serverName) {
    	
    	boolean validTable = false;
    	String[] checkTableList = restaurant.getTablesFromServer(serverName).split(" ");
    	if (checkTableList.length <= 1) {
    		System.out.print(COLOR_ERROR);
    		System.out.println("\nYou don't have any tables! Ask the host to assign you to a table.");
			System.out.print(COLOR_RESET);
    		return;
    	}
    	
    	Scanner userInput = new Scanner(System.in);
        int tableNum = getValidIntegerInput(userInput, "Which table are you taking orders from? (1-25): ");
    	String[] tableList = restaurant.getTablesFromServer(serverName).split(" ")[1].split(",");
    	for (int i = 0; i < tableList.length; i++) {
    		if(Integer.valueOf(tableList[i]).equals(tableNum)) validTable = true;
    	}
    	
    	if (validTable) {
	    	int customers = restaurant.getNumCustomers(tableNum);
	    	if (customers == 0) {
	    		System.out.print(COLOR_ERROR);
	    		System.out.println("\nThis table has not been seated yet. Try again when host has seated table.");
				System.out.print(COLOR_RESET);
	    		return;
	    	}
	    	for (int i = 0; i < customers; i++) {
	    		while(true) {
	    			System.out.print(COLOR_LIST);
		    		System.out.println("\nCostumer #" + (i+1));
		    		System.out.print(COLOR_RESET);
		    		
		    		System.out.print("Ordered item: ");
		    		System.out.print(COLOR_INPUT);
		    		String orderedItem = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());
		    		System.out.print(COLOR_RESET);
		    		String modification = "None";
	    		
		    		if ((!restaurant.getAppMenu().containsMenuItem(orderedItem) &&
		    			!restaurant.getDrinkMenu().containsMenuItem(orderedItem) &&
		    			!restaurant.getEntreeMenu().containsMenuItem(orderedItem) &&
		    			!restaurant.getDessertMenu().containsMenuItem(orderedItem))) {
		    			System.out.print(COLOR_ERROR);
		    			System.out.println("\nMenu item not found. Try again!");
		    			System.out.print(COLOR_RESET);
		    			continue;
		    		}
	    		
		    		if (this.restaurant.hasModification(orderedItem)) {
		    			System.out.print("Modification: ");
		    			System.out.print(COLOR_INPUT);
			    		modification = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip().toLowerCase());
			    		System.out.print(COLOR_RESET);
		    		}
		    		
					System.out.print(COLOR_RESET);
					
		    		try {
		    			restaurant.orderItem(tableNum, i + 1, orderedItem, modification);
		    		} catch (Exception e) {
		    			System.out.print(COLOR_ERROR);
		    			System.out.println("\nMenu item not found. Try again!");
		    			System.out.print(COLOR_RESET);
		    		}
	    		
	    		
		    		System.out.print("Next customer? (Y/N) ");
		    		
		    		System.out.print(COLOR_INPUT);
	    			if(userInput.nextLine().trim().equalsIgnoreCase("Y")) {
	    				System.out.print(COLOR_RESET);
	    				break;
	    			}
		    		System.out.print(COLOR_RESET);
	    		}
	    		System.out.print(COLOR_LIST);
	    		System.out.println("\n" + restaurant.getOrderFromCustomer(tableNum, (i+1)).toString());
	    		System.out.print(COLOR_RESET);
	    	}
	    	System.out.print(COLOR_LIST);
	    	System.out.println("\n" + restaurant.getTableOrder(tableNum).toString());
    		System.out.print(COLOR_RESET);
    	}
    	else {
    		System.out.print(COLOR_ERROR);
    		System.out.println("\nYou are not assigned to this table. Ask host to assign you to the table and try again!");
    		System.out.print(COLOR_RESET);
    		return;
    	}
    }
    
    public void closeOrder(String serverName) {
    
    	boolean validTable = false;
    	String[] checkTableList = restaurant.getTablesFromServer(serverName).split(" ");
    	if (checkTableList.length <= 1) {
    		System.out.print(COLOR_ERROR);
    		System.out.println("\nNo Tables Found. Ask host to assign you to a table.");
    		System.out.print(COLOR_RESET);
    		return;
    	}
    	
    	Scanner userInput = new Scanner(System.in);
        int tableNum = getValidIntegerInput(userInput, "Which table are you closing orders from? (1-25): ");
    	
    	String[] tableList = restaurant.getTablesFromServer(serverName).split(" ")[1].split(",");
    	for (int i = 0; i < tableList.length; i++) {
    		if(Integer.valueOf(tableList[i]).equals(tableNum)) validTable = true;
    	}
    	
    	if (validTable) {
	    	int customers = restaurant.getNumCustomers(tableNum);
	    	if (customers == 0) {
	    		System.out.print(COLOR_ERROR);
	    		System.out.println("\nThis table has not been seated yet. Try again when host has seated table.");
	    		System.out.print(COLOR_RESET);
	    		return;
	    	}
	    	
	    	String inputString;
	    	while (true) {
		    	System.out.println("1. Pay seperate\n2. Split bill evenly");
		    	System.out.print("\nChoose how customers are paying (1-2): ");

		    	System.out.print(COLOR_INPUT);
		    	inputString = userInput.nextLine().strip().toLowerCase();
	    		System.out.print(COLOR_RESET);
		    	
		    	switch (inputString) {
		    		case "1":
		    			break;
		    		case "2":
		    			this.restaurant.splitBillEvenly(tableNum);
		    			break;
		    		default:
		    			System.out.print(COLOR_ERROR);
		    			System.out.println("\nBad Option. Try again!");
		    			System.out.print(COLOR_RESET);
		    			break;
		    	
		    	}
		    	if (inputString.equals("1") || inputString.equals("2")) break;
	    	}
	    	
	    	for (int i = 0; i < customers; i++) {
	    		System.out.println("\nCustomer #" + (i+1));
	    		double tipAmt = 0.0;
	    	    boolean validInput = false;

	    	    while (!validInput) {
	    	        System.out.print("Enter tip amount: ");
	    	        try {
	    	        	System.out.print(COLOR_INPUT);
	    	            tipAmt = userInput.nextDouble();
			    		System.out.print(COLOR_RESET);
	    	            if (tipAmt < 0) {
	    	            	System.out.print(COLOR_ERROR);
	    	                System.out.println("Tip amount cannot be negative. Try again!");
	    		    		System.out.print(COLOR_RESET);
	    	                continue;
	    	            }
	    	            validInput = true;
	    	        } catch (InputMismatchException e) {
	    	        	System.out.print(COLOR_ERROR);
	    	            System.out.println("Tip must be a number. Try again!");
			    		System.out.print(COLOR_RESET);
	    	            userInput.nextLine(); 
	    	        }
	    	    }	    		
	    		
	    	    System.out.print(COLOR_LIST);
	    		System.out.println("\n" + this.restaurant.getOrderFromCustomer(tableNum, i + 1));
	    		System.out.print(COLOR_RESET);
	    		this.restaurant.closeOrder(tableNum, i + 1, tipAmt);
	    	}
	    	
    		System.out.print(COLOR_LIST);
	    	System.out.println(restaurant.getTableOrder(tableNum).toString());
    		System.out.print(COLOR_RESET);
	    	this.restaurant.clearCustomersFromTable(tableNum);
    		System.out.print(COLOR_SUCCESS);
	    	System.out.println("\nTable " + tableNum + " is now available to be seated!");
    	}
    	else {
    		System.out.print(COLOR_ERROR);
    		System.out.println("\nYou are not assigned to this table. Ask host to assign you to the table and try again!");
    		System.out.print(COLOR_RESET);

    		return;
    	}
    }
    
    public void getIndividualTable(String serverName) {
		System.out.print(COLOR_LIST);
    	System.out.println("YOUR " + restaurant.getTablesFromServer(serverName) + "\n");
		System.out.print(COLOR_RESET);
    	Scanner userInput = new Scanner(System.in);
        int choice = getValidIntegerInput(userInput, "Choose a table to view (1-25): ");
    	if ((choice < 1) || (choice > 25)) {
    		System.out.print(COLOR_ERROR);
    		System.out.print("Table number must be between 1 - 25");
    		System.out.print(COLOR_RESET);
    		return;
    	}
        
        
        System.out.print(COLOR_LIST);
    	restaurant.printServerTable(choice);
		System.out.print(COLOR_RESET);
    }
    
    
    
    // Display the menu, grouped by FoodCourse and then by specificCategory
    public void displayMenu() {
        System.out.print("\nWhich menu would you like to view? (App/Drink/Entree/Dessert/Full): ");
		System.out.print(COLOR_INPUT);
        String choice = scanner.nextLine().trim().toLowerCase();
		System.out.print(COLOR_RESET);
        restaurant.printMenu(choice);
        return;
        
    }
    
    public void viewSales() {
    	System.out.println("\nWhat would you like to view?");
    	System.out.println("1. Sales for all items\n2. Revenue for all items\n3. Sale for specific item\n4. Revenue for specific item");
    	System.out.print("Choose an option (1-4): ");
    	
		System.out.print(COLOR_INPUT);
    	Scanner userInput = new Scanner(System.in);
		String inputString = userInput.nextLine().strip().toLowerCase();
		System.out.print(COLOR_RESET);
		
		switch (inputString) {
			case "1":
				System.out.print(COLOR_LIST);
				System.out.println("\n" + this.restaurant.getSalesTracker().quantitySoldString() + "\n");
				System.out.print(COLOR_RESET);
				break;
			case "2":
				System.out.print(COLOR_LIST);
				System.out.println("\n" + this.restaurant.getSalesTracker().totalSalesString() + "\n");
				System.out.print(COLOR_RESET);
				break;
			case "3":
				this.saleOfItem();
				break;
			case "4":
				this.revenueOfItem();
				break;
			default:
				System.out.print(COLOR_ERROR);
				System.out.println("Bad option. Try again!");
				System.out.print(COLOR_RESET);
				break;
		}
    }
    
    public void saleOfItem() {
    	System.out.print("\nEnter in the name of the food item you wish to check: ");
    	
		System.out.print(COLOR_INPUT);
		Scanner userInput = new Scanner(System.in);
		String inputString = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip());
		System.out.print(COLOR_RESET);
		
		Menu myMenu = this.restaurant.getMenuForItem(inputString);
		if (myMenu == null) {
			System.out.print(COLOR_ERROR);
			System.out.println("Can't find this item. Try again!");
			System.out.print(COLOR_RESET);
			return;
		}
		else {
			MenuItem myItem = myMenu.getMenuItem(inputString);
			System.out.print(COLOR_LIST);
			System.out.println("\n" + inputString + ": " + this.restaurant.getSalesTracker().getQuantityForItem(myItem));
			System.out.print(COLOR_RESET);
			return;
		}
	}
    
    public void revenueOfItem() {
    	System.out.print("\nEnter in the name of the food item you wish to check: ");
    	
		System.out.print(COLOR_INPUT);
		Scanner userInput = new Scanner(System.in);
		String inputString = capitalizeFirstLetterOfEachWord(userInput.nextLine().strip());
		System.out.print(COLOR_RESET);
		
		Menu myMenu = this.restaurant.getMenuForItem(inputString);
		if (myMenu == null) {
			System.out.print(COLOR_INPUT);
			System.out.println("Can't find this item. Try again!");
			System.out.print(COLOR_RESET);
			return;
		}
		else {
			MenuItem myItem = myMenu.getMenuItem(inputString);
			System.out.print(COLOR_LIST);
			System.out.println("\n" + inputString + ": $" + this.restaurant.getSalesTracker().getSaleForItem(myItem));
			System.out.print(COLOR_RESET);
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
    
    public int getValidIntegerInput(Scanner userInput, String prompt) {
        int number = 0;
        boolean validInput = false;

        while (!validInput) {
        	System.out.print(COLOR_RESET);
            System.out.print(prompt);
            try {
            	System.out.print(COLOR_INPUT);
                number = Integer.parseInt(userInput.nextLine());
                if (number < 0) {
                	System.out.print(COLOR_ERROR);
                    System.out.println("Invalid input. Try again!");
                	System.out.print(COLOR_RESET);
                    continue;
                }
                validInput = true;
            	System.out.print(COLOR_RESET);
            } catch (NumberFormatException e) {
            	System.out.print(COLOR_ERROR);
                System.out.println("Invalid input. Try again!");
            	System.out.print(COLOR_RESET);
            }
        }
        return number;
    }
}
