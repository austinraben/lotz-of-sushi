package model;

public class HappyHourSwitch {
    private boolean isOn = false;

    public void toggle() {
        isOn = !isOn;
    }

    public boolean isHappyHour() {
        return isOn;
    }
}

