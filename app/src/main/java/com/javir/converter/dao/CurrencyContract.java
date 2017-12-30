package com.javir.converter.dao;

import android.provider.BaseColumns;

public class CurrencyContract {
    private CurrencyContract() {}

    static final String CREATE_CURRENCYS = "create table " + CurrencyContract.Currencys.TABLE_NAME + "(" + CurrencyContract.Currencys.COLUMN_KEY_ID + " integer primary key autoincrement,"
            + CurrencyContract.Currencys.COLUMN_CUR_ID + " integer," + CurrencyContract.Currencys.COLUMN_DATE + " text," + CurrencyContract.Currencys.COLUMN_ABBREVIATION + " text," + CurrencyContract.Currencys.COLUMN_SCALE + " integer,"
            + CurrencyContract.Currencys.COLUMN_NAME + " text," + CurrencyContract.Currencys.COLUMN_RATE + " real" + ")";

    public static class Currencys implements BaseColumns {
        public static final String TABLE_NAME = "currency";

        public static final String COLUMN_KEY_ID = "_id";
        public static final String COLUMN_CUR_ID = "cur_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_ABBREVIATION = "abbreviation";
        public static final String COLUMN_SCALE = "scale";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RATE = "rate";
    }
}
