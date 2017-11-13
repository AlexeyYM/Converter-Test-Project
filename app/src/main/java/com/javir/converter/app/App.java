package com.javir.converter.app;

import android.app.Application;

import com.javir.converter.utils.Constants;
import com.javir.converter.api.BankApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static BankApi bankApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bankApi = retrofit.create(BankApi.class);
    }

    public static BankApi getApi() {
        return bankApi;
    }
}
