/*
 * This class defines the behavior for a Menu object. There are four Menu objects used throughout the code.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Menu implements Iterable<String> {
	
    private final FoodCourse course;
    private HashMap<String, MenuItem> items; 
    
    public Menu(FoodCourse course) {
        this.course = course;
        this.items = new HashMap<>(); 
    }
    
    // copy constructor
    public Menu (Menu menu) {
    	this.course = menu.course;
    	this.items = new HashMap<>(menu.items);
    }

    // adds an item to the items HashMap 
    public void addItem(MenuItem item) {
        String itemName = item.getItemName();
        
        // maps the item with key of itemName string and value of MenuItem object
        items.put(itemName, item);
    }
  
    public List<MenuItem> getItems() {
        return new ArrayList<>(items.values()); 
    }

    public MenuItem getMenuItem(String itemName) {
        MenuItem item = items.get(itemName); 
        if (item == null) {
        	return null;
        }
        return new DiscountedMenuItem(item);
    }

    public FoodCourse getCourse() {
        return course;
    }
   
    public boolean containsMenuItem(String itemName) {
    	return items.containsKey(itemName);
    }
	
    // Print a Menu for a given FoodCourse
    public void printMenu() {
        System.out.println("\n" + course + ":");

        // Map specificCategory to its list of MenuItems
        Map<String, List<MenuItem>> itemsByCategory = new HashMap<>();
        for (MenuItem item : items.values()) {
            String category = item.getSpecificCategory().isEmpty() ? "Uncategorized" : item.getSpecificCategory();
            itemsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
        }

        // sorts Menu by category
        List<String> sortedCategories = new ArrayList<>(itemsByCategory.keySet());
        Collections.sort(sortedCategories, categoryComparator());

        for (String category : sortedCategories) {
        	
        	// Print category name only if not uncategorized
            if (!(sortedCategories.size() == 1 && category.equals("Uncategorized"))) {
                System.out.println("     " + category + ":");
            }

            // sorts items in category by name
            List<MenuItem> sortedCategoryItems = itemsByCategory.get(category);
            Collections.sort(sortedCategoryItems, itemNameComparator());

            // formats Menu string to show price and info next to name
            for (MenuItem item : sortedCategoryItems) {
                String menuItemInfo = String.format("        - %s ($%.2f)", item.getItemName(), item.getPrice());
                if (!item.getDescription().isEmpty()) {
                    menuItemInfo += " - " + item.getDescription();
                }
                System.out.println(menuItemInfo);
            }
        }
    }

    // Sort category names alphabetically
    public static Comparator<String> categoryComparator() {
        return new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        };
    }
    
    // Per category, sorted MenuItems alphabetically
    public static Comparator<MenuItem> itemNameComparator() {
        return new Comparator<MenuItem>() {
            public int compare(MenuItem item1, MenuItem item2) {
                return item1.getItemName().compareToIgnoreCase(item2.getItemName());
            }
        };
    }
    
	@Override
	public Iterator<String> iterator() {
		return items.keySet().iterator();
	}
}
