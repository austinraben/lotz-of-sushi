package model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int customerNum;  // our customers don't get names lol
    private List<Order> orders;
    
    public Customer(String Number) {
    	this.customerNum = customerNum;
        this.orders = new ArrayList<>();
    }
}
