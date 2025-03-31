package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private List<OrderedItem> items;
    private boolean isClosed;

    public Order() {
        this.items = new ArrayList<>();
        this.isClosed = false;
    }
}
