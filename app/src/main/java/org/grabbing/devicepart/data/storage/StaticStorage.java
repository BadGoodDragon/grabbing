package org.grabbing.devicepart.data.storage;

import org.grabbing.devicepart.Executor;
import org.grabbing.devicepart.activities.MainActivity;

public class StaticStorage {
    private static Executor executor;

    public static Executor getExecutor() {return executor;}
    public static void setExecutor(Executor executor) {StaticStorage.executor = executor;}

}
