package com.wehealth.pdqbook.getway.repertory;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by xiaoyang on 2017/2/1.
 */

public abstract class BaseDBTable {
    public static final String NO_Selection = null;
    public static final String[] NO_SelectionArgs = null;
    public static final String NO_GroupBy = null;
    public static final String NO_Having = null;
    public static final String NO_OrderBy = null;
    public static final String NO_WhereClause = null;
    public static final String[] NO_WhereArgs = null;

    // ref: https://www.sqlite.org/autoinc.html
    // this is defined by SQLite, rather than arbitrary value. It can be ROWID, _ROWID_, or OID
    public static final String FIELD_RAW_ID = "rowid";
    public static final long INVALID_RAW_ID = -1;
    public static final String WHERE_CLAUSE_REMOVE_ALL_AND_RETURN_COUNT = "1";
    public static final String SELECT_ALL_COLUMNS = "*";

    private SQLiteDatabase mDataBase;

    protected SQLiteDatabase db() {
        return mDataBase;
    }

    public BaseDBTable(SQLiteDatabase database) {
        if (database == null)
            throw new RuntimeException("DataBase can't be null");
        mDataBase = database;
    }

    public abstract String tableName();

    private void checkDBInstanceStatus() {
        if (mDataBase == null)
            throw new RuntimeException("Used without available database connection. Please do NOT save DataCacheDBTable instance.");
    }
}
