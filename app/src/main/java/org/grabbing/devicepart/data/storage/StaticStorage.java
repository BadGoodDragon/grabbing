package org.grabbing.devicepart.data.storage;

import org.grabbing.devicepart.Executor;

import org.grabbing.devicepart.activities.MainActivity;
import org.grabbing.devicepart.activities.Updater;
import org.grabbing.devicepart.activities.fragments.AccountLogInFragment;
import org.grabbing.devicepart.activities.fragments.AccountRegisterFragment;
import org.grabbing.devicepart.activities.fragments.FaceManagementFragment;
import org.grabbing.devicepart.activities.fragments.FaceRegisterFragment;

public class StaticStorage {
    private static Executor executor;

    public static Executor getExecutor() {return executor;}
    public static void setExecutor(Executor executor) {StaticStorage.executor = executor;}

    public static Updater updater;

    public static Updater getUpdater() {return updater;}
    public static void setUpdater(Updater updater) {StaticStorage.updater = updater;}


    private static MainActivity mainActivity;
    /*private static AccountLogInActivity accountLogInActivity;
    private static AccountRegisterActivity accountRegisterActivity;
    private static FaceManagementActivity faceManagementActivity;
    private static FaceRegisterActivity faceRegisterActivity;*/

    public static MainActivity getMainActivity() {return mainActivity;}
    /*public static AccountLogInActivity getAccountLogInActivity() {return accountLogInActivity;}
    public static AccountRegisterActivity getAccountRegisterActivity() {return accountRegisterActivity;}
    public static FaceManagementActivity getFaceManagementActivity() {return faceManagementActivity;}
    public static FaceRegisterActivity getFaceRegisterActivity() {return faceRegisterActivity;}*/

    public static void setMainActivity(MainActivity mainActivity) {StaticStorage.mainActivity = mainActivity;}
    /*public static void setAccountLogInActivity(AccountLogInActivity accountLogInActivity) {StaticStorage.accountLogInActivity = accountLogInActivity;}
    public static void setAccountRegisterActivity(AccountRegisterActivity accountRegisterActivity) {StaticStorage.accountRegisterActivity = accountRegisterActivity;}
    public static void setFaceManagementActivity(FaceManagementActivity faceManagementActivity) {StaticStorage.faceManagementActivity = faceManagementActivity;}
    public static void setFaceRegisterActivity(FaceRegisterActivity faceRegisterActivity) {StaticStorage.faceRegisterActivity = faceRegisterActivity;}*/

    private static AccountLogInFragment accountLogInFragment;
    private static AccountRegisterFragment accountRegisterFragment;
    private static FaceManagementFragment faceManagementFragment;
    private static FaceRegisterFragment faceRegisterFragment;

    public static AccountLogInFragment getAccountLogInFragment() {return accountLogInFragment;}
    public static AccountRegisterFragment getAccountRegisterFragment() {return accountRegisterFragment;}
    public static FaceManagementFragment getFaceManagementFragment() {return faceManagementFragment;}
    public static FaceRegisterFragment getFaceRegisterFragment() {return faceRegisterFragment;}

    public static void setAccountLogInFragment(AccountLogInFragment accountLogInFragment) {StaticStorage.accountLogInFragment = accountLogInFragment;}
    public static void setAccountRegisterFragment(AccountRegisterFragment accountRegisterFragment) {StaticStorage.accountRegisterFragment = accountRegisterFragment;}
    public static void setFaceManagementFragment(FaceManagementFragment faceManagementFragment) {StaticStorage.faceManagementFragment = faceManagementFragment;}
    public static void setFaceRegisterFragment(FaceRegisterFragment faceRegisterFragment) {StaticStorage.faceRegisterFragment = faceRegisterFragment;}
}
