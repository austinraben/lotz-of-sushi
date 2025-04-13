package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Menu implements Iterable<String>{
	
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

	@Override
	public Iterator<String> iterator() {
		return items.keySet().iterator();
	}
	
	@Override
	public String toString() {
		String message = course.toString() + " Menu: \n";
		for (Map.Entry<String, MenuItem> entry : items.entrySet()) {
			message += entry.getKey() + " - $" + entry.getValue().getPrice() + "\n";
		}
		return message;
	}
}
