/*
 * This class represents a SalesTracker that keeps history of all the sales made by the restaurant. SalesTracker holds a quantitySold
 * HashMap that represents how many times a MenuItem has been ordered, totalSales HashMap that represents how much each MenuItem has
 * made in revenue, and tipsByServer HashMap that represents how many tips each server has earned.
 */

package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesTracker {
	
	private HashMap<MenuItem, Integer> quantitySold;
	private HashMap<MenuItem, Double> totalSales;
	private HashMap<Server, Double> tipsByServer;
	
	// default constructor
	public SalesTracker(ArrayList<Menu> menuList, ArrayList<String> menuItems, HashMap<String, Server> servers) {
		quantitySold = new HashMap<>();
		totalSales = new HashMap<>();
		tipsByServer = new HashMap<>();
		
		// initialize all HashMaps by getting all MenuItems/Servers and setting the values to 0/0.0
		for (Menu m : menuList) {
			for (String s : menuItems) 
				if (m.containsMenuItem(s)) {
					quantitySold.put(m.getMenuItem(s), 0);
					totalSales.put(m.getMenuItem(s), 0.0);
				}
			}
		
		for (String s : servers.keySet()) {
			tipsByServer.put(servers.get(s), 0.0);
		}
		
	}
	
	// copy constructor
	public SalesTracker(SalesTracker sales) {
		quantitySold = sales.quantitySold;
		totalSales = sales.totalSales;
		tipsByServer = sales.tipsByServer;
	}
	
	
	public void updateOrder(Order order) {
		/*
		 * This method updates the quantitySold and totalSales HashMaps for a given order. 
		 * It gets the OrderedItems list from the Order and counts each instance as well as
		 * adds the price of each OrderedItem.
		 */
		List<OrderedItem> orderedItems = order.getItems();
		for (OrderedItem orderItem : orderedItems) {
			// get count and price from sales tracker
			int count = quantitySold.get(orderItem);
			double price = totalSales.get(orderItem);
			
			count++;
			price += orderItem.getPrice();
			
			// update sales tracker
			quantitySold.put(orderItem, count);
			totalSales.put(orderItem, price);
		}
	}
	
	public void updateServerTips(HashMap<String, Server> servers) {
		/*
		 * This method updates the HashMap representing server tips by getting
		 * each servers tip attribute.
		 */
		for (Map.Entry<String, Server> entry : servers.entrySet()) {
			tipsByServer.put(entry.getValue(), entry.getValue().getTipsEarned());
		}
	}
	
	// getters
	
	public double getSaleForItem(MenuItem menuItem) {
		return totalSales.get(menuItem);
	}
	
	public int getQuantityForItem(MenuItem menuItem) {
		return quantitySold.get(menuItem);
	}
	
	public double getTipForServer(Server server) {
		return tipsByServer.get(server);
	}
	
	public double getTotalTips() {
		double sum = 0;
		for (Double d : tipsByServer.values()) {
			sum += d;
		}
		return sum;
	}
	
	public double getTotalSale() {
		double sum = 0;
		for (Double d : totalSales.values()) {
			sum += d;
		}
		return sum;
	}
	
	public int getTotalQuantity() {
		int quant = 0;
		for (Integer i : quantitySold.values()) {
			quant += i;
		}
		return quant;
	}
	
	public HashMap<MenuItem, Double> getTotalSales(){
		return new HashMap<>(totalSales);
	}
	
	public HashMap<MenuItem, Integer> getQuantitySold(){
		return new HashMap<>(quantitySold);
	}
	
	// toString methods
	
	public String quantitySoldSortedString() {
		String message = "---Sorted Menu Items Sold---\n";
		List<Map.Entry<MenuItem, Integer>> entries = new ArrayList<>(quantitySold.entrySet());

	    entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // sort descending by value
	    for (Map.Entry<MenuItem, Integer> entry : entries) {
	        message += entry.getKey().getItemName() + ": " + entry.getValue() + "\n";
	    }
		return message;
	}
	
	
	public String totalSalesSortedString() {
		String message = "---Sorted Menu Items Sales---\n";
		double total = 0.0;
		List<Map.Entry<MenuItem, Double>> entries = new ArrayList<>(totalSales.entrySet());

	    entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue())); // sort descending by value
	    for (Map.Entry<MenuItem, Double> entry : entries) {
	        message += entry.getKey().getItemName() + ": $" + entry.getValue() + "\n";
	        total += entry.getValue();
	    }
	    
	    message += "\nTotal Sales: $" + total + "\n";
		return message;
	}
	
	public String totalTipsSortedString() {
		String message = "---Sorted Server Tips---\n";
		double total = 0.0;
		
		ArrayList<Server> myList = new ArrayList<Server>(tipsByServer.keySet());
		Collections.sort(myList, Server.sortByTipsComparator());
		for (int i=myList.size() - 1; i >= 0; i--) {
			message += myList.get(i).getServerName() + ": $" + myList.get(i).getTipsEarned() + "\n";
			total += myList.get(i).getTipsEarned();
		}
		message += "\nTotal Tips Collected: $" + total + "\n";
		return message;
	}
	
	public String quantitySoldString() {
		String message = "---Menu Items Sold---\n";
		for (Map.Entry<MenuItem, Integer> entry : quantitySold.entrySet()) {
			message += entry.getKey().getItemName() + ": " + entry.getValue() + "\n";
		}
		return message;
	}
	
	public String totalSalesString() {
		String message = "---Menu Items Sales---\n";
		double total = 0.0;
		for (Map.Entry<MenuItem, Double> entry : totalSales.entrySet()) {
			message += entry.getKey().getItemName() + ": $" + entry.getValue() + "\n";
			total += entry.getValue();
		}
		message += "\nTotal Sales: $" + total + "\n";
		
		return message;
	}
	
	public String totalTipsString() {
		String message = "---Server Tips---\n";
		double total = 0.0;
		for (Map.Entry<Server, Double> entry : tipsByServer.entrySet()) {
			message += entry.getKey().getServerName() + ": $" + entry.getValue() + "\n";
			total += entry.getValue();
		}
		message += "\nTotal Tips Collected: $" + total + "\n";
		return message;
	}
	
	// method to keep track of Server with the highest tips:
	public Server getHighestTippedServer() {
		ArrayList<Server> myList = new ArrayList<Server>(tipsByServer.keySet());
		Collections.sort(myList, Server.sortByTipsComparator());
		return myList.get(myList.size() - 1);
	}
}
