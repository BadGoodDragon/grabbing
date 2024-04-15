package org.grabbing.devicepart.managers;

import androidx.appcompat.app.AppCompatActivity;

import org.grabbing.devicepart.activities.ActivitiesActions;
import org.grabbing.devicepart.activities.MainActivity;

public class UIManager {
    private static ActivitiesActions currentActivity;

    private static MainActivity mainActivity;

    public static ActivitiesActions getCurrentActivity() {return currentActivity;}
    public static void setCurrentActivity(ActivitiesActions currentActivity) {UIManager.currentActivity = currentActivity;}


    public static MainActivity getMainActivity() {return mainActivity;}
    public static void setMainActivity(MainActivity mainActivity) {UIManager.mainActivity = mainActivity;}
}
