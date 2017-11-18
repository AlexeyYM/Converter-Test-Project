package com.javir.converter.app;

import android.app.Application;
import android.content.Context;

import com.javir.converter.dao.DBHelper;
import com.javir.converter.utils.Constants;
import com.javir.converter.api.BankApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static BankApi bankApi;
    private static Retrofit retrofit;

    private static Context context;

    private static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        dbHelper = new DBHelper(context);


        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bankApi = retrofit.create(BankApi.class);
    }

    public static Context getContext() {
        return context;
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static BankApi getApi() {
        return bankApi;
    }
}
