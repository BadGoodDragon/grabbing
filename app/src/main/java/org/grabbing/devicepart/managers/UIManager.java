package org.grabbing.devicepart.managers;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.grabbing.devicepart.activities.ActivitiesActions;
import org.grabbing.devicepart.activities.FaceManagementActivity;
import org.grabbing.devicepart.activities.MainActivity;

import java.util.List;
import java.util.Objects;

public class UIManager {
    private static ActivitiesActions currentActivity;

    private static MainActivity mainActivity;
    private static FaceManagementActivity faceManagementActivity;

    public static void setCurrentActivity(ActivitiesActions currentActivity) {UIManager.currentActivity = currentActivity;}

    public static void setMainActivity(MainActivity mainActivity) {UIManager.mainActivity = mainActivity;}
    public static void setFaceManagementActivity(FaceManagementActivity faceManagementActivity) {UIManager.faceManagementActivity = faceManagementActivity;}

    public static void setError(String error) {
        currentActivity.setError(error);
    }

    public static void nextStep() {
        currentActivity.finishNow();
    }

    public static void dataTransmission(String title, String data) {
        if (Objects.equals(title, "face name")) {
            mainActivity.setFaceName(data);
        } else if (Objects.equals(title, "token")) {
            mainActivity.setToken(data);
        }
    }

    public static void dataTransmission(String title, int data) {
        if (Objects.equals(title, "authorization status")) {
            mainActivity.setAuthStatus(data);
        }
    }

    public static void dataTransmission(String title, List<String> list) {
        if (Objects.equals(title, "list of linked users")) {
            faceManagementActivity.setListOfLinkedUsers(list);
        }
    }

    public static void dataTransmission(String title, boolean data) {
        if (Objects.equals(title, "set button status")) {
            mainActivity.setButtonStatus(data);
        }
    }

    public static void update() {
        mainActivity.update();
    }

}
