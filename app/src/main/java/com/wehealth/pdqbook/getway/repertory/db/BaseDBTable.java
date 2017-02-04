package com.wehealth.pdqbook.getway.repertory.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wehealth.pdqbook.BuildConfig;

/**
 * Created by xiaoyang on 2017/2/1.
 */

public abstract class BaseDBTable {
    private static final String TAG = BaseDBTable.class.getSimpleName();
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

    public void createTableIfNecessary() {
        CreateTableSQL createTableSQL = getDefaultCreateTableSQL();
        String sqlStr = createTableSQL.toString();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, sqlStr);
        }
        mDataBase.execSQL(sqlStr);
    }

    protected CreateTableSQL getDefaultCreateTableSQL() {
        DBColumnsInfo dbColumnsInfo = getDBColumnsInfo();
        CreateTableSQL createTableSQL = new CreateTableSQL(tableName());
        for (IDBColumnInfo columnInfo : dbColumnsInfo.getColumns()) {
            createTableSQL.addColumn(columnInfo);
        }
        return createTableSQL;
    }

    public void dropTable(SQLiteDatabase db) {
        PDQDBHelper.dropTable(db, tableName());
    }

    protected long replaceRecord(ContentValues values) {
        beginTransaction();
        try {
            long rowId = db().replace(tableName(), null, values);
            return rowId;
        } finally {
            endTransaction();
        }
    }

    protected void getRecord(@NonNull String[] keyColumnNames, @NonNull String[] keyFieldValues) {
        beginTransaction();
        Cursor cursor = null;
        try {
            db().query(tableName(),
                    getDBColumnsInfo().columnNameList(),
                    new SQLExprBuilder().multipleFieldEquals(keyColumnNames).toString(),
                    keyFieldValues,
                    NO_GroupBy,
                    NO_Having,
                    NO_OrderBy
            );
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            endTransaction();
        }
    }







    public abstract String tableName();

    public abstract DBColumnsInfo getDBColumnsInfo();

    private void checkDBInstanceStatus() {
        if (mDataBase == null)
            throw new RuntimeException("Used without available database connection. Please do NOT save DataCacheDBTable instance.");
    }

    public void beginTransaction() {
        checkDBInstanceStatus();
        mDataBase.beginTransaction();
    }

    public void endTransaction() {
        mDataBase.setTransactionSuccessful();
        mDataBase.endTransaction();
    }
}
