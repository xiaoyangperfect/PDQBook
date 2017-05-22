package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/4/2017.
 */

public interface IRecordReceiver {
    void onBegin();
    void onStartRecord(long rowId);
    void onFieldValue(String fieldName, Object value, boolean isNull);
    void onEndRecord(long rowId);
    void onEnd();
}
