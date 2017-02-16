package com.wehealth.pdqbook.getway.repertory.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by xiaoyang on 2017/2/1.
 */

public class DBHelper extends DatabaseHelper {
    public DBHelper(DBInfo dbInfo) {
        super(dbInfo);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }
}
