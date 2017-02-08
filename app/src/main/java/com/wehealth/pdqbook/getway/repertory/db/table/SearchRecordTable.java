package com.wehealth.pdqbook.getway.repertory.db.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.wehealth.pdqbook.getway.repertory.db.BaseDBTable;
import com.wehealth.pdqbook.getway.repertory.db.CreateTableSQL;
import com.wehealth.pdqbook.getway.repertory.db.DBColumnDataType;
import com.wehealth.pdqbook.getway.repertory.db.DBColumnsInfo;
import com.wehealth.pdqbook.getway.repertory.db.IRecordReceiver;

import java.util.Locale;

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
            column(SEARCH_RECORD_NUMBER, DBColumnDataType.INTEGER);
            column(SEARCH_RECORD_USER, DBColumnDataType.TEXT);
            column(SEARCH_RECORD_STATUS, DBColumnDataType.TEXT);
        }
    };

    public SearchRecordTable(SQLiteDatabase database) {
        super(database);
    }

    public void getSearchRecords(@NonNull IRecordReceiver recordReceiver) {
        getAllRecords(recordReceiver);
    }

    public void setSearchRecord(ContentValues cv) {
        beginTransaction();
        ContentValues contentValues = cv;
        contentValues.put(SEARCH_RECORD_NUMBER, cv.getAsInteger(SEARCH_RECORD_NUMBER) + 1);
        int rowId = db().updateWithOnConflict(tableName(),
                contentValues, SEARCH_RECORD_CONTENT + " = ? and " + SEARCH_RECORD_USER + " = ?",
                new String[]{cv.getAsString(SEARCH_RECORD_CONTENT), cv.getAsString(SEARCH_RECORD_USER)}, SQLiteDatabase.CONFLICT_IGNORE);
        if (rowId != 1) {
            db().insert(tableName(), null, cv);
        }
        endTransaction();
    }

    @Override
    public void createTableIfNecessary() {
        CreateTableSQL createTableSQL = getDefaultCreateTableSQL();
        createTableSQL.addTableConstraint(String.format(Locale.US, "PRIMARY KEY (%s, %s) ON CONFLICT REPLACE", SEARCH_RECORD_CONTENT, SEARCH_RECORD_USER));
        db().execSQL(createTableSQL.toString());
    }

    @Override
    public String tableName() {
        return TABLE_NAME_SEARCH_RECORD;
    }

    @Override
    public DBColumnsInfo getDBColumnsInfo() {
        return columnsInfo;
    }
}
