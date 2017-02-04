package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/4/2017.
 */

public interface IDBColumnInfo {
    public String columnName();
    public DBColumnDataType dataType();
    public DBColumnConstraint columnConstraint();
}
