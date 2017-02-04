package com.wehealth.pdqbook.tool;

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
}
