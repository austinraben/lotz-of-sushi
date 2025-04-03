package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu {

    protected FoodCourse course;
    protected HashMap<String, MenuItem> items;

    public Menu(FoodCourse course) {
        this.course = course;
        this.items = new HashMap<>();
    }
    
    // methods to view/search menu
    public HashMap<String, MenuItem> getItems(){
    	return new HashMap<>(items);
    }
    
    public MenuItem getMenuItem(String itemName) {
    	return items.get(itemName);
    }
    
    public boolean containsMenuItem(String itemName) {
    	return items.containsKey(itemName);
    }
}