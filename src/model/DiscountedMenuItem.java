package model;

public class DiscountedMenuItem extends MenuItem {
    private MenuItem wrapped;

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
        if (HappyHourManager.isHappyHour() && 
            (getFoodCourse() == FoodCourse.APPS || getFoodCourse() == FoodCourse.DRINKS)) {
            return Math.round((super.getPrice() * 0.8) * 100) / 100.0;
        }
        return super.getPrice();
    }
}
