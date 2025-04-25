/*
 * This class creates an object that turns the Happy Hour function on and off. Part of the 
 * implementation for the DECORATOR design pattern.
 */
package model;

public class HappyHourManager {
    private static HappyHourSwitch switchModel = new HappyHourSwitch();

    // returns true if Happy Hour is on and false if Happy Hour is off
    public static boolean isHappyHour() {
        return switchModel.isHappyHour();
    }

    // toggles the swicth on and off
    public static void toggleHappyHour() {
        switchModel.toggle();
    }
}
