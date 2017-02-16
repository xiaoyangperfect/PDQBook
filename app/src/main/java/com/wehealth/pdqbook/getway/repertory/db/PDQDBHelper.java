package com.wehealth.pdqbook.getway.repertory.db;

import android.database.sqlite.SQLiteDatabase;

import com.wehealth.pdqbook.getway.repertory.db.table.SearchRecordTable;

/**
 * @Author yangxiao on 2/16/2017.
 */

public class PDQDBHelper extends DBHelper {
    public PDQDBHelper(DBInfo dbInfo) {
        super(dbInfo);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (BaseDBTable table : tableList(db)) {
            table.createTableIfNecessary();
        }
    }

    private BaseDBTable[] tableList(SQLiteDatabase db) {
        return new BaseDBTable[]{
                new SearchRecordTable(db)
        };
    }
}
