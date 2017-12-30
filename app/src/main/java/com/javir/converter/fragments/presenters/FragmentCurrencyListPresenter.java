package com.javir.converter.fragments.presenters;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.javir.converter.app.App;
import com.javir.converter.dao.CurrencyDAO;
import com.javir.converter.dao.DBHelper;
import com.javir.converter.fragments.views.FragmentCurrencyListView;
import com.javir.converter.model.CurrencyModel;

import java.util.List;

public class FragmentCurrencyListPresenter {
    private final FragmentCurrencyListView fragmentCurrencyListView;

    public FragmentCurrencyListPresenter(@NonNull FragmentCurrencyListView fragmentCurrencyListView) {
        this.fragmentCurrencyListView = fragmentCurrencyListView;
    }

    public void updateCurrencyList(@NonNull List<CurrencyModel> currencyList) {
        CurrencyDAO.updateCurrencysList(currencyList);
    }
}
