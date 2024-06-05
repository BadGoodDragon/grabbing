package org.grabbing.devicepart.activities;

import android.util.Log;

import org.grabbing.devicepart.activities.fragments.FaceManagementFragment;

public class Updater extends Thread {
    private static boolean onRun = false;

    public static boolean isOnRun() {return onRun;}

    private static MainActivity mainActivity;
    private static FaceManagementFragment faceManagementFragment;

    public static void setMainActivity(MainActivity mainActivity) {Updater.mainActivity = mainActivity;}
    public static void setFaceManagementFragment(FaceManagementFragment faceManagementFragment) {Updater.faceManagementFragment = faceManagementFragment;}

    private static final int DELAY = 1000;

    @Override
    public void run() {
        onRun = true;
        for (;;) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("update", "update");

            mainActivity.updateCallThread();
            if (faceManagementFragment != null) {
                faceManagementFragment.updateCallThread();
            }
        }
    }
}
