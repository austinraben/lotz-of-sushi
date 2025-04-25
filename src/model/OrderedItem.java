/*
 * This class defines behavior for an OrderedItem, which is an extension of MenuItem. It represents
 * MenuItems ordered by Customers, only adding a modification attribute to a MenuItem.
 */
package model;

public class OrderedItem extends MenuItem {
    private final String modification;
    private final MenuItem decoratedItem;
    private final double decoratedPrice;
    
    public OrderedItem(MenuItem menuItem, String modification, MenuItem decoratedItem) {
        super(menuItem);
        this.decoratedItem = menuItem;
        this.decoratedPrice = decoratedItem.getPrice(); 
        
        // adds customer-specified modifications to applicable items
        if (this.isModifiable()) {
        	this.modification = modification;
        }
        else {
        	this.modification = "This item can not be modified";
        }    
    }

    public MenuItem getDecoratedItem() {
        return decoratedItem;
    }
    
    public String getModification() {
        return modification;
    }
    
    @Override
    public double getPrice() {
    	String roundedPrice = String.format("%.2f", decoratedPrice);
    	return Double.parseDouble(roundedPrice);    }
}