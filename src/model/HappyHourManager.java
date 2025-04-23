package model;

public class HappyHourManager {
    private static HappyHourSwitch switchModel = new HappyHourSwitch();

    public static boolean isHappyHour() {
        return switchModel.isHappyHour();
    }

    public static void toggleHappyHour() {
        switchModel.toggle();
    }
}
