package model;

public class HappyHourDecorator implements Switch {
	protected final RegularSwitch reg;
	
	public HappyHourDecorator(RegularSwitch reg) {
		this.reg = reg;
	}
	
	public void toggle() {
		reg.toggle();
	}
	
	public boolean isHappyHour() {
		return reg.isHappyHour();
	}
}
