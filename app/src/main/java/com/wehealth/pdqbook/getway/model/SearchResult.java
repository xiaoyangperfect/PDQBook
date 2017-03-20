package com.wehealth.pdqbook.getway.model;

import com.google.gson.stream.JsonReader;
import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.content.AppContext;
import com.wehealth.pdqbook.content.PDQApp;
import com.wehealth.pdqbook.getway.PDQInterface;
import com.wehealth.pdqbook.getway.error.PDQException;
import com.wehealth.pdqbook.tool.GsonTool;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @Author yangxiao on 2/28/2017.
 */

public class SearchResult {

    public static void load(Call call, String url) {
        if (call != null) {
            call.cancel();
        }
        call = PDQInterface.request(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new PDQException(new Exception(AppContext.instance().getString(R.string.search_error))));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
                SearchResult result = GsonTool.parserJson(reader, SearchResult.class);
                EventBus.getDefault().postSticky(result);
            }
        });
    }

    private boolean isSuccess;
    private String message;
    private int error;
    private SearchResultData Data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public SearchResultData getData() {
        return Data;
    }

    public void setData(SearchResultData data) {
        Data = data;
    }
}
