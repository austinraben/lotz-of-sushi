package model;

public class OrderedItem extends MenuItem{
    private String modification; 

    public OrderedItem(FoodCourse foodCourse, String specificCategory, String itemName, 
            double price, boolean modifiable, String description, String modification) {
        super(foodCourse, specificCategory, itemName, price, modifiable, description);
        if (this.isModifiable()) {
        	this.modification = modification;
        }
        else {
        	this.modification = "This item can not be modified";
        }
    }
    
    
    public OrderedItem(MenuItem menuItem, String modification) {
        super(menuItem);
        if (this.isModifiable()) {
        	this.modification = modification;
        }
        else {
        	this.modification = "This item can not be modified";
        }
    }
    
    
    // getters
    public String getModification() {
    	return modification;
    }

}
