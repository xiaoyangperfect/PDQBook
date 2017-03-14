package com.wehealth.pdqbook.getway.model;

/**
 * @Author yangxiao on 2/28/2017.
 */

public class SearchResult {
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
