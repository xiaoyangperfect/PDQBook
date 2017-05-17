package com.wehealth.pdqbook.getway.repertory;

import android.content.Context;
import android.content.SharedPreferences;

import com.wehealth.pdqbook.content.AppContext;

/**
 * @Author yangxiao on 2/8/2017.
 */

public class SharedPreferenceUtil {
    public static final String USER = "admin";

    private SharedPreferences sp;

    public SharedPreferenceUtil() {
        sp = AppContext.instance().getSharedPreferences(USER, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        sp.edit().putString(key, value);
    }

    public String getString(String key) {
        return sp.getString(key, null);
    }

    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value);
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value);
    }

    public void putFloat(String key, float value) {
        sp.edit().putFloat(key, value);
    }

    public void putLong(String key, long value) {
        sp.edit().putLong(key, value);
    }
}
