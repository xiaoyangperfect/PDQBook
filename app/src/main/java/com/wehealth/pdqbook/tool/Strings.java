package com.wehealth.pdqbook.tool;

import com.wehealth.pdqbook.fragment.MainFragment;

/**
 * Created by xiaoyang on 2016/12/9.
 */

public class Strings {
    public static final String INTNET_CONTENT_URL = "url";
    public static final String INTENT_TITLE = "title";

    //intent type
    public static final String INTNET_URL_TYPE = "type";
    public static final String URL_TYPE_CANCERPAGE = "cancer_page";
    public enum WebPageUrlType {
        cancerPage,
        riskInspection
    }

    //fragment onclick listener param uri
    private static final String INTNET_URI = "content://com.wehealth.pdqbook?%s#%s";
    public static final String getIntentUri(String fragment, String... params) {
        if (params.length % 2 != 0)
            throw new RuntimeException("params must be even number");
        String paramStr = "";
        for (int i = 0; i < params.length; i++) {
            if (i % 2 == 0) {
                paramStr = paramStr + "&" + params[i];
            } else if (i % 2 == 1) {
                paramStr = paramStr + "=" + params[i];
            }
        }
        if (paramStr.length() > 1)
            paramStr = paramStr.substring(1);
        return String.format(INTNET_URI, paramStr, fragment);
    }
}
