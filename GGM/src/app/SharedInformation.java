package app;

import classes.Car;
import java.util.prefs.Preferences;

public class SharedInformation {
    private SharedInformation() {}
    public static Car selectedCar;
    private static final Preferences prefs = Preferences.userNodeForPackage(SharedInformation.class);
    public static String accentColor = prefs.get("accentColor", "#dddddd");
    public static void saveAccent(String csscolor) {
        accentColor = csscolor;
        prefs.put("accentColor", csscolor);
    }
}