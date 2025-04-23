package model;

public class SwitchModelDecorator {
	private RegularSwitch regularSwitch;
	private HappyHourDecorator happyHourSwitch;
	private Switch sw = regularSwitch;
		
	private boolean happyHourIsOn = false;

	public SwitchModelDecorator() {
		regularSwitch = new RegularSwitch();
		happyHourSwitch = new HappyHourDecorator(regularSwitch);
		sw = regularSwitch;
	}
	
	public void toggleSwitch() {
		sw.toggle();
	}
	
	public boolean isHappyHour() {
		return sw.isHappyHour();
	}
	
	public void toggleHappyHour() {
		if(this.happyHourIsOn) {
			sw = regularSwitch;
			this.happyHourIsOn = false;
		} else {
			sw = happyHourSwitch;
			this.happyHourIsOn = true;
		}
		sw.toggle();
	}
}

