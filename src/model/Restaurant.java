package model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private List<Server> servers;
    private List<Table> tables;
    private Menu drinkMenu;
    private Menu appMenu;
    private Menu entreeMenu;
    private Menu dessertMenu;

    public Restaurant(String name) {
        this.name = name;
        this.servers = new ArrayList<>();
        this.tables = new ArrayList<>();
        this.drinkMenu = new DrinkMenu();
        this.appMenu = new AppMenu();
        this.entreeMenu = new EntreeMenu();
        this.dessertMenu = new DessertMenu();

    }
 
    // void loadMenuItems
    //    uses BufferedReader to read menu.txt
    //    populate the four menus
    
    // getters
    
    // sorters
    
}