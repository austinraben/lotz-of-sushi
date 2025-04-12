package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalesTracker {
	
	private HashMap<MenuItem, Integer> quantitySold;
	private HashMap<MenuItem, Double> totalSales;
	private HashMap<Server, Double> tipsByServer;
	
	public SalesTracker(ArrayList<Menu> menuList, ArrayList<String> menuItems, List<Server> servers) {
		for (Menu m : menuList) {
			for (String s : menuItems) {
				if (m.containsMenuItem(s)) {
					quantitySold.put(m.getMenuItem(s), 0);
					totalSales.put(m.getMenuItem(s), 0.0);
				}
			}
		}
		for (Server s : servers) {
			tipsByServer.put(s, 0.0);
		}
	}

	
	public void updateOrder(Order order, Server server) {
		List<OrderedItem> orderedItems = order.getItems();
		for (OrderedItem orderItem : orderedItems) {
			int count = quantitySold.get(orderItem);
			double price = totalSales.get(orderItem);
			count++;
			price += orderItem.getPrice();
			quantitySold.put(orderItem, count);
			totalSales.put(orderItem, price);
		}
		
		double tip = tipsByServer.get(server);
		tip += order.getTip();
		tipsByServer.put(server, tip);
	}
	
	public double getSaleForItem(MenuItem menuItem) {
		return totalSales.get(menuItem);
	}
	
	public int getQuantityForItem(MenuItem menuItem) {
		return quantitySold.get(menuItem);
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
	
	// Iterate through Order's MenuItems to track 
	//  (1) number of sales per item
	//  (2) revenue per item
}