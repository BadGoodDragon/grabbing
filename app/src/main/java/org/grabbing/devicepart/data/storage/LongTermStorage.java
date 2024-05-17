package org.grabbing.devicepart.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.grabbing.devicepart.domain.QueryData;

public class LongTermStorage {
    public static final QueryData queryReceiptManagerQuery = new QueryData("http://192.168.0.75:8090/receivingqueries", -2);
    public static final QueryData sendingResultManagerQuery = new QueryData("http://192.168.0.75:8090/returningresults", -3);
    public static final QueryData faceManagerQuery = new QueryData("http://192.168.0.75:8090/facemanagement", -4);
    public static final QueryData checkManagerQuery = new QueryData("http://192.168.0.75:8090/checkmanagement", -5);
    public static final QueryData accountQuery = new QueryData("http://192.168.0.75:8090/accountmanagement", -6);


    private static final String PREF_NAME = "data";
    private static final String KEY_SAVED_TOKEN = "savedToken";
    private static final String KEY_SAVED_USERNAME = "savedUsername";

    public static void saveToken(String token, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_SAVED_TOKEN, token);
        editor.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_SAVED_TOKEN, null);
    }

    public static void deleteToken(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(KEY_SAVED_TOKEN);
        editor.apply();
    }

    public static boolean hasSavedToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.contains(KEY_SAVED_TOKEN);
    }

    public static void saveUsername(String username, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_SAVED_USERNAME, username);
        editor.apply();
    }

    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_SAVED_USERNAME, null);
    }

    public static boolean hasSavedUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.contains(KEY_SAVED_USERNAME);
    }

    public static void deleteUsername(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(KEY_SAVED_USERNAME);
        editor.apply();
    }
}
