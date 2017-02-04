package com.wehealth.pdqbook.getway.repertory;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by xiaoyang on 2017/2/1.
 */

public class PDQDBHelper extends DatabaseHelper {
    public PDQDBHelper(DBInfo dbInfo) {
        super(dbInfo);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
