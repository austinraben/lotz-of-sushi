package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HappyHourSwitchTest {

	@Test
	void testToggle() {
		HappyHourSwitch mySwitch = new HappyHourSwitch();
		mySwitch.toggle();
		assertTrue(mySwitch.isHappyHour());
		mySwitch.toggle();
		assertFalse(mySwitch.isHappyHour());
	}

}
