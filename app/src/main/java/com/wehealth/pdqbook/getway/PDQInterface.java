package com.wehealth.pdqbook.getway;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by xiaoyang on 2017/3/14.
 */

public class PDQInterface {

    public static Call request(String url, Callback callback, String... params) {
        if (params == null || params.length == 0) {
            return HttpEngine.instance().getAsyncHttp(url, callback);
        } else {
            return HttpEngine.instance().postAsyncHttp();
        }
    }
}
