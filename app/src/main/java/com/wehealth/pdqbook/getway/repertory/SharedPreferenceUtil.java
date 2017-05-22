package com.wehealth.pdqbook.getway.repertory;

import android.content.Context;
import android.content.SharedPreferences;

import com.wehealth.pdqbook.content.AppContext;

/**
 * @Author yangxiao on 2/8/2017.
 */

public class SharedPreferenceUtil {
    public static String USER = "admin";

    private String WELCOME_SHOW_NUM = "welcomeShowNum";

    private SharedPreferences sp;

    public SharedPreferenceUtil() {
        sp = AppContext.instance().getSharedPreferences(USER, Context.MODE_PRIVATE);
    }

    public int getWelcomeShowNum() {
        return sp.getInt(WELCOME_SHOW_NUM, 0);
    }

    public void setWelcomeShowNum(int num) {
        sp.edit().putInt(WELCOME_SHOW_NUM, num).apply();
    }
}
