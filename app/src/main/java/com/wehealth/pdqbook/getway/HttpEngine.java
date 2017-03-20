package com.wehealth.pdqbook.getway;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by xiaoyang on 2017/3/14.
 */

public class HttpEngine {
    private static HttpEngine mHttpEngine;
    private OkHttpClient mOkHttpClient;

    public static HttpEngine instance() {
        if (mHttpEngine == null) {
            synchronized (HttpEngine.class) {
                if (mHttpEngine == null) {
                    mHttpEngine = new HttpEngine();
                }
            }
        }
        return mHttpEngine;
    }

    private HttpEngine() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public Call getAsyncHttp(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call postAsyncHttp() {
        return null;
    }
}
