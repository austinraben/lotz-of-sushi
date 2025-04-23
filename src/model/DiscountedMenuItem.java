package model;

public class DiscountedMenuItem extends MenuItem {
    private MenuItem wrapped;

    public DiscountedMenuItem(MenuItem wrapped) {
        super(wrapped); 
        this.wrapped = wrapped;
    }

    // 20% off
    @Override
    public double getPrice() {
        if (HappyHourManager.isHappyHour() && 
            (getFoodCourse() == FoodCourse.APPS || getFoodCourse() == FoodCourse.DRINKS)) {
            return super.getPrice() * 0.8;
        }
        return super.getPrice();
    }
}
