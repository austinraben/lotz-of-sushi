package model;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    BillTest.class,
    CustomerTest.class,
    HappyHourDecoratorTest.class,
    MenuItemTest.class,
    MenuTest.class,
    OrderTest.class,
    RestaurantTest.class,
    SalesTrackerTest.class,
    ServerTest.class,
    TableTest.class
})
public class AllTests {
	// Annotations above handle all tests
}