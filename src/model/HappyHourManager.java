package model;

public class HappyHourManager {
    private static SwitchModelDecorator switchModel = new SwitchModelDecorator();

    public static boolean isHappyHour() {
        return switchModel.isHappyHour();
    }

    public static void toggleHappyHour() {
        switchModel.toggleHappyHour();
    }
}
