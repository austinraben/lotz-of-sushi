package model;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Restaurant {
    private String name;
    private HashMap<String, Server> servers;
    private HashMap<String, Table> tables;
    private Menu drinkMenu;
    private Menu appMenu;
    private Menu entreeMenu;
    private Menu dessertMenu;

    public Restaurant(String name) {
        this.name = name;
        this.servers = new HashMap<>();
        this.tables = new HashMap<>();
        this.drinkMenu = new DrinkMenu();
        this.appMenu = new AppMenu();
        this.entreeMenu = new EntreeMenu();
        this.dessertMenu = new DessertMenu();

    }
    
    public void hireServer(String name) {
    	servers.put(name, new Server(name));
    }
 
    // void loadMenuItems
    //    uses BufferedReader to read menu.txt
    //    populate the four menus
    
    // getters
    
    // sorters
    
}