package com.wehealth.pdqbook.getway.datamodel;

import com.wehealth.pdqbook.getway.repertory.db.IRecordReceiver;
import com.wehealth.pdqbook.getway.repertory.db.PDQDB;
import com.wehealth.pdqbook.getway.repertory.db.table.SearchRecordTable;

import java.util.ArrayList;

/**
 * Created by xiaoyang on 2017/2/16.
 */

public class SearchRecord {


    public static ArrayList<SearchRecord> get() {
        final ArrayList<SearchRecord> records = new ArrayList<>();
        new SearchRecordTable(PDQDB.instance().getReadableDatabase()).getSearchRecords(new IRecordReceiver() {
            SearchRecord record;
            @Override
            public void onBegin() {

            }

            @Override
            public void onStartRecord(long rowId) {
                record = new SearchRecord();
            }

            @Override
            public void onFieldValue(String fieldName, Object value, boolean isNull) {
                switch (fieldName) {
                    case SearchRecordTable.SEARCH_RECORD_CONTENT:
                        record.setContent((String) value);
                        break;
                    case SearchRecordTable.SEARCH_RECORD_DATE:
                        record.setDate((String) value);
                        break;
                }
            }

            @Override
            public void onEndRecord(long rowId) {
                records.add(record);

            }

            @Override
            public void onEnd() {

            }
        });
        return records;
    }



    private String content;
    private String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
