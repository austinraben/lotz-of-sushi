package model;

import java.util.Comparator;
import java.util.Objects;

public class MenuItem {

	private FoodCourse foodCourse; 
    private String specificCategory; 
    private String itemName;
    private double price;
    private boolean isModifiable;
    private String description;

    public MenuItem(FoodCourse foodCourse, String specificCategory, String itemName, 
                    double price, boolean modifiable, String description) {
        this.foodCourse = foodCourse;
        this.specificCategory = specificCategory;
        this.itemName = itemName;
        this.price = price;
        this.isModifiable = modifiable;
        this.description = description;
    }
    
    // copy constructor
    public MenuItem(MenuItem menuItem) {
    	this.foodCourse = menuItem.foodCourse;
    	this.specificCategory = menuItem.specificCategory;
    	this.itemName = menuItem.itemName;
    	this.price = menuItem.price;
    	this.isModifiable = menuItem.isModifiable;
    	this.description = menuItem.description;
    }
    
    public String getName() {
    	return itemName;
    }
    
    
    
    
	// getters
    
    public FoodCourse getFoodCourse() {
		return foodCourse;
	}

	public String getSpecificCategory() {
		return specificCategory;
	}


	public String getItemName() {
		return itemName;
	}

	public double getPrice() {
		return price;
	}

	public boolean isModifiable() {
		return isModifiable;
	}

	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return itemName;
	}
	
	// incremementSales() for SalesTracker
	@Override
	public int hashCode() {
		return Objects.hash(itemName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MenuItem))
			return false;
		MenuItem other = (MenuItem) obj;
		return Objects.equals(itemName, other.itemName);
	}
	
}
