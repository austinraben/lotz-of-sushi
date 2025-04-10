package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public  class Menu {
	
    protected FoodCourse course;
    protected HashMap<String, MenuItem> items; 
    
    public Menu(FoodCourse course) {
        this.course = course;
        this.items = new HashMap<>(); 
    }

    // Add item to the menu using itemName as the key
    public void addItem(MenuItem item) {
        String itemName = item.getItemName();
        items.put(itemName, item);
    }

    // Search itemName and return itenName if found, otherwise null
    public MenuItem searchItemByName(String itemName) {
        return items.get(itemName); 
    }

    // getters
  
    public List<MenuItem> getItems() {
        return new ArrayList<>(items.values()); 
    }

    public MenuItem getMenuItem(String itemName) {
        return items.get(itemName); 
    }

    public FoodCourse getCourse() {
        return course;
    }
   
    public boolean containsMenuItem(String itemName) {
    	return items.containsKey(itemName);
    }
}
