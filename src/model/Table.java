/*
 * This class defines methods for a Table object. A Table object is immutable and is meant to connect the 
 * Servers with their Customers. Tables are solely identified by their tableNumber.
 */
package model;

import java.util.Objects;

public class Table {
	private int tableNumber;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    // getter for TableNumber
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
		return "TABLE " + tableNumber;
	}
       
    
}
