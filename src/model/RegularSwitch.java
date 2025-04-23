package model;

public class RegularSwitch implements Switch {
	
	private ToggleVal setting;
	
	public RegularSwitch() {
		this.setting = ToggleVal.OFF;
	}
	
	public void toggle() {
		if(setting == ToggleVal.ON)
			setting = ToggleVal.OFF;
		else
			setting = ToggleVal.ON;
	}
	
	public boolean isHappyHour() {
		return this.setting == ToggleVal.ON;
	}
}

