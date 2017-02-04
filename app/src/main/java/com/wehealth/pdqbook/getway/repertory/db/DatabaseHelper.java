package com.wehealth.pdqbook.getway.repertory.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wehealth.pdqbook.content.AppContext;

/**
 * Created by xiaoyang on 2017/1/30.
 */

public abstract class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PDQ.db";
    private DBInfo dbInfo;

    public static class DBInfo {
        private String dbName;
        private int dbVersion;
        public DBInfo(String databaseName, int databaseVersion) {
            this.dbName = databaseName;
            this.dbVersion = databaseVersion;
        }
    }

    public DatabaseHelper(DBInfo dbInfo) {
        super(AppContext.instance(), dbInfo.dbName, null, dbInfo.dbVersion);
    }

//    public DatabaseHelper(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }

//    public DatabaseHelper(Context context, String name) {
//        super(context, name, null, DB_VERSION);
//    }
//
//    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }

}
