package model;
import java.util.ArrayList;
import java.util.HashMap;

public class SalesTracker {
	
	private HashMap<MenuItem, Integer> quantitySold;
	private HashMap<MenuItem, Double> totalSales;
	private double totalTips;
	
	public SalesTracker(ArrayList<Menu> menuList, ArrayList<String> menuItems) {
		for (Menu m : menuList) {
			for (String s : menuItems) {
				if (m.containsMenuItem(s)) {
					quantitySold.put(m.getMenuItem(s), 0);
					totalSales.put(m.getMenuItem(s), 0.0);
				}
			}
		}
		totalTips = 0.0;
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