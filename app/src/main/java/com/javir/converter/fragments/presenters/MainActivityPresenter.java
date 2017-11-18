package com.javir.converter.fragments.presenters;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.javir.converter.app.App;
import com.javir.converter.dao.DBHelper;
import com.javir.converter.fragments.views.MainActivityView;
import com.javir.converter.interfaces.MainActivityPresenterInterface;
import com.javir.converter.model.CurrencyDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityPresenterInterface {
    private MainActivityView mainActivityView;

    private List<CurrencyDTO> currency = null;

    public MainActivityPresenter(@NonNull MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public List<CurrencyDTO> updateCurrency() {
        currency = mainActivityView.getCurrency();

        App.getApi().getData("0").enqueue(new Callback<List<CurrencyDTO>>() {
            @Override
            public void onResponse(Call<List<CurrencyDTO>> call, Response<List<CurrencyDTO>> response) {
                currency.addAll(response.body());

                SQLiteDatabase database = App.getDbHelper().getWritableDatabase();
                database.delete(DBHelper.TABLE_CURRENCY, null, null);

                for(CurrencyDTO cur : currency) {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(DBHelper.CUR_ID, cur.getCurID());
                    contentValues.put(DBHelper.ABBREVIATION, cur.getCurAbbreviation());
                    contentValues.put(DBHelper.DATE, cur.getDate());
                    contentValues.put(DBHelper.NAME, cur.getCurName());
                    contentValues.put(DBHelper.RATE, cur.getCurOfficialRate());
                    contentValues.put(DBHelper.SCALE, cur.getCurScale());

                    database.insert(DBHelper.TABLE_CURRENCY, null, contentValues);
                }

                mainActivityView.showSuccess();
            }

            @Override
            public void onFailure(Call<List<CurrencyDTO>> call, Throwable t) {
                mainActivityView.showError();
            }
        });

        return currency;
    }
}
