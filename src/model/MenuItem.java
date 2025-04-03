package model;

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
	
	// incremementSales() for SalesTracker

}
