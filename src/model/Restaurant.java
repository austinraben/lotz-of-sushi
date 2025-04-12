package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    private SalesTracker sales;

    public Restaurant(String name) {
        this.name = name;
        this.servers = new ArrayList<>();
        this.tables = new ArrayList<>();
        this.drinkMenu = new DrinkMenu();
        this.appMenu = new AppMenu();
        this.entreeMenu = new EntreeMenu();
        this.dessertMenu = new DessertMenu();
        
        initializeSalesTracker();
    }
    
    private void initializeSalesTracker() {
    	ArrayList<Menu> allMenus = new ArrayList<>();
    	allMenus.add(drinkMenu);
        allMenus.add(appMenu);
        allMenus.add(entreeMenu);
        allMenus.add(dessertMenu);
        
        ArrayList<String> allMenuItems = new ArrayList<>();
        for (Menu menu : allMenus) {
        	for (String s : menu) {
        		allMenuItems.add(s.toLowerCase().strip());
        	}
        }
        this.sales = new SalesTracker(allMenus, allMenuItems, servers);
    }
    
    public void updateSalesTracker() {
    	for (Server s : servers) {
    		List<Order> serverOrders = s.getOrders();
    		for (Order o : serverOrders) {
    			if (o.isClosed()) {
    				sales.updateOrder(o, s);
    			}
    		}
    	}
    }
    
    // read menu.txt, create MenuItems and add them to Menu and its respective child class
    public void loadMenuItems(String filename) {
        
    	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            
    		String line;
            
            while ((line = reader.readLine()) != null) {
            	
            	// skip empty lines and comments
                if (line.trim().isEmpty() || line.trim().startsWith("//")) {
                    continue;
                }

                String[] parts = line.split(",", 6);
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                // extract data from menu
                FoodCourse course = FoodCourse.valueOf(parts[0]);
                String category = parts[1];
                String itemName = parts[2];
                double price = Double.parseDouble(parts[3]);
                boolean modifiable = Boolean.parseBoolean(parts[4]);
                String description = (parts.length > 5) ? parts[5] : ""; 

                MenuItem item = new MenuItem(course, category, itemName, price, modifiable, description);

                Menu subMenu = getMenuForCourse(course);
                if (subMenu != null) {
                    subMenu.addItem(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Menu getMenuForCourse(FoodCourse course) {
        switch (course) {
            case DRINKS:
                return drinkMenu;
            case APPS:
                return appMenu;
            case ENTREES:
                return entreeMenu;
            case DESSERTS:
                return dessertMenu;
            default:
                return null;
        }
    }

    // getters
    
    public Menu getDrinkMenu() {
        return drinkMenu;
    }

    public Menu getAppMenu() {
        return appMenu;
    }

    public Menu getEntreeMenu() {
        return entreeMenu;
    }

    public Menu getDessertMenu() {
        return dessertMenu;
    }

    public Menu getMenu(FoodCourse course) {
        return getMenuForCourse(course);
    }   
    
    // sorters
}