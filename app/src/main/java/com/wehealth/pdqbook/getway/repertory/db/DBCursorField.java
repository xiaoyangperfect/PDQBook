package com.wehealth.pdqbook.getway.repertory.db;

import android.database.Cursor;
import android.util.Log;

/**
 * Created by yangxiao on 12/21/2016.
 */
public class DBCursorField {
    public DBCursorField(Cursor cursor, String fieldName) {
        _cursor = cursor;
        _fieldName = fieldName;
        _index = cursor.getColumnIndex(fieldName);
        if (_index < 0) {
            Log.d(TAG, "index < 0 for field " + fieldName);
        }
    }

    public byte[] getBlob() {
        return _cursor.getBlob(_index);
    }

    public double getDouble() {
        return _cursor.getDouble(_index);
    }

    public float getFloat() {
        return _cursor.getFloat(_index);
    }

    public int getInt() {
        return _cursor.getInt(_index);
    }

    public long getLong() {
        return _cursor.getLong(_index);
    }

    public short getShort() {
        return _cursor.getShort(_index);
    }

    public String getString() {
        return _cursor.getString(_index);
    }

    public int getType() {
        return _cursor.getType(_index);
    }

    public boolean exist() {
        return _index >= 0;
    }

    public byte[] getBlob(byte[] defaultValue) {
        return _index >= 0 ? _cursor.getBlob(_index) : defaultValue;
    }

    public double getDouble(double defaultValue) {
        return _index >= 0 ? _cursor.getDouble(_index) : defaultValue;
    }

    public float getFloat(float defaultValue) {
        return _index >= 0 ? _cursor.getFloat(_index) : defaultValue;
    }

    public int getInt(int defaultValue) {
        return _index >= 0 ? _cursor.getInt(_index) : defaultValue;
    }

    public long getLong(long defaultValue) {
        return _index >= 0 ? _cursor.getLong(_index) : defaultValue;
    }

    public short getShort(short defaultValue) {
        return _index >= 0 ? _cursor.getShort(_index) : defaultValue;
    }

    public String getString(String defaultValue) {
        return _index >= 0 ? _cursor.getString(_index) : defaultValue;
    }

    public int getType(int defaultValue) {
        return _index >= 0 ? _cursor.getType(_index) : defaultValue;
    }

    public static byte[] getBlob(Cursor cursor, String fieldName) {
        return cursor.getBlob(cursor.getColumnIndex(fieldName));
    }

    public static double getDouble(Cursor cursor, String fieldName) {
        return cursor.getDouble(cursor.getColumnIndex(fieldName));
    }

    public static float getFloat(Cursor cursor, String fieldName) {
        return cursor.getFloat(cursor.getColumnIndex(fieldName));
    }

    public static int getInt(Cursor cursor, String fieldName) {
        return cursor.getInt(cursor.getColumnIndex(fieldName));
    }

    public static long getLong(Cursor cursor, String fieldName) {
        return cursor.getLong(cursor.getColumnIndex(fieldName));
    }

    public static short getShort(Cursor cursor, String fieldName) {
        return cursor.getShort(cursor.getColumnIndex(fieldName));
    }

    public static String getString(Cursor cursor, String fieldName) {
        return cursor.getString(cursor.getColumnIndex(fieldName));
    }

    public static int getType(Cursor cursor, String fieldName) {
        return cursor.getType(cursor.getColumnIndex(fieldName));
    }

    public String fieldName() {
        return _fieldName;
    }

    public DBColumnDataType getDataType() {
        return _dataType;
    }

    public void setDataType(DBColumnDataType type) {
        _dataType = type;
    }

    private final String _fieldName;
    private DBColumnDataType _dataType;
    private final Cursor _cursor;
    private final int _index;

    private static final String TAG = DBCursorField.class.getSimpleName();
}
