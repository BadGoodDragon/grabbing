package org.grabbing.devicepart.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import org.grabbing.devicepart.domain.QueryData;

public class LongTermStorage {
    public static final QueryData queryReceiptManagerQuery = new QueryData("http://94.103.92.242:8090/", -2);
    public static final QueryData sendingResultManagerQuery = new QueryData("http://94.103.92.242:8090/ret", -3);
    public static final QueryData faceManagerQuery = new QueryData("http://94.103.92.242:8090/false", -4);
    public static final QueryData checkManagerQuery = new QueryData("http://94.103.92.242:8090/yes", -5);


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
}
