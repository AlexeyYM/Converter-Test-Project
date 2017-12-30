package com.javir.converter.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.javir.converter.app.App;
import com.javir.converter.fragments.views.FragmentCurrencyListView;
import com.javir.converter.model.CurrencyModel;

import java.util.List;

public class CurrencyDAO {
    private static SQLiteDatabase database;

    public static void addCurrencysInDB(@NonNull List<CurrencyModel> currencys) {
        database = App.getDbHelper().getWritableDatabase();
        database.delete(CurrencyContract.Currencys.TABLE_NAME, null, null);

        for(CurrencyModel cur : currencys) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(CurrencyContract.Currencys.COLUMN_CUR_ID, cur.getCurID());
            contentValues.put(CurrencyContract.Currencys.COLUMN_ABBREVIATION, cur.getCurAbbreviation());
            contentValues.put(CurrencyContract.Currencys.COLUMN_DATE, cur.getDate());
            contentValues.put(CurrencyContract.Currencys.COLUMN_NAME, cur.getCurName());
            contentValues.put(CurrencyContract.Currencys.COLUMN_RATE, cur.getCurOfficialRate());
            contentValues.put(CurrencyContract.Currencys.COLUMN_SCALE, cur.getCurScale());

            database.insert(CurrencyContract.Currencys.TABLE_NAME, null, contentValues);
        }
    }

    public static void updateCurrencysList(@NonNull List<CurrencyModel> currencys) {
        FragmentCurrencyListView view = new FragmentCurrencyListView();
        database = App.getDbHelper().getReadableDatabase();

        Cursor cursor = database.query(CurrencyContract.Currencys.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(CurrencyContract.Currencys.COLUMN_KEY_ID);
            int curIdIndex = cursor.getColumnIndex(CurrencyContract.Currencys.COLUMN_CUR_ID);
            int dateIndex = cursor.getColumnIndex(CurrencyContract.Currencys.COLUMN_DATE);
            int abbreviationIndex = cursor.getColumnIndex(CurrencyContract.Currencys.COLUMN_ABBREVIATION);
            int scaleIndex = cursor.getColumnIndex(CurrencyContract.Currencys.COLUMN_SCALE);
            int nameIndex = cursor.getColumnIndex(CurrencyContract.Currencys.COLUMN_NAME);
            int rateIndex = cursor.getColumnIndex(CurrencyContract.Currencys.COLUMN_RATE);
            do {
                CurrencyModel currencyModel = new CurrencyModel();
                currencyModel.setCurID(cursor.getInt(curIdIndex));
                currencyModel.setDate(cursor.getString(dateIndex));
                currencyModel.setCurAbbreviation(cursor.getString(abbreviationIndex));
                currencyModel.setCurScale(cursor.getInt(scaleIndex));
                currencyModel.setCurName(cursor.getString(nameIndex));
                currencyModel.setCurOfficialRate(cursor.getDouble(rateIndex));

                currencys.add(currencyModel);
            } while ((cursor.moveToNext()));
        } else {
            view.showError();
        }
        cursor.close();

        view.setCurrency(currencys);
    }
}
