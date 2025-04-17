package model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    BillTest.class,
    CustomerTest.class,
    MenuTest.class,
    MenuItemTest.class,
    OrderTest.class,
    SalesTrackerTest.class,
    TableTest.class
})
public class AllTests {
	// Annotations above handle all tests
}