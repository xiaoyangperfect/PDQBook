package com.wehealth.pdqbook.tool;

import com.wehealth.pdqbook.fragment.MainFragment;

/**
 * Created by xiaoyang on 2016/12/9.
 */

public class Strings {
    public static final String PLACEHOLDER = "placeholder";

    public static final String INTNET_CONTENT_URL = "url";
    public static final String INTENT_TITLE = "title";
    public static final String INTENT_CONTENT = "content";

    //intent type
    public static final String INTENT_ACTION_TYPE = "type";
    public static final String URL_TYPE_CANCERPAGE = "cancer_page";
    public enum IntentActionUrlType {
        cancerPage,
        riskInspection,
        search,
        cancerArticleQuestion,
        appointment
    }

    //fragment onclick listener param uri
    private static final String INTNET_URI = "content://com.wehealth.pdqbook?%s#%s";
    public static final String getIntentUri(String fragment, String... params) {
        if (params.length % 2 != 0)
            throw new IllegalArgumentException("params must be even number");
        String paramStr = "";
        if (params.length == 0) {
            paramStr = PLACEHOLDER;
        } else {
            for (int i = 0; i < params.length; i++) {
                if (i % 2 == 0) {
                    paramStr = paramStr + "&" + params[i];
                } else if (i % 2 == 1) {
                    paramStr = paramStr + "=" + params[i];
                }
            }
            if (paramStr.length() > 1)
                paramStr = paramStr.substring(1);
        }

        return String.format(INTNET_URI, paramStr, fragment);
    }
}
