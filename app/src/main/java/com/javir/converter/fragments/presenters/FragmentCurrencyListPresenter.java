package com.javir.converter.fragments.presenters;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.javir.converter.R;
import com.javir.converter.dao.DBHelper;
import com.javir.converter.fragments.views.FragmentCurrencyListView;
import com.javir.converter.model.CurrencyDTO;

import java.util.List;

/**
 * Created by alexeyy on 13.11.17.
 */

public class FragmentCurrencyListPresenter {
    private final FragmentCurrencyListView fragmentCurrencyListView;

    private DBHelper dbHelper;

    public FragmentCurrencyListPresenter(@NonNull FragmentCurrencyListView fragmentCurrencyListView) {
        this.fragmentCurrencyListView = fragmentCurrencyListView;
    }

    public void updateCurrencyList(@NonNull List<CurrencyDTO> currencyList) {
        dbHelper = new DBHelper(fragmentCurrencyListView.getContext());

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_CURRENCY, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int curIdIndex = cursor.getColumnIndex(DBHelper.CUR_ID);
            int dateIndex = cursor.getColumnIndex(DBHelper.DATE);
            int abbreviationIndex = cursor.getColumnIndex(DBHelper.ABBREVIATION);
            int scaleIndex = cursor.getColumnIndex(DBHelper.SCALE);
            int nameIndex = cursor.getColumnIndex(DBHelper.NAME);
            int rateIndex = cursor.getColumnIndex(DBHelper.RATE);
            do {
                CurrencyDTO currencyDTO = new CurrencyDTO();
                currencyDTO.setCurID(cursor.getInt(curIdIndex));
                currencyDTO.setDate(cursor.getString(dateIndex));
                currencyDTO.setCurAbbreviation(cursor.getString(abbreviationIndex));
                currencyDTO.setCurScale(cursor.getInt(scaleIndex));
                currencyDTO.setCurName(cursor.getString(nameIndex));
                currencyDTO.setCurOfficialRate(cursor.getDouble(rateIndex));

                currencyList.add(currencyDTO);
            } while ((cursor.moveToNext()));
        } else {
            fragmentCurrencyListView.showError();
        }
        cursor.close();

        fragmentCurrencyListView.setCurrency(currencyList);
    }
}