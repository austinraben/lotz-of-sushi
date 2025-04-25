/*
 * This class represents a DiscountedMenuItem that inherits from MenuItem and has a MenuItem reference. This class demonstrates
 * decorator design pattern by implementing additional functionality (discounted price) for MenuItems of specific Menus that
 * can be turned on and off in the client code.
 */
package model;

public class DiscountedMenuItem extends MenuItem {
    private MenuItem wrapped;
    
    // default constructor
    public DiscountedMenuItem(MenuItem wrapped) {
        super(wrapped); 
        this.wrapped = wrapped;
    }
    
    // copy constructor
    public DiscountedMenuItem(DiscountedMenuItem other) {
    	super(other);
    	this.wrapped = other.wrapped;
    }

    // 20% off
    @Override
    public double getPrice() {
    	/*
    	 * This method implements additional functionality of a sale when a certain "Happy Hour" button is pressed. This
    	 * functionality can be turned on and off in the client code
    	 */
        if (HappyHourManager.isHappyHour() && 
            (getFoodCourse() == FoodCourse.APPS || getFoodCourse() == FoodCourse.DRINKS)) {
            return Math.round((super.getPrice() * 0.8) * 100) / 100.0;
        }
        return super.getPrice();
    }
}
