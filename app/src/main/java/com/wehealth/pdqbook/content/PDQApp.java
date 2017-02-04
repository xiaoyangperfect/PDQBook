package com.wehealth.pdqbook.content;

import android.app.Application;

/**
 * Created by xiaoyang on 2017/2/1.
 */

public class PDQApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.set(this);
    }
}
