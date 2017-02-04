package com.wehealth.pdqbook.content;

import android.content.Context;

/**
 * Created by xiaoyang on 2017/2/1.
 */

public class AppContext {
    private static Context mContext;

    public static Context instance() {
        return mContext;
    }

    public static synchronized void set(Context context) {
        if (mContext != null && mContext != context) {
            throw new RuntimeException("Application context changed!");
        }
        mContext = context;
    }
}
