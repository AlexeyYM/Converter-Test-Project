package com.javir.converter.fragments.presenters;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.javir.converter.R;
import com.javir.converter.app.App;
import com.javir.converter.dao.CurrencyContract;
import com.javir.converter.fragments.views.FragmentConverterView;
import com.javir.converter.model.CurrencyModel;

import java.util.Map;

public class FragmentConverterPresenter {
    private final FragmentConverterView fragmentConverterView;

    public FragmentConverterPresenter(@NonNull FragmentConverterView fragmentConverterView) {
        this.fragmentConverterView = fragmentConverterView;
    }

    public double rate(double officialRate, double scale) {
        return officialRate / scale;
    }

    public double convert(double value, double rateInitial, double rateResult) {
        return value * (rateInitial / rateResult);
    }

    public Map<String, CurrencyModel> getCurrencyFromDB(@NonNull Map<String, CurrencyModel> currency) {
        SQLiteDatabase database = App.getDbHelper().getReadableDatabase();

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

                currency.put(currencyModel.getCurAbbreviation(), currencyModel);
            } while ((cursor.moveToNext()));
        } else {
            fragmentConverterView.showError();
        }
        cursor.close();

        return currency;
    }

    public String[] initialAbbreviatons(Map<String, CurrencyModel> currencyDTOs) {
        String[] result = new String[currencyDTOs.size() + 1];
        result[0] = fragmentConverterView.getString(R.string.abbreviationBelRub).toString();
        int count = 1;

        for (Map.Entry<String, CurrencyModel> cur : currencyDTOs.entrySet()) {
            result[count] = cur.getKey();
            count++;
        }

        return result;
    }
}
