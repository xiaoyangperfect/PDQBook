package com.wehealth.pdqbook.tool;

import android.webkit.JavascriptInterface;

/**
 * @Author yangxiao on 2/15/2017.
 */

public abstract class WebListJavascriptInterface implements WebJavascriptInterface {
    public abstract void WebDocListDidSelected(String json);
}
