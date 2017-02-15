package com.wehealth.pdqbook.tool;

import android.annotation.SuppressLint;

/**
 * @Author yangxiao on 2/15/2017.
 */

public interface WebListJavascriptInterface extends WebJavascriptInterface {
    @SuppressLint("JavascriptInterface")
    void WebDocListDidSelected(String json);
}
