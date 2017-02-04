package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/4/2017.
 */

public interface IRecordReceiver {
    public void onStartRecord();
    public void onFieldValue(String fieldName, Object value, boolean isNull);
}
