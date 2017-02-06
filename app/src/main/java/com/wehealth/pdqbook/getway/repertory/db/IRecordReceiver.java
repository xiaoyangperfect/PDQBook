package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/4/2017.
 */

public interface IRecordReceiver {
    public void onBegin();
    public void onStartRecord(long rowId);
    public void onFieldValue(String fieldName, Object value, boolean isNull);
    public void onEndRecord(long rowId);
    public void onEnd();
}
