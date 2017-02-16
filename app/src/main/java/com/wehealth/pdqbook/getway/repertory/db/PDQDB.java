package com.wehealth.pdqbook.getway.repertory.db;

/**
 * @Author yangxiao on 2/16/2017.
 */

public class PDQDB {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "PDQ.db";

    private static PDQDBHelper mDbHelper;

    private PDQDB(){}

    public static PDQDBHelper instance() {
        if (mDbHelper == null) {
            synchronized (PDQDB.class) {
                if (mDbHelper == null) {
                    DatabaseHelper.DBInfo info = new DatabaseHelper.DBInfo(DB_NAME, DB_VERSION);
                    mDbHelper = new PDQDBHelper(info);
                }
            }
        }
        return mDbHelper;
    }
}
