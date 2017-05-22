package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/4/2017.
 */

public interface IDBColumnInfo {
    String columnName();
    DBColumnDataType dataType();
    DBColumnConstraint columnConstraint();
}
