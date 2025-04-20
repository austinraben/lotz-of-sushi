package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Table {
	private int tableNumber;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    // getters & setters
    public int getTableNumber() {
    	return tableNumber;
    }

	@Override
	public int hashCode() {
		return Objects.hash(tableNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		return tableNumber == other.tableNumber;
	}
	
	@Override
	public String toString() {
		return "TABLE " + (tableNumber + 1);
	}

    
    
    
    
}
