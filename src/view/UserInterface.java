package view;

import model.Restaurant;

public class UserInterface {
	private Restaurant lotzOfSushi;
    private java.util.Scanner scanner;
    private java.util.Random random;
    
    public UserInterface(Restaurant restaurant) {
        this.lotzOfSushi = restaurant;
        this.scanner = new java.util.Scanner(System.in);
        this.random = new java.util.Random();
    }
    
    public static void main(String[] args) {
    	// initialize Restaurant
    	// run start()
    }
    
    public void start() {
    	
    }
    
    // Main Menu
    // Option 1: Assign Tables to Servers
    // Option 2: Act as a Server
    // Option 3: Search Menu
    // Option 4: View Menu
    // Option 5: View Sorted Menu by sales or revenue
    // Option 6: View Sorted Server Tips
    
    // Option 2: Act as a Server (Customer decisions are random)
    // - create Order 
    //   - add OrderedItems per Customer (for each of the 4 Menus)
    // - close the order
    // - calculate the bill (either evenly split or individually)
    // - close the table
}
