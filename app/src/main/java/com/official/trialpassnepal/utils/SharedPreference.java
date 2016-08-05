package com.official.trialpassnepal.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

public class SharedPreference extends Application {

    private SharedPreferences prefs;

    Context _cntx;

    public SharedPreference(Context cntx) {

        _cntx = cntx;
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setKeyValues(String key, String value) {
        prefs.edit().putString(key, value).commit();
        prefs.edit().commit();
    }

    public void setKeyValues(String key, int value
    ) {
        prefs.edit().putInt(key, value).commit();
        prefs.edit().commit();
    }
//
//    public void setKeyValues(String key, long value) {
//        prefs.edit().putLong(key, value).commit();
//        prefs.edit().commit();
//    }
//
//    public long getLongValues(String key){
//        long value = 0l;
//        value = prefs.getLong(key, value);
//        return value;
//    }

    public void setKeyValues(String key, boolean value) {
        prefs.edit().putBoolean(key, value).commit();
        prefs.edit().commit();
    }

    public String getStringValues(String key) {
        String value;
//        if (key.equals(CommonDef.SharedPrefKeys.LAST_UPDATE_TIMESTAMP)) {
//
//            long text = prefs.getLong(key, 0l);
//            value = "" + text;
//        } else {
        System.out.println("THe key is: "+key);
            value = prefs.getString(key, "");
//        }

        return value;
    }


    public int getIntValues(String key) {
        int nullValue = 0;
        int value = prefs.getInt(key, nullValue);
        return value;
    }

    public boolean getBoolValues(String key) {
        boolean value = false;
        value = prefs.getBoolean(key, value);
        return value;
    }


    public Map<String, ?> getAllVal() {

        Map<String, ?> keys = prefs.getAll();
        return keys;
    }

    public void clearData() {
        prefs.edit().clear().commit();

    }


}
