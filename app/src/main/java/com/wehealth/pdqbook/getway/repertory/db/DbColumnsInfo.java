package com.wehealth.pdqbook.getway.repertory.db;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import static com.wehealth.pdqbook.getway.repertory.db.BaseDBTable.FIELD_RAW_ID;

/**
 * @Author yangxiao on 2/4/2017.
 */

public class DBColumnsInfo {
    private ArrayList<Column> mColumns;

    public ArrayList<Column> getColumns() {
        return mColumns;
    }

    public String[] columnNameList() {
        String[] nameList = new String[mColumns.size()];
        for (int i = 0; i < mColumns.size(); i++) {
            nameList[i] = mColumns.get(i).columnName;
        }
        return nameList;
    }

    public String[] columnNameListWithRawID() {
        String[] nameArray = new String[mColumns.size() + 1];
        nameArray[0] = FIELD_RAW_ID;
        for (int i = 0; i < mColumns.size(); i++) {
            nameArray[i+1] = mColumns.get(i).columnName();
        }
        return nameArray;
    }

    public DBCursorField[] createCursorFields(List<String> names, Cursor cursor) {
        DBCursorField[] fields = new DBCursorField[names != null ? names.size() : mColumns.size()];
        int fieldIdx = 0;
        for (int i = 0; i < mColumns.size(); i++) {
            IDBColumnInfo info = mColumns.get(i);
            if (names.contains(info.columnName())) {
                DBCursorField field = new DBCursorField(cursor, info.columnName());
                field.setDataType(info.dataType());
                fields[fieldIdx] = field;
                ++fieldIdx;
            }
        }
        return fields;
    }

    protected void column(String name, DBColumnDataType type) {
        mColumns.add(new Column(name, type));
    }

    protected void column(String name, DBColumnDataType type, DBColumnConstraint constraint) {
        mColumns.add(new Column(name, type, constraint));
    }

    class Column implements IDBColumnInfo {
        private String columnName;
        private DBColumnDataType columnDataType;
        private DBColumnConstraint columnConstraint;

        Column(String name, DBColumnDataType type) {
            this(name, type, DBColumnConstraint.None);
        }

        Column(String name, DBColumnDataType type, DBColumnConstraint constraint) {
            this.columnName = name;
            this.columnDataType = type;
            this.columnConstraint = constraint;
        }

        @Override
        public String columnName() {
            return columnName;
        }

        @Override
        public DBColumnDataType dataType() {
            return columnDataType;
        }

        @Override
        public DBColumnConstraint columnConstraint() {
            return columnConstraint;
        }
    }
}
