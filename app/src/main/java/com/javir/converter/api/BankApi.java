package com.javir.converter.api;

import com.javir.converter.model.CurrencyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BankApi {
    @GET("/API/ExRates/Rates")
    Call<List<CurrencyModel>> getData(@Query("Periodicity") String period);
}
