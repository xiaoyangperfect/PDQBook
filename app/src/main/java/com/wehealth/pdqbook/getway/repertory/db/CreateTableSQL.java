package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/4/2017.
 */

public class CreateTableSQL {
    private StringBuilder mBuilder = new StringBuilder();
    private boolean mNeedAddColumn = false;
    private boolean mColumnDefinitionEnd = false;


    public CreateTableSQL(String tableName) {
        mBuilder.append("create table if not exists ");
        mBuilder.append(tableName);
        mBuilder.append(" (");
    }

    @Override
    public String toString() {
        return mBuilder.toString() + " )";
    }

    public CreateTableSQL addColumn(IDBColumnInfo columnInfo) {
        return addColumn(columnInfo.columnName(), columnInfo.dataType(), columnInfo.columnConstraint());
    }

    private CreateTableSQL addColumn(String s, DBColumnDataType dbColumnDataType, DBColumnConstraint dbColumnConstraint) {
        if (mColumnDefinitionEnd) {
            throw new RuntimeException("Column definitions should be added first before table constraint.");
        }
        if (mNeedAddColumn) {
            mBuilder.append(", ");
        } else {
            mNeedAddColumn = true;
        }
        mBuilder.append(s);
        mBuilder.append(" ");
        mBuilder.append(dbColumnDataType);
        mBuilder.append(" ");
        mBuilder.append(dbColumnConstraint);
        return this;
    }

    public CreateTableSQL addTableConstraint(String tableConstraint) {
        if (!mNeedAddColumn) {
            throw new RuntimeException("Table constraint should be added after column definitions.");
        }
        if (!mColumnDefinitionEnd) {
            mColumnDefinitionEnd = true;
        }
        mBuilder.append(", ");
        mBuilder.append(tableConstraint);
        return this;
    }
}
