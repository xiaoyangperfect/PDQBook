package com.wehealth.pdqbook.tool;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * @Author yangxiao on 3/17/2017.
 */

public class GsonTool {
    public static <T> T parserJson(JsonReader reader, Class<T> t) {
        Gson gson = new Gson();
        T result = gson.fromJson(reader, t);
        return result;
    }

    public static <T> T parserJson(String json, Class<T> t) {
        Gson gson = new Gson();
        T result = gson.fromJson(json, t);
        return result;
    }
}
