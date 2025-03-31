package model;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    protected FoodCourse course;
    protected List<MenuItem> items;

    public Menu(FoodCourse course) {
        this.course = course;
        this.items = new ArrayList<>();
    }
    
    // methods to view/search menu
}