package org.grabbing.devicepart.wrappers;

public class QuickCompletion {
    private boolean stop;

    public QuickCompletion() {
        this.stop = false;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isStop() {
        return stop;
    }
}
