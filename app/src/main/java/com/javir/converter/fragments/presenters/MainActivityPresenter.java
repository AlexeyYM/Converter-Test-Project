package com.javir.converter.fragments.presenters;

import android.support.annotation.NonNull;

import com.javir.converter.app.App;
import com.javir.converter.dao.CurrencyDAO;
import com.javir.converter.fragments.views.MainActivityView;
import com.javir.converter.interfaces.MainActivityPresenterInterface;
import com.javir.converter.model.CurrencyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityPresenterInterface {
    private MainActivityView mainActivityView;

    private List<CurrencyModel> currency = null;

    public MainActivityPresenter(@NonNull MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public List<CurrencyModel> updateCurrency() {
        currency = mainActivityView.getCurrency();

        App.getApi().getData("0").enqueue(new Callback<List<CurrencyModel>>() {
            @Override
            public void onResponse(Call<List<CurrencyModel>> call, Response<List<CurrencyModel>> response) {
                currency.addAll(response.body());

                CurrencyDAO.addCurrencysInDB(currency);

                mainActivityView.showSuccess();
            }

            @Override
            public void onFailure(Call<List<CurrencyModel>> call, Throwable t) {
                mainActivityView.showError();
            }
        });

        return currency;
    }
}
