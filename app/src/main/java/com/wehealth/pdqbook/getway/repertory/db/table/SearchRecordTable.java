package com.wehealth.pdqbook.getway.repertory.db.table;

import android.database.sqlite.SQLiteDatabase;

import com.wehealth.pdqbook.getway.repertory.db.BaseDBTable;
import com.wehealth.pdqbook.getway.repertory.db.DBColumnDataType;
import com.wehealth.pdqbook.getway.repertory.db.DBColumnsInfo;

/**
 * @Author yangxiao on 2/8/2017.
 */

public class SearchRecordTable extends BaseDBTable {

    public static final String TABLE_NAME_SEARCH_RECORD = "search_records_table";
    public static final String SEARCH_RECORD_CONTENT = "search_record_content";
    public static final String SEARCH_RECORD_NUMBER = "search_record_number";
    public static final String SEARCH_RECORD_USER = "search_record_user";
    public static final String SEARCH_RECORD_STATUS = "search_record_status";

    private DBColumnsInfo columnsInfo = new DBColumnsInfo(){
        {
            column(SEARCH_RECORD_CONTENT, DBColumnDataType.TEXT);
            column(SEARCH_RECORD_NUMBER, DBColumnDataType.TEXT);
            column(SEARCH_RECORD_USER, DBColumnDataType.TEXT);
            column(SEARCH_RECORD_STATUS, DBColumnDataType.TEXT);
        }
    };

    public SearchRecordTable(SQLiteDatabase database) {
        super(database);
    }

    @Override
    public String tableName() {
        return TABLE_NAME_SEARCH_RECORD;
    }

    @Override
    public DBColumnsInfo getDBColumnsInfo() {
        return null;
    }
}
