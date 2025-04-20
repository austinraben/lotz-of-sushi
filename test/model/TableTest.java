package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TableTest {

	@Test
	void testGetTableNum() {
		Table myTable = new Table(20);
		assertEquals(myTable.getTableNumber(), 20);
	}
	
	@Test
	void testEqualsSame() {
		Table myTable = new Table(20);
		assertEquals(myTable, myTable);
	}
	
	@Test
	void testEqualsDiffClass() {
		Table myTable = new Table(20);
		assertFalse(myTable.equals("myTable"));
	}
	
	@Test
	void testEqualsNull() {
		Table myTable = new Table(20);
		assertFalse(myTable.equals(null));
	}

	
	@Test
	void testEqualsTrue() {
		Table t1 = new Table(1);
		Table t2 = new Table(1);
		assertEquals(t1, t2);
	}
	
	@Test
	void testEqualsFalse() {
		Table t1 = new Table(1);
		Table t2 = new Table(2);
		assertFalse(t1.equals(t2));
	}
	
	@Test
    public void testHashCodeEquality() {
        Table t1 = new Table(5);
        Table t2 = new Table(5);
        
        assertEquals(t1.hashCode(), t2.hashCode());
    }
	
	@Test
    public void testToStringFormat() {
        Table t = new Table(0);
        assertEquals("TABLE 1", t.toString());
    }
}


