package model;

public class OrderedItem extends MenuItem {
    private final String modification;

    public OrderedItem(MenuItem menuItem, String modification) {
        super(menuItem);
        if (this.isModifiable()) {
        	this.modification = modification;
        }
        else {
        	this.modification = "This item can not be modified";
        }    
    }

    public String getModification() {
        return modification;
    }
}