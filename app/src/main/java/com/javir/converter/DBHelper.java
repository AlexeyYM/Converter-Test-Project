package com.javir.converter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "currencydb";
    public static final String TABLE_CURRENCY = "currency";

    public static final String KEY_ID = "_id";
    public static final String CUR_ID = "cur_id";
    public static final String DATE = "date";
    public static final String ABBREVIATION = "abbreviation";
    public static final String SCALE = "scale";
    public static final String NAME = "name";
    public static final String RATE = "rate";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_CURRENCY + "(" + KEY_ID + " integer primary key autoincrement,"
                + CUR_ID + " integer," + DATE + " text," + ABBREVIATION + " text," + SCALE + " integer,"
                + NAME + " text," + RATE + " real" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_CURRENCY);

        onCreate(sqLiteDatabase);
    }
}
