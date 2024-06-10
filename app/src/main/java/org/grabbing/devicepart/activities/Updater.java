package org.grabbing.devicepart.activities;

import android.util.Log;

import org.grabbing.devicepart.activities.fragments.FaceManagementFragment;
import org.grabbing.devicepart.activities.fragments.MyQueriesFragment;

public class Updater extends Thread {
    private static boolean onRun = false;

    public static boolean isOnRun() {return onRun;}

    private static MainActivity mainActivity;
    private static FaceManagementFragment faceManagementFragment;
    private static MyQueriesFragment myQueriesFragment;

    public static void setMainActivity(MainActivity mainActivity) {Updater.mainActivity = mainActivity;}
    public static void setFaceManagementFragment(FaceManagementFragment faceManagementFragment) {Updater.faceManagementFragment = faceManagementFragment;}
    public static void setMyQueriesFragment(MyQueriesFragment myQueriesFragment) {Updater.myQueriesFragment = myQueriesFragment;}

    private static final int DELAY = 1000;

    private int count;

    @Override
    public void run() {
        count = 0;
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
            if (myQueriesFragment != null && count % 5 == 0) {
                myQueriesFragment.updateCallThread();
            }
            count++;
        }
    }
}
