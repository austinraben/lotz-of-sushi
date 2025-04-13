package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesTracker {
	
	private HashMap<MenuItem, Integer> quantitySold;
	private HashMap<MenuItem, Double> totalSales;
	private HashMap<Server, Double> tipsByServer;
	
	public SalesTracker(ArrayList<Menu> menuList, ArrayList<String> menuItems, HashMap<String, Server> servers) {
		quantitySold = new HashMap<>();
		totalSales = new HashMap<>();
		tipsByServer = new HashMap<>();
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
		List<OrderedItem> orderedItems = order.getItems();
		for (OrderedItem orderItem : orderedItems) {
			int count = quantitySold.get(orderItem);
			double price = totalSales.get(orderItem);
			count++;
			price += orderItem.getPrice();
			quantitySold.put(orderItem, count);
			totalSales.put(orderItem, price);
		}
	}
	
	public void updateServerTips(HashMap<String, Server> servers) {
		for (Map.Entry<String, Server> entry : servers.entrySet()) {
			tipsByServer.put(entry.getValue(), entry.getValue().getTipsEarned());
		}
	}
	
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
}