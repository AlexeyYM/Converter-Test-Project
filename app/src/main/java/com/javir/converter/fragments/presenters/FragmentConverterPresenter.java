package com.javir.converter.fragments.presenters;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.javir.converter.R;
import com.javir.converter.dao.DBHelper;
import com.javir.converter.fragments.views.FragmentConverterView;
import com.javir.converter.model.CurrencyDTO;

import java.util.Map;

public class FragmentConverterPresenter {
    private final FragmentConverterView fragmentConverterView;

    private DBHelper dbHelper;

    public FragmentConverterPresenter(@NonNull FragmentConverterView fragmentConverterView) {
        this.fragmentConverterView = fragmentConverterView;
    }

    public double rate(double officialRate, double scale) {
        return officialRate / scale;
    }

    public double convert(double value, double rateInitial, double rateResult) {
        return value * (rateInitial / rateResult);
    }

    public Map<String, CurrencyDTO> getCurrencyFromDB(@NonNull Map<String, CurrencyDTO> currency) {
        dbHelper = new DBHelper(fragmentConverterView.getContext());

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

                currency.put(currencyDTO.getCurAbbreviation(), currencyDTO);
            } while ((cursor.moveToNext()));
        } else {
            fragmentConverterView.showError();
        }
        cursor.close();

        return currency;
    }

    public String[] initialAbbreviatons(Map<String, CurrencyDTO> currencyDTOs) {
        String[] result = new String[currencyDTOs.size() + 1];
        result[0] = fragmentConverterView.getString(R.string.abbreviationBelRub).toString();
        int count = 1;

        for (Map.Entry<String, CurrencyDTO> cur : currencyDTOs.entrySet()) {
            result[count] = cur.getKey();
            count++;
        }

        return result;
    }
}
