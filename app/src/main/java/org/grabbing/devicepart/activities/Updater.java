package org.grabbing.devicepart.activities;

import android.util.Log;

import org.grabbing.devicepart.data.storage.StaticStorage;

public class Updater extends Thread {
    private boolean onRun = true;

    private final int DELAY = 1000;

    @Override
    public void run() {
        for (;;) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("update", "update");
            if (onRun) {
                StaticStorage.getMainActivity().updateCallThread();
                if (StaticStorage.getFaceManagementFragment() != null) {
                    StaticStorage.getFaceManagementFragment().updateCallThread();
                }
            }
        }
    }

    public void setOnRun(boolean onRun) {
        this.onRun = onRun;
    }
}
