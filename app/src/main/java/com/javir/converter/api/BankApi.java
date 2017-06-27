package com.javir.converter.api;

import com.javir.converter.dto.CurrencyDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BankApi {
    @GET("/API/ExRates/Rates")
    Call<List<CurrencyDTO>> getData(@Query("Periodicity") String period);
}
