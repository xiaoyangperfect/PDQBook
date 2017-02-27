package com.wehealth.pdqbook.getway.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Author yangxiao on 2/27/2017.
 */

public class ArticleIntent {
    private String url;
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleIntent parser(String json) {
        try {
            ArticleIntent art = new ArticleIntent();
            JSONObject object = new JSONObject(json);
            art.setUrl(object.getString("link").replaceAll("#", "!!!"));
            art.setTitle(object.getString("title"));
            return art;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
