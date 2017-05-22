package com.wehealth.pdqbook.getway.model;

import com.wehealth.pdqbook.tool.GsonTool;

/**
 * Created by xiaoyang on 2017/3/29.
 */

public class RiskResult {

    private RiskResultData Data;
    private boolean isSuccess;
    private int isFaking;

    public RiskResultData getData() {
        return Data;
    }

    public void setData(RiskResultData data) {
        Data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getIsFaking() {
        return isFaking;
    }

    public void setIsFaking(int isFaking) {
        this.isFaking = isFaking;
    }

    public static RiskResult parser(String json) {
        return GsonTool.parserJson(json, RiskResult.class);
    }
}
