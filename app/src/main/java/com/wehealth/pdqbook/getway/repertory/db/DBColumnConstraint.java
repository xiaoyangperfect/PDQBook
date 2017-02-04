package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/4/2017.
 */

public enum DBColumnConstraint {
    None(""),
    PrimaryKey("PRIMARY KEY NOT NULL"),
    PrimaryKeyOnConflictReplace("PRIMARY KEY NOT NULL ON CONFLICT REPLACE"),
    PrimaryKeyOnConflictIgnore("PRIMARY KEY NOT NULL ON CONFLICT IGNORE"),
    NotNull("NOT NULL"),
    Unique("UNIQUE"),
    ;

    public final String SQL;

    DBColumnConstraint(String sqlStr) {
        SQL = sqlStr;
    }

    @Override
    public String toString() {
        return SQL;
    }
}
