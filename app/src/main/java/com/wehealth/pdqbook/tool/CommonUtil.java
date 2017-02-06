package com.wehealth.pdqbook.tool;

import android.content.Context;
import android.content.pm.PackageManager;

import com.wehealth.pdqbook.content.AppContext;

/**
 * @Author yangxiao on 2/6/2017.
 */

public class CommonUtil {
    public static <T> T callAndEatException(Function<T> f) {
        try {
            f.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static String versionName() {
        Context appContext = AppContext.instance();
        try {
            return appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String currentTimestampString() {
        return Long.toString(System.currentTimeMillis());
    }

    public static <T> T callAndEatAllException(Function<T> f) {
        try {
            f.call();
        }  catch (Exception e) {
            e.printStackTrace();
            // we eat it to avoid exception spreading. if caller need it, they should catch exceptions by themselves.
        }
        return null;
    }

    public static <T> boolean equalsWithAllowedNull(T left, T right) {
        return (left != null) ? left.equals(right) : (right == null);
    }
}
