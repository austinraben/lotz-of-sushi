/*
 * This class defines the on/off function for the Happy Hour.
 */
package model;

public class HappyHourSwitch {
    private boolean isOn = false;
    
    // switches the value of the isOn variable
    public void toggle() {
        isOn = !isOn;
    }

    // returns value of switch
    public boolean isHappyHour() {
        return isOn;
    }
}

