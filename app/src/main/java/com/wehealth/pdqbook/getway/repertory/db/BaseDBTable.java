package com.wehealth.pdqbook.getway.repertory.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.wehealth.pdqbook.BuildConfig;
import com.wehealth.pdqbook.tool.ArrayUtils;
import com.wehealth.pdqbook.tool.CommonUtil;
import com.wehealth.pdqbook.tool.Function;

import java.util.Arrays;

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

    protected long insertRecord(ContentValues values) {
        beginTransaction();
        try {
            return db().insert(tableName(), null, values);
        } finally {
            endTransaction();
        }
    }

    protected boolean updateRecord(ContentValues values) {
        beginTransaction();
        try {
            int updateCount = db().updateWithOnConflict(tableName(), values, NO_WhereClause, NO_SelectionArgs, SQLiteDatabase.CONFLICT_IGNORE);
            if (updateCount != 1) {
                long rowId = db().insert(tableName(), null, values);
                return rowId > 0 ? true : false;
            } else {
                return true;
            }
        } finally {
            endTransaction();
        }
    }

    protected void getRecord(@NonNull String[] keyColumnNames, @NonNull String[] keyFieldValues,
                             @NonNull final IRecordReceiver recordReceiver) {
        beginTransaction();
        Cursor cursor = null;
        try {
            cursor = db().query(tableName(),
                    getDBColumnsInfo().columnNameList(),
                    new SQLExprBuilder().multipleFieldEquals(keyColumnNames).toString(),
                    keyFieldValues,
                    NO_GroupBy,
                    NO_Having,
                    NO_OrderBy
            );
            if (cursor != null && cursor.moveToFirst()) {
//                CommonUtil.callAndEatException(new Function<Void>() {
//                    @Override
//                    public void call() {
//                        recordReceiver.onStartRecord();
//                    }
//                });
                for (IDBColumnInfo column:
                        getDBColumnsInfo().getColumns()) {
                    callReceiverOnFieldValue(cursor, column.columnName(), column.dataType(), recordReceiver);
                }
//                CommonUtil.callAndEatException(new Function<Void>() {
//                    @Override
//                    public void call() {
//                        recordReceiver.onEnd();
//                    }
//                });
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            endTransaction();
        }
    }

    public void getRecords(@NonNull String[] keyColumnNames, @NonNull String[] keyFieldValues,
                           @NonNull IRecordReceiver recordsReceiver) {
        getRecords(getDBColumnsInfo().columnNameListWithRawID(),
                new SQLExprBuilder().multipleFieldEquals(keyColumnNames).toString(),
                keyFieldValues,
                NO_GroupBy,
                NO_Having,
                NO_OrderBy,
                recordsReceiver);
    }

    public void getRecordsWithRawID(String[] keyColumnNames,
                                    String selectionSQLStr,
                                    String[] keyFieldValues,
                                    String groupBy,
                                    String having,
                                    String orderBy,
                                    @NonNull IRecordReceiver recordsReceiver) {
        String[] realKeyColumnNames = keyColumnNames;
        if (keyColumnNames != null) {
            if (!TextUtils.equals(keyColumnNames[0], FIELD_RAW_ID)) {
                realKeyColumnNames = ArrayUtils.concatAll(new String[]{FIELD_RAW_ID}, keyColumnNames);
            }
        } else {
            realKeyColumnNames = getDBColumnsInfo().columnNameListWithRawID();
        }
        getRecords(realKeyColumnNames, selectionSQLStr, keyFieldValues, groupBy, having, orderBy, recordsReceiver);
    }

    public void getRecords(String[] keyColumnNames,
                           String selectionSQLStr,
                           String[] keyFieldValues,
                           String groupBy,
                           String having,
                           String orderBy,
                           @NonNull IRecordReceiver recordsReceiver) {
        if (ArrayUtils.contains(keyFieldValues, null)) {
            Log.e(TAG, "binding value is null");
            return;
        }

        beginTransaction();
        Cursor cursor = null;
        try {
            String[] columnNames = keyColumnNames != null ? keyColumnNames : getDBColumnsInfo().columnNameListWithRawID();
            cursor = db().query(tableName(),
                    columnNames,
                    selectionSQLStr,
                    keyFieldValues,
                    groupBy,
                    having,
                    orderBy
            );
            callRecordsFromCursor(columnNames, cursor, recordsReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            endTransaction();
        }

    }

    private static String[] columnNamesWithoutRawID(String[] columnNames) {
        return (TextUtils.equals(columnNames[0], FIELD_RAW_ID)) ? Arrays.copyOfRange(columnNames, 1, columnNames.length) : columnNames;
    }

    protected void callRecordsFromCursor(String[] columnNames,
                                         Cursor cursor,
                                         @NonNull final IRecordReceiver recordReceiver) {
        if (cursor != null && cursor.moveToFirst()) {
            CommonUtil.callAndEatAllException(new Function<Void>() {
                @Override
                public void call() {
                    recordReceiver.onBegin();
                }
            });
            DBCursorField[] fields = getDBColumnsInfo().createCursorFields(Arrays.asList(columnNamesWithoutRawID(columnNames)), cursor);
            DBCursorField cursorField = new DBCursorField(cursor, FIELD_RAW_ID);
            long rowId;
            do {
                rowId = cursorField.getLong(INVALID_RAW_ID);
                final long finalRowId = rowId;
                CommonUtil.callAndEatException(new Function<Void>() {
                    @Override
                    public void call() {
                        recordReceiver.onStartRecord(finalRowId);
                    }
                });
                for (DBCursorField field : fields) {
                    callReceiverOnFieldValue(field, recordReceiver);
                }
                CommonUtil.callAndEatException(new Function<Void>() {
                    @Override
                    public void call() {
                        recordReceiver.onEndRecord(finalRowId);
                    }
                });
            } while (cursor.moveToNext());
            CommonUtil.callAndEatException(new Function<Void>() {
                @Override
                public void call() {
                    recordReceiver.onEnd();
                }
            });
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

    private static void callReceiverOnFieldValue(Cursor cursor, String fieldName, DBColumnDataType dataType, IRecordReceiver receiver) {
        try {
            switch (dataType) {
                case NULL:
                    receiver.onFieldValue(fieldName, null, true);
                    break;
                case TEXT:
                    receiver.onFieldValue(fieldName, DBCursorField.getString(cursor, fieldName), false);
                    break;
                case INTEGER:
                    receiver.onFieldValue(fieldName, DBCursorField.getLong(cursor, fieldName), false);
                    break;
                case REAL:
                    receiver.onFieldValue(fieldName, DBCursorField.getDouble(cursor, fieldName), false);
                    break;
                case BLOB:
                    receiver.onFieldValue(fieldName, DBCursorField.getBlob(cursor, fieldName), false);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // we eat it to avoid exception spreading. if caller need it, they should catch exceptions in onFieldValue by themselves.
        }
    }

    private static void callReceiverOnFieldValue(DBCursorField field, IRecordReceiver receiver) {
        try {
            switch (field.getDataType()) {
                case NULL:
                    receiver.onFieldValue(field.fieldName(), null, true);
                    break;
                case TEXT:
                    receiver.onFieldValue(field.fieldName(), field.getString(), false);
                    break;
                case INTEGER:
                    receiver.onFieldValue(field.fieldName(), field.getLong(), false);
                    break;
                case REAL:
                    receiver.onFieldValue(field.fieldName(), field.getDouble(), false);
                    break;
                case BLOB:
                    receiver.onFieldValue(field.fieldName(), field.getBlob(), false);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // we eat it to avoid exception spreading. if caller need it, they should catch exceptions in onFieldValue by themselves.
        }
    }
}
