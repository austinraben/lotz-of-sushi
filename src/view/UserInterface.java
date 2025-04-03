package view;

import model.Restaurant;
import model.Menu;
import model.MenuItem;
import model.FoodCourse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterface {
    private Restaurant restaurant;
    private java.util.Scanner scanner;
    private java.util.Random random;

    public UserInterface(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.scanner = new java.util.Scanner(System.in);
        this.random = new java.util.Random();
    }

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Lotz of Sushi");
        
        try {
            restaurant.loadMenuItems("data/menu.txt");
        } catch (Exception e) {
            System.err.println("Error loading menu: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        UserInterface ui = new UserInterface(restaurant);
        ui.displayMenu();
    }

    // Display the menu, grouped by FoodCourse and then by specificCategory
    public void displayMenu() {
        System.out.println("=== Lotz of Sushi Menu ===");
        
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

