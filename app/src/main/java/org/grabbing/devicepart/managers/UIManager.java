package org.grabbing.devicepart.managers;



import org.grabbing.devicepart.data.storage.StaticStorage;

import java.util.List;
import java.util.Objects;

public class UIManager {
    public static void setSuccessAccountLogInActivity() {
        StaticStorage.getAccountLogInFragment().successCallThread();
    }
    public static void setErrorAccountLogInActivity() {
        StaticStorage.getAccountLogInFragment().errorCallThread();
    }

    public static void setSuccessAccountRegisterActivity() {
        StaticStorage.getAccountRegisterFragment().successCallThread();

    }
    public static void setErrorAccountRegisterActivity() {
        StaticStorage.getAccountRegisterFragment().errorCallThread();
    }

    public static void setErrorMainActivity() {
        StaticStorage.getMainActivity().setStartButtonOnClickErrorCallThread();
    }

    public static void setFaceNameMainActivity(String faceName) {
        StaticStorage.getMainActivity().setFaceNameCallThread(faceName);
    }
    public static void setCheckTokenResultMainActivity(int status) {
        StaticStorage.getMainActivity().setCheckTokenResultCallThread(status);
    }

    public static void setListOfLinkedUsersFaceManagementActivity(List<String> list) {
        StaticStorage.getFaceManagementFragment().setListOfLinkedUsersCallThread(list);
    }

    public static void setErrorFaceManagementActivity() {
        StaticStorage.getFaceManagementFragment().setErrorCallThread();
    }
    public static void setSuccessFaceRegisterActivity() {
        StaticStorage.getFaceRegisterFragment().successCallThread();
    }
    public static void setErrorFaceRegisterActivity() {
        StaticStorage.getFaceRegisterFragment().errorCallThread();
    }
}
