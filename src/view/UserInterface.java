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
    
    private final String MAIN_MENU = "Welcome to Lotz of Sushi! What would you like to do today?\n\n"+
    									"1. Assign Tables to Servers\n2. Act as a Server\n3. Search Menu\n4. View Menu\n5. View Sales\n" +
    									"6. View Tips\n";
    									

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
        //ui.displayMenu();
    }
    
    public void mainMenu() {
    	System.out.println(MAIN_MENU);
    	System.out.print("Enter the number of the command you would like (1-6): ");
    	
    	Scanner userInput = new Scanner(System.in);
		String inputString = userInput.nextLine().strip().toLowerCase();

		switch(inputString) {
			case "1":
				break;
			case "assign tables":
				break;
			case "2":
				break;
			case "act as a server":
				break;
			case "3":
				break;
			case "4":
				this.displayMenu();
				break;
			case "view menu":
				this.displayMenu();
				break;
			case "5":
				this.viewSales();
				break;
			case "6":
				break;
			default:
				System.out.println("Command not found. Please try again!");
				break;
		}
		
		Scanner wait = new Scanner(System.in);
		System.out.println("Hit enter to return to list of commands\n");
		String waitResponse = wait.nextLine();
    }
    
    
    // Display the menu, grouped by FoodCourse and then by specificCategory
    public void displayMenu() {
        System.out.println("\n=== Lotz of Sushi Menu ===");
        
        // Iterate over all FoodCourse values for consistent ordering
        for (FoodCourse course : FoodCourse.values()) {
            Menu menu = restaurant.getMenu(course);
            if (menu == null || menu.getItems().isEmpty()) {
                continue; // Skip empty menus
            }

            // Group items by specificCategory
            Map<String, List<MenuItem>> itemsByCategory = new HashMap<>();
            // AI-generated.. creates anonymous ArrayList for each filtered category
            for (MenuItem item : menu.getItems()) {
                String category = item.getSpecificCategory().isEmpty() ? "Uncategorized" : item.getSpecificCategory();
                itemsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            }

            // Print the FoodCourse header
            System.out.println("\n" + course + ":");

            // Sort categories alphabetically
            // AI-generated.. creates ArrayList<> and uses :: to sort)
            List<String> sortedCategories = new ArrayList<>(itemsByCategory.keySet());
            sortedCategories.sort(String::compareToIgnoreCase);

            // Iterate over each category
            for (String category : sortedCategories) {
                // Skip "Uncategorized" header if it's the only category, or print it explicitly
                if (!(sortedCategories.size() == 1 && category.equals("Uncategorized"))) {
                    System.out.println("     " + category + ":");
                }

                // Sort items within the category by itemName
                List<MenuItem> items = itemsByCategory.get(category);
                items.sort((a, b) -> a.getItemName().compareToIgnoreCase(b.getItemName()));

                // Print each item
                for (MenuItem item : items) {
                    String displayLine = String.format("        - %s ($%.2f)", item.getItemName(), item.getPrice());
                    if (!item.getDescription().isEmpty()) {
                        displayLine += " - " + item.getDescription();
                    }
                    System.out.println(displayLine);
                }
            }
        }
        // extra newline character for spacing
        System.out.println();
    }
    
    public void viewSales() {
    	System.out.println("\nWhat would you like to view?");
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

